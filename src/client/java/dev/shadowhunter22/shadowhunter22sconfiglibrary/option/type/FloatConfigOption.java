//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractOptionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.FloatSliderEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.NumberConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;

import net.minecraft.text.Text;

public class FloatConfigOption<T extends Float> implements NumberConfigOption<T> {
	private final String key, translationKey, definition;
	private T value, defaultValue, min, max;

	public FloatConfigOption(String definition, String key, T min, T max, T value, T defaultValue) {
		this.definition = definition;

		this.key = key;
		this.translationKey = TranslationUtil.translationKey("option", definition, this.key);

		this.value = value;
		this.min = min;
		this.max = max;
		this.defaultValue = defaultValue;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getTranslationKey() {
		return this.translationKey;
	}

	@Override
	public Text getText() {
		return Text.translatable(TranslationUtil.translationKey("option", this.definition, this.key));
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (T) value;
	}

	@Override
	public T getMin() {
		return this.min;
	}

	@Override
	public void setMin(Object value) {
		this.min = (T) value;
	}

	@Override
	public T getMax() {
		return this.max;
	}

	@Override
	public void setMax(Object value) {
		this.max = (T) value;
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
	public <D extends ConfigData> AbstractOptionEntry asEntry(AutoConfigManager<D> manager, int width) {
		return new FloatSliderEntry(
				manager,
				this.key,
				width
		);
	}
}
