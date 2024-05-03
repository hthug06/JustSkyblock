package fr.ht06.skyblockplugin.Events;

import fr.ht06.skyblockplugin.Inventory.IslandInventory;
import fr.ht06.skyblockplugin.LoadSchematic;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

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

            if (event.getSlot()< SkyblockPlugin.getInstance().getConfig().getConfigurationSection("IS.").getKeys(false).size()){

                Map<String, String> island = SkyblockPlugin.islandList.getIslandBySlot(event.getSlot());
                if (!new File(getServer().getPluginsFolder().getAbsoluteFile() + "/SkyblockPlugin/Schematic/" + island.get("Schematic")).exists()){
                    getLogger().severe("The file :'" + island.get("Schematic")+"' didn't exist");
                    player.sendMessage("The schematic of the island didn't exist, please contact an administrator");
                    return;
                }

                //Si le joueur n'a pas d'île
                if (main.hasIS.containsKey(player.getName()) && !main.hasIS.get(player.getName())) {
                    //Si quelqu'un a déjà une ile
                    if (!main.hasIS.isEmpty()) {

                        player.sendMessage("§aCreation of the Island");
                        //Pour que les joueur ai pas ls meme coordonnées (temporaire)
                        boolean find = false;
                        int x = 0;
                        int z = 0;
                        List<Integer> finale = new ArrayList<>();
                        Location loc = null;
                        while (!find) {
                            x = x + coords[random.nextInt(0, 2)];
                            z = z + coords[random.nextInt(0, 2)];
                            finale.add(x);
                            finale.add(z);
                            if (!main.CoordsTaken.containsValue(finale)) {
                                main.CoordsTaken.put(player.getName(), finale);
                                loc = new Location(Bukkit.getWorld("world_Skyblock"), x, 70, z);
                                find = true;
                            } else {
                                finale = new ArrayList<>();
                            }
                        }

                        //new LoadSchematic(loc,"world_Skyblock", "islandPlain");

                        //a régler
                        new LoadSchematic(loc, "world_Skyblock", island.get("Schematic"));

                        player.closeInventory();
                        player.teleport(new Location(Bukkit.getWorld("world_Skyblock"), x, 70, z));

                        main.hasIS.put(player.getName(), true);
                        main.IScoor.put(player.getName(), new Location(Bukkit.getWorld("world_Skyblock"), x, 70, z));
                        SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(Bukkit.getWorld("world_Skyblock"),
                                main.IScoor.get(player.getName()).getX(), 0, main.IScoor.get(player.getName()).getZ()));

                    }

                    //Si personne n'a d'île donc 1er joueur
                    else {
                        player.sendMessage("You are the first player to create an island, thank you!");
                        player.sendMessage("Creation of the world 'Skyblock'");

                        Location loc = new Location(Bukkit.getWorld("world_Skyblock"), 0, 70, 0);
                        //new LoadSchematic(loc,"world_Skyblock", "IslandPlain");
                        new LoadSchematic(loc, loc.getWorld().getName(), island.get("Schematic"));
                        player.sendMessage("§aCreation of the Island");

                        player.teleport(new Location(Bukkit.getWorld("world_Skyblock"), 0, 70, 0));
                        main.hasIS.put(player.getName(), true);
                        main.IScoor.put(player.getName(), new Location(Bukkit.getWorld("world_Skyblock"), 0, 70, 0));
                        int x = 0;
                        int z = 0;
                        List<Integer> finale = new ArrayList<>();
                        finale.add(x);
                        finale.add(z);
                        main.CoordsTaken.put(player.getName(), finale);

                        SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(Bukkit.getWorld("world_Skyblock"),
                                main.IScoor.get(player.getName()).getX(), 0, main.IScoor.get(player.getName()).getZ()));

                    }
                }


                //System.out.println(main.getServer().getWorldContainer().getAbsolutePath()+"/Skyblock/");
            }

        }




    }


}