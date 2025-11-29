package com.stonemason.tiptool;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public class LangResourceReloader implements ISelectiveResourceReloadListener {
    protected final List<String> langFilePrefixes = new LinkedList<>();

    protected LoadSettings settings;

    public LangResourceReloader() {
        settings = LoadSettings.builder().build();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (!resourcePredicate.test(VanillaResourceType.LANGUAGES)) {
            return;
        }

        Load load = new Load(settings);

        for (String prefix : langFilePrefixes) {
            this.loadPrefix(prefix, load);
        }

    }

    public void addLangFile(String langFile) {
        langFilePrefixes.add(langFile);
        this.loadPrefix(langFile);
        // pass
    }

    protected void loadPrefix(String prefix) {
        loadPrefix(prefix, new Load(settings));
    }

    protected void loadPrefix(String prefix, Load load) {
        String newLang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

        Map<String, String> map = new HashMap<>();

        File file = new File("scripts/" + prefix + ".en_us.lang.yml");

        tryLoadFile(load, file, map);

        if (!newLang.equals("en_us")) {
            file = new File("scripts/" + prefix + "." + newLang + ".lang.yml");

            tryLoadFile(load, file, map);
        }


        if (!map.isEmpty()) {
            LanguageMap.instance.languageList.putAll(map);
        }
    }

    public static String tooltipKey(String itemName, String category) {
        return "tiptool." + itemName + ".tooltip." + category;
    }

    public static String tooltipKey(String itemName, String category, int line) {
        return "tiptool." + itemName + ".tooltip." + category + "." + line;
    }

    private static void tryLoadFile(Load load, File file, Map<String, String> map) {
        if (file.exists()) {
            try {
                Object loaded = load.loadFromInputStream(Files.newInputStream(file.toPath()));
                if (loaded instanceof Map topMap) {
                    for (Object o : topMap.entrySet()) {
                        if (o instanceof Map.Entry entry && entry.getValue() instanceof Map innerMap && entry.getKey() instanceof String itemName) {
                            TipTool.LOGGER.error("wow");
                            for (Object innerO : innerMap.entrySet()) {
                                if (innerO instanceof Map.Entry innerEntry && innerEntry.getValue() instanceof String text && innerEntry.getKey() instanceof String category) {
                                    if (text.contains("\n")) {
                                        var split = text.split("\n");
                                        for (int i = 0; i < split.length; i++) {
                                            map.put(tooltipKey(itemName, category, i + 1), split[i]);
                                        }
                                    } else {
                                        map.put(tooltipKey(itemName, category), text);
                                    }
                                }
                            }
                        }
                    }
                } else {

                    TipTool.LOGGER.error("Failed to load language file " + file.getAbsolutePath());
                }

            } catch (IOException e) {
                TipTool.LOGGER.error("Failed to load language file " + file.getAbsolutePath(), e);
            }
        }
    }


}
