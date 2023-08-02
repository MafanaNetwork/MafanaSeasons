package me.TahaCheji.data.seasons;

import me.TahaCheji.data.Season;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Summer extends Season {
    public Summer() {
        super("Summer", ChatColor.YELLOW + "☼", 1);
    }



    @Override
    public boolean eventOne(World world) {
        for (Player player : world.getPlayers()) {
            Location playerLocation = player.getLocation();
            int radius = 128;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Location blockLocation = playerLocation.clone().add(x, y, z);
                        Block block = blockLocation.getBlock();
                        if (block.getType() == Material.SNOW || block.getType() == Material.SNOW_BLOCK) {
                            block.setType(Material.AIR);
                        }
                        if(block.getType() == Material.ICE || block.getType() == Material.PACKED_ICE) {
                            block.setType(Material.WATER);
                        }
                        world.setBiome(block.getLocation(), Biome.DESERT);
                        world.setStorm(false);
                        world.setWeatherDuration(0);
                        world.setThundering(false);
                    }
                }
            }
        }
        Fall.restoreLeaves();
        return true;
    }
}
