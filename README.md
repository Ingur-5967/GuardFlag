## |- The plugin is under development -|

# GuardFlag
Extends the basic set of flags from worldguard by using its API

# Version
The plugin ONLY supports version 1.12.2

# Commands
```
> /drg (Main command)
> /drg info <region-id> (info about region)
> /drg regions (Shows all regions) [BETA]
> /drg flag <region-id> <flag-id> <argument:state>[for more arguments] OR <argument> (Set the flag value for the current region)

* The commands can only be executed on the player's side
```

# Files
```
Files with information about the region are stored in a separate folder of the plugin.

When the server is started and the plugin is initialized, all files of the region are checked for their relevance (the presence of a region or a world). If the file has not passed verification, it is deleted.

The region file is created after adding a flag to the region (Provided that the file was missing)
```

# Dependencies
- WorldGuard [1.12.2]
- WorldEdit [1.12.2]


### Ideas
- Add a GUI with flexible region settings (Flags, filters, etc.)
- Adding different filters for the region and its members
