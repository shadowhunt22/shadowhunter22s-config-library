//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.test;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.autoconfig.ConfigData;

@Config(name = "config-library", file = "test2")
public class TestConfig2 implements ConfigData {
    public String test1 = "Test";

    @ConfigEntry.Gui.Section
    @ConfigEntry.Integer(min = 0, max = 100)
    public int test2 = 3;

    public boolean test3 = true;

    @ConfigEntry.Gui.Section
    public boolean test4 = false;

    public enum Location {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight
    }

    Location test5 = Location.BottomLeft;

    Location test6 = Location.TopLeft;

    Location test7 = Location.TopLeft;
}