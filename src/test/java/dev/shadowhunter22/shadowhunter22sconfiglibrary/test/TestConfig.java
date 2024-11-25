//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.test;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;

@Config(name = "config-library", file = "test")
public class TestConfig implements ConfigData {
    public String test1 = "Test";

    @ConfigEntry.Gui.Section
    @ConfigEntry.Integer(min = 0, max = 100)
    public int test2 = 3;

    public boolean test3 = true;

    @ConfigEntry.Gui.Section
    public boolean test4 = false;

    public enum Location {
        topLeft,
        topRight,
        bottomLeft,
        bottomRight
    }

    Location test5 = Location.topLeft;

    Location test6 = Location.topRight;

    Location test7 = Location.bottomLeft;
}