package fr.ht06.justskyblock.Inventory.upgrade;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UpgradeMain implements InventoryHolder, Listener {

    Inventory inv;

    public UpgradeMain(Player player, Island island){
        this.inv = Bukkit.createInventory(this, 9, Component.text("Upgrade your island"));
        init(player, island);
    }
    public UpgradeMain(){}

    private void init(Player player, Island island) {
        ItemStack customGenUpgrade =new ItemStack(Material.COBBLESTONE);
        ItemMeta customMeta = customGenUpgrade.getItemMeta();
        customMeta.displayName(MiniMessage.miniMessage().deserialize("<gradient:#e67e22:#424949:#3498db>Custom generator upgrade").decoration(TextDecoration.ITALIC, false));
        customMeta.lore(List.of(Component.text("Upgrade or select the level of your custom cobblestone generator",NamedTextColor.GRAY)));
        customGenUpgrade.setItemMeta(customMeta);

        inv.setItem(0, customGenUpgrade);

        ItemStack BorderUpgrade =new ItemStack(Material.BARRIER);
        ItemMeta BorderMeta = BorderUpgrade.getItemMeta();
        BorderMeta.displayName(Component.text("Island size upgrade", TextColor.color(0x5dade2)).decoration(TextDecoration.ITALIC, false));
        BorderMeta.lore(List.of(Component.text("Learn how to increase the worldBorder of your island", NamedTextColor.GRAY)));
        BorderUpgrade.setItemMeta(BorderMeta);

        inv.setItem(1, BorderUpgrade);
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

        if (event.getClickedInventory().getHolder() instanceof UpgradeMain) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);
            if (event.getSlot() == 0) player.openInventory(new CustomCobbleGenUpgrade(player, island).getInventory());
            if (event.getSlot() == 1) {
                player.openInventory(new IslandSizeUpgrade(player).getInventory());


            }

        }
    }
}
