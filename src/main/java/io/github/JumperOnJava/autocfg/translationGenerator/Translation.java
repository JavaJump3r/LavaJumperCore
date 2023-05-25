package io.github.JumperOnJava.autocfg.translationGenerator;

import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Translation {
    Map<String,String> translationMap = new HashMap<>();
    String prefix;
    public Translation(String modid){
        this.prefix = modid;
    }
    //Use this method to generate file with untranslated strings
    public void generateTranlationMap()
    {
        var file = FabricLoader.getInstance().getGameDir().resolve(".autoTranslate").resolve(prefix+"-output.json").toFile();
        var text = new GsonBuilder().setPrettyPrinting().create().toJson(translationMap);
        try{
            file.getParentFile().mkdirs();
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] strToBytes = text.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        translationMap.clear();
    }
    private void addKeyToTranslation(String key)
    {
        if(!I18n.hasTranslation(key))
            translationMap.put(key,"");
    }
    //Use this metod to get key
    public MutableText get(String key){
        addKeyToTranslation(prefix +"."+key);
        return Text.translatable(prefix +"."+key);
    }
}