package io.github.javajumper.lavajumper.common;

import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class Translation {
    static Map<String,String> translationMap = new HashMap<>();

    /**
     * Generates file for translation in game and puts it into .autoTranslateOutput folder
     * works only in development Enviroment
     */
    public static void generateTranlationMap()
    {
        var file = FabricLoader.getInstance().getGameDir().resolve(".autoTranslateOutput").resolve("output.txt").toFile();
        FileReadWrite.write(file,new GsonBuilder().setPrettyPrinting().create().toJson(translationMap));
        translationMap = new HashMap<>();
    }
    private static void addKeyToTranslation(String key)
    {
        if(!I18n.hasTranslation(key))
            translationMap.put(key,"");
    }
    public static MutableText get(String key){
        addKeyToTranslation("javajumper."+key);
        return Text.translatable("javajumper."+key);
    }
    public static MutableText getLocal(String key)
    {
        //some haccs
        var fullclassname = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
        //ActionTextRenderer.sendChatMessage(Arrays.toString(fullclassname));
        var className = fullclassname[fullclassname.length-1];
        //ActionTextRenderer.sendChatMessage(className);
        var text = get(String.format(
                "%s.%s",
                className,
                key));
        return text;
    }
}