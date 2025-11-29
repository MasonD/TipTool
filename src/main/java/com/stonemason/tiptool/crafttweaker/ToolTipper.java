package com.stonemason.tiptool.crafttweaker;

import com.stonemason.tiptool.TipTool;
import com.stonemason.tiptool.crafttweaker.types.ITooltipSectionFormatter;
import com.stonemason.tiptool.crafttweaker.types.TooltipSection;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.util.IngredientMap;
import crafttweaker.runtime.events.CrTLoaderLoadingEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.*;

@ZenRegister
@ZenClass("mods.tiptool.TipTool")
public class ToolTipper {
    private static final List<TooltipSection> sections = new LinkedList<>();
    //    private static final Set<IIngredient> enabledItems = new HashSet<>();
    private static final IngredientMap<String> keyMapping = new IngredientMap<>();

    @ZenMethod
    public static void registerLangFiles(String langFilePrefix) {
        CraftTweakerAPI.apply(new RegisterLangFileAction(langFilePrefix));
    }


    @ZenMethod
    public static TooltipSection registerTooltipSection(String id, @Optional ITooltipSectionFormatter formatter) {
        if (sections.stream().anyMatch(section -> section.getId().equals(id))) {
            throw new IllegalArgumentException("A tooltip section with the id " + id + " already exists!");
        }
        TooltipSection section = new TooltipSection(id, formatter != null ? formatter : ITooltipSectionFormatter.empty);
        sections.add(section);
        return section;
    }


    public static void onScriptLoadFinish(CrTLoaderLoadingEvent.Finished event) {
        if (!Objects.equals(event.getLoader().getMainName(), "crafttweaker")) {
            return;
        }
    }

    public static void enableForIngredient(IIngredient ingredient) {
//        enabledItems.add(ingredient);
        enableForIngredient(ingredient, "");
    }

    public static void enableForIngredient(IIngredient ingredient, @Nullable String key) {
        keyMapping.register(ingredient, key != null ? key : "");
    }

    public static IFormattedText[] getTooltips(IItemStack itemStack, boolean isShifted) {
        List<String> keys = keyMapping.getEntries(itemStack);
        if (keys.isEmpty()) {
            return new IFormattedText[0];
        }
        IFormattedText[] tooltips = new IFormattedText[0];

        String key = keys.get(0).isEmpty() ? Item.REGISTRY.getNameForObject(((ItemStack) itemStack.getInternal()).getItem()).toString() : keys.get(0);

        for (TooltipSection section : sections) {
            tooltips = ArrayUtils.addAll(tooltips, section.formatLines(key, isShifted));
        }

        return tooltips;
    }

    public static boolean isEnabled(IItemStack itemStack) {
        return true;//enabledItems.contains(itemStack);
    }

    private static class RegisterLangFileAction implements IAction {
        private final String langFilePrefix;

        public RegisterLangFileAction(String langFile) {
            this.langFilePrefix = langFile;
        }

        @Override
        public void apply() {
            TipTool.languageManger.addLangFile(langFilePrefix);
        }

        @Override
        public String describe() {
            return "Adding language files scripts/" + langFilePrefix + ".*.lang.yml";
        }
    }


}
