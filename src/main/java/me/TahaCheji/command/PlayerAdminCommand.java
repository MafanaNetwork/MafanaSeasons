package me.TahaCheji.command;

import me.TahaCheji.MafanaSeasons;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("mfseason")) {
            if(!sender.isOp()) {
                return true;
            }
            if(args.length == 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    // Get the current day and season
                    String currentSeason = MafanaSeasons.getInstance().getSeasonsMysql().getSeason();

                    player.sendMessage("Current Day: " + MafanaSeasons.getInstance().getSeasonsMysql().getDay());
                    player.sendMessage("Current Season: " + currentSeason);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("setDay")) {
                MafanaSeasons.getInstance().getSeasonManager().setDay(Integer.parseInt(args[1]));
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
