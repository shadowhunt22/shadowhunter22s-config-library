//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibrary;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfigEntryWidget<E extends AbstractConfigEntryWidget.Entry<E>> extends ElementListWidget<E> {
	// part of the Easter egg.  hey you, no peeking at the code!
	public static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibrary.MOD_ID + "/Greeter");

	private final List<Character> chars;
	private int pressedCharCount = 0;

	public AbstractConfigEntryWidget(MinecraftClient client, int width, int height) {
		// need to do height - 54 because of Mojank (height - widget starting position)
		super(client, width, height - 54, 54, 27);

		List<Character> chars = Lists.newArrayList();

		if (client.player != null) {
			for (char chr : client.player.getName().getString().toCharArray()) {
				chars.add(chr);
			}
		}

		this.chars = chars;
	}

	protected @Nullable ClickableWidget getWidgetAtPosition(double mouseX, double mouseY) {
		E entry = this.getEntryAtPosition(mouseX, mouseY);

		if (entry != null) {
			for (ClickableWidget child : entry.children) {
				double childX = child.getX();
				double childWidth = child.getWidth();
				double childY = child.getY();
				double childHeight = child.getHeight();

				ClickableWidget widthAtPosition = mouseX >= childX && mouseX <= childX + childWidth && mouseY >= childY && mouseY <= childY + childHeight ? child : null;

				if (widthAtPosition != null) {
					return widthAtPosition;
				}
			}
		}

		return null;
	}

	@Override
	protected int getScrollbarX() {
		return this.width - 10;
	}

	@Override
	public int getRowWidth() {
		return this.width - 10;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (E child : this.children()) {
			child.setFocused(null);
		}

		for (E child : this.children()) {
			boolean clicked = child.mouseClicked(mouseX, mouseY, button);

			if (clicked) {
				this.setFocused(child);

				return true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		for (E child : this.children()) {
			boolean scrolled = child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

			if (scrolled) {
				return true;
			}
		}

		return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		for (E child : this.children()) {
			for (Element widget : child.children()) {
				if (widget instanceof SliderWidget && widget.isMouseOver(mouseX, mouseY)) {
					boolean dragged = widget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

					if (dragged) {
						return true;
					}
				}
			}
		}

		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for (E child : this.children()) {
			boolean released = child.mouseReleased(mouseX, mouseY, button);

			if (released) {
				return true;
			}
		}

		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		for (E child : this.children()) {
			boolean over = child.isMouseOver(mouseX, mouseY);

			if (over) {
				return true;
			}
		}

		return super.isMouseOver(mouseX, mouseY);
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		for (E child : this.children()) {
			child.mouseMoved(mouseX, mouseY);
		}
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		for (E child : this.children()) {
			boolean charTyped = child.charTyped(chr, modifiers);

			if (charTyped) {
				return true;
			}
		}

		if (this.client.player != null && !this.chars.isEmpty()) {
			if (this.chars.get(this.pressedCharCount) == chr) {
				this.pressedCharCount++;
			} else {
				this.pressedCharCount = 0;
			}

			if (this.chars.size() == this.pressedCharCount) {
				LOGGER.info("Hello, {}!", this.client.player.getName().getString());
				this.pressedCharCount = 0;
			}
		}

		return super.charTyped(chr, modifiers);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		for (E child : this.children()) {
			boolean keyPressed = child.keyPressed(keyCode, scanCode, modifiers);

			if (keyPressed) {
				return true;
			}
		}

		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		for (E child : this.children()) {
			boolean keyReleased = child.keyReleased(keyCode, scanCode, modifiers);

			if (keyReleased) {
				return true;
			}
		}

		return super.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	protected void drawMenuListBackground(DrawContext context) {
	}

	@Override
	protected void drawHeaderAndFooterSeparators(DrawContext context) {
	}

	public abstract static class Entry<E extends Entry<E>> extends ElementListWidget.Entry<E> {
		final AbstractEntry entry;
		final List<ClickableWidget> children = Lists.newArrayList();

		public Entry(AbstractEntry entry) {
			this.entry = entry;
			this.entry.getLayoutWidget().forEachChild(this.children::add);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			this.setFocused(null);

			for (Element child : this.children()) {
				boolean clicked = child.mouseClicked(mouseX, mouseY, button);

				if (clicked) {
					this.setFocused(child);

					return true;
				}
			}

			return super.mouseClicked(mouseX, mouseY, button);
		}

		@Override
		public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
			for (Element child : this.children()) {
				boolean scrolled = child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

				if (scrolled) {
					return true;
				}
			}

			return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
		}

		@Override
		public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
			for (Element child : this.children()) {
				boolean dragged = child.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

				if (dragged) {
					return true;
				}
			}

			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}

		@Override
		public boolean isMouseOver(double mouseX, double mouseY) {
			for (Element child : this.children()) {
				boolean over = child.isMouseOver(mouseX, mouseY);

				if (over) {
					return true;
				}
			}

			return super.isMouseOver(mouseX, mouseY);
		}

		@Override
		public void mouseMoved(double mouseX, double mouseY) {
			for (Element child : this.children()) {
				child.mouseMoved(mouseX, mouseY);
			}
		}

		@Override
		public boolean charTyped(char chr, int modifiers) {
			for (Element child : this.children()) {
				boolean charTyped = child.charTyped(chr, modifiers);

				if (charTyped) {
					return true;
				}
			}

			return super.charTyped(chr, modifiers);
		}

		@Override
		public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
			for (Element child : this.children()) {
				boolean keyReleased = child.keyReleased(keyCode, scanCode, modifiers);

				if (keyReleased) {
					return true;
				}
			}

			return super.keyReleased(keyCode, scanCode, modifiers);
		}

		@Override
		public List<? extends Selectable> selectableChildren() {
			return this.children;
		}

		@Override
		public List<? extends Element> children() {
			return this.children.stream()
					.filter(widget -> !(widget instanceof TextWidget))
					.filter(widget -> {
						if (widget instanceof AbstractButtonWidget buttonWidget) {
							return buttonWidget.active;
						}

						return true;
					})
					.toList();
		}
	}
}
