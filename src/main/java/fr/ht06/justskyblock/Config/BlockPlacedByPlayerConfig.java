package fr.ht06.justskyblock.Config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BlockPlacedByPlayerConfig {

    private static File file;
    private static FileConfiguration dataFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "placedByPlayer.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException ignored){

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
