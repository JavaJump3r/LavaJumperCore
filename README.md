# LavaJumper-core
JavaJumper's library
It contains some common code i use in different projects

# Classes
### Common
BinaryReader - kinda port of BinaryReader from c#

Binder - fast creation of binds

FileReadWrite - easy read and write of text files 
with some exceptions handling

InventoryTools - some functions for interacting with inventory
uses TickActionQueue

TickActionQueue -  Queue for any actions that should be happen less than N times per tick.
Actions implement TickQueueAction

Translation - Allows you to export untranslated strings for further translation (.autoTranslateOutput folder)

### Datatypes

Angle - stores angle, allows to do some operations

CircleSlice - stores info about segment of cirgle

LimitedHashMap - stores limited amount of elements, removes eldest entry if overflows

## Gui

AskScreen - allows you to create prompt screens

AskScreenManager - handles lifecycle of AskScreen

GuiHelper - some common (for me) methods for working with gui

HoverManager - helps handling hovering effects

### Widgets

SubScreen - Widget that wraps Screen and allows to display multiple screens at once.
Very useful when designing complex guis

PizzaWidget(Slice) - Widget for creating radial menus like in https://modrinth.com/mod/jjpizza

TextureWidget - displays texture by identifier.

SliderWidget - general purpose slider widget

ScrollListWidget(Entry) - general purpose scroll list widget. 
You can add entries which contain other gui elements

## AutoCfg
Code of this part of mod is not very nice, but i'm lazy to rewrite it

Automatic generation of config screens using annotations

```java
//make sure YACL is loaded;
if(!FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3"))
    return;
//create config manager
var config = new ConfigGenerator("config-file-name");
//restore or create new config file
config.restoreConfig();
//add any class to configure
config.addClassToConfig(ConfigTestClass.class);
```
Add @Configurable annotation to public static field  

Customize its name and category with @CustomCategory annotation

Make sure every class with these annotations is added to config using configGenerator.addClassToConfig( 
