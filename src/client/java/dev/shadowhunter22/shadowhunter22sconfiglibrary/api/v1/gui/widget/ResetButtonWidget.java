//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.DoubleConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.type.FloatConfigOption;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ResetButtonWidget extends AbstractButtonWidget {
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ShadowHunter22sConfigLibraryClient.MOD_ID, "textures/gui/reset_button.png");
	private final AbstractButtonWidget.PressAction action;

	private final ConfigOption<?> option;

	protected ResetButtonWidget(int x, int y, int width, int height, ConfigOption<?> option, AbstractButtonWidget.PressAction action) {
		super(x, y, width, height, Component.empty(), action);

		this.option = option;
		this.action = action;

		if (this.option instanceof FloatConfigOption<?> floatConfigOption) {
			float epsilon = 0.00001f;
			this.active = Math.abs(floatConfigOption.getValue() - floatConfigOption.getDefaultValue()) > epsilon;
		} else if (this.option instanceof DoubleConfigOption<?> doubleConfigOption) {
			float epsilon = 0.00001f;
			this.active = Math.abs(doubleConfigOption.getValue() - doubleConfigOption.getDefaultValue()) > epsilon;
		} else {
			this.active = option.getValue() != option.getDefaultValue();
		}
	}

	public static ResetButtonWidget.Builder builder(ConfigOption<?> option, AbstractButtonWidget.PressAction action) {
		return new ResetButtonWidget.Builder(option, action);
	}

	private int getU() {
		return 0;
	}

	private int getV() {
		if (this.option instanceof FloatConfigOption<?> floatConfigOption) {
			float epsilon = 0.00001f;

			if (Math.abs(floatConfigOption.getValue() - floatConfigOption.getDefaultValue()) < epsilon) {
				return 0;
			}
		} else if (this.option instanceof DoubleConfigOption<?> doubleConfigOption) {
			float epsilon = 0.00001f;

			if (Math.abs(doubleConfigOption.getValue() - doubleConfigOption.getDefaultValue()) < epsilon) {
				return 0;
			}
		}

		if (this.option.getValue() == this.option.getDefaultValue()) {
			return 0;
		} else {
			if (this.isHoveredOrFocused()) {
				return 40;
			}
		}

		return this.active ? 20 : 40;
	}

	@Override
	protected void renderContents(GuiGraphics graphics, int mouseX, int mouseY, float deltaTicks) {
		graphics.blit(
				RenderPipelines.GUI_TEXTURED,
				TEXTURE,
				this.getX(),
				this.getY(),
				this.getU(),
				this.getV(),
				20,
				20,
				20,
				60
		);
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
