package fr.ht06.skyblockplugin.Config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataConfig {

    private static File file;
    private static FileConfiguration dataFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SkyblockPlugin").getDataFolder(), "data.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){

            }
        }
        dataFile = YamlConfiguration.loadConfiguration(file);

    }

    public static FileConfiguration get(){
        return dataFile;
    }

    public static void save(){
        try{
            dataFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        dataFile = YamlConfiguration.loadConfiguration(file);
    }

}
