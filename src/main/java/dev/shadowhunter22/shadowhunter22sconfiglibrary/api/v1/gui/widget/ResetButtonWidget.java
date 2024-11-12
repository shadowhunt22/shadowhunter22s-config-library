//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ResetButtonWidget extends PressableWidget {
	private static final Identifier TEXTURE = new Identifier(ShadowHunter22sConfigLibraryClient.MOD_ID, "textures/gui/reset_button.png");
	private final PressAction action;

	private final ConfigOption<?> option;

	protected ResetButtonWidget(int x, int y, int width, int height, ConfigOption<?> option, ResetButtonWidget.PressAction action) {
		super(x, y, width, height, Text.empty());

		this.option = option;
		this.action = action;
	}

	public static ResetButtonWidget.Builder builder(ConfigOption<?> option, ResetButtonWidget.PressAction action) {
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
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
		context.drawTexture(TEXTURE, this.getX(), this.getY(), this.getU(), this.getV(), 20, 20, 20, 60);
	}

	@Override
	public void onPress() {
		this.action.onPress(this);
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
	}

	public static class Builder {
		private final ConfigOption<?> option;
		private final ResetButtonWidget.PressAction action;

		private int x, y, width, height;

		public Builder(ConfigOption<?> option, ResetButtonWidget.PressAction action) {
			this.option = option;
			this.action = action;
		}

		public Builder dimensions(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;

			return this;
		}

		public ResetButtonWidget build() {
			return new ResetButtonWidget(this.x, this.y, this.width, this.height, this.option, this.action);
		}
	}

	@Environment(EnvType.CLIENT)
	public interface PressAction {
		void onPress(ResetButtonWidget button);
	}
}
