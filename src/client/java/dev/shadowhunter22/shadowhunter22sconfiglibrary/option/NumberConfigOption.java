//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.option;

public interface NumberConfigOption<T> extends ConfigOption<T> {
	T getMin();
	void setMin(Object value);

	T getMax();
	void setMax(Object value);
}
