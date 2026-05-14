//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
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

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import org.apache.commons.compress.utils.Lists;

public abstract class AbstractConfigScreen extends Screen {
	protected final Screen parent;
	protected final AutoConfigManager<? extends ConfigData> manager;

	private final List<ConfigCategory> categories = new ArrayList<>();
	protected boolean renderingCategories = false;

	protected <T extends ConfigData> AbstractConfigScreen(AutoConfigManager<T> manager, Screen parent) {
		super(Text.translatable(TranslationUtil.translationKey("screen.title", manager.getDefinition())));

		this.manager = manager;
		this.parent = parent;
	}

	protected abstract void init();

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		context.drawText(this.textRenderer, this.title, this.width / 2 - (this.textRenderer.getWidth(this.title) / 2), this.renderingCategories ? 37 : 10, Colors.WHITE, true);
	}

	@Override
	protected void clearAndInit() {
		this.categories.clear(); // re-initialize all categories, not add more to them in Screen#init!
		super.clearAndInit();
	}

	@Override
	public void close() {
		this.manager.getConfig().afterScreenClose();
		this.client.setScreen(this.parent);
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
			this.categories.add(ConfigCategory.create(this.manager, this.client, entry.getKey()));
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
	 * Builds, initializes, selects the first tab, and adds a {@link TabNavigationWidget} as a drawable child.  If only one category has been created,
	 * then a {@link ConfigEntryWidget} will be added and not a {@link TabNavigationWidget}.
	 */
	public void addTabWidget() {
		Tab[] tabs = this.getTabs();

		if (tabs.length == 0) {
			return;
		}

		if (tabs.length == 1) {
			CategoryTab tab = ((CategoryTab) tabs[0]);
			this.addDrawableChild(tab.getEntryWidget());
		} else {
			TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);

			TabNavigationWidget widget = TabNavigationWidget.builder(tabManager, this.width)
					.tabs(tabs)
					.build();

			this.addDrawableChild(widget);

			widget.selectTab(0, false);
			widget.init();

			this.renderingCategories = true;
		}
	}
}
