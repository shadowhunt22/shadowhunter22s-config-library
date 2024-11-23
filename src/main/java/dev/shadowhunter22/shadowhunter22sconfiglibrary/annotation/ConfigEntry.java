//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ConfigEntry {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Integer {
        int min();
        int max();

        /**
         * Whether min() and max() will be changed and it's changes need to be persisted
         */
        boolean dynamic() default false;
    }

    interface Gui {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.FIELD)
        @interface Section {
        }
    }
}
