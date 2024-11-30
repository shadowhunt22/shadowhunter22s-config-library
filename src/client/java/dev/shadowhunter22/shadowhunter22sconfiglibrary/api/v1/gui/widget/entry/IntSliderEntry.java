//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.AbstractButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ResetButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.SliderWidgetInvoker;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.IntegerConfigOption;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class IntSliderEntry extends AbstractOptionEntry {
	private final IntegerConfigOption<Integer> typedOption;
	private TextWidget textWidget;
	private SliderWidget sliderWidget;
	private AbstractButtonWidget resetButton;
	public <T extends ConfigData> IntSliderEntry(AutoConfigManager<T> manager, String optionKey, int width) {
		super(manager, optionKey, width);

		this.typedOption = (IntegerConfigOption<Integer>) this.option;
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		this.textWidget = new TextWidget(250, 20, this.translatableText(this.typedOption.getTranslationKey()), this.client.textRenderer);
		this.textWidget.alignLeft();
		this.textWidget.setX(this.textWidget.getX() + 15);

		this.sliderWidget = this.createSlider();

		this.resetButton = ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.listWidget.addWidget(this.textWidget);
		this.listWidget.addWidget(this.sliderWidget);
		this.listWidget.addWidget(this.resetButton);

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
				IntSliderEntry.this.manager.getSerializer().setValue(IntSliderEntry.this.manager, IntSliderEntry.this.key, newValue);
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

		this.manager.getConfig().afterChange(this.manager.getConfig().getClass(), this.key);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.textWidget.render(context, mouseX, mouseY, delta);
		this.sliderWidget.render(context, mouseX, mouseY, delta);
		this.resetButton.render(context, mouseX, mouseY, delta);
	}
}
