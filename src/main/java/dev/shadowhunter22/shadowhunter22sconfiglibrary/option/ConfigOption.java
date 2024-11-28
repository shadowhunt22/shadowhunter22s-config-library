//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.option;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractOptionEntry;
import net.minecraft.text.Text;

public interface ConfigOption<T> {
    String getKey();
    String getTranslationKey();

    T getValue();
    void setValue(Object value);

    T getDefaultValue();
    void setDefaultValue(Object value);

    Text getText();

    <D extends ConfigData> AbstractOptionEntry asEntry(AutoConfigManager<D> manager, int width);
}