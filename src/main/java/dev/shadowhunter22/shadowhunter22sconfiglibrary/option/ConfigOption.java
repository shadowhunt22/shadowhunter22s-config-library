//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.option;

import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

public interface ConfigOption<T> {
    String getKey();
    String getTranslationKey();

    T getValue();
    void setValue(Object value);

    T getDefaultValue();
    void setDefaultValue(Object value);

    Text getText();
}