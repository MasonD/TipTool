package com.stonemason.tiptool.crafttweaker.types;

import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.mc1120.formatting.FormattedStringJoin;
import crafttweaker.mc1120.formatting.IMCFormattedString;
import net.minecraft.util.text.TextComponentTranslation;

public class TranslationFormattedString implements IFormattedText, IMCFormattedString {
    private final String key;

    public TranslationFormattedString(String key) {
        this.key = key;
    }

    @Override
    public IFormattedText add(IFormattedText other) {
        return cat(other);
    }

    @Override
    public IFormattedText cat(IFormattedText other) {
        return new FormattedStringJoin(this, (IMCFormattedString) other);
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getTooltipString() {
        return (new TextComponentTranslation(key).getFormattedText()) ;
    }

    @Override
    public String getTooltipString(String context) {
        return getTooltipString();
    }
}
