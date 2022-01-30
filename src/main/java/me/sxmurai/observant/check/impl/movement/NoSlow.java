package me.sxmurai.observant.check.impl.movement;

import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import me.sxmurai.observant.profile.move.MovementData;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Checks if a player is going too fast while using an item
 */
@Register(name = CheckType.MOVEMENT_NOSLOW, category = Category.MOVING, configPath = "checks.moving.noslow")
public class NoSlow extends Check {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // check the interaction
        if (!event.getAction().equals(Action.LEFT_CLICK_AIR) ||
                !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            return;
        }

        // if we are using an item in our hand
        if (event.useItemInHand().equals(Event.Result.ALLOW)) {
            ItemStack stack = event.getItem();

            // check if the type is edible
            if (stack == null || !stack.getType().isEdible()) {
                return;
            }

            MovementData data = getAC().getMovementManager().getMovementData(event.getPlayer());
            if (data == null) {
                return;
            }

            // get our violation
            Violation violation = getProfile(event.getPlayer()).getViolation(type);

            boolean debug = false;

            // we cannot sprint while eating
            if (event.getPlayer().isSprinting()) {
                // TODO: surrounded in an if statement if we should cancel if a violation happens
                event.setCancelled(true);

                violation.value += 0.1;
                violation.addTag("sprinting");

                debug = true;
            }

            // we now check for movement
            double speed = event.getPlayer().isSneaking() ?
                    MovementValues.WALK_SLOWDOWN_SNEAK :
                    MovementValues.WALK_SLOWDOWN;

            if (data.moveSpeed > speed && data.moving && event.getPlayer().getNoDamageTicks() == 0) {
                // TODO: surrounded in an if statement if we should cancel if a violation happens
                event.setCancelled(true);

                violation.value += 0.1;
                violation.addTag("speed");

                debug = true;
            }

            if (debug) {
                debug(event.getPlayer(), violation);
            }

            if (violation.value >= 0.7) {
                getProfile(event.getPlayer()).punish(PunishmentType.KICK, type);
            }
        }
    }
}
