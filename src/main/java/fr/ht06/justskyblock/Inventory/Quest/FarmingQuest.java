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

import java.util.List;

public class FarmingQuest implements InventoryHolder, Listener {

    Inventory inv;

    public FarmingQuest(){

    }

    public FarmingQuest(Player player, Island island){
        inv = Bukkit.createInventory(this, 54, Component.text("Farming Quest"));
        init(player, island);
    }

    private void init(Player player, Island island) {
        String crop = "";
        List<Integer> singleCropsPaliers = List.of(0, 64, 256, 576, 1000, 5000, 10000, 25000, 66666); //0 are for useless slot
        List<Integer> multipleCropsPaliers = List.of(0, 128, 512, 1296, 2500, 12500, 35000, 123456, 3333333);
        for (int i = 0; i<45; i++ ){

            ItemStack complete = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta completeMeta = complete.getItemMeta();

            ItemStack notComplete = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta notCompleteMeta = notComplete.getItemMeta();


            int j = i/9;
            if (j ==0) crop = "Wheat";
            if (j ==1) crop = "Carrot";
            if (j ==2) crop = "Potato";
            if (j ==3) crop = "Sugar_Cane";
            if (j ==4) crop = "Cactus";


            if (crop.equalsIgnoreCase("Wheat")|| crop.equalsIgnoreCase("Sugar_cane") || crop.equalsIgnoreCase("Cactus")){
                if (island.getCropsCounter().get(crop) >= singleCropsPaliers.get(i%9)){
                    StringBuilder st = new StringBuilder(crop + i%9);
                    island.getFarmingQuest().replace(st.toString(), true);
                }

                if (i%9 == 0) {
                    ItemStack itemForQuest = new ItemStack(Material.getMaterial(crop.toUpperCase()));
                    ItemMeta itemForQuestMeta = itemForQuest.getItemMeta();
                    itemForQuestMeta.displayName(Component.translatable(itemForQuest.translationKey()).append(Component.text(" Quest ->")));
                    itemForQuest.setItemMeta(itemForQuestMeta);
                    inv.setItem(i, itemForQuest);
                }

                else {
                    StringBuilder st = new StringBuilder(crop + i%9);

                    //quest OK
                    if (island.getFarmingQuest().get(st.toString())) {
                        completeMeta.displayName(Component.text(crop.replace("_", " ") + " quest level " + i%9, TextColor.color(getCropColor(crop))).decoration(TextDecoration.ITALIC, false));
                        completeMeta.lore(List.of(Component.text("You have already complete this quest", TextColor.color(0x27AE60))));
                        complete.setItemMeta(completeMeta);
                        inv.setItem(i, complete);
                    }
                    //quest Not OkK
                    else {
                        notCompleteMeta.displayName(Component.text(crop.replace("_", " ") + " quest level " + i%9, TextColor.color(getCropColor(crop))).decoration(TextDecoration.ITALIC, false));
                        notCompleteMeta.lore(List.of(Component.text(crop.replace("_", " ")
                                        +  " progression: "
                                        + island.getCropsCounter().get(crop)
                                        +"/"
                                        + singleCropsPaliers.get(i%9), TextColor.color(0xE74C3C)),
                                Component.text("You haven't completed this quest yet", TextColor.color(0xCB4335))));
                        notComplete.setItemMeta(notCompleteMeta);
                        inv.setItem(i, notComplete);
                    }
                }
            }
            else{
                if (island.getCropsCounter().get(crop) >= multipleCropsPaliers.get(i%9)){
                    StringBuilder st = new StringBuilder(crop + i%9);
                    island.getFarmingQuest().replace(st.toString(), true);
                }


                if (i%9 == 0) {
                    ItemStack itemForQuest = new ItemStack(Material.getMaterial(crop.toUpperCase()));
                    ItemMeta itemForQuestMeta = itemForQuest.getItemMeta();
                    itemForQuestMeta.displayName(Component.translatable(itemForQuest.translationKey()).append(Component.text(" Quest ->")));
                    itemForQuest.setItemMeta(itemForQuestMeta);
                    inv.setItem(i, itemForQuest);
                } else {
                    StringBuilder st = new StringBuilder(crop + i%9);

                    //quest OK
                    if (island.getFarmingQuest().get(st.toString())) {
                        completeMeta.displayName(Component.text(crop.replace("_", " ") + " quest level " + i%9, TextColor.color(getCropColor(crop))).decoration(TextDecoration.ITALIC, false));
                        completeMeta.lore(List.of(Component.text("You have already complete this quest", TextColor.color(0x27AE60))));
                        complete.setItemMeta(completeMeta);
                        inv.setItem(i, complete);
                    }
                    //quest Not OkK
                    else {
                        notCompleteMeta.displayName(Component.text(crop.replace("_", " ") + " quest level " + i%9, TextColor.color(getCropColor(crop))).decoration(TextDecoration.ITALIC, false));
                        notCompleteMeta.lore(List.of(Component.text(crop.replace("_", " ")
                                +  " progression: "
                                + island.getCropsCounter().get(crop)
                                +"/"
                                + multipleCropsPaliers.get(i%9), TextColor.color(0xE74C3C)),
                                Component.text("You haven't completed this quest yet", TextColor.color(0xCB4335))));
                        notComplete.setItemMeta(notCompleteMeta);
                        inv.setItem(i, notComplete);
                    }
                }
            }


        }

        ItemStack compost = new ItemStack(Material.COMPOSTER, 1);
        ItemMeta compostMeta = compost.getItemMeta();
        compostMeta.displayName(Component.text("SOON", TextColor.color(0xC0392B)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true));
        compostMeta.lore(List.of(Component.text("Start make farm, this will be useful for later ;)", TextColor.color(0x707B7C))));
        compost.setItemMeta(compostMeta);
        inv.setItem(49, compost);


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

        if (event.getClickedInventory().getHolder() instanceof FarmingQuest) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);
            if (event.getSlot() == 45) player.performCommand("is quest");

            if (event.getSlot() == 53){
                player.openInventory(new FarmingQuest2(player, island).getInventory());
            }
        }
    }

    private int getCropColor(String crop){
        if (crop.equalsIgnoreCase("Wheat")) return 0xF7DC6F;
        if (crop.equalsIgnoreCase("Carrot")) return 0xDC7633;
        if (crop.equalsIgnoreCase("Potato")) return 0xC1A661;
        if (crop.equalsIgnoreCase("Sugar_cane")) return 0x61A228;
        if (crop.equalsIgnoreCase("Cactus")) return 0x196F3D ;
        return 0x707B7C;
    }
}
