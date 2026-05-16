//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.test;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigRegistry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

import com.mojang.blaze3d.platform.InputConstants;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShadowHunter22sConfigLibraryTestMod implements ClientModInitializer {
	public static final String MOD_ID = "shadowhunter22s-config-library-testmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TestConfig test;
	public static final TestConfig2 test2;

	static {
		if (FabricLoader.getInstance().isModLoaded("shadowhunter22s-config-library")) {
			test = ConfigRegistry.register(TestConfig.class).getConfig();
			test2 = ConfigRegistry.register(TestConfig2.class).getConfig();
		} else {
			test = new TestConfig();
			test2 = new TestConfig2();
		}
	}

	KeyMapping keyMapping = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key." + MOD_ID + ".open.menu",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_RIGHT_SHIFT,
			KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "mod"))
	));

	@Override
	public void onInitializeClient() {
		new TestConfigMigration().migrate();

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (this.keyMapping.consumeClick()) {
				// client.setScreen(ConfigRegistry.getConfigScreen(TestConfig.class, client.screen).get());
				client.setScreen(ConfigRegistry.getConfigScreen(TestConfig2.class, client.screen).get());
				// client.setScreen(new TestConfig2Screen(ConfigRegistry.getConfigManager(TestConfig2.class), client.screen));
			}
		});

		LOGGER.info("Successfully loaded {}!", MOD_ID);
	}
}
