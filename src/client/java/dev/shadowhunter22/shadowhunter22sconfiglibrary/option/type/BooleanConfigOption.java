//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractOptionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.BooleanEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class BooleanConfigOption<T extends Boolean> implements ConfigOption<T> {
	private final String key, translationKey;
	private final Component enabled, disabled;
	private T value, defaultValue;

	public BooleanConfigOption(String definition, String key, T value, T defaultValue) {
		this.key = key;
		this.translationKey = TranslationUtil.translationKey("option", definition, this.key);

		this.value = value;
		this.defaultValue = defaultValue;

		this.enabled = Component.translatable("option.shadowhunter22s-config-library.enabled");
		this.disabled = Component.translatable("option.shadowhunter22s-config-library.disabled");
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
	public Component getText() {
		return this.value.booleanValue() ? Component.literal(this.enabled.getString()).withStyle(ChatFormatting.GREEN) : Component.literal(this.disabled.getString()).withStyle(ChatFormatting.RED);
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
	public <D extends ConfigData> AbstractOptionEntry asEntry(AutoConfigManager<D> manager, int width) {
		return new BooleanEntry(
				manager,
				this.key,
				width
		);
	}
}
