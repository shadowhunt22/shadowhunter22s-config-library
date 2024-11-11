//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.test;import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShadowHunter22sConfigLibraryTestMod implements ClientModInitializer {
	public static final String MOD_ID = "shadowhunter22s-config-library-testmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key." + MOD_ID + ".open.menu",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_RIGHT_SHIFT,
			"category."  + MOD_ID +  ".mod"
	));

	public static final TestConfig test = Config.register(TestConfig.class).getConfig();

	@Override
	public void onInitializeClient() {
		Config.getConfigManager(TestConfig.class).getSerializer();

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (keyBinding.wasPressed()) {
				client.setScreen(Config.getConfigScreen(TestConfig.class, client.currentScreen).get());
			}
		});

        LOGGER.info("Successfully loaded {}!", MOD_ID);
	}
}