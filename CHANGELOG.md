Changes: 

- Bump version
- Added a double and float config option

- Fixed [#3](https://github.com/shadowhunt22/shadowhunter22s-config-library/issues/3)

API Additions:

- Created a new interface named `NumberConfigOption` that all number-based config options implement.
- Created a new class named `AbstractSliderEntry` that all slider entries extend.

Breaking Changes:

#### AbstractConfigScreen

- `getTabs` is now a protected method (was public).

#### ConfigEntryWidgetHolder

- Renamed the field `list` to `entryWidget`.

#### ListWidget

- Removed `ListWidget` in favor for `SimpleLayoutWidget` (see below changes for `AbstractEntry`).

#### CategoryTab

- The `CategoryTab` constructor no longer takes a `Screen`.
- The field `category` is now private.

#### ConfigCategory

- Renamed the field `text` to `categoryTitle`.
- Renamed the method `add(AbstractEntry entry)` to `addEntry(AbstractEntry entry)`.
- Renamed the method `getText()` to `getCategoryName()`.

- Removed the method `add(int index, AbstractEntry entry)`.

#### AbstractEntry

- Replaced the field `listWidget` with `layout`.
- Replaced the method `getListWidget` with `getLayoutWidget`.