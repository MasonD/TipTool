## TipTool

Minecraft 1.12.2 CraftTweaker addon to simplify adding translated item tooltips with standardised formatting to many items, intended for use in modpacks.

### Instructions:

First, define tooltip sections and their formatting with
```zenscript
import mods.tiptool.TipTool;

val section = TipTool.registerTooltipSection("sectionKey");
section.aqua(); # Any of the formatting options from format.*
section.header(
    format.red(section.translate('header')), # lang key prefixed with 'tiptool.sectionKey.tooltip.' 
    2 # optional indent level (as number of spaces) automatically applied to body underneath the header
);
```

Then, enable automatic tooltipping on items 
```zenscript
# will use lang keys 'tiptool.iron_ingot.tooltip.sectionKey' for each section, 
# OR 'tiptool.iron_ingot.tooltip.sectionKey.1' - 'tiptool.iron_ingot.tooltip.sectionKey.n' for multiline sections
<minecraft:iron_ingot>.automateTooltips()

# All variants of stone will receive the same tooltips from 'tiptool.iron_ingot.tooltip.sectionKey'
<minecraft:stone>.automateTooltips()

# Only regular stone will receive the tooltips from 'tiptool.iron_ingot.tooltip.sectionKey'
<minecraft:stone:0>.automateTooltips()

# specify an explicit language key if you want to use different tooltips for different variants
# this will use language keys 'tiptool.variantLangKey.sectionKey' for each section
<minecraft:stone:1>.automateTooltips("variantLangKey")

# A more likely example for variants
val woolDefinition = <minecraft:wool>.definition;
for i in 0 to 15 {
    woolDefinition.makeStack(i).automateTooltips("minecraft:wool:" + i);
}
```

### Defining translations
You can define translations in regular lang files, but that can become cumbersome quickly. Instead, you can use the `TipTool.registerLangFiles(path)` method to register a set of `.yml` lang files to be loaded specifically for tooltips. The path is relative to the scripts/ directory.
```zenscript
# scripts/some_zs.zs
mods.tiptool.TipTool.registerLangFiles("path/tooltip")
```
```yml
# scripts/path/tooltip/en_us.yml
minecraft:iron_ingot:
  sectionKey: translation for the "sectionKey" section of minecraft:iron_ingot's tooltip 
  multilineSection: |
    Multiple lines will be split up into separate lang keys automatically
    minecraft:iron_ingot will have multiple lines of translated tooltips for this section
    Under the hood, the lang keys used are:
    - tiptool.minecraft:iron_ingot.tooltip.multilineSection.1
    - tiptool.minecraft:iron_ingot.tooltip.multilineSection.2
    - etc.
    You can mix single- and multi-line tooltips for the same section on different items.
minecraft:stone:1:
  multilineSection: sections themselves don't care if you use a single line or multiple
sectionKey:
  key: translation for text created with `registerTooltipSection("sectionKey").translate("key")` 
  header: This matches up with the `section.translate('header')` in the header example above
```