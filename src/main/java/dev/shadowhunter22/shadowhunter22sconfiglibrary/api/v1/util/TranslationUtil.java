//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.util;

import org.jetbrains.annotations.ApiStatus;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;

@ApiStatus.Internal
public class TranslationUtil {
    // TODO remove "ShadowHunter22sConfigLibraryClient.MOD_ID" from translation key

    public static String translationKey(String type, String id) {
        return type + "." + ShadowHunter22sConfigLibraryClient.MOD_ID + "." + id;
    }

    public static String translationKey(String type, String id, String value) {
        return type + "." + ShadowHunter22sConfigLibraryClient.MOD_ID + "." + id + "." + value;
    }
}