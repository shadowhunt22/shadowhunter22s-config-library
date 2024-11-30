//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.widget.ClickableWidget;

public class ListWidget {
	public final List<ClickableWidget> children = new ArrayList<>();

	public <T extends ClickableWidget> void addWidget(T widget) {
		this.children.add(widget);
	}
}
