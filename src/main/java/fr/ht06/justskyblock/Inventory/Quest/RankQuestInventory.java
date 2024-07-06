package fr.ht06.justskyblock.Inventory.Quest;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
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

public class RankQuestInventory implements InventoryHolder, Listener {

    Inventory inv;

    public RankQuestInventory(){

    }

    public RankQuestInventory(Player player, Island island){
        inv =  Bukkit.createInventory(this, 54, Component.text("Ranks Quest"));
        init(player, island);
    }


    private void init(Player player, Island island){
        List<Integer> litseInt = List.of(0, 9, 18,  27, 36, 37, 38, 39, 40, 41, 42, 43, 44, 35, 26, 17, 8, 7, 6, 5, 4, 3, 2, 11, 20, 21, 22, 23, 24);
        ItemStack Text = new ItemStack(Material.GLASS_PANE, 1);
        ItemStack fill = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setHideTooltip(true);
        fill.setItemMeta(fillMeta);

        ItemStack rankAcquired = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta acMeta = rankAcquired.getItemMeta();

        ItemStack rankNOTAcquired = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta notAcMeta = rankNOTAcquired.getItemMeta();


        for (int i = 0; i< inv.getSize()-9; i++){
            inv.setItem(i, fill);
        }


        int j = 1;
        for (Integer i: litseInt){
            if (island.getRank() >= j){
                acMeta.displayName(Component.text("Rank " + j  + " acquired"));
                rankAcquired.setItemMeta(acMeta);
                rankAcquired.setAmount(j);

                inv.setItem(i, rankAcquired);
            }

            else{
                notAcMeta.displayName(Component.text("You don't acquired this rank"));
                notAcMeta.lore(List.of(Component.text("Click Here to go to the rankup page")));
                rankNOTAcquired.setItemMeta(notAcMeta);
                rankNOTAcquired.setAmount(j);
                inv.setItem(i, rankNOTAcquired);
            }
            j++;
        }

        ItemStack mainpage = new ItemStack(Material.ARROW, 1);
        ItemMeta mainPageMeta = mainpage.getItemMeta();
        mainPageMeta.displayName(Component.text("Go to main quest page").decoration(TextDecoration.ITALIC, false));
        mainpage.setItemMeta(mainPageMeta);
        inv.setItem(45, mainpage);

        ItemStack nextpage = new ItemStack(Material.ARROW, 1);
        ItemMeta nextMeta = nextpage.getItemMeta();
        nextMeta.displayName(Component.text("Go to page 2 (SOON)").decoration(TextDecoration.ITALIC, false));
        nextpage.setItemMeta(nextMeta);
        inv.setItem(53, nextpage);

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        Island island = JustSkyblock.islandManager.getIslandbyplayer(player.getName());

        if (event.getClickedInventory() == null) return;


        if (event.getClickedInventory().getHolder() instanceof RankQuestInventory){
            event.setCancelled(true);
            if (event.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)){
                player.performCommand("is rankup");
            }

            if (event.getSlot() == 45) player.performCommand("is quest");
            //if (event.getSlot() == 53) player.openInventory(new LumberQuest2(player, island).getInventory());
        }

    }


}
