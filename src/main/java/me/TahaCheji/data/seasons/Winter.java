package me.TahaCheji.data.seasons;

import me.TahaCheji.data.Season;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Winter extends Season {
    public Winter() {
        super("Winter", ChatColor.AQUA + "❄", 3);
    }


    @Override
    public boolean whileThere(World world) {
        for (Chunk chunk : world.getLoadedChunks()) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int y = world.getHighestBlockYAt(chunk.getBlock(x, 0, z).getLocation());
                    Block block = chunk.getBlock(x, y, z);
                    world.setBiome(block.getLocation(), Biome.SNOWY_TAIGA);
                }
            }
        }
        return true;
    }


    @Override
    public boolean eventOne(World world) {
        world.setStorm(true);
        world.setWeatherDuration(3000);
        world.setThundering(false);
        world.setThunderDuration(0);
        return true;
    }
}
