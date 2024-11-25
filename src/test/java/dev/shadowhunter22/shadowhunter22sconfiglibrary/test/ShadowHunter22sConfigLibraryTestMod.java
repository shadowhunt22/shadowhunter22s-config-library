//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.test;import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
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

	public static final TestConfig test;
	public static final TestConfig2 test2;

	@Override
	public void onInitializeClient() {
		new TestConfigMigration().migrate();

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (this.keyBinding.wasPressed()) {
				client.setScreen(Config.getConfigScreen(TestConfig2.class, client.currentScreen).get());
			}
		});

        LOGGER.info("Successfully loaded {}!", MOD_ID);
	}

	static {
		if (FabricLoader.getInstance().isModLoaded("shadowhunter22s-config-library")) {
			test = Config.register(TestConfig.class).getConfig();
			test2 = Config.register(TestConfig2.class).getConfig();
		} else {
			test = new TestConfig();
			test2 = new TestConfig2();
		}
	}
}