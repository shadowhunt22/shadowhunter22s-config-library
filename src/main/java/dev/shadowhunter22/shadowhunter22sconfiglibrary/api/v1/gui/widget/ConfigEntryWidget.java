//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import com.google.common.collect.Lists;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category.ConfigCategory;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractOptionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.SectionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigEntryWidget extends ElementListWidget<ConfigEntryWidget.Entry> {
    private final AutoConfigManager<?> manager;
    public final List<ConfigCategory> categories = new ArrayList<>();

    public <T extends ConfigData> ConfigEntryWidget(AutoConfigManager<T> configManager,  MinecraftClient client, int width, int height) {
        super(client, width, height, 50, height, 27);

        this.manager = configManager;

        this.setRenderBackground(false);
        this.setRenderHorizontalShadows(false);
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.width - 10;
    }

    @Override
    public int getRowWidth() {
        return this.width + 100;
    }

    public void add(AbstractEntry entry) {
        if (entry instanceof ConfigOption<?> option) {
            this.add(option.getKey(), option);
        } else {
            this.addEntry(entry.build());
        }
    }

    public void add(String key, ConfigOption<?> option) {
        boolean addedConfigOptionEntryToCategory = false;

        Field field;

        try {
            field = this.manager.getConfig().getClass().getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        AbstractOptionEntry entry = option.asEntry(this.manager, this.width);
        this.addEntry(entry.build());

        if (
                field.isAnnotationPresent(ConfigEntry.Gui.Section.class) &&
                field.isAnnotationPresent(ConfigEntry.Gui.Category.class)
        ) {
            this.addSection(key, true);
            this.addToOrCreateCategory(entry, false);
            addedConfigOptionEntryToCategory = true;
        } else if (field.isAnnotationPresent(ConfigEntry.Gui.Section.class)) {
            this.addSection(key);
        } else if (field.isAnnotationPresent(ConfigEntry.Gui.Category.class)) {
            this.addToOrCreateCategory(entry, true);
            addedConfigOptionEntryToCategory = true;
        }


        if (!addedConfigOptionEntryToCategory) {
            this.addToOrCreateCategory(entry, false);
        }
    }

    private void addSection(String key, boolean createCategory) {
        SectionEntry entry = new SectionEntry(this.manager, key, this.width);
        this.addToOrCreateCategory(entry, createCategory);

        this.addEntry(entry.build());
    }

    public void addSection(String key) {
        this.addSection(key, false);
    }

    private void addToOrCreateCategory(AbstractEntry entry, boolean createCategory) {
        if (createCategory) {
            this.categories.add(
                    new ConfigCategory(
                            this.manager,
                            this.client.currentScreen,
                            Text.translatable(
                                    TranslationUtil.translationKey("text", this.manager.getDefinition(), entry.getKey(), "@Category")

                            )
                    )
            );
        }

        if (!this.categories.isEmpty()) {
            ConfigCategory category = this.categories.get(this.categories.size() - 1);
            this.categories.set(this.categories.size() - 1, category.add(entry));
        }
    }

    /**
     * The minimum required categories for the categories screen to be displayed is two or more
     */
    public boolean hasMinimumRequiredCategories() {
        return this.categories.size() > 1;
    }

    public static class Entry extends ElementListWidget.Entry<Entry> {
        private final List<ClickableWidget> children = Lists.newArrayList();

        public Entry(AbstractEntry entry) {
            if (entry.getLayout() != null) {
                entry.getLayout().forEachChild(this.children::add);
            }
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return this.children;
        }

        @Override
        public List<? extends Element> children() {
            return this.children;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            for (ClickableWidget child : this.children) {
                child.setY(y);
                child.render(context, mouseX, mouseY, tickDelta);
            }
        }
    }
}