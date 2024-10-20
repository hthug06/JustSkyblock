package fr.ht06.justskyblock.Inventory.upgrade;

import fr.ht06.justskyblock.CreateItem;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static net.kyori.adventure.text.Component.text;

public class UpgradeGenLvl implements InventoryHolder, Listener {

    MiniMessage miniMessage =  MiniMessage.miniMessage();
    Inventory inv;
    public UpgradeGenLvl(Player player, Island  island, Integer lvl){
        inv = Bukkit.createInventory(this, 45, Component.text("Cobblestone generator upgrade"));
        init(player, island, lvl);

    }
    public UpgradeGenLvl(){}

    private void init(Player player, Island island, Integer lvl) {
        List<Integer> GlassPaneSlots = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18 ,26 ,27, 35, 36, 37, 38, 39, 41, 42, 43, 44);

        ItemStack itemRankup = new ItemStack(Material.DIAMOND_BLOCK, 1);
        ItemMeta rankupMeta = itemRankup.getItemMeta();
        rankupMeta.displayName(Component.text("Upgrade").decoration(TextDecoration.ITALIC, false));
        rankupMeta.lore(List.of(Component.text("Generator level: ").decoration(TextDecoration.ITALIC, false)
                        .append(text(island.getCobbleGenLevel()).decoration(TextDecoration.ITALIC, false))
                        .append(text(" -> ").decoration(TextDecoration.ITALIC, false))
                        .append(text(island.getCobbleGenLevel() +1).decoration(TextDecoration.ITALIC, false)),
                Component.text("Click here to upgrade your generator").decoration(TextDecoration.ITALIC, false)));
        rankupMeta.setEnchantmentGlintOverride(true);
        itemRankup.setItemMeta(rankupMeta);

        ItemStack noRankup = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta noRankupMeta = noRankup.getItemMeta();
        noRankupMeta.displayName(Component.text("You don't have the all the required item", TextColor.color(0xC0392B)).decoration(TextDecoration.ITALIC, false));
        noRankup.setItemMeta(noRankupMeta);

        ItemStack reloadItem = new ItemStack(Material.REDSTONE_TORCH, 1);
        ItemMeta reloadMeta = reloadItem.getItemMeta();
        reloadMeta.displayName(Component.text("Reload the page").decoration(TextDecoration.ITALIC, false));
        reloadItem.setItemMeta(reloadMeta);

        placeItemToSlot(island.getCobbleGenLevel(), player);
        Map<Material, Integer> itemMap = ListIntoMap(JustSkyblock.customGeneratorConfig .getStringList("CustomGenerator.ForUpgrade."+lvl));
        putGlassPane(playerHasItems(player, itemMap), GlassPaneSlots);
        if (playerHasItems(player, itemMap)){
            inv.setItem(22, itemRankup);
        }
        else{
            inv.setItem(22, noRankup);
        }
        inv.setItem(40, reloadItem);

//        player.sendMessage(String.valueOf(playerHasItems(player, new HashMap<Material, Integer>(Map.of(Material.COBBLESTONE, 64)))));
//        player.sendMessage(String.valueOf(playerHasItems(player, ListIntoMap(JustSkyblock.getInstance().getConfig().getStringList("CustomGenerator.ForUpgrade."+(lvl+1))))));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    public boolean playerHasItems(Player player, Map<Material, Integer> items) {
        Inventory inventory = player.getInventory();
        Map<Material, Integer> itemCount = new HashMap<>();

