//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.util.TranslationUtil;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class IntegerConfigOption<T extends Integer> implements ConfigOption<T> {
    private final Config definition;
    private final String key, translationKey;
    private T value;
    private final T defaultValue, min, max;

    public IntegerConfigOption(Config definition, String key, T min, T max, T value, T defaultValue) {
        this.definition = definition;

        this.key = key;
        this.translationKey = TranslationUtil.translationKey("option", definition.name(), this.key);

        this.value = value;
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }

    @Override
    public Text getText() {
        return Text.translatable(TranslationUtil.translationKey("option", this.definition.name(), this.key));
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (T) value;
    }

    public T getMin() {
        return this.min;
    }

    public T getMax() {
        return this.max;
    }

    @Override
    public T getDefaultValue() {
        return this.defaultValue;
    }
}