//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigCategory {
	protected final AutoConfigManager<? extends ConfigData> manager;
	protected final ConfigEntryWidget configEntryWidget;

	private final Component categoryName;

	public <T extends ConfigData> ConfigCategory(AutoConfigManager<T> manager, Screen parent, Component categoryName) {
		this.manager = manager;
		this.configEntryWidget = new ConfigEntryWidget(manager, Minecraft.getInstance(), parent.width, parent.height);

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
	public static <T extends ConfigData> ConfigCategory create(AutoConfigManager<T> manager, Minecraft client, String key) {
		return new ConfigCategory(
				manager,
				client.gui.screen(),
				Component.translatable(TranslationUtil.translationKey("text", manager.getDefinition(), key, "@Category"))
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
	 *
	 * @return {@link CategoryTab}
	 */
	public CategoryTab getTab() {
		return new CategoryTab(this);
	}

	public Component getCategoryName() {
		return this.categoryName;
	}
}
