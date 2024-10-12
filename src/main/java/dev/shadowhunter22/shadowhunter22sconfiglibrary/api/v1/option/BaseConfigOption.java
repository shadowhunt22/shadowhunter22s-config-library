//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option;

import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface BaseConfigOption<T> {
    ConfigOption<T> asConfigOption();

    Text getText();
    T getValue();
    void setValue(T value);
    T getDefaultValue();

    String getTranslationKey();
}