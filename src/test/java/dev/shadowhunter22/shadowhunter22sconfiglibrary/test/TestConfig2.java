//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.test;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;

@Config(name = "config-library", file = "test2")
public class TestConfig2 implements ConfigData {
	public String TEST_1 = "Test"; // will be ignored

	@ConfigEntry.Gui.Category
	@ConfigEntry.Gui.Section
	@ConfigEntry.Integer(min = 0, max = 100)
	public int TEST_2 = 3;

	@ConfigEntry.Float(min = 0, max = 100)
	public float FLOAT_TEST_1 = 3f;

	@ConfigEntry.Float(min = 0, max = 1)
	public float FLOAT_TEST_2 = 0.75f;

	@ConfigEntry.Float(min = 0.5f, max = 1f)
	public float FLOAT_TEST_3 = 0.5f;

	@ConfigEntry.Double(min = 0.5f, max = 1f)
	public double DOUBLE_TEST_1 = 0.5f;

	public boolean TEST_3 = true;

	@ConfigEntry.Gui.Category
	@ConfigEntry.Gui.Section
	public boolean TEST_4 = false;

	Location TEST_5 = Location.TopLeft;

	@ConfigEntry.Gui.Section
	Location TEST_6 = Location.TopRight;

	Location TEST_7 = Location.BottomLeft;

	Location TEST_8 = Location.TopLeft;

	Location TEST_9 = Location.TopLeft;

	public enum Location {
		TopLeft,
		TopRight,
		BottomLeft,
		BottomRight
	}
}
