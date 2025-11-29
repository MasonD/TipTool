package com.stonemason.tiptool.crafttweaker.types;

import com.stonemason.tiptool.LangResourceReloader;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.LanguageMap;
import stanhebben.zenscript.annotations.*;

import java.util.ArrayList;

@ZenRegister
@ZenClass("mods.tiptool.TooltipSection")
public class TooltipSection {

    public final String id;

    protected ITooltipSectionFormatter formatter;
    protected ITooltipSectionFormatter formatterUnshifted;


    public TooltipSection(String id, ITooltipSectionFormatter formatter) {
        this.id = id;
        if (formatter == null) {
            throw new IllegalArgumentException("Formatter cannot be null");
        }
        this.formatter = formatter;
    }

    public IFormattedText[] formatLines(String name, boolean isShifted) {
        var format = !isShifted && formatterUnshifted != null ? formatterUnshifted : formatter;
        if (name == null) {
            return new IFormattedText[0];
        }
        String lineless = LangResourceReloader.tooltipKey(name, this.id);
        if (LanguageMap.instance.isKeyTranslated(lineless)) {
            return format.format(new IFormattedText[]{new TranslationFormattedString(lineless)});
        }
        ArrayList<IFormattedText> lines = new ArrayList<>();
        int i = 1;
        String key;
        while (LanguageMap.instance.isKeyTranslated(key = LangResourceReloader.tooltipKey(name, this.id, i))) {
            lines.add(new TranslationFormattedString(key));
            i++;
        }
        if (!lines.isEmpty()) {
            return format.format(lines.toArray(new IFormattedText[0]));
        }

        return new IFormattedText[0];
    }


    @ZenGetter("formatter")
    public ITooltipSectionFormatter getFormatter() {
        return formatter;
    }

    @ZenSetter("formatter")
    public void setFormatter(ITooltipSectionFormatter formatter) {
        this.formatter = formatter;
    }

    @ZenGetter("id")
    public String getId() {
        return id;
    }

    @ZenMethod
    public TooltipSection setFormatterUnshifted(ITooltipSectionFormatter formatter) {
        this.formatterUnshifted = formatter;
        return this;
    }

    protected ModifiedTooltipSectionFormatter getModifiedFormatter() {
        if (!(formatter instanceof ModifiedTooltipSectionFormatter)) {
            formatter = new ModifiedTooltipSectionFormatter(formatter);
        }
        return (ModifiedTooltipSectionFormatter) formatter;
    }


    @ZenMethod
    public TooltipSection header(IFormattedText header, @Optional int indentLevel) {
        getModifiedFormatter().setHeader(header);
        getModifiedFormatter().setIndentLevel(indentLevel);
        return this;
    }

    @ZenMethod
    public TooltipSection black() {
        this.getModifiedFormatter().setColor(TextFormatting.BLACK);
        return this;
    }


    @ZenMethod
    public TooltipSection darkBlue() {
        this.getModifiedFormatter().setColor(TextFormatting.DARK_BLUE);
        return this;
    }


    @ZenMethod
    public TooltipSection darkGreen() {
        this.getModifiedFormatter().setColor(TextFormatting.DARK_GREEN);
        return this;
    }


    @ZenMethod
    public TooltipSection darkAqua() {
        this.getModifiedFormatter().setColor(TextFormatting.DARK_AQUA);
        return this;
    }


    @ZenMethod
    public TooltipSection darkRed() {
        this.getModifiedFormatter().setColor(TextFormatting.DARK_RED);
        return this;
    }


    @ZenMethod
    public TooltipSection darkPurple() {
        this.getModifiedFormatter().setColor(TextFormatting.DARK_PURPLE);
        return this;
    }


    @ZenMethod
    public TooltipSection gold() {
        this.getModifiedFormatter().setColor(TextFormatting.GOLD);
        return this;
    }


    @ZenMethod
    public TooltipSection gray() {
        this.getModifiedFormatter().setColor(TextFormatting.GRAY);
        return this;
    }


    @ZenMethod
    public TooltipSection darkGray() {
        this.getModifiedFormatter().setColor(TextFormatting.DARK_GRAY);
        return this;
    }


    @ZenMethod
    public TooltipSection blue() {
        this.getModifiedFormatter().setColor(TextFormatting.BLUE);
        return this;
    }


    @ZenMethod
    public TooltipSection green() {
        this.getModifiedFormatter().setColor(TextFormatting.GREEN);
        return this;
    }


    @ZenMethod
    public TooltipSection aqua() {
        this.getModifiedFormatter().setColor(TextFormatting.AQUA);
        return this;
    }


    @ZenMethod
    public TooltipSection red() {
        this.getModifiedFormatter().setColor(TextFormatting.RED);
        return this;
    }


    @ZenMethod
    public TooltipSection lightPurple() {
        this.getModifiedFormatter().setColor(TextFormatting.LIGHT_PURPLE);
        return this;
    }


    @ZenMethod
    public TooltipSection yellow() {
        this.getModifiedFormatter().setColor(TextFormatting.YELLOW);
        return this;
    }


    @ZenMethod
    public TooltipSection white() {
        this.getModifiedFormatter().setColor(TextFormatting.WHITE);
        return this;
    }


    @ZenMethod
    public TooltipSection obfuscated() {
        this.getModifiedFormatter().setColor(TextFormatting.OBFUSCATED);
        return this;
    }


    @ZenMethod
    public TooltipSection bold() {
        this.getModifiedFormatter().setStyle(TextFormatting.BOLD);
        return this;
    }


    @ZenMethod
    public TooltipSection strikethrough() {
        this.getModifiedFormatter().setStyle(TextFormatting.STRIKETHROUGH);
        return this;
    }


    @ZenMethod
    public TooltipSection underline() {
        this.getModifiedFormatter().setStyle(TextFormatting.UNDERLINE);
        return this;
    }


    @ZenMethod
    public TooltipSection italic() {
        this.getModifiedFormatter().setStyle(TextFormatting.ITALIC);
        return this;
    }

}
