//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.function.Consumer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import org.jetbrains.annotations.Nullable;

public class ConfigEntryWidgetHolder<T extends AbstractConfigEntryWidget<?>> extends ClickableWidget {
	public final T entryWidget;

	public ConfigEntryWidgetHolder(T entryWidget) {
		super(0, 0, 100, 0, Text.empty());

		this.entryWidget = entryWidget;
	}

	@Override
	public void forEachChild(Consumer<ClickableWidget> consumer) {
		for (AbstractConfigEntryWidget.Entry<?> child : this.entryWidget.children()) {
			for (ClickableWidget element : child.children) {
				consumer.accept(element);
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.entryWidget.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		return this.entryWidget.mouseScrolled(mouseX, mouseY, amount);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return this.entryWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return this.entryWidget.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return this.entryWidget.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		this.entryWidget.mouseMoved(mouseX, mouseY);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		return this.entryWidget.charTyped(chr, modifiers);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return this.entryWidget.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return this.entryWidget.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
		this.entryWidget.render(context, mouseX, mouseY, delta);
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
	}

	@Override
	public @Nullable GuiNavigationPath getNavigationPath(GuiNavigation navigation) {
		return this.entryWidget.getNavigationPath(navigation);
	}

	@Override
	public @Nullable GuiNavigationPath getFocusedPath() {
		return this.entryWidget.getFocusedPath();
	}
}
