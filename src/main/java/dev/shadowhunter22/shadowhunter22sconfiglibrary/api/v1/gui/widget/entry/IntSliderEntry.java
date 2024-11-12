//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import java.lang.reflect.Field;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ResetButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.IntegerConfigOption;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class IntSliderEntry<T extends ConfigData> extends AbstractOptionEntry<T> {
	private final IntegerConfigOption<Integer> typedOption;

	public IntSliderEntry(ConfigManager<T> manager, Field field, ConfigOption<?> option, int width) {
		super(manager, field, option, width);

		this.typedOption = (IntegerConfigOption<Integer>) option;
	}

	private SliderWidget sliderWidget;

	@Override
	public ConfigEntryWidget.Entry build() {
		TextWidget textWidget = new TextWidget(250, 20, this.translatableText(this.typedOption.getTranslationKey()), this.client.textRenderer);
		textWidget.alignLeft();
		textWidget.setX(textWidget.getX() + 15);

		this.sliderWidget = this.createSlider();

		ResetButtonWidget resetButton = ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(field, this.manager.getConfig(), this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.layout.addBody(textWidget);
		this.layout.addBody(this.sliderWidget);
		this.layout.addBody(resetButton);

		return new ConfigEntryWidget.Entry(this);
	}

	private SliderWidget createSlider() {
		return new SliderWidget(this.width - 151, 0, 105, 20, ScreenTexts.EMPTY, this.typedOption.getValue()) {
			{
				this.updateMessage();
				this.setValue((this.value - IntSliderEntry.this.typedOption.getMin()) / (IntSliderEntry.this.typedOption.getMax() - IntSliderEntry.this.typedOption.getMin()));
			}

			@Override
			protected void updateMessage() {
				this.setMessage(Text.of(IntSliderEntry.this.typedOption.getValue().toString()));
			}

			@Override
			protected void applyValue() {
				int newValue = MathHelper.floor(MathHelper.clampedLerp(IntSliderEntry.this.typedOption.getMin(), IntSliderEntry.this.typedOption.getMax(), this.value));

				IntSliderEntry.this.typedOption.setValue(newValue);
				IntSliderEntry.this.manager.getSerializer().setValue(IntSliderEntry.this.field, IntSliderEntry.this.manager.getConfig(), newValue);
				IntSliderEntry.this.update();
			}
		};
	}

	@Override
	public void update() {
		this.manager.save();

		if (this.sliderWidget != null) {
			this.sliderWidget.setValue(((double) this.typedOption.getValue() - IntSliderEntry.this.typedOption.getMin()) / (IntSliderEntry.this.typedOption.getMax() - IntSliderEntry.this.typedOption.getMin()));
			this.sliderWidget.setMessage(Text.of(this.typedOption.getValue().toString()));
		}
	}
}
