package me.sxmurai.observant.check.impl.movement;

import com.google.common.collect.Lists;
import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import me.sxmurai.observant.profile.move.MovementData;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

/**
 * Our inventory move check
 *
 * Checks if they are clicking in their inventory while moving around
 */
@Register(name = CheckType.MOVEMENT_INVENTORY, category = Category.MOVING, configPath = "checks.moving.invmove")
public class InventoryMove extends Check {
    /**
     * Inventory interactions that we can check for
     */
    public static final List<InventoryAction> WHITELIST = Lists.newArrayList(
            InventoryAction.PICKUP_ALL,
            InventoryAction.PICKUP_SOME,
            InventoryAction.PICKUP_ONE,
            InventoryAction.PICKUP_HALF
    );

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // If the inventory action is not whitelisted to be checked
        if (!WHITELIST.contains(event.getAction())) {
            return;
        }

        // get our player that clicked
        Player player = (Player) event.getWhoClicked();
        if (player == null) {
            return;
        }

        // get our violation
        Violation violation = getProfile(player).getViolation(type);

        boolean debug = false;

        // if the player is somehow sprinting while in a gui
        if (player.isSprinting()) {
            // TODO: surrounded in an if statement if we should cancel if a violation happens
            event.setCancelled(true);

            violation.value += 0.1;
            violation.addTag("sprinting");

            debug = true;
        }

        // get the players movement data
        MovementData data = getAC().getMovementManager().getMovementData(player);
        if (data == null) {
            return;
        } else {
            // if the move speed is greater than 0 and we're moving
            // however, we have to take into account if they are being attacked while in the inventory GUI
            // which without that extra check would flag a legit player
            if (data.moveSpeed != 0.0 && data.moving && player.getNoDamageTicks() == 0) {
                // TODO: surrounded in an if statement if we should cancel if a violation happens
                event.setCancelled(true);

                violation.value += 0.1;
                violation.addTag("moving_nodamage");

                debug = true;
            }
        }

        if (debug) {
            // TODO: debug should be taken from the config
            debug(player, violation);
        }

        // TODO: 0.4 should be the config value
        if (violation.value >= 0.4) {
            getProfile(player).punish(PunishmentType.KICK, type);
        }
    }
}
