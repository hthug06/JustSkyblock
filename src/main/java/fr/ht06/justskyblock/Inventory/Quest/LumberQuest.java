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

public class LumberQuest implements InventoryHolder, Listener {

    Inventory inv ;

    public LumberQuest(Player player, Island island){
        inv = Bukkit.createInventory(this, 54, Component.text("Mining Quest"));
        init(player, island);
    }
    public LumberQuest(){

    }

    private void init(Player player, Island island){
        String wood_Type = "";
        List<Integer> singleMineralPaliers = List.of(0, 64, 256, 576, 1000, 5000, 10000, 25000, 66666); //0 are for useless slot

        for (int i = 0; i<45; i++ ){

            ItemStack complete = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta completeMeta = complete.getItemMeta();

            ItemStack notComplete = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta notCompleteMeta = notComplete.getItemMeta();


            int j = i/9;
            if (j == 0) wood_Type = "Oak";
            if (j == 1) wood_Type = "Spruce";
            if (j == 2) wood_Type = "Birch";
            if (j == 3) wood_Type = "Jungle";
            if (j == 4) wood_Type = "Acacia";

            if (island.getLumberCounter().get(wood_Type) >= singleMineralPaliers.get(i%9)){
                StringBuilder st = new StringBuilder(wood_Type + i%9);
                island.getLumberQuest().replace(st.toString(), true);
            }
            ItemStack itemForQuest = new ItemStack(Material.getMaterial(wood_Type.toUpperCase() + "_LOG"));
            if (i%9 == 0) {
                ItemMeta itemForQuestMeta = itemForQuest.getItemMeta();
                itemForQuestMeta.displayName(Component.translatable(itemForQuest.translationKey()).append(Component.text(" Quest ->")));
                itemForQuest.setItemMeta(itemForQuestMeta);
                inv.setItem(i, itemForQuest);
            }
            else {
                StringBuilder st = new StringBuilder(wood_Type + i%9);
                //quest OK
                if (island.getLumberQuest().get(st.toString())) {
                    completeMeta.displayName(Component.text(wood_Type.replace("_", " ") + " quest level " + i%9, TextColor.color(getMineralColor(wood_Type))).decoration(TextDecoration.ITALIC, false));
                    completeMeta.lore(List.of(Component.text("You have already complete this quest", TextColor.color(0x27AE60))));
                    complete.setItemMeta(completeMeta);
                    inv.setItem(i, complete);
                }
                //quest Not OkK
                else {
                    notCompleteMeta.displayName(Component.text(wood_Type.replace("_", " ") + " quest level " + i%9, TextColor.color(getMineralColor(wood_Type))).decoration(TextDecoration.ITALIC, false));
                    notCompleteMeta.lore(List.of(Component.text(wood_Type.replace("_", " ")
                                    +  " progression: "
                                    + island.getLumberCounter().get(wood_Type)
                                    +"/"
                                    + singleMineralPaliers.get(i%9), TextColor.color(0xE74C3C)),
                            Component.text("You haven't completed this quest yet", TextColor.color(0xCB4335))));
                    notComplete.setItemMeta(notCompleteMeta);
                    inv.setItem(i, notComplete);
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
        mainPageMeta.displayName(Component.text("Go to main quest page").decoration(TextDecoration.ITALIC, false));
        mainpage.setItemMeta(mainPageMeta);
        inv.setItem(45, mainpage);

        ItemStack nextpage = new ItemStack(Material.ARROW, 1);
        ItemMeta nextMeta = nextpage.getItemMeta();
        nextMeta.displayName(Component.text("Go to page 2").decoration(TextDecoration.ITALIC, false));
        nextpage.setItemMeta(nextMeta);
        inv.setItem(53, nextpage);

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

        if (event.getClickedInventory().getHolder() instanceof LumberQuest) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);
            if (event.getSlot() == 45) player.performCommand("is quest");
            if (event.getSlot() == 53) player.openInventory(new LumberQuest2(player, island).getInventory());

        }
    }

    private int getMineralColor(String Mineral){
        if (Mineral.equalsIgnoreCase("Oak")) return 0xAD8D54;
        if (Mineral.equalsIgnoreCase("Spruce")) return 0x795933;
        if (Mineral.equalsIgnoreCase("Birch")) return 0xC6B579;
        if (Mineral.equalsIgnoreCase("Jungle")) return 0xA87853;
        if (Mineral.equalsIgnoreCase("Acacia")) return 0xAB5C31 ;
        return 0x707B7C;
    }

}
