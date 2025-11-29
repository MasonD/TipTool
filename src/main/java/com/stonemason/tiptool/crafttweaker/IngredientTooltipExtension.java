package com.stonemason.tiptool.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ZenRegister
@ZenExpansion( "crafttweaker.item.IIngredient")
public class IngredientTooltipExtension {
    @ZenMethod
    public static void automateTooltips(IIngredient ingredient, @Optional @Nullable String langKey) {
        CraftTweakerAPI.apply(new EnableTooltipAutomationAction(ingredient, langKey));
    }


    private static class EnableTooltipAutomationAction implements IAction {
        private final IIngredient ingredient;
        private final String langKey;
        public EnableTooltipAutomationAction(IIngredient ingredient, @Nullable String langKey) {
            this.ingredient = ingredient;
            this.langKey = langKey;
        }

        @Override
        public void apply() {
            ToolTipper.enableForIngredient(ingredient, langKey);
        }

        @Override
        public String describe() {
            return "Enabling tooltip automation for " + ingredient;
        }
    }
}
