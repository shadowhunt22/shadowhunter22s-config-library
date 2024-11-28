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

public abstract class AbstractConfigScreen extends Screen {
	protected final Screen parent;
	protected final AutoConfigManager<? extends ConfigData> manager;

	protected <T extends ConfigData> AbstractConfigScreen(AutoConfigManager<T> manager, Screen parent) {
		super(Text.translatable(TranslationUtil.translationKey(manager.getDefinition(), "title")));

		this.manager = manager;
		this.parent = parent;
	}

	protected abstract void init();

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context);
		super.render(context, mouseX, mouseY, delta);

		// disable for now
		// context.drawText(this.textRenderer, this.title, this.width / 2 - (this.textRenderer.getWidth(this.title) / 2), 10, Colors.WHITE, true);
	}

	@Override
	public void renderBackground(DrawContext context) {
		context.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
	}

	@Override
	public void close() {
		this.manager.getConfig().afterScreenClose();
		this.client.setScreen(this.parent);
	}
}
