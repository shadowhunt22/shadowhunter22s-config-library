//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.BooleanConfigOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;

@ApiStatus.Internal
public class BooleanEntryBuilder<T extends ConfigData> {
    private final ConfigManager<T> configManager;
    private final MinecraftClient client;
    private final int width;

    public BooleanEntryBuilder(MinecraftClient client, ConfigManager<T> configManager, int width) {
        this.client = client;
        this.configManager = configManager;
        this.width = width;
    }

    public ConfigEntryWidget.Entry build(ConfigEntryWidget<T> parentWidget, Field field, BooleanConfigOption<Boolean> configOption) {
        ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this.client.currentScreen);

        TextWidget textWidget = new TextWidget(250, 20, Text.translatable(configOption.getTranslationKey()), this.client.textRenderer);
        textWidget.alignLeft();
        textWidget.setX(textWidget.getX() + 15);

        ButtonWidget toggleButton = ButtonWidget.builder(configOption.getText(), button -> {
            configOption.setValue(!configOption.getValue());
            this.configManager.getSerializer().setValue(field, this.configManager.getConfig(), configOption.getValue());
            parentWidget.update();
        }).dimensions(this.width - 151, 0, 75, 20).build();

        ButtonWidget resetButton = ButtonWidget.builder(Text.translatable("controls.reset"), button -> {
            configOption.setValue(configOption.getDefaultValue());
            this.configManager.getSerializer().setValue(field, this.configManager.getConfig(), configOption.getValue());
            parentWidget.update();
        }).dimensions(this.width - 75, 0, 50, 20).build();

        resetButton.active = configOption.getValue() != configOption.getDefaultValue();

        layout.addBody(textWidget);
        layout.addBody(toggleButton);
        layout.addBody(resetButton);

        return new ConfigEntryWidget.Entry(configManager, configOption.asConfigOption(), layout, toggleButton, resetButton);
    }
}