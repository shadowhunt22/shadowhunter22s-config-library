//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import java.util.ArrayList;
import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category.CategoryTab;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category.ConfigCategory;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

import org.apache.commons.compress.utils.Lists;

public abstract class AbstractConfigScreen extends Screen {
	protected final Screen parent;
	protected final AutoConfigManager<? extends ConfigData> manager;

	private final List<ConfigCategory> categories = new ArrayList<>();
	protected boolean renderingCategories = false;

	protected <T extends ConfigData> AbstractConfigScreen(AutoConfigManager<T> manager, Screen parent) {
		super(Component.translatable(TranslationUtil.translationKey("screen.title", manager.getDefinition())));

		this.manager = manager;
		this.parent = parent;
	}

	protected abstract void init();

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractRenderState(graphics, mouseX, mouseY, delta);

		graphics.text(this.font, this.title, this.width / 2 - (this.font.width(this.title) / 2), this.renderingCategories ? 37 : 10, CommonColors.WHITE, true);
	}

	@Override
	protected void rebuildWidgets() {
		this.categories.clear(); // re-initialize all categories, not add more to them in Screen#init!
		super.rebuildWidgets();
	}

	@Override
	public void onClose() {
		this.manager.getConfig().afterScreenClose();
		this.minecraft.setScreen(this.parent);
	}

	/**
	 * Add an entry to an existing or new category.
	 *
	 * @param entry             an {@link AbstractEntry}.
	 * @param createNewCategory a boolean to specify whether to place the entry and subsequent entries into a new category.
	 * @return {@link AbstractConfigScreen}
	 */
	public AbstractConfigScreen addToOrCreateCategory(AbstractEntry entry, boolean createNewCategory) {
		if (createNewCategory) {
			this.categories.add(ConfigCategory.create(this.manager, this.minecraft, entry.getKey()));
		}

		if (!this.categories.isEmpty()) {
			ConfigCategory category = this.categories.get(this.categories.size() - 1);
			this.categories.set(this.categories.size() - 1, category.addEntry(entry));
		}

		return this;
	}

	/**
	 * Add an entry to an existing category.
	 *
	 * @param entry an {@link AbstractEntry}.
	 * @return {@link AbstractConfigScreen}
	 */
	public AbstractConfigScreen addToOrCreateCategory(AbstractEntry entry) {
		this.addToOrCreateCategory(entry, false);
		return this;
	}

	protected Tab[] getTabs() {
		List<Tab> tabs = Lists.newArrayList();

		for (ConfigCategory category : this.categories) {
			tabs.add(category.getTab());
		}

		return tabs.toArray(new Tab[0]);
	}

	/**
	 * Builds, initializes, selects the first tab, and adds a {@link TabNavigationBar} as a drawable child.  If only one category has been created,
	 * then a {@link ConfigEntryWidget} will be added and not a {@link TabNavigationBar}.
	 */
	public void addTabWidget() {
		Tab[] tabs = this.getTabs();

		if (tabs.length == 0) {
			return;
		}

		if (tabs.length == 1) {
			CategoryTab tab = ((CategoryTab) tabs[0]);
			this.addRenderableWidget(tab.getEntryWidget());
		} else {
			TabManager tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);

			TabNavigationBar widget = TabNavigationBar.builder(tabManager, this.width)
					.addTabs(tabs)
					.build();

			this.addRenderableWidget(widget);

			widget.selectTab(0, false);
			widget.arrangeElements();

			this.renderingCategories = true;
		}
	}
}
