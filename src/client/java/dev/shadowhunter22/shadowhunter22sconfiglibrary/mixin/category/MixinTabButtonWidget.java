//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.category;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TabButtonWidget.class)
public abstract class MixinTabButtonWidget extends ClickableWidget {
	public MixinTabButtonWidget(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	@Shadow
	public abstract boolean isCurrentTab();

	@Unique
	private static final ButtonTextures TRANSPARENT_TAB_BUTTON_TEXTURES = new ButtonTextures(
			new Identifier(ShadowHunter22sConfigLibraryClient.MOD_ID, "widget/tab_selected"),
			new Identifier(ShadowHunter22sConfigLibraryClient.MOD_ID, "widget/tab"),
			new Identifier(ShadowHunter22sConfigLibraryClient.MOD_ID, "widget/tab_selected_highlighted"),
			new Identifier(ShadowHunter22sConfigLibraryClient.MOD_ID, "widget/tab_highlighted")
	);

	@Unique
	private final MinecraftClient client = MinecraftClient.getInstance();

	@ModifyArg(
			method = "renderButton",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
			)
	)
	private Identifier changeButtonTexture(Identifier texture) {
		if (this.client.currentScreen instanceof AbstractConfigScreen) {
			return TRANSPARENT_TAB_BUTTON_TEXTURES.get(this.isCurrentTab(), this.isHovered());
		}

		return texture;
	}
}
