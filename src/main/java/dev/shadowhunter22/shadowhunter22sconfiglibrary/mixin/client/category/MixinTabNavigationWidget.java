//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.client.category;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TabNavigationWidget.class)
public class MixinTabNavigationWidget {
	@Shadow @Final private GridWidget grid;
	@Shadow @Final private ImmutableList<TabButtonWidget> tabButtons;
	@Shadow private int tabNavWidth;
	@Unique private final MinecraftClient client = MinecraftClient.getInstance();

	@WrapOperation(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"
			)
	)
	private void preventBlackBarRendering(DrawContext context, int x1, int y1, int x2, int y2, int color, Operation<Void> original) {
		if (!(this.client.currentScreen instanceof AbstractConfigScreen)) {
			original.call(context, x1, y1, x2, y2, color);
		}
	}

	@WrapOperation(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V"
			)
	)
	private void changeHeaderTexture(DrawContext context, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (this.client.currentScreen instanceof AbstractConfigScreen) {
			Identifier transparentTexture = Identifier.of(ShadowHunter22sConfigLibraryClient.MOD_ID, "textures/gui/header_separator.png");

			RenderSystem.enableBlend();

			context.drawTexture(transparentTexture, 0, this.grid.getY() + this.grid.getHeight() - 2, 0.0F, 0.0F, this.tabButtons.get(0).getX(), 2, 32, 2);

			int i = this.tabButtons.get(this.tabButtons.size() - 1).getX() + this.tabButtons.get(this.tabButtons.size() - 1).getWidth();
			context.drawTexture(transparentTexture, i, this.grid.getY() + this.grid.getHeight() - 2, 0.0F, 0.0F, this.tabNavWidth, 2, 32, 2);

			RenderSystem.disableBlend();
		} else {
			original.call(context, texture, x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}
}
