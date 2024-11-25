//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.util;

public class TranslationUtil {
    public static String translationKey(String type, String name, String id) {
        return String.format("%s.%s.%s", type, name, id);
    }

    public static String translationKey(String type, String name, String id, String value) {
        return String.format("%s.%s.%s.%s", type, name, id, value);
    }
}