        for (Material material : items.keySet()) {
            itemCount.put(material, 0);
        }

        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                Material material = item.getType();
                if (itemCount.containsKey(material)) {
                    itemCount.put(material, itemCount.get(material) + item.getAmount());
                }
            }
        }

        for (Map.Entry<Material, Integer> entry : items.entrySet()) {
            Material material = entry.getKey();
            int requiredAmount = entry.getValue();
            if (itemCount.getOrDefault(material, 0) < requiredAmount) {
                return false;
            }
        }
        return true;
    }

    private Map<Material, Integer> ListIntoMap(List<String> list){
        Map<Material, Integer> FinalList = new HashMap<>();
        for (String str: list){
            FinalList.put(Material.getMaterial(str.split(":")[0].toUpperCase()),
                    Integer.valueOf(str.split(":")[1]));
        }
        return FinalList;
    }

    private void putGlassPane(Boolean hasItem, List<Integer> slot){
        ItemStack itemGlassgreen = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = itemGlassgreen.getItemMeta();
        itemMeta.setHideTooltip(true);
        itemGlassgreen.setItemMeta(itemMeta);

        ItemStack itemGlassRed = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta itemMetaRed = itemGlassRed.getItemMeta();
        itemMetaRed.setHideTooltip(true);
        itemGlassRed.setItemMeta(itemMeta);

        if (hasItem){
            for (Integer integer: slot){
                inv.setItem(integer, itemGlassgreen);
            }
        }
        else{
            for (Integer integer: slot){
                inv.setItem(integer, itemGlassRed);
            }
        }
    }

    private void placeItemToSlot(Integer level, Player player){
        List<Integer> placement = List.of(11, 13, 15, 20, 24, 29, 31, 33);
        String[] splited;
        ItemStack item;
        int i = 0;
        for (String str:JustSkyblock.customGeneratorConfig.getStringList("CustomGenerator.ForUpgrade."+level)){
            splited = str.split(":");
            item = CreateItem.createItem(null,Integer.parseInt(splited[1]), Material.getMaterial(splited[0].toUpperCase()));
            ItemMeta itemMeta = item.getItemMeta();
            AtomicInteger nbrItems = new AtomicInteger(item.getAmount());
            //player.sendMessage(String.valueOf(getNumberofItemInInv(player, item.getType()) >= Integer.parseInt(splited[1])));
            if (getNumberofItemInInv(player, item.getType()) >= Integer.parseInt(splited[1])) {    //for future update : use player.getInventory.containAtLEast
                itemMeta.lore(List.of(Component.text(getNumberofItemInInv(player, item.getType()) + "/" + Integer.parseInt(splited[1])).decoration(TextDecoration.ITALIC, false),
                        Component.text("You have enough ", TextColor.color(0x27AE60 )).decoration(TextDecoration.ITALIC, false)
                                .append(Component.translatable(item.translationKey(), TextColor.color(0x27AE60 )).decoration(TextDecoration.ITALIC, false))
                                .append(text(" to rankup", TextColor.color(0x27AE60 ))).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.ITALIC, false)));
            }
            else {
                if (item.getAmount() == 0) item.setAmount(1);
                List<Component> lore = new java.util.ArrayList<>(List.of(Component.text(getNumberofItemInInv(player, item.getType()) + "/" + Integer.parseInt(splited[1])).decoration(TextDecoration.ITALIC, false),
                        Component.text("You don't have enough ", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false)
                                .append(Component.translatable(item.translationKey(), TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false))
                                .append(text(" to rankup", TextColor.color(0xE74C3C))).decoration(TextDecoration.ITALIC, false)));
                itemMeta.lore(lore);
            }
            item.setItemMeta(itemMeta);
            inv.setItem(placement.get(i), item);
            i+=1;
        }
    }

    private Integer getNumberofItemInInv(Player player, Material item){
        int nbr = 0;
        for (ItemStack itemStack: player.getInventory().getContents()){
            if (itemStack !=null){
                if (itemStack.getType().equals(item)) nbr+= itemStack.getAmount();
            }
        }
        return nbr;
    }

    public void removeItemForUpgrade(Player player, Integer level){
        int i = 0;
//        player.sendMessage(String.valueOf(JustSkyblock.getInstance().getConfig().getStringList("CustomGenerator.ForUpgrade."+level)));
        Map<Material, Integer>itemFromTier = ListIntoMap(JustSkyblock.customGeneratorConfig.getStringList("CustomGenerator.ForUpgrade."+level));
//        player.sendMessage(String.valueOf(itemFromTier));
        for (ItemStack item: player.getInventory().getStorageContents()){
            //player.sendMessage(itemFromTier.toString());
            if (item != null) {
                if (itemFromTier.containsKey(item.getType())){
                    if (itemFromTier.get(item.getType()) > 64 || itemFromTier.get(item.getType()) > item.getAmount()){
                        itemFromTier.put(item.getType(),itemFromTier.get(item.getType()) - item.getAmount());
                        player.getInventory().getItem(i).setAmount(0);
                    }
                    else if (itemFromTier.get(item.getType()) <= item.getAmount()){
                        player.getInventory().getItem(i).setAmount(item.getAmount() - itemFromTier.get(item.getType()));
                        itemFromTier.remove(item.getType());
                    }
                }
            }
            i++;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        Island island = JustSkyblock.islandManager.getIslandbyplayer(player.getName());
        //@NotNull List<HumanEntity> human = event.getViewers();
        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof UpgradeGenLvl) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);

            if (event.getSlot() == 22 && event.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)){
                removeItemForUpgrade(player, island.getCobbleGenLevelUnlock());
                player.closeInventory();
                island.setCobbleGenLevelUnlock(island.getCobbleGenLevelUnlock()+1);
                island.setCobbleGenLevel(island.getCobbleGenLevel()+1);
                island.BroadcastMessage("Your Island Generator is now level " + island.getCobbleGenLevelUnlock());

            }

            if (event.getSlot() == 40) {
                player.closeInventory();
                player.openInventory(new UpgradeGenLvl(player, island, island.getCobbleGenLevel()).getInventory());
            }
        }
    }

}


