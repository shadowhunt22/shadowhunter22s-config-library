//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class AbstractOptionEntry extends AbstractEntry {
	protected final ConfigOption<?> option;

	public <T extends ConfigData> AbstractOptionEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);

		this.option = GuiRegistry.getOption(manager.getConfig().getClass(), key);
	}

	protected abstract void update();

	protected Text translatableText(String text) {
		return Text.translatable(text).formatted(Formatting.GRAY);
	}
}
