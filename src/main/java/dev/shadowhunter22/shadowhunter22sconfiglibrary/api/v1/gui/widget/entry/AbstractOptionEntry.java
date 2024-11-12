//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AbstractConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class AbstractOptionEntry extends AbstractEntry {
	protected AbstractConfigManager manager;
	protected ConfigOption<?> option;

	public AbstractOptionEntry(AbstractConfigManager manager, String optionKey, ConfigOption<?> option, int width) {
		this.manager = manager;
		this.optionKey = optionKey;
		this.option = option;
		this.width = width;
	}

	protected abstract void update();

	protected Text translatableText(String text) {
		return Text.translatable(text).formatted(Formatting.GRAY);
	}
}
