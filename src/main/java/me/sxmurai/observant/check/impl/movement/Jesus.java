package me.sxmurai.observant.check.impl.movement;

import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

@Register(name = CheckType.MOVEMENT_JESUS, category = Category.MOVING, configPath = "checks.moving.jesus")
public class Jesus extends Check {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // if we're in a liquid already
        if (event.getPlayer().getEyeLocation().getBlock().isLiquid()) {
            return;
        }

        // get our from and to locations
        Location from = event.getFrom();
        Location to = event.getTo();

        // get the world from our player
        World world = event.getPlayer().getWorld();

        // check if we have moved into another block
        if (!world.getBlockAt(from).getType().equals(world.getBlockAt(to).getType())) {
            // figure out if we moved in or out of a liquid
            boolean in = world.getBlockAt(from).isLiquid();
            boolean out = world.getBlockAt(to).isLiquid();

            // get the violation
            Violation violation = getProfile(event.getPlayer()).getViolation(type);

            // if neither are water blocks, fuck right off
            if (!in && !out) {
                violation.reward(0.1);
                return;
            }

            // check if we are passing out of water
            // TODO: easily bypassable
            if (in && world.getBlockAt(to).isEmpty()) {

                return;
            }

            boolean debug = false;

            // easiest way we can check, is if they are on the ground while walking on water.
            // obviously, you cannot be on the fucking ground so...
            if (in && out) {
                if (event.getPlayer().isOnGround()) {
                    violation.addTag("onground");
                    violation.value += 0.1;
                }

                debug = true;
            }

            // water does not have a full bounding box, and most jesus's have a maxY of 0.99 or something instead of a full block
            // we can take advantage of this and yknow
            if ((double) ((int) from.getY()) != from.getY()) {
                violation.addTag("uneven_y");
                violation.value += 0.1;

                debug = true;
            }

            if (debug) {
                debug(event.getPlayer(), violation);
            }

            if (violation.value >= getMaxViolation()) {
                getProfile(event.getPlayer()).punish(PunishmentType.KICK, type);
            }
        }
    }
}
