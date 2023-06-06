package io.github.JumperOnJava.autocfg;


import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import io.github.JumperOnJava.autocfg.translationGenerator.Translation;
import io.github.JumperOnJava.autocfg.valuetypes.MenuValue;
import io.github.JumperOnJava.lavajumper.common.FileReadWrite;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;

public class ConfigGenerator {
    private File configFile;
    private String configName;
    private Translation translation;
    private Logger LOGGER = LoggerFactory.getLogger("ConfigGenerator");
    private SerializerContainer serializerContainer;
    public SerializerContainer getSerializerContainer(){
        return serializerContainer;
    }
    public ConfigGenerator(String configName){
        this.configName = configName;
        configFile = FabricLoader.getInstance().getConfigDir().resolve(configName+".json").toFile();
        translation = new Translation(configName);
        this.serializerContainer = new SerializerContainer();
    }
    private Set<Class> configurableClasses = new HashSet<>();
    public void addClassToConfig(Class c){
        configurableClasses.add(c);
    }
    private List<Category> getEverything() {
        List<MenuValue> menuValues = new LinkedList<>();
        for(var c : configurableClasses)
        {
            menuValues.addAll(getCategoryAllFieldsOfClass(c));
        }
        Map<String, Category> valuesByCategory = new HashMap<>();
        for(var value : menuValues)
        {
            var key = value.getTranslationKey().split("\\.")[0];
            if(!valuesByCategory.containsKey(key))
                valuesByCategory.put(key,new Category("categoryName."+key, new LinkedList<>()));
            valuesByCategory.get(key).fields.add(value);
        }
        List<Category> categories = new LinkedList<>(valuesByCategory.values());
        return categories;
    }
    private List<MenuValue> getCategoryAllFieldsOfClass(Class type) {
        var list = new LinkedList<MenuValue>();

            for(var field : type.getFields()) {
                if (field.isAnnotationPresent(Configurable.class)) {
                    var fieldType = field.getType();
                    var fieldMetadata = field.getAnnotation(Configurable.class);

                    String fieldPath = String.format("%s.%s", type.getSimpleName(), field.getName());
                    String translationKey;
                    if(field.isAnnotationPresent(CustomCategory.class))
                    {
                        CustomCategory categoryInfo = field.getAnnotation(CustomCategory.class);
                        String category;
                        var fieldName
                            = categoryInfo.name().equals(CustomCategory.empty)
                                ? field.getName()
                                : categoryInfo.name();

                        translationKey = String.format("%s.%s", type.getSimpleName(), fieldName);

                        if(!categoryInfo.category().equals(CustomCategory.empty))
                        {
                            translationKey = categoryInfo.category()+"."+translationKey;
                        }
                    }
                    else {
                        translationKey = fieldPath;
                    }
                    MenuValue element = serializerContainer.createMenuValueByClass(fieldType,translationKey,fieldPath,new StaticFieldValue(field),fieldMetadata);
                    list.add(element);
                }
            }
        return list;
    }
    public void saveConfig(List<Category> categories)
    {
        var config = new HashMap<String,HashMap<String,String>>();
        for (var category : categories)
        {
            for(var field : category.fields)
            {
                var className = field.getFieldPath().split("\\.")[0];
                var fieldName = field.getFieldPath().split("\\.")[1];
                if(!config.containsKey(className))
                    config.put(className,new HashMap<>());
                config.get(className).put(fieldName,field.getValue().toString());
            }
        }
        var json = new Gson().toJson(config);
        //ActionTextRenderer.sendChatMessage(json);
        FileReadWrite.write(configFile,json);
    }
    public void restoreConfig(){
        getEverything();
        String jsonString = "";
        jsonString = FileReadWrite.read(configFile);
        if(jsonString.equals(""))
            jsonString="{}";
        Map<String, LinkedTreeMap<String,String>> config;
        try{
            config = new HashMap<>(new Gson().fromJson(jsonString,HashMap.class));
        }
        catch (Exception e){
            config = new HashMap<>();
        }

        config.forEach((typeName,fields)->{
            Class foundType = null;
            for(var type : configurableClasses)
            {
                if(typeName.equals(type.getSimpleName()))
                    foundType = type;
            }
            if(foundType == null)
                return;
            var e = new BiConsumer<String,String>(){
                Class foundType;
                @Override
                public void accept(String fieldName, String fieldValue) {
                    try{
                        var field = foundType.getField(fieldName);
                        var fieldType = field.getType();
                        field.set(null, serializerContainer.parseObject(fieldType,fieldValue));
                    }
                    catch (NoSuchFieldException e){
                        LOGGER.info("no field with name "+fieldName+" found, ignoring");
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            };
            e.foundType = foundType;
            fields.forEach(e);
        });
    }

    /**
     * Will return null if YACL is not loaded
     * @param categories
     * @param parent
     * @return
     */
    public Screen getConfigScreen(List<Category> categories, Screen parent)
    {
        if(!FabricLoader.getInstance().isModLoaded("yet-another-config-lib"))
            return null;
        var builder = YetAnotherConfigLib.createBuilder();
        builder.title(Text.of("LavaJumper Configuration XDD"));
        for(var category : categories)
        {
            var categoryBuilder = ConfigCategory.createBuilder();
            categoryBuilder.name(translation.get(category.categoryTranslationKey));
            for(var field : category.fields)
            {
                var optionBuilder = Option.createBuilder(field.getTarget());
                optionBuilder.name(translation.get((field.getTranslationKey())));
                optionBuilder.binding(
                        field.getDefaultValue(),
                        field::getValue,
                        field::setValue
                );
                optionBuilder.controller(b -> field.getController((Option<?>)b));
                categoryBuilder.option(optionBuilder.build());
            }
            builder.category(categoryBuilder.build());
        }
        var e = new Runnable(){
            List<Category> categories;
            @Override
            public void run() {
                saveConfig(categories);
            }
        };
        e.categories = categories;
        builder.save(e);
        return builder.build().generateScreen(parent);
    }
    public Screen getFinishedConfigScreen(Screen parent)
    {
        restoreConfig();
        return getConfigScreen(getEverything(),parent);
    }
}
