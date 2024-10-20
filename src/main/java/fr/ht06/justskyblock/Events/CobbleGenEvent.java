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

import java.util.*;

public class CobbleGenEvent implements Listener {

    Random random = new Random();
    IslandManager islandManager=JustSkyblock.islandManager;

    @EventHandler
    public void onGenCobble(BlockFromToEvent event){

        Block source = event.getBlock();
        Block generatedBlock = event.getToBlock();
        Island island = islandManager.getIslandbyLocation(event.getBlock().getLocation());

        if (!JustSkyblock.customGeneratorConfig.getConfigurationSection("CustomGenerator").getBoolean("Enable")) return;

        if (source.getType() != Material.LAVA) {
            return;
        }

        if (!this.isCobbleGenerator(generatedBlock)) {
            return;
        }
        if (!source.getWorld().getName().equalsIgnoreCase(JustSkyblock.getInstance().getWorldName())){
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
        float luck = 0; //the chance the block appear

        for (Object s : JustSkyblock.customGeneratorConfig.getList("CustomGenerator.level."+island.getCobbleGenLevel())){ //loop for every block
            float chance = Float.parseFloat(s.toString().split(":")[1]);  //get the chance the block appear
            luck+= chance; //add to the luck
            if (nbrRandom<=luck){
                String block = s.toString().split(":")[0];
                return Material.getMaterial(block.toUpperCase());
            }
        }
        return Material.COBBLESTONE;
    }

}
