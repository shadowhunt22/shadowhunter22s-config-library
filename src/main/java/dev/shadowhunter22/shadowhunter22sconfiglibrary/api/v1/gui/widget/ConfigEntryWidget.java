//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import com.google.common.collect.Lists;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.BooleanEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.EnumEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.IntSliderEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.SectionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.BooleanConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.EnumConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.IntegerConfigOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;

import java.lang.reflect.Field;
import java.util.List;

public class ConfigEntryWidget extends ElementListWidget<ConfigEntryWidget.Entry> {
    private final AutoConfigManager<?> manager;

    public <T extends ConfigData> ConfigEntryWidget(AutoConfigManager<T> configManager, MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
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

    public void add(AbstractEntry entry) {
        if (entry instanceof ConfigOption<?> option) {
            this.add(option.getKey(), option);
        } else {
            this.addEntry(entry.build());
        }
    }

    public void add(String key, ConfigOption<?> option) {
        if (this.manager instanceof AutoConfigManager<?>) {
            Field field;

            try {
                field = this.manager.getConfig().getClass().getDeclaredField(key);
            } catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}

            if (field.isAnnotationPresent(ConfigEntry.Gui.Section.class)) {
                this.addSection(key);
            }
		}

        if (option instanceof BooleanConfigOption<?>) {
            this.addEntry(new BooleanEntry(this.manager, key, this.width).build());
        }

        if (option instanceof IntegerConfigOption<?>) {
            this.addEntry(new IntSliderEntry(this.manager, key, this.width).build());
        }

        if (option instanceof EnumConfigOption<?>) {
            this.addEntry(new EnumEntry<>(this.manager, key, this.width).build());
        }
    }

    private void addSection(String optionKey) {
        this.addEntry(new SectionEntry(this.manager, optionKey, this.width).build());
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
            this.children.forEach(child -> {
                child.setY(y);
                child.render(context, mouseX, mouseY, tickDelta);
            });
        }
    }
}