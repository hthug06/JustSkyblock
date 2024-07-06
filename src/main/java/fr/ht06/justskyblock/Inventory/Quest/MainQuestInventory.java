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

public class MainQuestInventory implements InventoryHolder, Listener {

    Inventory inv;
    Island island;

    public MainQuestInventory() {

    }

    public MainQuestInventory(Player player, Island island){
        inv =  Bukkit.createInventory(this, 9, Component.text("Quest"));
        this.island = island;
        init(player, island);
    }


    private void init(Player player, Island island){
        ItemStack ranksQuest = new ItemStack(Material.BEACON, island.getRank());
        ItemMeta ranksMeta = ranksQuest.getItemMeta();
        ranksMeta.displayName(Component.text("Ranks Quest").decoration(TextDecoration.ITALIC, false));
        ranksMeta.lore(List.of(Component.text("Follow your ranks progression here", TextColor.color(0x67AB69))));
        ranksQuest.setItemMeta(ranksMeta);

        inv.setItem(0, ranksQuest);

        ItemStack farmingQuest = new ItemStack(Material.STONE_HOE);
        ItemMeta farmingMeta = farmingQuest.getItemMeta();
        farmingMeta.displayName(Component.text("Farming Quest").decoration(TextDecoration.ITALIC, false));
        farmingMeta.lore(List.of(Component.text("Every quest related to farming here", TextColor.color(0x67AB69))));
        farmingQuest.setItemMeta(farmingMeta);

        inv.setItem(1, farmingQuest);

        ItemStack miningQuest = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta miningMeta = miningQuest.getItemMeta();
        miningMeta.displayName(Component.text("Mining Quest").decoration(TextDecoration.ITALIC, false));
        miningMeta.lore(List.of(Component.text("Every quest related to mining here", TextColor.color(0x67AB69))));
        miningQuest.setItemMeta(miningMeta);

        inv.setItem(2, miningQuest);

        ItemStack LumberQuest = new ItemStack(Material.STONE_AXE);
        ItemMeta LumberMeta = LumberQuest.getItemMeta();
        LumberMeta.displayName(Component.text("Farming Quest").decoration(TextDecoration.ITALIC, false));
        LumberMeta.lore(List.of(Component.text("Every quest related to wood cutting here", TextColor.color(0x67AB69))));
        LumberQuest.setItemMeta(LumberMeta);

        inv.setItem(3, LumberQuest);
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

        if (event.getClickedInventory().getHolder() instanceof MainQuestInventory) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);
            if (event.getSlot() == 0){
                player.openInventory(new RankQuestInventory(player, island).getInventory());
            }
            if (event.getSlot() == 1){
                player.openInventory(new FarmingQuest(player, island).getInventory());
            }
            if (event.getSlot() ==2){
                player.openInventory(new MiningQuest(player, island).getInventory());
            }
            if (event.getSlot() ==3){
                player.openInventory(new LumberQuest(player, island).getInventory());
            }
        }
    }
}
