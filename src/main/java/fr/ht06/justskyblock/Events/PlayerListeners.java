package fr.ht06.justskyblock.Events;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PlayerListeners implements Listener {
    JustSkyblock main;
    static IslandManager islandManager = JustSkyblock.islandManager;
    Island island;
    MiniMessage miniMessage = MiniMessage.miniMessage();


    public PlayerListeners(JustSkyblock main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendMessage(miniMessage.deserialize("<gradient:#2E86C1:#229954:#2E86C1>This server is in developpement (mainly the skyblock) "));
        player.sendMessage(miniMessage.deserialize("<gradient:#2E86C1:#229954:#2E86C1>Skyblock is in version alpha-1.5 created by me (ht06)"));
        player.sendMessage(miniMessage.deserialize("<gradient:#2E86C1:#229954:#2E86C1>NO MORE RESET! you can now play witouth fear!"));
        player.sendMessage(Component.text("If you find a bug, contact ht06 on discord").color(TextColor.color(0xE74C3C)));

        //if (islandManager.playerHasIsland(player.getName())) island = islandManager.getIslandbyplayer(player.getName()).getIsland();
    }



    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        //player.sendMessage(event.getBlock().getType().name());

        //Exceptions (like carrot =) carrots)
        Map<String, String> exceptionsFarming = new HashMap<>();
        exceptionsFarming.put("CARROTS", "CARROT");
        exceptionsFarming.put("POTATOES", "POTATO");

        Map<String, String> exceptionsMining = new HashMap<>();
        exceptionsMining.put("Coal", "Coal_Ore");
        exceptionsMining.put("Iron", "Iron_Ore");
        exceptionsMining.put("Copper", "Copper_Ore");
        exceptionsMining.put("Lapis_lazuli", "Lapis_Ore");


        //if (islandManager.playerHasIsland(player.getName())) island = islandManager.getIslandbyplayer(player.getName()).getIsland();
        if (!player.isOp()){
            if (!islandManager.playerHasIsland(player.getName())){
                event.setCancelled(true);
                return;
            }

            Island island = islandManager.getIslandbyplayer(player.getName());

            if (!onHisIsland(player)){
                event.setCancelled(true);
            }
        }

        //Delete block in placebyPlayer
       /* if (JustSkyblock.placeByPlayer.contains(event.getBlock().getLocation())){
            JustSkyblock.placeByPlayer.remove(event.getBlock().getLocation());

            Block eventBlock = event.getBlock();
            Block block = event.getBlock();
            while (block.getType().equals(eventBlock.getType())){
                block = block.getRelative(BlockFace.UP).getState().getBlock();
                JustSkyblock.placeByPlayer.remove(block.getLocation());
                if (!block.getType().equals(eventBlock.getType())){
                    break;
                }
            }
        }*/
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDrop(BlockDropItemEvent event){
        Player player = event.getPlayer();

        //Exceptions (like carrot =) carrots)
        Map<String, String> exceptions = new HashMap<>();
        exceptions.put("CARROTS", "CARROT");
        exceptions.put("POTATOES", "POTATO");

        List<Material> age15 = new ArrayList<>(List.of(Material.CACTUS, Material.SUGAR_CANE));

        if (!player.isOp()){
            if (!islandManager.playerHasIsland(player.getName())){
                event.setCancelled(true);
                return;
            }

            if (!onHisIsland(player)){
                event.setCancelled(true);
                return;
            }

            Island island = islandManager.getIslandbyplayer(player.getName());

            for (Item item : event.getItems()) {

                //For Farming
                if (island.getCropsCounter().containsKey(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name()))) {
                    org.bukkit.block.data.Ageable ageable = (org.bukkit.block.data.Ageable) event.getBlockState().getBlockData();
                    //Check for block like sugar can and cactus
                    if (age15.contains(event.getBlockState().getType()) ){
                        Location location = event.getBlockState().getLocation();

                        //if the block isn't place by the player && if this is not the base block
                        if (event.getBlockState().getBlock().getRelative(BlockFace.DOWN).getType().equals(event.getBlockState().getType())
                                && !JustSkyblock.placeByPlayer.contains(location)){

                            //Add to the counter
                            island.getCropsCounter().replace(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name()),
                                    island.getCropsCounter().get(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name())) + 1);
                            JustSkyblock.placeByPlayer.remove(location);

                            //Now check for the block on top of the other
                            Block eventBlock = event.getBlockState().getBlock();
                            Block block = event.getBlockState().getBlock();
                            while (block.getType().equals(eventBlock.getType())) {
                                block = block.getRelative(BlockFace.UP).getState().getBlock();

                                if (block.getType().equals(Material.AIR)) return;
                                island.getCropsCounter().replace(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name()),
                                        island.getCropsCounter().get(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name())) + 1);

                                if (JustSkyblock.placeByPlayer.contains(block.getLocation())) {
                                    JustSkyblock.placeByPlayer.remove(block.getLocation());
                                    if (!block.getType().equals(eventBlock.getType())) {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    //Check for basic crops like wheat, carrots, potato...
                    if (!(ageable.getAge() == ageable.getMaximumAge())) return;

                    if (!item.getItemStack().getType().name().contains("SEED")) {
                        if (exceptions.containsKey(event.getBlockState().getType().name())) {
                            String key = capitalizeFirstAndAfterUnderscore(exceptions.get(event.getBlockState().getType().name()));
                            String fKey = capitalizeFirstAndAfterUnderscore(key);
                            island.getCropsCounter().replace(fKey, island.getCropsCounter().get(fKey) + item.getItemStack().getAmount());
                        }
                        else{
                            String fKey = capitalizeFirstAndAfterUnderscore(event.getBlockState().getType().name());
                            island.getCropsCounter().replace(fKey, island.getCropsCounter().get(fKey) + item.getItemStack().getAmount());
                        }
                    }
                    return;
                }

                String name;
                //For Mining
                name = capitalizeFirstAndAfterUnderscore(event.getBlockState().getType().name().replace("LAPIS", "Lapis_lazuli").replace("_ORE", "")).replace("Lazuli", "lazuli");
                if (island.getMineralCounter().containsKey(name)){
                    if (JustSkyblock.placeByPlayer.contains(event.getBlockState().getLocation())){
                        JustSkyblock.placeByPlayer.remove(event.getBlockState().getLocation());
                    }
                    else {
                        //player.sendMessage(String.valueOf(JustSkyblock.placeByPlayer.contains(event.getBlockState().getLocation())));
                        island.getMineralCounter().replace(name, island.getMineralCounter().get(name) + item.getItemStack().getAmount());
                    }
                }

                //For Wood Cutting
                name = capitalizeFirstAndAfterUnderscore(event.getBlockState().getType().name().replace("_LOG", "").replace("_STEM", ""));
                if (island.getLumberCounter().containsKey(name)){
                    if (JustSkyblock.placeByPlayer.contains(event.getBlockState().getLocation())){
                        JustSkyblock.placeByPlayer.remove(event.getBlockState().getLocation());
                    }
                    else {
                        island.getLumberCounter().replace(name, island.getLumberCounter().get(name) + item.getItemStack().getAmount());
                    }
                }
            }

        }
        //If OP players want to play
        else{
            Island island = islandManager.getIslandbyplayer(player.getName());

            for (Item item : event.getItems()) {

                //For Farming
                if (island.getCropsCounter().containsKey(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name()))) {
                    org.bukkit.block.data.Ageable ageable = (org.bukkit.block.data.Ageable) event.getBlockState().getBlockData();
                    //Check for block like sugar can and cactus
                    if (age15.contains(event.getBlockState().getType()) ){
                        Location location = event.getBlockState().getLocation();

                        //if the block isn't place by the player && if this is not the base block
                        if (event.getBlockState().getBlock().getRelative(BlockFace.DOWN).getType().equals(event.getBlockState().getType())
                        && !JustSkyblock.placeByPlayer.contains(location)){

                            //Add to the counter
                            island.getCropsCounter().replace(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name()),
                                    island.getCropsCounter().get(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name())) + 1);
                            JustSkyblock.placeByPlayer.remove(location);

                            //Now check for the block on top of the other
                            Block eventBlock = event.getBlockState().getBlock();
                            Block block = event.getBlockState().getBlock();
                            while (block.getType().equals(eventBlock.getType())) {
                                block = block.getRelative(BlockFace.UP).getState().getBlock();

                                if (block.getType().equals(Material.AIR)) return;
                                island.getCropsCounter().replace(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name()),
                                        island.getCropsCounter().get(capitalizeFirstAndAfterUnderscore(item.getItemStack().getType().name())) + 1);

                                if (JustSkyblock.placeByPlayer.contains(block.getLocation())) {
                                    JustSkyblock.placeByPlayer.remove(block.getLocation());
                                    if (!block.getType().equals(eventBlock.getType())) {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    //Check for basic crops like wheat, carrots, potato...
                    if (!(ageable.getAge() == ageable.getMaximumAge())) return;

                    if (!item.getItemStack().getType().name().contains("SEED")) {
                        if (exceptions.containsKey(event.getBlockState().getType().name())) {
                            String key = capitalizeFirstAndAfterUnderscore(exceptions.get(event.getBlockState().getType().name()));
                            String fKey = capitalizeFirstAndAfterUnderscore(key);
                            island.getCropsCounter().replace(fKey, island.getCropsCounter().get(fKey) + item.getItemStack().getAmount());
                        }
                        else{
                            String fKey = capitalizeFirstAndAfterUnderscore(event.getBlockState().getType().name());
                            island.getCropsCounter().replace(fKey, island.getCropsCounter().get(fKey) + item.getItemStack().getAmount());
                        }
                    }
                    return;
                }

                String name;
                //For Mining
                name = capitalizeFirstAndAfterUnderscore(event.getBlockState().getType().name().replace("LAPIS", "Lapis_lazuli").replace("_ORE", "")).replace("Lazuli", "lazuli");
                if (island.getMineralCounter().containsKey(name)){
                    if (JustSkyblock.placeByPlayer.contains(event.getBlockState().getLocation())){
                        JustSkyblock.placeByPlayer.remove(event.getBlockState().getLocation());
                    }
                    else {
                        //player.sendMessage(String.valueOf(JustSkyblock.placeByPlayer.contains(event.getBlockState().getLocation())));
                        island.getMineralCounter().replace(name, island.getMineralCounter().get(name) + item.getItemStack().getAmount());
                    }
                }

                //For Wood Cutting
                name = capitalizeFirstAndAfterUnderscore(event.getBlockState().getType().name().replace("_LOG", "").replace("_STEM", ""));
                if (island.getLumberCounter().containsKey(name)){
                    if (JustSkyblock.placeByPlayer.contains(event.getBlockState().getLocation())){
                        JustSkyblock.placeByPlayer.remove(event.getBlockState().getLocation());
                    }
                    else {
                        island.getLumberCounter().replace(name, island.getLumberCounter().get(name) + item.getItemStack().getAmount());
                    }
                }
            }
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        List<Material> materialList = new ArrayList<>(List.of(Material.CACTUS, Material.SUGAR_CANE, //Farming
                Material.STONE, Material.COAL, Material.IRON_ORE, Material.COPPER_ORE, Material.LAPIS_ORE,  //Mining
                Material.GOLD_ORE, Material.REDSTONE_ORE, Material.EMERALD_ORE, Material.DIAMOND_ORE, Material.OBSIDIAN, //Mining 2
                Material.OAK_LOG, Material.SPRUCE_LOG, Material.ACACIA_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG,   //Wood
                Material.DARK_OAK_LOG, Material.MANGROVE_LOG, Material.CHERRY_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM));        //Wood 2
        if (materialList.contains(event.getBlockPlaced().getType()) && !(JustSkyblock.placeByPlayer.contains(event.getBlockPlaced().getLocation()))){
            JustSkyblock.placeByPlayer.add(event.getBlockPlaced().getLocation());
        }

        if (!player.isOp()){
            if (!islandManager.playerHasIsland(player.getName())){
                event.setCancelled(true);
                return;
            }
            if (!onHisIsland(player)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler //MARCHE SUIIII
    public void OnInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();

        //toutes les sécurités

        //si le joueur est OP/Admin/à la perm il bypass
        if (player.isOp() || player.hasPermission("justskyblock.bypass")) return;

        //si le joueur à une île ou n'en a pas

        island = islandManager.getIslandbyName(getAnotherPlayerIslandName(player));

        //Si le joueur essaye de cramer l'ile avec briquet /firecharge
        if (player.getInventory().getItemInMainHand().getType().equals(Material.FLINT_AND_STEEL)) event.setCancelled(true);
        if (player.getInventory().getItemInMainHand().getType().equals(Material.FIRE_CHARGE)) event.setCancelled(true);

        //Si le joueur n'est pas dans le monde des îles
        if (!player.getWorld().getName().equalsIgnoreCase("world_Skyblock")) return;


        List<Material> listMat = new ArrayList<Material>();
        for (Map.Entry<String, Boolean> v : island.getAllSettings().entrySet()){
            listMat.add(Material.getMaterial(v.getKey()));
        }

        if (!onHisIsland(player)){
            //permet d'enelver une erreur que j'ai pas vraiment compris
            //en gros quand on clique comme un gogole dans tout les sens en interragissant avec un block ça met une erreur
            try {
                event.getClickedBlock().getType();
            }catch (NullPointerException ignored){
                return;
            }

            if (listMat.contains(Objects.requireNonNull(event.getClickedBlock()).getType())){
                if (!island.getAllSettings().get(event.getClickedBlock().getType().name())) event.setCancelled(true);
            }
            else if (event.getClickedBlock().getType().name().contains("BED") && !island.getAllSettings().get("WHITE_BED")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("BUTTON") && !island.getAllSettings().get("OAK_BUTTON")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("SHULKER_BOX") && !island.getAllSettings().get("SHULKER_BOX")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("CAMPFIRE") && !island.getAllSettings().get("CAMPFIRE")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.BEACON)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.COMPOSTER)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.LODESTONE)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.BEE_NEST)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.BEEHIVE)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.CHISELED_BOOKSHELF)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.COMPARATOR)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.REPEATER)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.DRAGON_EGG)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.RESPAWN_ANCHOR)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("SIGN")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("CAULDRON")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("REDSTONE")) event.setCancelled(true);
        }
    }


    @EventHandler
    public void EntityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)) return;
        else{
            Player player = (Player) event.getDamager();
            if (player.isOp() || player.hasPermission("justskyblock.bypass")) return;
        }

        if(event.getEntityType() == EntityType.END_CRYSTAL && onHisIsland((Player) event.getDamager()) ) {
            event.setCancelled(false);
        }
        else if (event.getEntityType() == EntityType.END_CRYSTAL && !onHisIsland((Player) event.getDamager())) {
            Player player = (Player) event.getDamager();
            player.sendMessage(Component.text("You are not allowed to do this"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if(event.getRecipe().getResult().hasItemFlag(ItemFlag.HIDE_ENCHANTS)){

        }
        if (!islandManager.playerHasIsland(event.getWhoClicked().getName())){
            event.setCancelled(true);
        }
    }

    public static String capitalizeFirstAndAfterUnderscore(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        str = str.toLowerCase();

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : str.toCharArray()) {
            if (c == '_') {
                result.append(c);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static boolean contains(Location loc, Location l1, Location l2) {
        return loc.getBlockX() >= l1.getBlockX() && loc.getBlockX() <= l2.getBlockX()
                && loc.getBlockY() >= l1.getBlockY() && loc.getBlockY() <= l2.getBlockY()
                && loc.getBlockZ() >= l1.getBlockZ() && loc.getBlockZ() <= l2.getBlockZ();
    }

    public static boolean onHisIsland(Player player) {
        Location loc;
        for (Island is : islandManager.getAllIsland()) {
            loc= new Location(Bukkit.getWorld("world_Skyblock"), is.getIslandCoordinates().get(0), 70, is.getIslandCoordinates().get(1));
            if (contains(player.getLocation(), loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                if (is.isOnThisIsland(player.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getAnotherPlayerIslandName(Player player) {
        Location loc;
        for (Island is : islandManager.getAllIsland()) {
            loc= new Location(Bukkit.getWorld("world_Skyblock"), is.getIslandCoordinates().get(0), 70, is.getIslandCoordinates().get(1));
            if (contains(player.getLocation(), loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                return is.getIslandName();
            }
        }
        return null;
    }

    public static String getAnotherPlayerIsland_PlayerName(Player player) {
        Location loc;
        for (Island is : islandManager.getAllIsland()) {
            loc= new Location(Bukkit.getWorld("world_Skyblock"), is.getIslandCoordinates().get(0), 70, is.getIslandCoordinates().get(1));
            if (contains(player.getLocation(), loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                return is.getOwner();
            }
        }
        return null;
    }

    public ItemStack getTier1() {
        ItemStack item;
        item = new ItemStack(Material.BEACON, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text("Tier 1 Talisman"));
        itemMeta.lore(List.of(Component.text("This is a talisman to rankup your island to tier 1 to tier 2"),
                Component.text("DON'T LOSE THIS TALISMAN")));

        itemMeta.setRarity(ItemRarity.RARE);
        itemMeta.setEnchantmentGlintOverride(true);
        item.setItemMeta(itemMeta);

        return item;
    }

}
