package com.stonemason.tiptool;


import com.stonemason.tiptool.crafttweaker.ToolTipper;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tooltip.IngredientTooltips;
import crafttweaker.mc1120.formatting.IMCFormattedString;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID, value = Side.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void onItemTooltip(ItemTooltipEvent ev) {
        if (!ev.getItemStack().isEmpty()) {
            IItemStack itemStack = CraftTweakerMC.getIItemStack(ev.getItemStack());
            var name = itemStack.getName();
            if (ToolTipper.isEnabled(itemStack)) {
                for (IFormattedText tooltip : ToolTipper.getTooltips(itemStack, Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))) {
                    ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
                }
            }
        }

    }

//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    public void onGuiOpenEvent(GuiOpenEvent ev) {
//        Minecraft minecraft = Minecraft.getMinecraft();
//        if (minecraft.player != null && !alreadyChangedThePlayer) {
//            alreadyChangedThePlayer = true;
//            RecipeBookClient.rebuildTable();
//            minecraft.populateSearchTreeManager();
//            ((SearchTree)minecraft.getSearchTreeManager().get(SearchTreeManager.ITEMS)).recalculate();
//            ((SearchTree)minecraft.getSearchTreeManager().get(SearchTreeManager.RECIPES)).recalculate();
//            CraftTweakerAPI.logInfo("Fixed the RecipeBook");
//        }
//
//    }
}
