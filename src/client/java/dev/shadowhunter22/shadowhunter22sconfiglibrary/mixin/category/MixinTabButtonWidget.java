//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.category;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TabButtonWidget.class)
public abstract class MixinTabButtonWidget {
	@Unique private final MinecraftClient client = MinecraftClient.getInstance();

	@ModifyArg(
			method = "renderButton",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawNineSlicedTexture(Lnet/minecraft/util/Identifier;IIIIIIIIIIII)V"
			)
	)
	private Identifier changeButtonTexture(Identifier texture) {
		if (this.client.currentScreen instanceof AbstractConfigScreen) {
			return Identifier.of(ShadowHunter22sConfigLibraryClient.MOD_ID, "textures/gui/tabs.png");
		}

		return texture;
	}
}
