//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.util;

public class TranslationUtil {
    public static String translationKey(String definition, String type) {
        return String.format("text.%s.%s", definition, type);
    }

    public static String translationKey(String definition, String type, String key) {
        return String.format("text.%s.%s.%s", definition, type, key);
    }

    public static String translationKey(String definition, String type, String key, String value) {
        return String.format("text.%s.%s.%s.%s", definition, type, key, value);
    }
}