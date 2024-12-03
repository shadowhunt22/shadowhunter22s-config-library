# ShadowHunter22's Config Library

  Before going into any specifics, I need to list out what this library IS and what it ISN'T.

## What this library IS

- An abstraction of my config system used by the majority of my mods.  All of my mods that have their own config system, with the exception being `armor-indicator`, implement the same exact code.  That same code they implement is also very brittle and limiting in what I can do.
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

For extensive details about this mod and its API, head on over to the [wiki](https://github.com/shadowhunt22/shadowhunter22s-config-library/wiki).

## Versions

This mod is for *Fabric* only. There is no plan to support Forge.

This mod is available for the following Minecraft versions:

`1.20`
`1.20.1`
`1.20.2`
`1.20.3`
`1.20.4`
`1.20.5`
`1.20.6`

## Client and Server Support

This mod only needs to be on the client and will not work on the server.