package me.sxmurai.observant.check.impl.movement;

import com.google.common.collect.Lists;
import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
        Location playerLocation = event.getPlayer().getLocation().subtract(0.0, 1.0, 0.0);
        World world = event.getPlayer().getWorld();

        if (!LIQUID_MATERIALS.contains(world.getBlockAt(playerLocation).getType())) {
            return;
        }


    }
}
