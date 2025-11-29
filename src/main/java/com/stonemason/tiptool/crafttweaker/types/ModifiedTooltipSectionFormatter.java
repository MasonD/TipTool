package com.stonemason.tiptool.crafttweaker.types;

import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.mc1120.formatting.FormattedMarkupString;
import crafttweaker.mc1120.formatting.FormattedString;
import crafttweaker.mc1120.formatting.IMCFormattedString;
import net.minecraft.util.text.TextFormatting;

import java.util.stream.Stream;

public class ModifiedTooltipSectionFormatter implements ITooltipSectionFormatter {
    protected final ITooltipSectionFormatter wrapped;

    protected IFormattedText header;

    protected TextFormatting color;
    protected TextFormatting style;

    int indentLevel = 0;

    public ModifiedTooltipSectionFormatter(ITooltipSectionFormatter wrapped) {
        this.wrapped = wrapped;
    }

    public void setColor(TextFormatting color) {
        this.color = color;
    }

    @Override
    public IFormattedText[] format(IFormattedText[] lines) {
        FormattedString indent;
        if (indentLevel != 0) {
            StringBuilder indentBuilder = new StringBuilder();
            for (int i = 0; i < indentLevel; i++) {
                indentBuilder.append(" ");
            }
            indent = new FormattedString(indentBuilder.toString());
        } else {
            indent = null;
        }
        var stream = Stream.of(wrapped.format(lines)).map(line -> {
            if (color != null) {
                line = new FormattedMarkupString(color, (IMCFormattedString) line);
            }
            if (style != null) {
                line = new FormattedMarkupString(color, (IMCFormattedString) line);
            }
            if (indent != null) {
                line = (indent).cat(line);
            }
            return line;
        });

        if (header != null) {
            stream = Stream.concat(Stream.of(header), stream);
        }

        return stream.toArray(IFormattedText[]::new);
    }

    public void setHeader(IFormattedText header) {
        this.header = header;
    }

    public void setStyle(TextFormatting textFormatting) {
        this.style = textFormatting;
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }
}
