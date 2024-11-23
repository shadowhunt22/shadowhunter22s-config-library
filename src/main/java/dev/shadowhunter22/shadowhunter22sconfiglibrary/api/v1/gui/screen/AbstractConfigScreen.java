//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.util.TranslationUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

public abstract class AbstractConfigScreen extends Screen {
	protected final Screen parent;
	protected final AutoConfigManager<? extends ConfigData> configManager;

	protected <T extends ConfigData> AbstractConfigScreen(AutoConfigManager<T> configManager, Screen parent) {
		super(Text.translatable(TranslationUtil.translationKey("text", configManager.getDefinition(), "title")));

		this.configManager = configManager;
		this.parent = parent;
	}

	protected abstract void init();

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		context.drawText(this.textRenderer, this.title, this.width / 2 - (this.textRenderer.getWidth(this.title) / 2), 10, Colors.WHITE, true);
	}

	@Override
	public void close() {
		this.client.setScreen(this.parent);
	}
}
