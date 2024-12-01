//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.test;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.BooleanEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.EnumEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.IntPlusMinusEntry;

import net.minecraft.client.gui.screen.Screen;

/**
 * With categories.
 */
public class TestConfig2Screen extends AbstractConfigScreen {
	protected <T extends ConfigData> TestConfig2Screen(AutoConfigManager<T> manager, Screen parent) {
		super(manager, parent);
	}

	@Override
	protected void init() {
		this.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_5", this.width), true);

		this.addToOrCreateCategory(new IntPlusMinusEntry(this.manager, "TEST_2", this.width), true)
				.addToOrCreateCategory(new BooleanEntry(this.manager, "TEST_3", this.width))
				.addToOrCreateCategory(new BooleanEntry(this.manager, "TEST_4", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_5", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_6", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_7", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_8", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_9", this.width));

		this.addTabWidget();
	}
}
