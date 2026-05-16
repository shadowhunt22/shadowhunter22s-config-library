//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.test;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.BooleanEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.DoubleSliderEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.EnumEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.FloatSliderEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.IntSliderEntry;

import net.minecraft.client.gui.screens.Screen;

/**
 * A custom-built config screen with categories.
 */
public class TestConfig2Screen extends AbstractConfigScreen {
	protected <T extends ConfigData> TestConfig2Screen(AutoConfigManager<T> manager, Screen parent) {
		super(manager, parent);
	}

	@Override
	protected void init() {
		// a test of using code to generate the same config layout as an auto-generated config screen

		this.addToOrCreateCategory(new IntSliderEntry(this.manager, "TEST_2", this.width), true)
				.addToOrCreateCategory(new FloatSliderEntry(this.manager, "FLOAT_TEST_1", this.width))
				.addToOrCreateCategory(new FloatSliderEntry(this.manager, "FLOAT_TEST_2", this.width))
				.addToOrCreateCategory(new FloatSliderEntry(this.manager, "FLOAT_TEST_3", this.width))
				.addToOrCreateCategory(new DoubleSliderEntry(this.manager, "DOUBLE_TEST_1", this.width))
				.addToOrCreateCategory(new BooleanEntry(this.manager, "TEST_3", this.width));

		this.addToOrCreateCategory(new BooleanEntry(this.manager, "TEST_4", this.width), false)
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_5", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_6", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_7", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_8", this.width))
				.addToOrCreateCategory(new EnumEntry<>(this.manager, "TEST_9", this.width));

		this.addTabWidget();
	}
}
