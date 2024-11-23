//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AbstractConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ResetButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.IntegerConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.client.SliderWidgetInvoker;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class IntSliderEntry extends AbstractOptionEntry {
	private final IntegerConfigOption<Integer> typedOption;

	public IntSliderEntry(AbstractConfigManager manager, String optionKey, ConfigOption<?> option, int width) {
		super(manager, optionKey, option, width);

		this.typedOption = (IntegerConfigOption<Integer>) option;
	}

	private SliderWidget sliderWidget;

	@Override
	public ConfigEntryWidget.Entry build() {
		TextWidget textWidget = new TextWidget(250, 20, this.translatableText(this.typedOption.getTranslationKey()), this.client.textRenderer);
		textWidget.alignLeft();
		textWidget.setX(textWidget.getX() + 15);

		this.sliderWidget = this.createSlider();

		ResetButtonWidget resetButton = (ResetButtonWidget) ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.optionKey, this.typedOption.getValue());
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
				((SliderWidgetInvoker) ((SliderWidget) this)).invokeSetValue((this.value - IntSliderEntry.this.typedOption.getMin()) / (IntSliderEntry.this.typedOption.getMax() - IntSliderEntry.this.typedOption.getMin()));
			}

			@Override
			protected void updateMessage() {
				this.setMessage(Text.of(IntSliderEntry.this.typedOption.getValue().toString()));
			}

			@Override
			protected void applyValue() {
				int newValue = MathHelper.floor(MathHelper.clampedLerp(IntSliderEntry.this.typedOption.getMin(), IntSliderEntry.this.typedOption.getMax(), this.value));

				IntSliderEntry.this.typedOption.setValue(newValue);
				IntSliderEntry.this.manager.getSerializer().setValue(IntSliderEntry.this.manager, IntSliderEntry.this.optionKey, newValue);
				IntSliderEntry.this.update();
			}
		};
	}

	@Override
	public void update() {
		this.manager.save();

		if (this.sliderWidget != null) {
			((SliderWidgetInvoker) this.sliderWidget).invokeSetValue(((double) this.typedOption.getValue() - IntSliderEntry.this.typedOption.getMin()) / (IntSliderEntry.this.typedOption.getMax() - IntSliderEntry.this.typedOption.getMin()));
			this.sliderWidget.setMessage(Text.of(this.typedOption.getValue().toString()));
		}
	}
}
