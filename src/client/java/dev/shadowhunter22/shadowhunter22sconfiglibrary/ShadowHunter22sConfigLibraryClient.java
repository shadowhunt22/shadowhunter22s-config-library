//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary;

import net.fabricmc.api.ClientModInitializer;

public class ShadowHunter22sConfigLibraryClient implements ClientModInitializer {
	public static final String MOD_ID = ShadowHunter22sConfigLibrary.MOD_ID;

	@Override
	public void onInitializeClient() {
		ShadowHunter22sConfigLibrary.LOGGER.info("Successfully loaded {}!", MOD_ID);
	}
}
