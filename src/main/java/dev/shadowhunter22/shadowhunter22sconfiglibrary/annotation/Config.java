//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {
    /**
     * The name of the config folder that will be stored in .minecraft/config.  This should follow the
     * <a href="https://betterprogramming.pub/string-case-styles-camel-pascal-snake-and-kebab-case-981407998841">Kebab case</a> programming naming convention.
     */
    String name();

    /**
     * The name of the json file. If no value is supplied, it will default to {@code options}.  There is no need to suffix the string with {@code .json}.
     */
    String file() default "";
}
