//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibrary;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;

import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfigEntryWidget<E extends AbstractConfigEntryWidget.Entry<E>> extends ContainerObjectSelectionList<E> {
	// part of the Easter egg.  hey you, no peeking at the code!
	public static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibrary.MOD_ID + "/Greeter");

	private final List<Character> chars;
	private int pressedCharCount = 0;

	public AbstractConfigEntryWidget(Minecraft minecraft, int width, int height) {
		// need to do height - 54 because of Mojank (height - widget starting position)
		super(minecraft, width, height - 54, 54, 27);

		List<Character> chars = Lists.newArrayList();

		if (minecraft.player != null) {
			for (char chr : minecraft.player.getName().getString().toCharArray()) {
				chars.add(chr);
			}
		}

		this.chars = chars;
	}

	protected @Nullable AbstractWidget getWidgetAtPosition(double mouseX, double mouseY) {
		E entry = this.getEntryAtPosition(mouseX, mouseY);

		if (entry != null) {
			for (AbstractWidget child : entry.widgets) {
				double childX = child.getX();
				double childWidth = child.getWidth();
				double childY = child.getY();
				double childHeight = child.getHeight();

				AbstractWidget widthAtPosition = mouseX >= childX && mouseX <= childX + childWidth && mouseY >= childY && mouseY <= childY + childHeight ? child : null;

				if (widthAtPosition != null) {
					return widthAtPosition;
				}
			}
		}

		return null;
	}

	@Override
	protected int scrollBarX() {
		return this.width - 10;
	}

	@Override
	public int getRowWidth() {
		return this.width - 10;
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubled) {
		for (E child : this.children()) {
			child.setFocused(null);
		}

		for (E child : this.children()) {
			boolean clicked = child.mouseClicked(mouseButtonEvent, doubled);

			if (clicked) {
				this.setFocused(child);

				return true;
			}
		}

		return super.mouseClicked(mouseButtonEvent, doubled);
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
	public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double offsetX, double offsetY) {
		for (E child : this.children()) {
			for (GuiEventListener widget : child.children()) {
				if (widget instanceof AbstractSliderButton && widget.isMouseOver(mouseButtonEvent.x(), mouseButtonEvent.y())) {
					boolean dragged = widget.mouseDragged(mouseButtonEvent, offsetX, offsetY);

					if (dragged) {
						return true;
					}
				}
			}
		}

		return super.mouseDragged(mouseButtonEvent, offsetX, offsetY);
	}

	@Override
	public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
		for (E child : this.children()) {
			boolean released = child.mouseReleased(mouseButtonEvent);

			if (released) {
				return true;
			}
		}

		return super.mouseReleased(mouseButtonEvent);
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
	public boolean charTyped(CharacterEvent characterEvent) {
		for (E child : this.children()) {
			boolean charTyped = child.charTyped(characterEvent);

			if (charTyped) {
				return true;
			}
		}

		if (this.minecraft.player != null && !this.chars.isEmpty()) {
			if (this.chars.get(this.pressedCharCount) == characterEvent.codepointAsString().charAt(0)) {
				this.pressedCharCount++;
			} else {
				this.pressedCharCount = 0;
			}

			if (this.chars.size() == this.pressedCharCount) {
				LOGGER.info("Hello, {}!", this.minecraft.player.getName().getString());
				this.pressedCharCount = 0;
			}
		}

		return super.charTyped(characterEvent);
	}

	@Override
	public boolean keyPressed(KeyEvent keyEvent) {
		for (E child : this.children()) {
			boolean keyPressed = child.keyPressed(keyEvent);

			if (keyPressed) {
				return true;
			}
		}

		return super.keyPressed(keyEvent);
	}

	@Override
	public boolean keyReleased(KeyEvent keyEvent) {
		for (E child : this.children()) {
			boolean keyReleased = child.keyReleased(keyEvent);

			if (keyReleased) {
				return true;
			}
		}

		return super.keyReleased(keyEvent);
	}

	@Override
	protected void renderListBackground(GuiGraphics graphics) {
	}

	@Override
	protected void renderListSeparators(GuiGraphics graphics) {
	}

	public abstract static class Entry<E extends Entry<E>> extends ContainerObjectSelectionList.Entry<E> {
		final AbstractEntry entry;
		final List<AbstractWidget> widgets = Lists.newArrayList();

		public Entry(AbstractEntry entry) {
			this.entry = entry;
			this.entry.getLayoutWidget().visitWidgets(this.widgets::add);
		}

		@Override
		public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubled) {
			this.setFocused(null);

			for (GuiEventListener child : this.children()) {
				boolean clicked = child.mouseClicked(mouseButtonEvent, doubled);

				if (clicked) {
					this.setFocused(child);

					return true;
				}
			}

			return super.mouseClicked(mouseButtonEvent, doubled);
		}

		@Override
		public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
			for (GuiEventListener child : this.children()) {
				boolean scrolled = child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

				if (scrolled) {
					return true;
				}
			}

			return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
		}

		@Override
		public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double offsetX, double offsetY) {
			for (GuiEventListener child : this.children()) {
				boolean dragged = child.mouseDragged(mouseButtonEvent, offsetX, offsetY);

				if (dragged) {
					return true;
				}
			}

			return super.mouseDragged(mouseButtonEvent, offsetX, offsetY);
		}

		@Override
		public boolean isMouseOver(double mouseX, double mouseY) {
			for (GuiEventListener child : this.children()) {
				boolean over = child.isMouseOver(mouseX, mouseY);

				if (over) {
					return true;
				}
			}

			return super.isMouseOver(mouseX, mouseY);
		}

		@Override
		public void mouseMoved(double mouseX, double mouseY) {
			for (GuiEventListener child : this.children()) {
				child.mouseMoved(mouseX, mouseY);
			}
		}

		@Override
		public boolean charTyped(CharacterEvent characterEvent) {
			for (GuiEventListener child : this.children()) {
				boolean charTyped = child.charTyped(characterEvent);

				if (charTyped) {
					return true;
				}
			}

			return super.charTyped(characterEvent);
		}

		@Override
		public boolean keyReleased(KeyEvent keyEvent) {
			for (GuiEventListener child : this.children()) {
				boolean keyReleased = child.keyReleased(keyEvent);

				if (keyReleased) {
					return true;
				}
			}

			return super.keyReleased(keyEvent);
		}

		@Override
		public List<? extends NarratableEntry> narratables() {
			return this.widgets;
		}

		@Override
		public List<? extends GuiEventListener> children() {
			return this.widgets.stream()
					.filter(widget -> !(widget instanceof StringWidget))
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
