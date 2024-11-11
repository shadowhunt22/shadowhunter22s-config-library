//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import java.lang.reflect.Field;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class AbstractOptionEntry<T extends ConfigData> extends AbstractEntry {
	protected ConfigManager<T> manager;
	protected ConfigOption<?> option;

	public AbstractOptionEntry(ConfigManager<T> manager, Field field, ConfigOption<?> option, int width) {
		this.manager = manager;
		this.field = field;
		this.option = option;
		this.width = width;
	}

	protected abstract void update();

	protected Text translatableText(String text) {
		return Text.translatable(text).formatted(Formatting.GRAY);
	}
}
