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
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.EnumConfigOption;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;

public class EnumEntry<E extends Enum<E>> extends AbstractOptionEntry {
	private final EnumConfigOption<E> typedOption;

	private StringWidget stringWidget;
	private Button toggleButton;
	private AbstractButtonWidget resetButton;

	public <T extends ConfigData> EnumEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);

		// noinspection unchecked
		this.typedOption = (EnumConfigOption<E>) this.option;
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		this.stringWidget = new StringWidget(150, 20, this.translatableText(this.typedOption.getTranslationKey()), this.minecraft.font);
		this.stringWidget.setX(this.stringWidget.getX() + 15);

		this.toggleButton = Button.builder(this.typedOption.getText(), button -> {
			this.typedOption.cycle();
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).bounds(this.width - 151, 0, 105, 20).build();

		this.resetButton = ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.layout.addBody(this.stringWidget);
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

		this.manager.getConfig().afterChange(this.manager.getConfig().getClass(), this.key);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		this.stringWidget.extractRenderState(graphics, mouseX, mouseY, delta);
		this.toggleButton.extractRenderState(graphics, mouseX, mouseY, delta);
		this.resetButton.extractRenderState(graphics, mouseX, mouseY, delta);
	}
}
