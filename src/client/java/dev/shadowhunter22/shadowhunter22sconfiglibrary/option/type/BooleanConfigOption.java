//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractOptionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.BooleanEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BooleanConfigOption<T extends Boolean> implements ConfigOption<T> {
	private final String key, translationKey;
	private final Text enabled, disabled;
	private T value, defaultValue;

	public BooleanConfigOption(String definition, String key, T value, T defaultValue) {
		this.key = key;
		this.translationKey = TranslationUtil.translationKey("option", definition, this.key);

		this.value = value;
		this.defaultValue = defaultValue;

		this.enabled = Text.translatable("option.shadowhunter22s-config-library.enabled");
		this.disabled = Text.translatable("option.shadowhunter22s-config-library.disabled");
	}

	@Override
	public Text getText() {
		return this.value.booleanValue() ? Text.literal(this.enabled.getString()).formatted(Formatting.GREEN) : Text.literal(this.disabled.getString()).formatted(Formatting.RED);
	}

	@Override
	public String getTranslationKey() {
		return this.translationKey;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = (T) value;
	}

	@Override
	public T getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public void setDefaultValue(Object value) {
		this.defaultValue = (T) value;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public <D extends ConfigData> AbstractOptionEntry asEntry(AutoConfigManager<D> manager, int width) {
		return new BooleanEntry(
				manager,
				this.key,
				width
		);
	}
}
