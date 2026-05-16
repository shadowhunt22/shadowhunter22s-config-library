//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.Screen;

import org.apache.commons.compress.utils.Lists;

public class SimpleLayoutWidget implements Layout {
	public final List<AbstractWidget> widets = Lists.newArrayList();
	private final FrameLayout body = new FrameLayout();
	private final Screen screen;

	public SimpleLayoutWidget(Screen screen) {
		this.screen = screen;
	}

	public <T extends AbstractWidget> void addBody(T widget) {
		this.body.addChild(widget);
		this.widets.add(widget);
	}

	@Override
	public void visitChildren(Consumer<LayoutElement> consumer) {
		this.body.visitChildren(consumer);
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public void setX(int x) {
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public void setY(int y) {
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
