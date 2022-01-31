package me.sxmurai.observant.check.impl.movement;

import com.google.common.collect.Lists;
import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

@Register(name = CheckType.MOVEMENT_JESUS, category = Category.MOVING, configPath = "checks.moving.jesus")
public class Jesus extends Check {
    private static final List<Material> LIQUID_MATERIALS = Lists.newArrayList(
            Material.WATER,
            Material.STATIONARY_WATER,
            Material.LAVA,
            Material.STATIONARY_LAVA
    );

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        Location playerLocation = location.subtract(0.0, 1.0, 0.0);
        World world = player.getWorld();

        if (!LIQUID_MATERIALS.contains(world.getBlockAt(playerLocation).getType())) {
            return;
        }

        // check if the player is in water, if so we can just assume their swimming
        if (LIQUID_MATERIALS.contains(world.getBlockAt(event.getTo()).getType())) {
            return;
        }

        // with most jesus's, their bounding box max y is below a whole number, so we can just do a simple check
        if ((double) (int) location.getY() != location.getY()) {
            // TODO: this if statement is useless and will always flag back if the player is getting out of water.
        }
    }
}
