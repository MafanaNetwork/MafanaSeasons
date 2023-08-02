package me.TahaCheji.event;

import me.TahaCheji.MafanaSeasons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerLeaveEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        MafanaSeasons.getInstance().getSeasonManager().removePlayer(event.getPlayer());
    }
}
