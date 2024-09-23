//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.test;import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.Config;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShadowHunter22sConfigLibraryTestMod implements ClientModInitializer {
	public static final String MOD_ID = "shadowhunter22s-config-library-testmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
        Config.register(TestConfig.class).save();

        LOGGER.info("Successfully loaded {}!", MOD_ID);
	}
}