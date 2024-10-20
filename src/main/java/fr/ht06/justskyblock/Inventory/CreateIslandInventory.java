package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.CreateItem;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandByConfigYAML;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.IslandManager.IslandWorldBorder;
import fr.ht06.justskyblock.JustSkyblock;
import fr.ht06.justskyblock.LoadSchematic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class CreateIslandInventory implements InventoryHolder, Listener {
    private Inventory inv;
    private IslandManager islandManager = JustSkyblock.islandManager;
    MiniMessage miniMessage = MiniMessage.miniMessage();
    Island island;

    public CreateIslandInventory(){
        inv = Bukkit.createInventory(this, 9, Component.text("Create your Island"));  //création de l'inventaire
        init();//Pour mettre les item
    }

    private void init(){

        ItemStack item;
        List<IslandByConfigYAML> configIsland = islandManager.getAllIslandByconfigYML();

        for (IslandByConfigYAML island: configIsland){
            item = CreateItem.createItem(island.getName(), 1, island.getBlock(), island.getLore());

            inv.setItem(island.getSlot(), item);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if (Objects.requireNonNull(event.getClickedInventory()).getHolder() instanceof CreateIslandInventory){//Vérification si c'estle bon inventaire
            event.setCancelled(true);

            if (event.getSlot()< islandManager.getAllIslandByconfigYML().size()){
                IslandByConfigYAML islandByConfigYAML = islandManager.getIslandByConfigYAMLBySlot(event.getSlot());

                if (!new File(getServer().getPluginsFolder().getAbsoluteFile() + "/JustSkyblock/Schematic/" +islandManager.getIslandByConfigYAMLBySlot(event.getSlot()).getSchematic()).exists()){
                    getLogger().severe("The file schematic file of the island:"+ islandManager.getIslandByConfigYAMLBySlot(event.getSlot()).getSchematic()+ "didn't exist");
                    player.sendMessage("The schematic of the island didn't exist, please contact an administrator");
                    return;
                }

                //if the player already have an island
                if (!islandManager.playerHasIsland(player.getName())) {
                    //if someone else has an island
                    if (!islandManager.getAllIsland().isEmpty()) {
                        player.sendMessage("§aCreation of the Island");
                        List<Integer> coord = getIslandCoordinate();
                        Location islandCoord = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName())
                                ,coord.get(0)
                                ,Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()).getHighestBlockYAt(coord.get(0), coord.get(1))
                                ,coord.get(1));
                        Location loc = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()),coord.get(0), 70, coord.get(1));

                        this.island = new Island(player.getName()+"'s Island", islandCoord, loc, islandByConfigYAML.getType());
                        this.island.setOwner(player.getUniqueId());
                        JustSkyblock.islandManager.addIsland(this.island);

                        new LoadSchematic(loc, JustSkyblock.getInstance().getWorldName(), islandByConfigYAML.getSchematic());

                        player.closeInventory();
                        player.teleport(islandManager.getIslandbyplayer(player.getName()).getIslandSpawn());

                        // Set the worldBorder
                        player.setWorldBorder(IslandWorldBorder.setWorldBorder(this.island));

                        Component title = miniMessage.deserialize("<gradient:#52BE80:#5499C7:#52BE80><u><b>Welcome to Skyblock");
                        Component subtitle = Component.text("If you are lost, use /is help", TextColor.color(0xccd1d1));

                        Title mainTitle = Title.title(title, subtitle, Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(1)));

                        player.showTitle(mainTitle);

                    }

                    //If no one has an island (the first player)
                    else {
                        player.sendMessage("You are the first player to create an island, thank you!");
                        player.sendMessage("Creation of the world 'Skyblock'");

                        Location loc = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), 0, 70, 0);
                        //new LoadSchematic(loc,JustSkyblock.getInstance().getWorldName(), "IslandPlain");
                        new LoadSchematic(loc, loc.getWorld().getName(), islandByConfigYAML.getSchematic());
                        player.sendMessage("§aCreation of the Island");

                        Location location = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), 0, 70, 0);

                        player.teleport(location);

                        this.island = new Island(player.getName()+"'s Island", location, loc, islandByConfigYAML.getType());
                        this.island.setOwner(player.getUniqueId());
                        islandManager.addIsland(this.island);

                        fillChest();

                        player.setWorldBorder(IslandWorldBorder.setWorldBorder(island));

                        Component title = miniMessage.deserialize("<gradient:#52BE80:#5499C7:#52BE80><u><b>Welcome to Skyblock");
                        Component subtitle = Component.text("If you are lost, use /is help", TextColor.color(0xccd1d1));

                        Title mainTitle = Title.title(title, subtitle, Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(1)));

                        player.showTitle(mainTitle);

                        Bukkit.dispatchCommand(player, "is level");

                    }
                }
            }
        }
    }

    private Location findChest() {
        //y
        for (int y = -64; y < 320/*couche max à couche min*/; y++) {
            Location isLoc1 = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), this.island.getCoordinates().getBlockX(), 70, this.island.getCoordinates()
                    .getBlockZ())
                    .clone().add(-((double) this.island.getSize() / 2), -200, -((double) this.island.getSize() / 2));
            ;
            int x1 = isLoc1.getBlockX();
            int z1 = isLoc1.getBlockZ();

            Location isLoc2 = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), this.island.getCoordinates().getBlockX(), 70, this.island.getCoordinates()
                    .getBlockZ())
                    .clone().add(((double) this.island.getSize() / 2), 300, ((double) this.island.getSize() / 2));
            ;
            int x2 = isLoc2.getBlockX();
            int z2 = isLoc2.getBlockZ();
            //x
            for (int x = x1; x < x2; x++) {
                //z
                for (int z = z1; z < z2; z++) {
                    if (Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()).getBlockAt(new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z)).getType().equals(Material.CHEST)){
                        return new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z);
                    }

                }
            }
        }
        return null; //No chest on the island
    }

    private void fillChest(){

        if (findChest() != null) {   //No chest on the island = n
            Chest chest = (Chest) Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()).getBlockAt(findChest()).getState();

            for (String toChest: JustSkyblock.getInstance().getConfig().getStringList("Island."+island.getType()+".Chest")){
                Material item = Material.getMaterial(toChest.split(":")[0].toUpperCase());
                int amount = Integer.parseInt(toChest.split(":")[1]);
                if (item != null) {
                    chest.getBlockInventory().addItem(new ItemStack(item, amount));
                }
            }
        }
    }

    public List<Integer> getIslandCoordinate() {

        List<List<Integer>> liste = new ArrayList<>();
        List<Integer> twoValue = new ArrayList<>();
        int enHaut = 1;
        int aDroite = 0;
        int enBas = 1;
        int aGauche = 0;
        while (true){
            for (int x = -enHaut * 1000; x <= enHaut * 1000; x += 1000) {
                for (int z = -enHaut * 1000; z <= enHaut * 1000; z += 1000) {
                    twoValue.add(x);
                    twoValue.add(z);
                    if (!liste.contains(twoValue) && !islandManager.IslandCoordinateTaken(twoValue)) {
                        return twoValue;
                    }
                    else {
                        liste.add(twoValue);
                    }
                    twoValue = new ArrayList<>();
                }
                twoValue = new ArrayList<>();
            }
            enHaut += 1;
            for (int x = -aDroite * 1000; x <= aDroite * 1000; x += 1000) {
                for (int z = -aDroite * 1000; z <= aDroite * 1000; z += 1000) {
                    twoValue.add(x);
                    twoValue.add(z);
                    if (!liste.contains(twoValue) && !islandManager.IslandCoordinateTaken(twoValue)) {
                        return twoValue;
                    }
                    else {
                        liste.add(twoValue);
                    }
                    twoValue = new ArrayList<>();
                }
                twoValue = new ArrayList<>();
            }
            aDroite += 1;
            for (int x = -enBas * 1000; x <= enBas * 1000; x += 1000) {
                for (int z = -enBas * 1000; z <= enBas * 1000; z += 1000) {
                    twoValue.add(x);
                    twoValue.add(z);
                    if (!liste.contains(twoValue) && !islandManager.IslandCoordinateTaken(twoValue)) {
                        return twoValue;
                    }
                    else {
                        liste.add(twoValue);
                    }
                    twoValue = new ArrayList<>();
                }
                twoValue = new ArrayList<>();
            }
            enBas += 1;
            for (int x = -aGauche * 1000; x <= aGauche * 1000; x += 1000) {
                for (int z = -aGauche * 1000; z <= aGauche * 1000; z += 1000) {
                    twoValue.add(x);
                    twoValue.add(z);
                    if (!liste.contains(twoValue) && !islandManager.IslandCoordinateTaken(twoValue)) {
                        return twoValue;
                    }
                    else {
                        liste.add(twoValue);
                    }
                    twoValue = new ArrayList<>();
                }
                twoValue = new ArrayList<>();
            }
            aGauche += 1;

        }
    }
}
