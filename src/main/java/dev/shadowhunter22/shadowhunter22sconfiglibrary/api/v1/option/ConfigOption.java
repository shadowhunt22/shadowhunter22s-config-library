//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option;

import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ConfigOption<T> {
    private final BaseConfigOption<T> instance;
    private final String key, translationKey;

    public ConfigOption(BaseConfigOption<T> instance, String key, String translationKey) {
        this.instance = instance;
        this.key = key;
        this.translationKey = translationKey;
    }

    public String getKey() {
        return this.key;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public T getDefaultValue() {
        return instance.getDefaultValue();
    }

    public Text getText() {
        return instance.getText();
    }

    public T getValue() {
        return instance.getValue();
    }
}
