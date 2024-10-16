package fr.ht06.justskyblock.Inventory.upgrade;

import fr.ht06.justskyblock.CreateItem;
import fr.ht06.justskyblock.Events.PlayerListeners;
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

import java.util.ArrayList;
import java.util.List;

public class CustomCobbleGenUpgrade implements InventoryHolder, Listener {
    MiniMessage miniMessage =  MiniMessage.miniMessage();
    Inventory inv;
    public CustomCobbleGenUpgrade(Player player, Island island){
        inv = Bukkit.createInventory(this, 45, Component.text("Cobblestone generator upgrade"));
        init(player, island);

    }

    public CustomCobbleGenUpgrade(){}

    private void init(Player player, Island island) {
        //inv.setItem(0, JustSkyblock.createItem(Component.text("Tier 1"), 1, Material.COBBLESTONE, List.of(Component.text("tier 1"))));
        List<Material> materialList = new ArrayList<>(List.of(Material.COBBLESTONE, Material.COAL, Material.IRON_INGOT,
                Material.GOLD_INGOT, Material.REDSTONE, Material.DIAMOND, Material.EMERALD));
        int i = 1;
        int place = 10;
        ItemStack itemStack = null;

        // for the 2nd row of the inv
        for (int nbr = 0; nbr < 7 ;nbr++){
            itemStack = CreateItem.createItem(
                    Component.text("Tier " + (nbr+1) + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                    i,
                    materialList.get(nbr),
                    List.of(miniMessage.deserialize(JustSkyblock.getInstance().getConfig().getString("CustomGenerator.name."+(nbr+1)))));
            inv.setItem(place, itemStack);
            place++;
        }

        //for the 3rd row of the inv (% of every block)
        for (int lvl = 1; lvl <= 7; lvl++) {
            List<Component> lore = new ArrayList<>();
            Component component;
            for (Object s : JustSkyblock.getInstance().getConfig().getList("CustomGenerator.level." + lvl)) {
                String block = s.toString().split(":")[0];
                if (s.toString().contains(".")) {
                    float chance = Float.parseFloat(s.toString().split(":")[1]);
                    String msg = PlayerListeners.capitalizeFirstAndAfterUnderscore(block) + ": " + chance + " %";
                    component = miniMessage.deserialize(getBlockColor(PlayerListeners.capitalizeFirstAndAfterUnderscore(block))+ msg);
                } else {
                    int chance = Integer.parseInt(s.toString().split(":")[1]);
                    String msg = PlayerListeners.capitalizeFirstAndAfterUnderscore(block) + ": " + chance + " %";
                    component = miniMessage.deserialize(getBlockColor(PlayerListeners.capitalizeFirstAndAfterUnderscore(block))+ msg);                }
                lore.add(component);
            }
            ItemStack tier = CreateItem.createItem(Component.text("Tier "+lvl,TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false), 1, Material.CHEST, lore);

            inv.setItem(18 + lvl, tier);
        }


        //for the 3rd row of the inv (% of every block)
        for (int lvl = 1; lvl < 8; lvl++) {
            List<Component> lore = new ArrayList<>();
            Component component;
            ItemStack selectTier = CreateItem.createItem(Component.text("Tier "+(lvl),TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false), 1, Material.GREEN_STAINED_GLASS_PANE, lore);
            ItemStack unlockedTier = CreateItem.createItem(Component.text("Tier "+(lvl),TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false), 1, Material.YELLOW_STAINED_GLASS_PANE, lore);
            ItemStack lockedTier = CreateItem.createItem(Component.text("Tier "+(lvl),TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false), 1, Material.RED_STAINED_GLASS_PANE, lore);

            if (island.getCobbleGenLevelUnlock() >= lvl){
                unlockedTier.lore(List.of(Component.text("Click Here to select level "+ lvl, TextColor.color(0xf4d03f))));
                inv.setItem(27 + lvl, unlockedTier);
            }
            if (island.getCobbleGenLevel() == lvl){
                selectTier.lore(List.of(Component.text("Your generator is currently level "+lvl, TextColor.color(0x28b463))));
                inv.setItem(27 + lvl, selectTier);
            }
            if (island.getCobbleGenLevelUnlock() < lvl){
                lockedTier.lore(List.of(Component.text("Click here to unlock the next level", TextColor.color(0xcb4335))));
                inv.setItem(27 + lvl, lockedTier);
            }

        }
        ItemStack mainpage = new ItemStack(Material.ARROW, 1);
        ItemMeta mainPageMeta = mainpage.getItemMeta();
        mainPageMeta.displayName(Component.text("Go to main upgrade page").decoration(TextDecoration.ITALIC, false));
        mainpage.setItemMeta(mainPageMeta);
        inv.setItem(36, mainpage);
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

        if (event.getClickedInventory().getHolder() instanceof CustomCobbleGenUpgrade) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);
            if (event.getSlot() >= 28 && event.getSlot() <= 34){
                if (island.getCobbleGenLevelUnlock() >= event.getSlot()-27){
                    island.setCobbleGenLevel(event.getSlot()-27);
                    player.closeInventory();
                    player.openInventory(new CustomCobbleGenUpgrade(player, island).getInventory());
                }
                else {
                    player.openInventory(new UpgradeGenLvl(player, island, island.getCobbleGenLevel()).getInventory());
                }
            }
            if (event.getSlot() == 36) player.performCommand("is upgrade");
        }
    }



    public String getBlockColor(String blockName){
        //PAIN
        switch (blockName){
            case "Stone" -> {
                return "<color:#87908F>";
            }
            case "Andesite"->{
                return "<color:#585951>";
            }
            case "Gravel"->{
                return "<color:#4C4846>";
            }
            case "Granite" -> {
                return "<gradient:#864D45:#7E5546:#4B3329:#8E7B73:#7E5546>";
            }
            case "Diorite" -> {
                return "<gradient:#B8B8B8:#828080:#ede6d6:#6E6E6E:#B8B8B8>";
            }
            case "Deepslate"->{
                return "<gradient:#404040:#25252B:#303035>";
            }
            case "Coal_Ore" -> {
                return "<gradient:#3A3B32:#87908F:#1D1D1D:#3A3B32:#1D1D1D:#87908F>";
            }
            case "Deepslate_Coal_Ore" -> {
                return "<gradient:#3A3B32:#25252B:#1D1D1D:#3A3B32:#1D1D1D:#25252B>";
            }
            case "Iron_Ore" -> {
                return "<gradient:#B39886:#87908F:#6C5C43:#B39886:#6C5C43:#87908F>";
            }
            case "Deepslate_Iron_Ore" -> {
                return "<gradient:#B39886:#25252B:#6C5C43:#25252B:#B39886:#6C5C43:#25252B>";
            }
            case "Gold_Ore"->{
                return "<gradient:#E4D844:#87908F:#8D661D:#E4D844:#87908F>";
            }
            case "Deepslate_Gold_Ore"->{
                return "<gradient:#E4D844:#25252B:#8D661D:#E4D844:#25252B>";
            }
            case "Redstone_Ore"->{
                return "<gradient:#616161:#7D0505:#AE5D5D:#616161:#7D0505>";
            }
            case "Deepslate_Redstone_Ore"->{
                return "<gradient:#8C0505:#25252B:#B00606:#25252B>";
            }
            case "Diamond_Ore"->{
                return "<gradient:#0bd5d8:#585858:#1BBDC2:#585858>";
            }
            case "Deepslate_Diamond_Ore"->{
                return "<gradient:#0bd5d8:#25252B:#1BBDC2:#25252B>";
            }
            case "Emerald_Ore" -> {
                return "<gradient:#40F082:#87908F:#13661C:#40F082:#13661C:#87908F>";
            }
            case "Deepslate_Emerald_Ore" -> {
                return "<gradient:#40F082:#25252B:#13661C:#40F082:#13661C:#25252B>";
            }

        }
        return "<color:#87908F>";
    }

}
