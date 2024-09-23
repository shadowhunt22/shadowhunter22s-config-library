//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import com.google.common.collect.Lists;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.BooleanConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.EnumConfigOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.LayoutWidget;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.util.List;

@ApiStatus.Internal
public class ConfigEntryWidget<T extends ConfigData> extends ElementListWidget<ConfigEntryWidget.Entry> {
    // TODO simplify into an AbstractEntryBuilder?
    private final EnumEntryBuilder<T> enumBuilder;
    private final BooleanEntryBuilder<T> booleanBuilder;

    public ConfigEntryWidget(ConfigManager<T> configManager, MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);

        this.enumBuilder = new EnumEntryBuilder<>(client, configManager, width);
        this.booleanBuilder = new BooleanEntryBuilder<>(client, configManager, width);

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

    public <E extends EnumConfigOption<?>> void add(Field field, E configOption) {
        addEntry(this.enumBuilder.build(this, field, configOption));
    }

    public void add(Field field, BooleanConfigOption<Boolean> configOption) {
        addEntry(this.booleanBuilder.build(this, field, configOption));
    }

    public static class Entry extends ElementListWidget.Entry<Entry> {
        private final ConfigManager<?> configManager;
        private final ConfigOption<?> configOption;
        private final LayoutWidget layout;
        private final List<ClickableWidget> children = Lists.newArrayList();
        private final ButtonWidget toggleButton;
        private final ButtonWidget resetButton;

        Entry(ConfigManager<?> configManager, ConfigOption<?> configOption, LayoutWidget layout, ButtonWidget toggleButton, ButtonWidget resetButton) {
            this.configManager = configManager;
            this.configOption = configOption;
            this.layout = layout;
            this.toggleButton = toggleButton;
            this.resetButton = resetButton;
            this.layout.forEachChild(this.children::add);
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
            this.layout.forEachChild((child -> {
                child.setY(y);
                child.render(context, mouseX, mouseY, tickDelta);
            }));
        }

        public void update() {
            this.configManager.save();
            this.toggleButton.setMessage(this.configOption.getText());
            this.resetButton.active = this.configOption.getValue() != this.configOption.getDefaultValue();
        }
    }

    public void update() {
        this.updateChildren();
    }

    public void updateChildren() {
        this.children().forEach(ConfigEntryWidget.Entry::update);
    }
}