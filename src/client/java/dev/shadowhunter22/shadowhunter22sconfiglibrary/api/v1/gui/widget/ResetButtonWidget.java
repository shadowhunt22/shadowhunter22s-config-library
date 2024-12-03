//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ResetButtonWidget extends AbstractButtonWidget {
	private static final Identifier TEXTURE = Identifier.of(ShadowHunter22sConfigLibraryClient.MOD_ID, "textures/gui/reset_button.png");
	private final AbstractButtonWidget.PressAction action;

	private final ConfigOption<?> option;

	protected ResetButtonWidget(int x, int y, int width, int height, ConfigOption<?> option, AbstractButtonWidget.PressAction action) {
		super(x, y, width, height, Text.empty(), action);

		this.option = option;
		this.action = action;
	}

	public static ResetButtonWidget.Builder builder(ConfigOption<?> option, AbstractButtonWidget.PressAction action) {
		return new ResetButtonWidget.Builder(option, action);
	}

	private int getU() {
		return 0;
	}

	private int getV() {
		if (this.option.getValue() == this.option.getDefaultValue()) {
			return 0;
		} else {
			if (this.isSelected()) {
				return 40;
			}

			return this.active ? 20 : 40;
		}
	}

	@Override
	public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, this.getX(), this.getY(), this.getU(), this.getV(), 20, 20, 20, 60);
	}

	public static class Builder extends AbstractButtonWidget.Builder {
		private final ConfigOption<?> option;
		private final AbstractButtonWidget.PressAction action;

		public Builder(ConfigOption<?> option, AbstractButtonWidget.PressAction action) {
			super(action);

			this.option = option;
			this.action = action;
		}

		@Override
		public ResetButtonWidget build() {
			return new ResetButtonWidget(this.x, this.y, this.width, this.height, this.option, this.action);
		}
	}
}
