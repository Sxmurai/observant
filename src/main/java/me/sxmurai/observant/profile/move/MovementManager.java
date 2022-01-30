package me.sxmurai.observant.profile.move;

import me.sxmurai.observant.util.internal.Wrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MovementManager implements Wrapper, Listener {
    private final Map<UUID, MovementData> movement = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        double x = event.getFrom().getX() - event.getTo().getX();
        double z = event.getFrom().getZ() - event.getTo().getZ();

        MovementData data = movement.computeIfAbsent(event.getPlayer().getUniqueId(), (i) -> new MovementData());

        data.moveSpeed = Math.sqrt(x * x + z * z);
        data.lastTickPos = event.getFrom();
        data.moving = x != 0.0 && z != 0.0;

        // below line is for sniffing movement speeds
        // event.getPlayer().sendMessage(String.valueOf(data.moveSpeed));
    }

    /**
     * Gets a players movement data
     * @param player The player
     * @return Their MovementData object
     */
    public MovementData getMovementData(Player player) {
        return movement.computeIfAbsent(player.getUniqueId(), (i) -> new MovementData());
    }
}
