//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import com.google.common.collect.Lists;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.BooleanEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.EnumEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.IntSliderEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.SectionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.SimpleAbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.BaseConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.BooleanConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.EnumConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.IntegerConfigOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.util.List;

@ApiStatus.Internal
public class ConfigEntryWidget<T extends ConfigData> extends ElementListWidget<ConfigEntryWidget.Entry> {
    private final ConfigManager<T> manager;

    public ConfigEntryWidget(ConfigManager<T> configManager, MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);

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

    @Override
    protected void renderBackground(DrawContext context) {
        context.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
    }

    public void add(Field field, BaseConfigOption<?> option) {
        if (field.isAnnotationPresent(ConfigEntry.Gui.Section.class)) {
            this.addEntry(new SectionEntry<>(this.manager, field.getName()).build());
        }

        if (option instanceof BooleanConfigOption<?> typeOption) {
            this.addEntry(new BooleanEntry<>(this.manager, field, typeOption, this.width).build());
        }

        if (option instanceof IntegerConfigOption<?> typedOption) {
            this.addEntry(new IntSliderEntry<>(this.manager, field, typedOption, this.width).build());
        }

        if (option instanceof EnumConfigOption<?> typedOption) {
            this.addEntry(new EnumEntry<>(this.manager, field, typedOption, this.width).build());
        }
    }

    public static class Entry extends ElementListWidget.Entry<Entry> {
        private final List<ClickableWidget> children = Lists.newArrayList();

        public <T extends ConfigData> Entry(AbstractEntry<T> entry) {
            if (entry.getLayout() != null) {
                entry.getLayout().forEachChild(this.children::add);
            }
        }

        public Entry(SimpleAbstractEntry entry) {
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
            this.children.forEach(child -> {
                child.setY(y);
                child.render(context, mouseX, mouseY, tickDelta);
            });
        }
    }
}