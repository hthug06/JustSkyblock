package fr.ht06.justskyblock.Events;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.List;
import java.util.Random;

public class CobbleGenEvent implements Listener {

    Random random = new Random();
    IslandManager islandManager=JustSkyblock.islandManager;

    @EventHandler
    public void onGenCobble(BlockFromToEvent event){

        Block source = event.getBlock();
        Block generatedBlock = event.getToBlock();
        Island island = islandManager.getIslandbyLocation(event.getBlock().getLocation());

        if (!JustSkyblock.getInstance().getConfig().getConfigurationSection("CustomGenerator").getBoolean("Enable")) return;

        if (source.getType() != Material.LAVA) {
            return;
        }

        if (!this.isCobbleGenerator(generatedBlock)) {
            return;
        }

        event.setCancelled(true);
        generatedBlock.setType(chooseBlock(island));

    }

    private boolean isCobbleGenerator(Block generatedBlock) {
        BlockFace[] faces = {BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

        // At some face of the generated block has to be water or lava (by secondLiquid)
        for (BlockFace face : faces) {
            Block blockNextToGenerated = generatedBlock.getRelative(face, 1);
            if (blockNextToGenerated.getType() == Material.WATER) {
                return true;
            }
        }

        return false;
    }

    private Material chooseBlock(Island island){
        int nbrRandom = random.nextInt(101);
        if (island.getCobbleGenLevel() == 0){
            List<Material> blocksLVL1 = List.of(Material.STONE, Material.GRANITE, Material.DIORITE, Material.ANDESITE,
                    Material.COAL_ORE, Material.IRON_ORE, Material.EMERALD_ORE);
            if (nbrRandom<=50){
                return blocksLVL1.get(0);
            }
            else if (nbrRandom<=65){
                return blocksLVL1.get(1);
            }
            else if (nbrRandom<=80){
                return blocksLVL1.get(2);
            }
            else if (nbrRandom<=95){
                return blocksLVL1.get(3);
            }
            else if (nbrRandom<=99){
                return blocksLVL1.get(4);
            }
            else {
                return blocksLVL1.get(5);
            }
        }
        return null;
    }

}
