//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.function.Consumer;

import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;

public class ConfigEntryWidgetHolder<T extends AbstractConfigEntryWidget<?>> extends AbstractWidget {
	public final T entryWidget;

	public ConfigEntryWidgetHolder(T entryWidget) {
		super(0, 0, 100, 0, Component.empty());

		this.entryWidget = entryWidget;
	}

	@Override
	public void visitWidgets(Consumer<AbstractWidget> consumer) {
		for (AbstractConfigEntryWidget.Entry<?> child : this.entryWidget.children()) {
			for (AbstractWidget element : child.widgets) {
				consumer.accept(element);
			}
		}
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubled) {
		return this.entryWidget.mouseClicked(mouseButtonEvent, doubled);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		return this.entryWidget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	}

	@Override
	public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double offsetX, double offsetY) {
		return this.entryWidget.mouseDragged(mouseButtonEvent, offsetX, offsetY);
	}

	@Override
	public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
		return this.entryWidget.mouseReleased(mouseButtonEvent);
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
	public boolean charTyped(CharacterEvent characterEvent) {
		return this.entryWidget.charTyped(characterEvent);
	}

	@Override
	public boolean keyPressed(KeyEvent keyEvent) {
		return this.entryWidget.keyPressed(keyEvent);
	}

	@Override
	public boolean keyReleased(KeyEvent keyEvent) {
		return this.entryWidget.keyReleased(keyEvent);
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.entryWidget.render(graphics, mouseX, mouseY, delta);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
	}

	@Override
	public @Nullable ComponentPath nextFocusPath(FocusNavigationEvent navigationEvent) {
		return this.entryWidget.nextFocusPath(navigationEvent);
	}

	@Override
	public @Nullable ComponentPath getCurrentFocusPath() {
		return this.entryWidget.getCurrentFocusPath();
	}
}
