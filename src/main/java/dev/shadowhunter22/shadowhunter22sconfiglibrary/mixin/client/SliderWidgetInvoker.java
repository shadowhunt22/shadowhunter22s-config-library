//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.mixin.client;

import net.minecraft.client.gui.widget.SliderWidget;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SliderWidget.class)
public interface SliderWidgetInvoker {
	@Invoker("setValue")
	void invokeSetValue(double value);
}
