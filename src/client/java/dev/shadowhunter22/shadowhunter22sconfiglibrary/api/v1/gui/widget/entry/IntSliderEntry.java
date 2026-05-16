//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.AbstractButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ResetButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.AbstractSliderButtonInvoker;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.IntegerConfigOption;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class IntSliderEntry extends AbstractSliderEntry {
	private final IntegerConfigOption<Integer> typedOption;

	private StringWidget stringWidget;
	private AbstractSliderButton sliderWidget;
	private AbstractButtonWidget resetButton;

	public <T extends ConfigData> IntSliderEntry(AutoConfigManager<T> manager, String optionKey, int width) {
		super(manager, optionKey, width);

		// noinspection unchecked
		this.typedOption = (IntegerConfigOption<Integer>) this.option;
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		this.stringWidget = new StringWidget(250, 20, this.translatableText(this.typedOption.getTranslationKey()), this.minecraft.font);
		this.stringWidget.setX(this.stringWidget.getX() + 15);

		this.sliderWidget = this.createSliderWidget();

		this.resetButton = ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.layout.addBody(this.stringWidget);
		this.layout.addBody(this.sliderWidget);
		this.layout.addBody(this.resetButton);

		return new ConfigEntryWidget.Entry(this);
	}

	protected AbstractSliderButton createSliderWidget() {
		return new AbstractSliderButton(this.width - 151, 0, 105, 20, CommonComponents.EMPTY, this.typedOption.getValue()) {
			{
				this.updateMessage();
				((AbstractSliderButtonInvoker) ((AbstractSliderButton) this)).invokeSetValue((this.value - IntSliderEntry.this.typedOption.getMin()) / (IntSliderEntry.this.typedOption.getMax() - IntSliderEntry.this.typedOption.getMin()));
			}

			@Override
			protected void updateMessage() {
				this.setMessage(Component.literal(IntSliderEntry.this.typedOption.getValue().toString()));
			}

			@Override
			protected void applyValue() {
				int newValue = Mth.floor(Mth.clampedLerp(this.value, IntSliderEntry.this.typedOption.getMin(), IntSliderEntry.this.typedOption.getMax()));

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
			((AbstractSliderButtonInvoker) this.sliderWidget).invokeSetValue(((double) this.typedOption.getValue() - IntSliderEntry.this.typedOption.getMin()) / (IntSliderEntry.this.typedOption.getMax() - IntSliderEntry.this.typedOption.getMin()));
			this.sliderWidget.setMessage(Component.literal(this.typedOption.getValue().toString()));
		}

		if (this.resetButton != null) {
			this.resetButton.active = this.typedOption.getValue() != this.typedOption.getDefaultValue();
		}

		this.manager.getConfig().afterChange(this.manager.getConfig().getClass(), this.key);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		this.stringWidget.extractRenderState(graphics, mouseX, mouseY, delta);
		this.sliderWidget.extractRenderState(graphics, mouseX, mouseY, delta);
		this.resetButton.extractRenderState(graphics, mouseX, mouseY, delta);
	}
}
