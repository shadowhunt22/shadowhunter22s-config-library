//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import java.util.ArrayList;
import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
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
import net.minecraft.util.Identifier;

import com.mojang.blaze3d.systems.RenderSystem;

public abstract class AbstractConfigScreen extends Screen {
	private static final Identifier BLUR_BACKGROUND_TEXTURE = Identifier.of("textures/gui/menu_list_background.png");

	protected final Screen parent;
	protected final AutoConfigManager<? extends ConfigData> manager;
	private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);
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

		boolean renderingCategories = this.categories.size() > 1 || this.renderingCategories;

		context.drawText(this.textRenderer, this.title, this.width / 2 - (this.textRenderer.getWidth(this.title) / 2), renderingCategories ? 37 : 10, Colors.WHITE, true);
	}

	@Override
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
		RenderSystem.enableBlend();
		context.drawTexture(BLUR_BACKGROUND_TEXTURE, 0, 0, 0, 0, this.width, this.height, 32, 32);
		RenderSystem.disableBlend();
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

	public AbstractConfigScreen addToOrCreateCategory(AbstractEntry entry, boolean createNewCategory) {
		if (createNewCategory) {
			this.categories.add(
					new ConfigCategory(
							this.manager,
							this,
							Text.translatable(
									TranslationUtil.translationKey("text", this.manager.getDefinition(), entry.getKey(), "@Category")
							)
					)
			);
		}

		if (!this.categories.isEmpty()) {
			ConfigCategory category = this.categories.get(this.categories.size() - 1);
			this.categories.set(this.categories.size() - 1, category.add(entry));
		}

		return this;
	}

	public AbstractConfigScreen addToOrCreateCategory(AbstractEntry entry) {
		this.addToOrCreateCategory(entry, false);
		return this;
	}

	public Tab[] getTabs() {
		List<Tab> tabs = new ArrayList<>();

		for (ConfigCategory category : this.categories) {
			tabs.add(category.getTab());
		}

		return tabs.toArray(new Tab[0]);
	}

	/**
	 * Builds, initializes, selects the first tab, and adds a {@link TabNavigationWidget} as a drawable child.
	 */
	public void addTabWidget() {
		TabNavigationWidget widget = TabNavigationWidget.builder(this.tabManager, this.width)
				.tabs(this.getTabs())
				.build();

		this.addDrawableChild(widget);

		widget.selectTab(0, false);
		widget.init();
	}
}
