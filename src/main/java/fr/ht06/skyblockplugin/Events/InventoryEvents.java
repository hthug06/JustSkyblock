package fr.ht06.skyblockplugin.Events;

import fr.ht06.skyblockplugin.Inventory.IslandInventory;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.util.*;

public class InventoryEvents implements Listener {
    private Random random = new Random();
    private SkyblockPlugin main;
    private Integer[] coords = {1000, -1000};

    public InventoryEvents(SkyblockPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        //@NotNull List<HumanEntity> human = event.getViewers();
        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof IslandInventory){//Vérification si c'estle bon inventaire
            event.setCancelled(true);

            if (event.getCurrentItem().getType() == Material.GRASS_BLOCK){

                //Si le joueur n'a pas d'île
                if (main.hasIS.containsKey(player) && !main.hasIS.get(player)){
                    //Si le monde skyblock existe
                    if(new File(main.getServer().getWorldContainer().getAbsolutePath()+"/world_Skyblock/").exists()){

                        player.sendMessage("§aCréation de l'île");
                        //Pour que les joueur ai pas ls meme coordonnées (temporaire)
                        boolean find = false;
                        int x = 0;
                        int z = 0;
                        List<Integer> finale = new ArrayList<>();
                        while (!find){
                            x = x +coords[random.nextInt(0, 2)];
                            z = z +coords[random.nextInt(0, 2)];
                            finale.add(x);
                            finale.add(z);
                            if (!main.CoordsTaken.contains(finale)){
                                main.CoordsTaken.add(finale);
                                find = true;
                            }
                            else{
                                finale = new ArrayList<>();
                            }
                        }

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "load IslandPlain " + x + " " + 70 + " " + z + " " + "world_Skyblock");

                        player.closeInventory();
                        player.teleport(new Location(Bukkit.getWorld("world_Skyblock"), x, 70, z));

                        main.hasIS.put(player, true);
                        main.IScoor.put(player, new Location(Bukkit.getWorld("world_Skyblock"), x, 70, z));
                        SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(Bukkit.getWorld("world_Skyblock"),
                                main.IScoor.get(player).getX(), 0, main.IScoor.get(player).getZ() ) );

                    }

                    //Si le monde skyblock n'existe pas
                    else{
                        player.sendMessage("Vous êtes le premier joueur à créer une ile, MERCI");
                        player.sendMessage("Création du monde 'Skyblock'");

                        String settings = "{\"structures\":{\"structures\":{}},\"layers\":[{\"height\":9,\"block\":\"air\"},{\"height\":1,\"block\":\"air\"}],\"lakes\":false,\"features\":false,\"biome\":\"plains\"}";
                        WorldCreator worldcreator = new WorldCreator("world_Skyblock");
                        worldcreator.type(WorldType.FLAT).type(WorldType.FLAT).generatorSettings(settings).generateStructures(false);
                        worldcreator.createWorld();

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "load IslandPlain 0 70 0 world_Skyblock");
                        player.sendMessage("§aCréation de l'île");

                        player.teleport(new Location(Bukkit.getWorld("world_Skyblock"), 0, 70, 0));
                        main.hasIS.put(player, true);
                        main.IScoor.put(player, new Location(Bukkit.getWorld("world_Skyblock"), 0, 70, 0));
                        int x = 0;
                        int z = 0;
                        List<Integer> finale = new ArrayList<>();
                        finale.add(x);
                        finale.add(z);
                        main.CoordsTaken.add(finale);
                        SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(Bukkit.getWorld("world_Skyblock"),
                                main.IScoor.get(player).getX(), 0, main.IScoor.get(player).getZ() ) );
                    }
                }


                //System.out.println(main.getServer().getWorldContainer().getAbsolutePath()+"/Skyblock/");


            }

            if (event.getCurrentItem().getType() == Material.SNOW_BLOCK){
                player.sendMessage(String.valueOf(main.hasIS));
                player.sendMessage(String.valueOf(main.IScoor));
            }

        }
    }


}
