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

import java.util.Arrays;
import java.util.List;

@ApiStatus.Internal
public class EnumConfigOption<T extends Enum<T>> implements ConfigOption<T> {
    private final Config definition;

    private final String key, translationKey;
    private T value;
    private final T defaultValue;
    private final T[] values;

    public EnumConfigOption(Config definition, String key, T[] values, T value, T defaultValue) {
        this.definition = definition;

        this.key = key;
        this.translationKey = TranslationUtil.translationKey("option", definition.name(), key);

        this.value = value;
        this.defaultValue = defaultValue;
        this.values = values;
    }

    @Override
    public Text getText() {
        return Text.translatable(TranslationUtil.translationKey("option", this.definition.name(), this.key, String.valueOf(this.value)));
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    public T[] getValues() {
        return this.values;
    }

    @Override
    public void setValue(Object value) {
        this.value = (T) value;
    }

    @Override
    public T getDefaultValue() {
        return this.defaultValue;
    }

    public void cycle() {
        List<T> values = Arrays.stream(this.values).toList();

        int index = values.indexOf(this.value);
        this.value = values.get((index + 1) % values.size());
    }
}