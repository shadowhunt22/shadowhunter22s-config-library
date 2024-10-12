//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import java.lang.reflect.Field;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.BaseConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.EnumConfigOption;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class EnumEntry<T extends ConfigData, E extends Enum<E>> extends AbstractEntry<T> {
	private final EnumConfigOption<E> typedOption;

	public EnumEntry(ConfigManager<T> manager, Field field, BaseConfigOption<?> option, int width) {
		super(manager, field, option, width);

		this.typedOption = (EnumConfigOption<E>) option;
	}

	private ButtonWidget toggleButton;
	private ButtonWidget resetButton;

	@Override
	public ConfigEntryWidget.Entry build() {
		TextWidget textWidget = new TextWidget(150, 20, Text.translatable(this.typedOption.getTranslationKey()), this.client.textRenderer);
		textWidget.alignLeft();
		textWidget.setX(textWidget.getX() + 15);

		this.toggleButton = ButtonWidget.builder(this.typedOption.getText(), button -> {
			this.typedOption.cycle();
			this.manager.getSerializer().setValue(this.field, this.manager.getConfig(), this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 151, 0, 75, 20).build();

		this.resetButton = ButtonWidget.builder(Text.translatable("controls.reset"), button -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.field, this.manager.getConfig(), this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 75, 0, 50, 20).build();

		this.resetButton.active = this.typedOption.getValue() != this.typedOption.getDefaultValue();

		this.layout.addBody(textWidget);
		this.layout.addBody(this.toggleButton);
		this.layout.addBody(this.resetButton);

		return new ConfigEntryWidget.Entry(this);
	}

	@Override
	public void update() {
		this.manager.save();

		if (this.toggleButton != null) {
			this.toggleButton.setMessage(this.typedOption.getText());
		}

		if (this.resetButton != null) {
			this.resetButton.active = this.typedOption.getValue() != this.typedOption.getDefaultValue();
		}
	}
}
