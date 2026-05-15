//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.function.Consumer;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
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
	public boolean mouseClicked(Click click, boolean doubled) {
		return this.entryWidget.mouseClicked(click, doubled);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		return this.entryWidget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	}

	@Override
	public boolean mouseDragged(Click click, double offsetX, double offsetY) {
		return this.entryWidget.mouseDragged(click, offsetX, offsetY);
	}

	@Override
	public boolean mouseReleased(Click click) {
		return this.entryWidget.mouseReleased(click);
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
	public boolean charTyped(CharInput input) {
		return this.entryWidget.charTyped(input);
	}

	@Override
	public boolean keyPressed(KeyInput input) {
		return this.entryWidget.keyPressed(input);
	}

	@Override
	public boolean keyReleased(KeyInput input) {
		return this.entryWidget.keyReleased(input);
	}

	@Override
	public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
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
