//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.function.Consumer;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.LayoutWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.Widget;

public class SimpleLayoutWidget implements LayoutWidget {
	private final SimplePositioningWidget body = new SimplePositioningWidget();
	private final Screen screen;

	public SimpleLayoutWidget(Screen screen) {
		this.screen = screen;
	}

	public <T extends Widget> void addBody(T widget) {
		this.body.add(widget);
	}

	@Override
	public void forEachElement(Consumer<Widget> consumer) {
		this.body.forEachElement(consumer);
	}

	@Override
	public void setX(int x) {
	}

	@Override
	public void setY(int y) {
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getWidth() {
		return this.screen.width;
	}

	@Override
	public int getHeight() {
		return this.screen.height;
	}
}
