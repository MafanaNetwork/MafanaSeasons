package me.TahaCheji.command;

import me.TahaCheji.MafanaSeasons;
import me.TahaCheji.data.seasons.Fall;
import me.TahaCheji.data.seasons.Spring;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("mfseason")) {
            if(!sender.isOp()) {
                return true;
            }
            Player p = (Player) sender;
            if(args.length == 0) {

                // Get the current day and season
                String currentSeason = MafanaSeasons.getInstance().getSeasonsMysql().getSeason();

                p.sendMessage("Current Day: " + MafanaSeasons.getInstance().getSeasonsMysql().getDay());
                p.sendMessage("Current Season: " + currentSeason);
                return true;
            }
            if(args[0].equalsIgnoreCase("removeFlowers")) {
                Spring.removeSpawnedFlowers();
                return true;
            }
            if(args[0].equalsIgnoreCase("setFlowers")) {
                Spring.spawnRandomFlowersAroundPlayer(p.getPlayer(), 15, 16);
                return true;
            }
            if(args[0].equalsIgnoreCase("removeLeaves")) {
                double chance = 0.5; // Adjust the chance as desired (between 0.0 and 1.0)
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location playerLocation = player.getLocation();
                    int radius = 5; // Adjust the radius as desired

                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -radius; y <= radius; y++) {
                            for (int z = -radius; z <= radius; z++) {
                                Location blockLocation = playerLocation.clone().add(x, y, z);
                                Block block = blockLocation.getBlock();
                                if (block.getType() == Material.OAK_LEAVES || block.getType() == Material.BIRCH_LEAVES || block.getType() == Material.SPRUCE_LEAVES) {
                                    if (Math.random() < chance) {
                                        Fall.getChangedBlocks().put(block.getLocation(), block.getType());
                                        block.setType(Material.AIR);
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("setLeaves")) {
                for (Location blockLocation : Fall.getChangedBlocks().keySet()) {
                    Block block = blockLocation.getBlock();
                    block.setType(Fall.getChangedBlocks().get(blockLocation)); // Change to the appropriate leaf material
                }
                Fall.getChangedBlocks().clear(); // Clear the list after restoring the leaves
                return true;
            }
            if(args[0].equalsIgnoreCase("setDay")) {
                MafanaSeasons.getInstance().getSeasonsMysql().setDay(Integer.parseInt(args[1]));
                return true;
            }
            if(args[0].equalsIgnoreCase("setSeason")) {
                MafanaSeasons.getInstance().getSeasonManager().changeSeason(Objects.requireNonNull(MafanaSeasons.getInstance().getSeason(args[1])));
                return true;
            }
            if(args[0].equalsIgnoreCase("nextSeason")) {
                MafanaSeasons.getInstance().getSeasonManager().nextSeason();
                return true;
            }
            if(args[0].equalsIgnoreCase("nextDay")) {
                MafanaSeasons.getInstance().getSeasonManager().nextDay();
                return true;
            }
            return true;
        }
        return false;
    }
}
