//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.function.Consumer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import org.jetbrains.annotations.Nullable;

public class ConfigEntryWidgetHolder<T extends AbstractConfigEntryWidget<?>> extends ClickableWidget {
	public final T list;

	public ConfigEntryWidgetHolder(T list) {
		super(0, 0, 100, 0, Text.empty());

		this.list = list;
	}

	@Override
	public void forEachChild(Consumer<ClickableWidget> consumer) {
		for (AbstractConfigEntryWidget.Entry<?> child : this.list.children()) {
			for (Element element : child.children()) {
				consumer.accept((ClickableWidget) element);
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.list.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		return this.list.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return this.list.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return this.list.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return this.list.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		this.list.mouseMoved(mouseX, mouseY);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		return this.list.charTyped(chr, modifiers);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return this.list.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return this.list.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
		this.list.render(context, mouseX, mouseY, delta);
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
	}

	@Override
	public void setFocused(boolean focused) {
		this.list.setFocused(focused);
	}

	@Override
	public @Nullable GuiNavigationPath getNavigationPath(GuiNavigation navigation) {
		return this.list.getNavigationPath(navigation);
	}

	@Override
	public @Nullable GuiNavigationPath getFocusedPath() {
		return this.list.getFocusedPath();
	}
}
