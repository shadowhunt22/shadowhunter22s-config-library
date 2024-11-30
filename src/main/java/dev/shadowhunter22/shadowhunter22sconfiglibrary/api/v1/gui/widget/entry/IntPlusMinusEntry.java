package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.AbstractButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ResetButtonWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.IntegerConfigOption;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class IntPlusMinusEntry extends AbstractOptionEntry {
	private final IntegerConfigOption<Integer> typedOption;
	private TextWidget textWidget;
	private ButtonWidget addButton;
	private ButtonWidget subtractButton;
	private AbstractButtonWidget resetButton;
	public <T extends ConfigData> IntPlusMinusEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);

		this.typedOption = (IntegerConfigOption<Integer>) this.option;
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		this.textWidget = new TextWidget(150, 20, this.translatableText(this.typedOption.getTranslationKey()), this.client.textRenderer);
		this.textWidget.alignLeft();
		this.textWidget.setX(this.textWidget.getX() + 15);

		this.addButton = ButtonWidget.builder(Text.of("+"), button -> {
			this.typedOption.setValue(this.typedOption.getValue() + 1);
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 87, 0, 20, 20).build();

		this.subtractButton = ButtonWidget.builder(Text.of("-"), button -> {
			this.typedOption.setValue(this.typedOption.getValue() - 1);
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 66, 0, 20, 20).build();

		this.resetButton = ResetButtonWidget.builder(this.typedOption, action -> {
			this.typedOption.setValue(this.typedOption.getDefaultValue());
			this.manager.getSerializer().setValue(this.manager, this.key, this.typedOption.getValue());
			this.update();
		}).dimensions(this.width - 45, 0, 20, 20).build();

		this.listWidget.addWidget(this.textWidget);
		this.listWidget.addWidget(this.addButton);
		this.listWidget.addWidget(this.subtractButton);
		this.listWidget.addWidget(this.resetButton);

		return new ConfigEntryWidget.Entry(this);
	}

	@Override
	protected void update() {
		this.subtractButton.active = this.typedOption.getValue() != this.typedOption.getMin();
		this.addButton.active = this.typedOption.getValue() != this.typedOption.getMax();

		this.manager.save();
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.textWidget.render(context, mouseX, mouseY, delta);
		this.addButton.render(context, mouseX, mouseY, delta);
		this.subtractButton.render(context, mouseX, mouseY, delta);
		this.resetButton.render(context, mouseX, mouseY, delta);
	}
}
