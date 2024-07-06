package fr.ht06.justskyblock.Inventory.Quest;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

public class MiningQuest2 implements InventoryHolder, Listener {

    Inventory inv;

    public MiningQuest2(Player player, Island island){
        inv = Bukkit.createInventory(this, 54, Component.text("Mining Quest (Page 2)"));
        init(player, island);
    }
    public MiningQuest2(){

    }

    private void init(Player player, Island island){
        String Mineral = "";
        List<Integer> singleMineralPaliers = List.of(0, 64, 256, 576, 1000, 5000, 10000, 25000, 66666); //0 are for useless slot
        List<Integer> multipleMineralPaliers = List.of(0, 192, 768, 2500, 12500, 35000, 80000, 250000, 666666);
        Map<String, String> oreReplacement = new HashMap<>();
        oreReplacement.put("Gold", "Gold_Ingot");
        oreReplacement.put("Copper", "Copper_Ingot");

        for (int i = 0; i<45; i++ ){

            ItemStack complete = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta completeMeta = complete.getItemMeta();

            ItemStack notComplete = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta notCompleteMeta = notComplete.getItemMeta();


            int j = i/9;
            if (j == 0) Mineral = "Gold";
            if (j == 1) Mineral = "Redstone";
            if (j == 2) Mineral = "Emerald";
            if (j == 3) Mineral = "Diamond";
            if (j == 4) Mineral = "Obsidian";

            if (Mineral.equalsIgnoreCase("Gold")|| Mineral.equalsIgnoreCase("Emerald") || Mineral.equalsIgnoreCase("Diamond")|| Mineral.equalsIgnoreCase("Obsidian")){
                if (island.getMineralCounter().get(Mineral) >= singleMineralPaliers.get(i%9)){
                    StringBuilder st = new StringBuilder(Mineral + i%9);
                    island.getMiningQuest().replace(st.toString(), true);
                }
                ItemStack itemForQuest;
                if (i%9 == 0) {
                    if (oreReplacement.containsKey(Mineral)){
                        itemForQuest = new ItemStack(Material.getMaterial(Mineral.toUpperCase() + "_INGOT"));
                    }
                    else{
                        itemForQuest = new ItemStack(Material.getMaterial(Mineral.toUpperCase()));
                    }
                    ItemMeta itemForQuestMeta = itemForQuest.getItemMeta();
                    itemForQuestMeta.displayName(Component.translatable(itemForQuest.translationKey()).append(Component.text(" Quest ->")));
                    itemForQuest.setItemMeta(itemForQuestMeta);
                    inv.setItem(i, itemForQuest);
                }

                else {
                    StringBuilder st = new StringBuilder(Mineral + i%9);
                    //quest OK
                    if (island.getMiningQuest().get(st.toString())) {
                        completeMeta.displayName(Component.text(Mineral.replace("_", " ") + " quest level " + i%9, TextColor.color(getMineralColor(Mineral))).decoration(TextDecoration.ITALIC, false));
                        completeMeta.lore(List.of(Component.text("You have already complete this quest", TextColor.color(0x27AE60))));
                        complete.setItemMeta(completeMeta);
                        inv.setItem(i, complete);
                    }
                    //quest Not OkK
                    else {
                        notCompleteMeta.displayName(Component.text(Mineral.replace("_", " ") + " quest level " + i%9, TextColor.color(getMineralColor(Mineral))).decoration(TextDecoration.ITALIC, false));
                        notCompleteMeta.lore(List.of(Component.text(Mineral.replace("_", " ")
                                        +  " progression: "
                                        + island.getMineralCounter().get(Mineral)
                                        +"/"
                                        + singleMineralPaliers.get(i%9), TextColor.color(0xE74C3C)),
                                Component.text("You haven't completed this quest yet", TextColor.color(0xCB4335))));
                        notComplete.setItemMeta(notCompleteMeta);
                        inv.setItem(i, notComplete);
                    }
                }
            }
            else{
                if (island.getMineralCounter().get(Mineral) >= multipleMineralPaliers.get(i%9)){
                    StringBuilder st = new StringBuilder(Mineral + i%9);
                    island.getMiningQuest().replace(st.toString(), true);
                }


                if (i%9 == 0) {
                    ItemStack itemForQuest;
                    if (oreReplacement.containsKey(Mineral)){
                        itemForQuest = new ItemStack(Material.getMaterial(Mineral.toUpperCase() + "_INGOT"));
                    }
                    else{
                        itemForQuest = new ItemStack(Material.getMaterial(Mineral.toUpperCase()));
                    }
                    ItemMeta itemForQuestMeta = itemForQuest.getItemMeta();
                    itemForQuestMeta.displayName(Component.translatable(itemForQuest.translationKey()).append(Component.text(" Quest ->")));
                    itemForQuest.setItemMeta(itemForQuestMeta);
                    inv.setItem(i, itemForQuest);
                } else {
                    StringBuilder st = new StringBuilder(Mineral + i%9);

                    //quest OK
                    if (island.getMiningQuest().get(st.toString())) {
                        completeMeta.displayName(Component.text(Mineral.replace("_", " ") + " quest level " + i%9, TextColor.color(getMineralColor(Mineral))).decoration(TextDecoration.ITALIC, false));
                        completeMeta.lore(List.of(Component.text("You have already complete this quest", TextColor.color(0x27AE60))));
                        complete.setItemMeta(completeMeta);
                        inv.setItem(i, complete);
                    }
                    //quest Not OkK
                    else {
                        notCompleteMeta.displayName(Component.text(Mineral.replace("_", " ") + " quest level " + i%9, TextColor.color(getMineralColor(Mineral))).decoration(TextDecoration.ITALIC, false));
                        notCompleteMeta.lore(List.of(Component.text(Mineral.replace("_", " ")
                                        +  " progression: "
                                        + island.getMineralCounter().get(Mineral)
                                        +"/"
                                        + multipleMineralPaliers.get(i%9), TextColor.color(0xE74C3C)),
                                Component.text("You haven't completed this quest yet", TextColor.color(0xCB4335))));
                        notComplete.setItemMeta(notCompleteMeta);
                        inv.setItem(i, notComplete);
                    }
                }
            }
        }

        ItemStack furnace = new ItemStack(Material.FURNACE, 1);
        ItemMeta furnaceMeta = furnace.getItemMeta();
        furnaceMeta.displayName(Component.text("SOON", TextColor.color(0xC0392B)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true));
        furnaceMeta.lore(List.of(Component.text("Start make farm, this will be useful for later ;)", TextColor.color(0x707B7C))));
        furnace.setItemMeta(furnaceMeta);
        inv.setItem(49, furnace);

        ItemStack mainpage = new ItemStack(Material.ARROW, 1);
        ItemMeta mainPageMeta = mainpage.getItemMeta();
        mainPageMeta.displayName(Component.text("Go to page 1").decoration(TextDecoration.ITALIC, false));
        mainpage.setItemMeta(mainPageMeta);
        inv.setItem(45, mainpage);

        /*ItemStack nextpage = new ItemStack(Material.ARROW, 1);
        ItemMeta nextMeta = nextpage.getItemMeta();
        nextMeta.displayName(Component.text("Go to page 2").decoration(TextDecoration.ITALIC, false));
        nextpage.setItemMeta(nextMeta);
        inv.setItem(53, nextpage);*/

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }



    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        Island island = JustSkyblock.islandManager.getIslandbyplayer(player.getName());
        //@NotNull List<HumanEntity> human = event.getViewers();
        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof MiningQuest2) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);
            if (event.getSlot() == 45) player.openInventory(new  MiningQuest(player, island).getInventory());

        }
    }

    private int getMineralColor(String Mineral){
        if (Mineral.equalsIgnoreCase("Gold")) return 0xFAF25E;
        if (Mineral.equalsIgnoreCase("Redstone")) return 0xA80F01 ;
        if (Mineral.equalsIgnoreCase("Emerald")) return 0x40F082;
        if (Mineral.equalsIgnoreCase("Diamond")) return 0x49EAD6;
        if (Mineral.equalsIgnoreCase("Obsidian")) return 0x271E3C ;
        return 0x707B7C;
    }

}
