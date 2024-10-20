package fr.ht06.justskyblock.Inventory.rankup;

import fr.ht06.justskyblock.IslandManager.Island;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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


public class RankupInventory implements InventoryHolder {

    Inventory inv;


    public RankupInventory(Player player, Island island) {
        this.inv = Bukkit.createInventory(this, 45, text("Rankup", TextColor.color(0x85929E)));
        init(player, island);
    }

    private void init(Player player, Island island){

        List<Integer> GlassPaneSlots = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18 ,26 ,27, 35, 36, 37, 38, 39, 41, 42, 43, 44);
        ItemStack itemGlassgreen = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = itemGlassgreen.getItemMeta();
        itemMeta.setHideTooltip(true);
        itemGlassgreen.setItemMeta(itemMeta);

        ItemStack itemGlassRed = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta itemMetaRed = itemGlassRed.getItemMeta();
        itemMeta.setHideTooltip(true);
        itemGlassRed.setItemMeta(itemMeta);

        ItemStack itemRankup = new ItemStack(Material.DIAMOND_BLOCK, 1);
        ItemMeta rankupMeta = itemRankup.getItemMeta();
        rankupMeta.displayName(Component.text("RANKUP").decoration(TextDecoration.ITALIC, false));
        rankupMeta.lore(List.of(Component.text("Island rank: ").decoration(TextDecoration.ITALIC, false)
                .append(text(island.getRank()).decoration(TextDecoration.ITALIC, false))
                .append(text(" -> ").decoration(TextDecoration.ITALIC, false))
                .append(text(island.getRank() +1).decoration(TextDecoration.ITALIC, false)),
                Component.text("Click here to Rankup").decoration(TextDecoration.ITALIC, false)));
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

        if (island.getRank() == 0) {
            Map<Material, Integer> itemsToCheck = getTierItem(0);
            boolean hasItems = playerHasItems(player, itemsToCheck);
            if (hasItems) {
                for (Integer integer : GlassPaneSlots) {
                    inv.setItem(integer, itemGlassgreen);
                }

                inv.setItem(22, itemRankup);
            } else {
                for (Integer integer : GlassPaneSlots) {
                    inv.setItem(integer, itemGlassRed);
                }

                inv.setItem(22, noRankup);
            }

            inv.setItem(40, reloadItem);

            putItemToSlots(player, itemsToCheck, Material.CHAIN, 11, inv);
            putItemToSlots(player, itemsToCheck, Material.COAL, 13, inv);
            putItemToSlots(player, itemsToCheck, Material.WHEAT, 15, inv);
            putItemToSlots(player, itemsToCheck, Material.CARROT, 20, inv);
            putItemToSlots(player, itemsToCheck, Material.POTATO, 24, inv);
            putItemToSlots(player, itemsToCheck, Material.OAK_LOG, 29, inv);
            putItemToSlots(player, itemsToCheck, Material.ROTTEN_FLESH, 31, inv);
            putItemToSlots(player, itemsToCheck, Material.BONE, 33, inv);
        }
        else{

            ItemStack soon = new ItemStack(Material.BEDROCK, 1);
            ItemMeta soonMeta = soon.getItemMeta();
            soonMeta.displayName(Component.text("SOON", TextColor.color(0xC0392B)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true));
            soon.setItemMeta(soonMeta);


            inv.setItem(22, soon);
        }
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

    public void putItemToSlots(Player player, Map<Material, Integer> itemsToCheck, Material material, Integer slot, Inventory inv){
        HashMap<Integer, ? extends ItemStack> allItems = player.getInventory().all(material);
        AtomicInteger nbrItems = new AtomicInteger(0);
        allItems.forEach((integer, itemStack) ->  nbrItems.addAndGet(itemStack.getAmount()));
        ItemStack item = new ItemStack(material, nbrItems.get());
        ItemMeta itemMeta = item.getItemMeta();
        if (nbrItems.get() >= itemsToCheck.get(item.getType())) {
            itemMeta.lore(List.of(Component.text(nbrItems.get() + "/" + itemsToCheck.get(item.getType())).decoration(TextDecoration.ITALIC, false),
                    Component.text("You have enough ", TextColor.color(0x27AE60 )).decoration(TextDecoration.ITALIC, false)
                            .append(Component.translatable(item.translationKey(), TextColor.color(0x27AE60 )).decoration(TextDecoration.ITALIC, false))
                            .append(text(" to rankup", TextColor.color(0x27AE60 ))).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.ITALIC, false)));
        }
        else{
            if (item.getAmount() == 0) item.setAmount(1);
            List<Component> lore = new java.util.ArrayList<>(List.of(Component.text(nbrItems.get() + "/" + itemsToCheck.get(item.getType())).decoration(TextDecoration.ITALIC, false),
                    Component.text("You don't have enough ", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false)
                            .append(Component.translatable(item.translationKey(), TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false))
                            .append(text(" to rankup", TextColor.color(0xE74C3C))).decoration(TextDecoration.ITALIC, false),
                    Component.text(" ")));

            switch (item.getType()){
                case COAL ,CHAIN -> lore.add(Component.text("Tip : Mine at the cobblestone generator", TextColor.color(0xBFC9CA )));

                case WHEAT, POTATO, CARROT-> lore.add(Component.text("Tip : Make field and farm this crop", TextColor.color(0xBFC9CA )));

                case OAK_LOG ->  lore.add(Component.text("Tip : Never forget to replant your saplings", TextColor.color(0xBFC9CA )));

                case BONE, ROTTEN_FLESH -> lore.add(Component.text("Tip : Make a mob farm", TextColor.color(0x7F8C8D)));
            }
            itemMeta.lore(lore);

        }

        item.setItemMeta(itemMeta);
        inv.setItem(slot, item);
    }

    public Map<Material, Integer> getTierItem(Integer integer){
        if (integer == 0) {
            Map<Material, Integer> itemsToCheck = new HashMap<>();
            itemsToCheck.put(Material.CHAIN, 32);
            itemsToCheck.put(Material.COAL, 16);
            itemsToCheck.put(Material.WHEAT, 48);
            itemsToCheck.put(Material.OAK_LOG, 64);
            itemsToCheck.put(Material.POTATO, 64);
            itemsToCheck.put(Material.BONE, 32);
            itemsToCheck.put(Material.ROTTEN_FLESH, 16);
            itemsToCheck.put(Material.CARROT, 16);
            return itemsToCheck;
        }
        return null;
    }


}


