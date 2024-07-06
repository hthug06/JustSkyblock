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

public class FarmingQuest2 implements InventoryHolder, Listener {

    Inventory inv;

    public FarmingQuest2(){

    }

    public FarmingQuest2(Player player, Island island){
        inv = Bukkit.createInventory(this, 54, Component.text("Farming Quest (page 2)"));
        init(player, island);
    }

    private void init(Player player, Island island) {
        ItemStack soon = new ItemStack(Material.BEDROCK, 1);
        ItemMeta soonMeta = soon.getItemMeta();
        soonMeta.displayName(Component.text("SOON", TextColor.color(0xC0392B)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true));
        soon.setItemMeta(soonMeta);


        inv.setItem(22, soon);


        ItemStack pageBefore = new ItemStack(Material.ARROW, 1);
        ItemMeta beforeMeta = pageBefore.getItemMeta();
        beforeMeta.displayName(Component.text("Go to page 1").decoration(TextDecoration.ITALIC, false));
        pageBefore.setItemMeta(beforeMeta);
        inv.setItem(45, pageBefore);
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

        if (event.getClickedInventory().getHolder() instanceof FarmingQuest2) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);
            if (event.getSlot() == 45){
                player.openInventory(new FarmingQuest(player, island).getInventory());
            }
        }
    }

}
