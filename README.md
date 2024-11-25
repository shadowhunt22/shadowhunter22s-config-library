# ShadowHunter22's Config Library

Before going into any specifics, I need to list out what this library IS and what it ISN'T.

## What this library IS

This library is:

- An abstraction of my config system used by the majority of my mods.  All of my mods that have their own config system, with the exception being `armor-indicator`, implement the same exact code.  This code is also very brittle and limiting in what I can do.
- An implementation to fit the needs of my mods.  There are plenty of far better config libraries out there, but I need one that fits the various needs of my mods.

## What this library ISN'T

- This library isn't something other mod developers should use to make their mods configurable.  Why?  Because I designed this library for the needs of my mods (and to be honest, other implementations out there are a lot better and cleaner to use).
- There are many, many, *many* other mods that have better implementations of a config system, such as:
  - [Configurable](https://modrinth.com/mod/configurable) by [Bawnorton](https://modrinth.com/user/Bawnorton)
  - [owo-lib](https://modrinth.com/mod/owo-lib)
  - [YACL](https://modrinth.com/mod/yacl) by [isXander](https://modrinth.com/user/isxander)
  - [Moonlight Lib](https://modrinth.com/mod/moonlight) by [MehVahdJukaar](https://modrinth.com/user/MehVahdJukaar)
  - [Cloth Config API](https://modrinth.com/mod/cloth-config) by [shedaniel](https://modrinth.com/user/shedaniel)

## Details

Now that I have aired that out, here is what this library does:

### AutoConfig

Like the Cloth Config API, this mod has a way to automatically build the config screen and the widgets to configure the values.  In order for a config file to be registered without throwing compile-time errors or crashing the game, the class needs to,

- Implement [`ConfigData`](/src/main/java/dev/shadowhunter22/shadowhunter22sconfiglibrary/api/v1/config/ConfigData.java) and at bare minimum specify the `name`, or what I like to specify as the`definition`, of the config class.  `file` is the name of the config file and will default to `options` if no value is present. 
- Decorate the class with the [`@Config`](/src/main/java/dev/shadowhunter22/shadowhunter22sconfiglibrary/annotation/Config.java) annotation.

To add sections, 

- A field needs to be decorated with the [`@ConfigEntry.Gui.Section`](/src/main/java/dev/shadowhunter22/shadowhunter22sconfiglibrary/annotation/ConfigEntry.java) annotation.

An `int` config option needs to,

- Be decorated with the [`@ConfigEntry.Integer`](/src/main/java/dev/shadowhunter22/shadowhunter22sconfiglibrary/annotation/ConfigEntry.java) annotation and specify a `min` and `max` value.

### Events

One of my mods, [Ender Eyes](https://modrinth.com/mod/ender-eyes), makes a lot of changes the config option's default and max values and after applying those changes on the client-side, send a packet to the server to make those changes visible to all players.  As such, this  mod provides the following methods, that can be optionally implemented, from [`ConfigData`](/src/main/java/dev/shadowhunter22/shadowhunter22sconfiglibrary/api/v1/config/ConfigData.java):     

- `ConfigData#afterMigration`
- `ConfigData#afterLoad`
- `ConfigData#afterSave`
- `ConfigData#afterChange`
- `ConfigData#afterScreenClose`

See [`ConfigData`](/src/main/java/dev/shadowhunter22/shadowhunter22sconfiglibrary/api/v1/config/ConfigData.java) for a description of each method.

### Migration

In my opinion, the only cool part about this mod is the ability to migrate a user's options from `config_a.json` to `config_b.json`.  See [`TestConfigMigration`](/src/test/java/dev/shadowhunter22/shadowhunter22sconfiglibrary/test/TestConfigMigration.java) for an example.