package com.stonemason.tiptool.crafttweaker.types;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.tiptool.ITooltipSectionFormatter")
public interface ITooltipSectionFormatter {
    ITooltipSectionFormatter identity = (lines) -> lines;
    ITooltipSectionFormatter empty = (lines) -> new IFormattedText[0];

    IFormattedText[] format(IFormattedText[] lines);
}
