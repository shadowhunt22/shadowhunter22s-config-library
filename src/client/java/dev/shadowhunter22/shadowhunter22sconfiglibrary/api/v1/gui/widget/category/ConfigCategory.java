//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category;

import java.util.ArrayList;
import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigCategory {
	protected final AutoConfigManager<? extends ConfigData> manager;
	private final Text text;
	private final Screen parent;
	public List<AbstractEntry> entries = new ArrayList<>();

	public <T extends ConfigData> ConfigCategory(AutoConfigManager<T> manager, Screen parent, Text text) {
		this.manager = manager;
		this.parent = parent;
		this.text = text;
	}

	public ConfigCategory add(AbstractEntry entry) {
		this.entries.add(entry);
		return this;
	}

	public ConfigCategory add(int index, AbstractEntry entry) {
		this.entries.add(index, entry);
		return this;
	}

	public CategoryTab getTab() {
		return new CategoryTab(this, this.parent);
	}

	public Text getText() {
		return this.text;
	}
}
