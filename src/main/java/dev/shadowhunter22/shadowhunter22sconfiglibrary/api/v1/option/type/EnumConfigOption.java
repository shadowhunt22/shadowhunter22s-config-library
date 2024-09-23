//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.BaseConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.util.TranslationUtil;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.List;

@ApiStatus.Internal
public class EnumConfigOption<T extends Enum<T>> implements BaseConfigOption<T> {
    private final Config definition;

    private final String key, translationKey;
    private T value;
    private final T defaultValue;
    private final List<T> enumClass;

    public EnumConfigOption(Config definition, String key, T[] enumClass, T value, T defaultValue) {
        this.definition = definition;

        this.key = key;
        this.translationKey = TranslationUtil.translationKey("option", definition.name(), key);

        this.value = value;
        this.defaultValue = defaultValue;
        this.enumClass = Arrays.stream(enumClass).toList();
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

    @SuppressWarnings("unchecked")
    public void setValue(Enum<?> value) {
        this.value = (T) value;
    }

    @Override
    public T getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public ConfigOption<?> asConfigOption() {
        return new ConfigOption<>(
                this,
                this.key,
                this.translationKey
        );
    }

    public void cycle() {
        int index = this.enumClass.indexOf(this.value);
        this.value = this.enumClass.get((index + 1) % this.enumClass.size());
    }
}