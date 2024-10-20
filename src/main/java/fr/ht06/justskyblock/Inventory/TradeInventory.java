package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TradeInventory implements InventoryHolder, Listener {

    Inventory inv;

    public TradeInventory(){
        this.inv = Bukkit.createInventory(this, 45, Component.text("Trading"));
        init();
    }

    private void init() {
        YamlConfiguration tradeConfig = JustSkyblock.tradeConfig;
        //for the main identifiant
        ItemStack item;
        for (String base: tradeConfig.getConfigurationSection("Trade.").getKeys(false)){

            //for the need and received
            for (String map : tradeConfig.getConfigurationSection("Trade."+base).getKeys(false)){

            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inv;
    }
}
