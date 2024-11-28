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
    public String TEST_1 = "Test";

    @ConfigEntry.Gui.Category
    @ConfigEntry.Gui.Section
    @ConfigEntry.Integer(min = 0, max = 100)
    public int TEST_2 = 3;

    public boolean TEST_3 = true;

    @ConfigEntry.Gui.Category
    @ConfigEntry.Gui.Section
    public boolean TEST_4 = false;

    public enum Location {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight
    }

    Location TEST_5 = Location.TopLeft;

    @ConfigEntry.Gui.Section
    Location TEST_6 = Location.TopRight;

    Location TEST_7 = Location.BottomLeft;

    Location TEST_8 = Location.TopLeft;

    Location TEST_9 = Location.TopLeft;
}