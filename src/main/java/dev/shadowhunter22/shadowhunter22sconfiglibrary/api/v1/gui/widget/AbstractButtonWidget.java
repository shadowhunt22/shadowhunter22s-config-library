//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;

public class AbstractButtonWidget extends PressableWidget {
	private final AbstractButtonWidget.PressAction action;

	public AbstractButtonWidget(int x, int y, int width, int height, Text text, AbstractButtonWidget.PressAction action) {
		super(x, y, width, height, text);
		this.action = action;
	}

	@Override
	public void onPress() {
		this.action.onPress(this);
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
	}

	public static abstract class Builder {
		protected final AbstractButtonWidget.PressAction action;
		protected int x, y, width, height;

		public Builder(AbstractButtonWidget.PressAction action) {
			this.action = action;
		}

		public Builder dimensions(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;

			return this;
		}

		public abstract AbstractButtonWidget build();
	}


	@Environment(EnvType.CLIENT)
	public interface PressAction {
		void onPress(AbstractButtonWidget button);
	}
}
