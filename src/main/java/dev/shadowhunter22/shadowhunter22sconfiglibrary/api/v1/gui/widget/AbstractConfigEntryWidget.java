//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.SliderWidget;

import org.jetbrains.annotations.Nullable;

public abstract class AbstractConfigEntryWidget<E extends AbstractConfigEntryWidget.Entry<E>> extends ElementListWidget<E> {
	public AbstractConfigEntryWidget(MinecraftClient client, int width, int height) {
		super(client, width, height, 54, height, 27);

		this.setRenderBackground(false);
		this.setRenderHorizontalShadows(false);
	}

	protected @Nullable ClickableWidget getWidgetAtPosition(double x, double y) {
		E entry = this.getEntryAtPosition(x, y);

		if (entry != null) {
			for (ClickableWidget child : entry.entry.getListWidget().children) {
				double childX = child.getX();
				double childWidth = child.getWidth();
				double childY = child.getY();
				double childHeight = child.getHeight();

				ClickableWidget widthAtPosition = x >= childX && x <= childX + childWidth && y >= childY && y <= childY + childHeight ? child : null;

				if (widthAtPosition != null) {
					return widthAtPosition;
				}
			}
		}

		return null;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (E child : this.children()) {
			boolean clicked = child.mouseClicked(mouseX, mouseY, button);

			if (clicked) {
				return true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		for (E child : this.children()) {
			boolean scrolled = child.mouseScrolled(mouseX, mouseY, amount);

			if (scrolled) {
				return true;
			}
		}

		return super.mouseScrolled(mouseX, mouseY, amount);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		for (E child : this.children()) {
			for (ClickableWidget widget : child.entry.getListWidget().children) {
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
	protected int getScrollbarPositionX() {
		return this.width - 10;
	}

	@Override
	public int getRowWidth() {
		return this.width - 10;
	}

	public abstract static class Entry<E extends Entry<E>> extends ElementListWidget.Entry<E> {
		final AbstractEntry entry;

		public Entry(AbstractEntry entry) {
			this.entry = entry;
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean clicked = child.mouseClicked(mouseX, mouseY, button);

				if (clicked) {
					return true;
				}
			}

			return super.mouseClicked(mouseX, mouseY, button);
		}

		@Override
		public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean scrolled = child.mouseScrolled(mouseX, mouseY, amount);

				if (scrolled) {
					return true;
				}
			}

			return super.mouseScrolled(mouseX, mouseY, amount);
		}

		@Override
		public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean dragged = child.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

				if (dragged) {
					return true;
				}
			}

			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}

		@Override
		public boolean mouseReleased(double mouseX, double mouseY, int button) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean released = child.mouseReleased(mouseX, mouseY, button);

				if (released) {
					return true;
				}
			}

			return super.mouseReleased(mouseX, mouseY, button);
		}

		@Override
		public boolean isMouseOver(double mouseX, double mouseY) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean over = child.isMouseOver(mouseX, mouseY);

				if (over) {
					return true;
				}
			}

			return super.isMouseOver(mouseX, mouseY);
		}

		@Override
		public void mouseMoved(double mouseX, double mouseY) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				child.mouseMoved(mouseX, mouseY);
			}
		}

		@Override
		public boolean charTyped(char chr, int modifiers) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean charTyped = child.charTyped(chr, modifiers);

				if (charTyped) {
					return true;
				}
			}

			return super.charTyped(chr, modifiers);
		}

		@Override
		public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean keyPressed = child.keyPressed(keyCode, scanCode, modifiers);

				if (keyPressed) {
					return true;
				}
			}

			return super.keyPressed(keyCode, scanCode, modifiers);
		}

		@Override
		public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
			for (ClickableWidget child : this.entry.getListWidget().children) {
				boolean keyReleased = child.keyReleased(keyCode, scanCode, modifiers);

				if (keyReleased) {
					return true;
				}
			}

			return super.keyReleased(keyCode, scanCode, modifiers);
		}

		@Override
		public List<? extends Selectable> selectableChildren() {
			return List.of(this.entry);
		}

		@Override
		public List<? extends Element> children() {
			return List.of(this.entry);
		}
	}
}
