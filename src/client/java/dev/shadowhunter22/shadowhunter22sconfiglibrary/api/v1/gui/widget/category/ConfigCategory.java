//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigCategory {
	protected final AutoConfigManager<? extends ConfigData> manager;
	protected final ConfigEntryWidget configEntryWidget;

	private final Text categoryName;

	public <T extends ConfigData> ConfigCategory(AutoConfigManager<T> manager, Screen parent, Text categoryName) {
		this.manager = manager;
		this.configEntryWidget = new ConfigEntryWidget(manager, MinecraftClient.getInstance(), parent.width, parent.height);

		this.categoryName = categoryName;
	}

	/**
	 * Create a new category for the config screen. Using this static method, instead of declaring {@code new ConfigCategory(...)},
	 * automatically creates a translation key to be used as the title of the category.
	 *
	 * @param manager the config manager of the config
	 * @param client  an instance of the Minecraft Client
	 * @param key     the key of the entry that will be used as the title of the category
	 * @param <T>     ConfigData
	 * @return
	 */
	public static <T extends ConfigData> ConfigCategory create(AutoConfigManager<T> manager, MinecraftClient client, String key) {
		return new ConfigCategory(
				manager,
				client.currentScreen,
				Text.translatable(TranslationUtil.translationKey("text", manager.getDefinition(), key, "@Category"))
		);
	}

	/**
	 * Add a new entry to the category.
	 *
	 * @param entry the config option entry.
	 * @return a ConfigCategory with the added entry.
	 */
	public ConfigCategory addEntry(AbstractEntry entry) {
		this.configEntryWidget.add(entry);
		return this;
	}

	/**
	 * Retrieve the tab this category is associated with.
	 * @return {@link CategoryTab}
	 */
	public CategoryTab getTab() {
		return new CategoryTab(this);
	}

	public Text getCategoryName() {
		return this.categoryName;
	}
}
