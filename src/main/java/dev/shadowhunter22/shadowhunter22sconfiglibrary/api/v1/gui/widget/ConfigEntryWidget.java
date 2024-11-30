//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category.ConfigCategory;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractOptionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.SectionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigEntryWidget extends AbstractConfigEntryWidget<ConfigEntryWidget.Entry> {
    private final AutoConfigManager<?> manager;
    public final List<ConfigCategory> categories = new ArrayList<>();

    public <T extends ConfigData> ConfigEntryWidget(AutoConfigManager<T> manger, MinecraftClient client, int width, int height) {
        super(client, width, height);

        this.manager = manger;
    }

    public void add(AbstractEntry entry) {
        if (entry instanceof ConfigOption<?> option) {
            this.add(option.getKey(), option);
        } else {
            this.addEntry(entry.build());
        }
    }

    public void add(String key, ConfigOption<?> option) {
        Field field;

        try {
            field = this.manager.getConfig().getClass().getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        boolean hasSectionAnnotation = ConfigRegistry.hasSectionAnnotation(field);
        boolean hasCategoryAnnotation = ConfigRegistry.hasCategoryAnnotation(field);

        AbstractOptionEntry entry = option.asEntry(this.manager, this.width);

        // fun :)

        if (hasSectionAnnotation && hasCategoryAnnotation) {
            this.addSection(key, true);
            this.addEntry(entry.build());
            this.addToOrCreateCategory(entry, false);
        } else if (hasSectionAnnotation) {
            this.addSection(key);
            this.addEntry(entry.build());
            this.addToOrCreateCategory(entry, false);
        } else if (hasCategoryAnnotation) {
            this.addToOrCreateCategory(entry, true);
        } else {
            this.addEntry(entry.build());
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

    public void addToOrCreateCategory(AbstractEntry entry, boolean createCategory) {
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
     * The minimum required categories for the categories screen to be displayed is two or more.
     */
    public boolean hasMinimumRequiredCategories() {
        return this.categories.size() > 1;
    }

    public static class Entry extends AbstractConfigEntryWidget.Entry<Entry> {
        public Entry(AbstractEntry entry) {
            super(entry);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.entry.setY(y);
            this.entry.render(context, mouseX, mouseY, tickDelta);
        }
    }
}