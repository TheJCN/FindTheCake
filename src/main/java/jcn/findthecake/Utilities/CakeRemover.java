package jcn.findthecake.Utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class CakeRemover {
    private Plugin plugin;
    public CakeRemover(Plugin plugin){
        this.plugin = plugin;
    }

    public void removeCakeInRadius(int radius, World world){
        for(int x = -radius; x <= radius; x++){
            for(int y = 40; y <= 100; y++){
                for(int z = -radius; z <= radius; z++){
                    Location blockLocation = new Location(world, x, y, z);
                    Block block = blockLocation.getBlock();
                    if(block.getType() == Material.CAKE){
                        Material air = Material.AIR;
                        block.setType(air);
                    }
                }
            }
        }
    }
}
