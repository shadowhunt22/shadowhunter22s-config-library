//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.autoconfig.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.autoconfig.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.util.TranslationUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;

@ApiStatus.Internal
public class ConfigScreen<T extends ConfigData> extends Screen {
    private final Screen parent;
    private final HashMap<String, ConfigOption<?>> options;
    private final AutoConfigManager<T> configManager;

    ConfigEntryWidget<T> configEntryWidget;

    protected ConfigScreen(AutoConfigManager<T> configManager, HashMap<String, ConfigOption<?>> options, Screen parent) {
        super(Text.translatable(TranslationUtil.translationKey("text", configManager.getDefinition(), "title")));

        this.configManager = configManager;
        this.options = options;
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.configEntryWidget = new ConfigEntryWidget<>(this.configManager, this.client, this.width, this.height, 50, this.height, 27);

        this.options.forEach((field, baseConfigOption) -> this.configEntryWidget.add(field, baseConfigOption));

        this.addDrawableChild(this.configEntryWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, this.title, this.width / 2 - (this.textRenderer.getWidth(this.title) / 2), 10, Colors.WHITE, true);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
