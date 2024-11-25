//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.util.HashMap;

public class ConfigScreen<T extends ConfigData> extends Screen {
    private final Screen parent;
    private final HashMap<String, ConfigOption<?>> options;
    private final AutoConfigManager<T> manager;

    ConfigEntryWidget configEntryWidget;

    protected ConfigScreen(AutoConfigManager<T> manager, HashMap<String, ConfigOption<?>> options, Screen parent) {
        super(Text.translatable(TranslationUtil.translationKey("text", manager.getDefinition(), "title")));

        this.manager = manager;
        this.options = options;
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.configEntryWidget = new ConfigEntryWidget(this.manager, this.client, this.width, this.height, 50, this.height, 27);

        this.options.forEach((key, option) -> this.configEntryWidget.add(key, option));

        this.addDrawableChild(this.configEntryWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, this.title, this.width / 2 - (this.textRenderer.getWidth(this.title) / 2), 10, Colors.WHITE, true);
    }

    @Override
    public void renderBackground(DrawContext context) {
        context.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
