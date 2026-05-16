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
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.IntegerConfigOption;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;

public class IntPlusMinusEntry extends AbstractOptionEntry {
	private final IntegerConfigOption<Integer> typedOption;

	private StringWidget stringWidget;
	private Button addButton;
	private Button subtractButton;
	private AbstractButtonWidget resetButton;

	public <T extends ConfigData> IntPlusMinusEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);

		// noinspection unchecked
		this.typedOption = (IntegerConfigOption<Integer>) this.option;
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		this.stringWidget = new StringWidget(150, 20, this.translatableText(this.typedOption.getTranslationKey()), this.minecraft.font);
		this.stringWidget.setX(this.stringWidget.getX() + 15);

		this.addButton = Button.builder(Component.literal("+"), button -> {
			this.typedOption.setValue(this.typedOption.getValue() + 1);
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).bounds(this.width - 87, 0, 20, 20).build();

		this.subtractButton = Button.builder(Component.literal("-"), button -> {
			this.typedOption.setValue(this.typedOption.getValue() - 1);
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).bounds(this.width - 66, 0, 20, 20).build();

		this.resetButton = ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.layout.addBody(this.stringWidget);
		this.layout.addBody(this.addButton);
		this.layout.addBody(this.subtractButton);
		this.layout.addBody(this.resetButton);

		return new ConfigEntryWidget.Entry(this);
	}

	@Override
	protected void update() {
		this.manager.save();

		this.subtractButton.active = this.typedOption.getValue() != this.typedOption.getMin();
		this.addButton.active = this.typedOption.getValue() != this.typedOption.getMax();

		if (this.resetButton != null) {
			this.resetButton.active = this.typedOption.getValue() != this.typedOption.getDefaultValue();
		}

		this.manager.getConfig().afterChange(this.manager.getConfig().getClass(), this.key);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.stringWidget.render(graphics, mouseX, mouseY, delta);
		this.addButton.render(graphics, mouseX, mouseY, delta);
		this.subtractButton.render(graphics, mouseX, mouseY, delta);
		this.resetButton.render(graphics, mouseX, mouseY, delta);
	}
}
