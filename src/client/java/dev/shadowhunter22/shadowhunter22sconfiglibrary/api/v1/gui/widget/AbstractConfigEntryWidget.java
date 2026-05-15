//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibrary;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

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
	public boolean mouseClicked(Click click, boolean doubled) {
		for (E child : this.children()) {
			child.setFocused(null);
		}

		for (E child : this.children()) {
			boolean clicked = child.mouseClicked(click, doubled);

			if (clicked) {
				this.setFocused(child);

				return true;
			}
		}

		return super.mouseClicked(click, doubled);
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
	public boolean mouseDragged(Click click, double offsetX, double offsetY) {
		for (E child : this.children()) {
			for (Element widget : child.children()) {
				if (widget instanceof SliderWidget && widget.isMouseOver(click.x(), click.y())) {
					boolean dragged = widget.mouseDragged(click, offsetX, offsetY);

					if (dragged) {
						return true;
					}
				}
			}
		}

		return super.mouseDragged(click, offsetX, offsetY);
	}

	@Override
	public boolean mouseReleased(Click click) {
		for (E child : this.children()) {
			boolean released = child.mouseReleased(click);

			if (released) {
				return true;
			}
		}

		return super.mouseReleased(click);
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
	public boolean charTyped(CharInput input) {
		for (E child : this.children()) {
			boolean charTyped = child.charTyped(input);

			if (charTyped) {
				return true;
			}
		}

		if (this.client.player != null && !this.chars.isEmpty()) {
			if (this.chars.get(this.pressedCharCount) == input.asString().charAt(0)) {
				this.pressedCharCount++;
			} else {
				this.pressedCharCount = 0;
			}

			if (this.chars.size() == this.pressedCharCount) {
				LOGGER.info("Hello, {}!", this.client.player.getName().getString());
				this.pressedCharCount = 0;
			}
		}

		return super.charTyped(input);
	}

	@Override
	public boolean keyPressed(KeyInput input) {
		for (E child : this.children()) {
			boolean keyPressed = child.keyPressed(input);

			if (keyPressed) {
				return true;
			}
		}

		return super.keyPressed(input);
	}

	@Override
	public boolean keyReleased(KeyInput input) {
		for (E child : this.children()) {
			boolean keyReleased = child.keyReleased(input);

			if (keyReleased) {
				return true;
			}
		}

		return super.keyReleased(input);
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
		public boolean mouseClicked(Click click, boolean doubled) {
			this.setFocused(null);

			for (Element child : this.children()) {
				boolean clicked = child.mouseClicked(click, doubled);

				if (clicked) {
					this.setFocused(child);

					return true;
				}
			}

			return super.mouseClicked(click, doubled);
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
		public boolean mouseDragged(Click click, double offsetX, double offsetY) {
			for (Element child : this.children()) {
				boolean dragged = child.mouseDragged(click, offsetX, offsetY);

				if (dragged) {
					return true;
				}
			}

			return super.mouseDragged(click, offsetX, offsetY);
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
		public boolean charTyped(CharInput input) {
			for (Element child : this.children()) {
				boolean charTyped = child.charTyped(input);

				if (charTyped) {
					return true;
				}
			}

			return super.charTyped(input);
		}

		@Override
		public boolean keyReleased(KeyInput input) {
			for (Element child : this.children()) {
				boolean keyReleased = child.keyReleased(input);

				if (keyReleased) {
					return true;
				}
			}

			return super.keyReleased(input);
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
