//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AbstractConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ResetButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.BooleanConfigOption;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;

public class BooleanEntry extends AbstractOptionEntry {
	private final ConfigOption<Boolean> typedOption;

	public BooleanEntry(AbstractConfigManager manager, String optionKey, ConfigOption<?> option, int width) {
		super(manager, optionKey, option, width);

		this.typedOption = (BooleanConfigOption<Boolean>) option;
	}

	private ButtonWidget toggleButton;

	@Override
	public ConfigEntryWidget.Entry build() {
		TextWidget textWidget = new TextWidget(250, 20, this.translatableText(this.typedOption.getTranslationKey()), this.client.textRenderer);
		textWidget.alignLeft();
		textWidget.setX(textWidget.getX() + 15);

		this.toggleButton = ButtonWidget.builder(this.typedOption.getText(), button -> {
			this.typedOption.setValue(!this.typedOption.getValue());
			this.manager.getSerializer().setValue(this.manager, this.optionKey, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 151, 0, 105, 20).build();

		ResetButtonWidget resetButton = (ResetButtonWidget) ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.optionKey, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.layout.addBody(textWidget);
		this.layout.addBody(this.toggleButton);
		this.layout.addBody(resetButton);

		return new ConfigEntryWidget.Entry(this);
	}

	@Override
	public void update() {
		this.manager.save();

		if (this.toggleButton != null) {
			this.toggleButton.setMessage(this.typedOption.getText());
		}
	}
}
