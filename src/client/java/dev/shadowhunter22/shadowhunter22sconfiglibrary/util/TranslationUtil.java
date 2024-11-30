//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.util;

public class TranslationUtil {
	public static String translationKey(String type, String definition) {
		return String.format("%s.%s", type, definition);
	}

	public static String translationKey(String type, String definition, String key) {
		return String.format("%s.%s.%s", type, definition, key);
	}

	public static String translationKey(String type, String definition, String key, String value) {
		return String.format("%s.%s.%s.%s", type, definition, key, value);
	}
}
