//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ResetButtonWidget;
import net.minecraft.text.Text;

public abstract class ButtonEntry extends AbstractEntry {
	private final Text text;
	private final ResetButtonWidget.PressAction action;

	public ButtonEntry(Text text, ResetButtonWidget.PressAction action) {
		this.text = text;
		this.action = action;
	}

	@Override
	protected Text translatableText(String text) {
		return this.text;
	}
}
