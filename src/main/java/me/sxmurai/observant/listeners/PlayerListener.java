package me.sxmurai.observant.listeners;

import me.sxmurai.observant.profile.ProfileManager;
import me.sxmurai.observant.util.internal.Wrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Wrapper, Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ProfileManager profileManager = getAC().getProfileManager();
        Player player = event.getPlayer();

        // if the player isnt exempt from checks, remove it
        if (!profileManager.isExempt(player.getUniqueId())) {
            profileManager.addProfile(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        getAC().getProfileManager().removeProfile(event.getPlayer().getUniqueId());
    }
}
