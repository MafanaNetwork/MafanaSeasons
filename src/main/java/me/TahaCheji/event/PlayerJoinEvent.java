package me.TahaCheji.event;

import me.TahaCheji.MafanaSeasons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        MafanaSeasons.getInstance().getSeasonManager().addPlayer(event.getPlayer());
        MafanaSeasons.getInstance().getSeasonManager().getCurrentSeason().onJoin(event.getPlayer().getWorld(), event.getPlayer());
    }
}
