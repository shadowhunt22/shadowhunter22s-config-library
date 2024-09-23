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
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BooleanConfigOption<T extends Boolean> implements BaseConfigOption<T> {
    private final String key, translationKey;
    private T value;
    private final T defaultValue;

    private final Text enabled;
    private final Text disabled;

    public BooleanConfigOption(Config definition, String key, T value, T defaultValue) {
        this.key = key;
        this.translationKey = TranslationUtil.translationKey("option", definition.name(), this.key);

        this.value = value;
        this.defaultValue = defaultValue;

        this.enabled = Text.translatable("option.shadowhunter22s-config-library.enabled");
        this.disabled = Text.translatable("option.shadowhunter22s-config-library.disabled");
    }

    @Override
    public Text getText() {
        return this.value.booleanValue() ? Text.literal(this.enabled.getString()).formatted(Formatting.GREEN) : Text.literal(this.disabled.getString()).formatted(Formatting.RED);
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public T getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public ConfigOption<T> asConfigOption() {
        return new ConfigOption<>(
                this,
                this.key,
                this.translationKey
        );
    }
}