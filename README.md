# SCore

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7dfab7ca1a8e478e91098bd3a5f9d217)](https://app.codacy.com/gh/Ssomar-Developement/SCore?utm_source=github.com&utm_medium=referral&utm_content=Ssomar-Developement/SCore&utm_campaign=Badge_Grade_Settings)
[![](https://jitpack.io/v/Ssomar-Developement/SCore.svg)](https://jitpack.io/#Ssomar-Developement/SCore)



SCore is a library for Minecraft plugins, particularly Ssomar's plugins. SCore includes many custom stuff, like custom  player/block/entity commands, custom conditions, has a variables / particles system, and efficient Class to create custom in-game editor.

#### Requirements
* Java 8 JDK or newer
* Spigot / Maven knowledges

#### Compiling from source
```mvn clean package install```

## Contributing
#### Pull Requests
If you make any changes or improvements to the plugin which you think would be beneficial to others, please consider making a pull request to merge your changes back into the upstream project.

Please make sure to have bug fixes/improvements in separated pull requests from new features/changing how features work.

#### Guide:
- Add new custom commands: 
    package path -> com.ssomar.score.commands.runnable
  - Detailed explanation:
    - For example, you want to add commands to `mixed_player_entity` commands, go to the package folder and create a new class.
      That class file must be a child class of the package's class (For example, `mixed_player_entity` class file is MixedCommand.java)
    
- Add new custom conditions: 
    package path -> com.ssomar.score.features.custom.conditions
