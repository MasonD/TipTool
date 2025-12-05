package com.stonemason.tiptool;

import com.stonemason.tiptool.crafttweaker.ToolTipper;
import crafttweaker.CraftTweakerAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.VERSION,
        dependencies = Tags.DEPENDS
)
public class TipTool {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    public static final LangResourceReloader languageManger = new LangResourceReloader();

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     *     Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);

        CraftTweakerAPI.tweaker.registerLoadFinishedEvent(ToolTipper::onScriptLoadFinish);

        if (event.getSide().isClient()) {
            ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(languageManger);
        }
    }


}
