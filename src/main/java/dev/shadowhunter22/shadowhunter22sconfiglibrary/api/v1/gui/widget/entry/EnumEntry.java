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
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.EnumConfigOption;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;

public class EnumEntry<E extends Enum<E>> extends AbstractOptionEntry {
	private final EnumConfigOption<E> typedOption;
	private TextWidget textWidget;
	private ButtonWidget toggleButton;
	private AbstractButtonWidget resetButton;
	public <T extends ConfigData> EnumEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);

		this.typedOption = (EnumConfigOption<E>) this.option;
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		this.textWidget = new TextWidget(150, 20, this.translatableText(this.typedOption.getTranslationKey()), this.client.textRenderer);
		this.textWidget.alignLeft();
		this.textWidget.setX(this.textWidget.getX() + 15);

		this.toggleButton = ButtonWidget.builder(this.typedOption.getText(), button -> {
			this.typedOption.cycle();
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 151, 0, 105, 20).build();

		this.resetButton = ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.listWidget.addWidget(this.textWidget);
		this.listWidget.addWidget(this.toggleButton);
		this.listWidget.addWidget(this.resetButton);

		return new ConfigEntryWidget.Entry(this);
	}

	@Override
	public void update() {
		this.manager.save();

		if (this.toggleButton != null) {
			this.toggleButton.setMessage(this.typedOption.getText());
		}

		this.manager.getConfig().afterChange(this.manager.getConfig().getClass(), this.key);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.textWidget.render(context, mouseX, mouseY, delta);
		this.toggleButton.render(context, mouseX, mouseY, delta);
		this.resetButton.render(context, mouseX, mouseY, delta);
	}
}
