//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.test;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.migration.AbstractConfigMigrationBuilder;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.migration.ConfigMigration;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.migration.EnumSpecification;

public class TestConfigMigration extends AbstractConfigMigrationBuilder<TestConfig2> {
	@Override
	protected ConfigMigration<TestConfig2> build() {
		ConfigMigration<TestConfig2> migration = new ConfigMigration<>(
				ShadowHunter22sConfigLibraryTestMod.test2,
				"config-library/test.json",
				"config-library/test2.json",
				"config-library/.migration.json"
		);

		EnumSpecification specification1 = this.specification1();

		migration
				.migrateInt("test2", "TEST_2")
				.migrateBoolean("test3", "TEST_3")
				.migrateBoolean("test4", "TEST_4")
				.migrateEnum("test5", "TEST_5", specification1) // define enum value mapper specification through a variable
				.migrateEnum("test6", "TEST_6", specification1)
				.migrateEnum("test7", "TEST_7", TestConfig2.Location.class, mapper -> { // or through a functional interface
					mapper.add("topLeft", "TopLeft");
					mapper.add("topRight", "TopRight");
					mapper.add("bottomLeft", "BottomLeft");
					mapper.add("bottomRight", "BottomRight");

					return mapper;
				});

		return migration;
	}

	private EnumSpecification specification1() {
		EnumSpecification specification = new EnumSpecification(TestConfig2.Location.class);

		specification.add("topLeft", "TopLeft");
		specification.add("topRight", "TopRight");
		specification.add("bottomLeft", "BottomLeft");
		specification.add("bottomRight", "BottomRight");

		return specification;
	}
}
