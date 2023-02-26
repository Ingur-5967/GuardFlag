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

### Ideas

### Description of commands
- `/give-guard <addition|picker|remover>` - gives convenient items for controlling blocks. Deleting, adding, informing.
- `/remove-block-guard <x> <y> <z>` - removes block protection at a distance if you know the coordinates.
- `/my-blocks` - generates a window in which you can scroll through the list of your protected blocks. To delete a block in the menu, you need to click on the block, all the necessary information of the block is in the description so you cannot confuse with anything.

### Only for the console
- `/change-plugin-language <language key>` - allows you to change the language in real time without rebooting

# Config.yml
When you first start the server with the plugin, a configuration file will appear that can be configured, the purpose of all parameters will be described from the bottom.

### Description of the config parameters
- `database-name-file: database.db` - determines the name of the database. **_IMPORTANT!_** _At the end of the name there must be an extension of the **.db**_
---
```yml
  allows-in-worlds:
    world: true
    nether: false
    ender: false
  ```
- Setting permissions for the plugin, in which worlds it will work and in which not.
---
- `maximum-protected-block: 10` - sets the maximum value of protected blocks for each player.
- `plugin-language: eng|ru|uk` - sets the default value of the language to be used in the plugin

# Assembly of the project
_**[Download maven](https://maven.apache.org/download.cgi)**_ and write `mvn package` in the [project folder](https://github.com/Vitalij3/block-guard-plugin.git). A folder with the name _**"target"**_ will appear, there should be a file with the extension **_.jar_** - this is the plugin
