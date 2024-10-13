//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.client;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SliderWidget.class)
public interface SliderWidgetInvoker {
	@Accessor("TEXTURE")
	static Identifier getTexture() {
		throw new AssertionError();
	}

	@Invoker("getYImage")
	int invokeGetYImage();

	@Invoker("getTextureV")
	int invokeGetTextureV();
}
