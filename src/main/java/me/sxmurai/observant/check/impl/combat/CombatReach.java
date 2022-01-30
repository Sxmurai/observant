package me.sxmurai.observant.check.impl.combat;

import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import me.sxmurai.observant.util.math.DistanceUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Represents a combat reach check
 *
 * This will check if the player is able to hit things
 */
@Register(name = CheckType.COMBAT_REACH, category = Category.COMBAT, configPath = "checks.combat.range")
public class CombatReach extends Check {
    @EventHandler
    public void onPlayerCombat(EntityDamageByEntityEvent event) {
        // if the event was not an attack by an entity
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            return;
        }

        // check if the entity attacking another entity is a player
        if (!(event.getDamager() instanceof Player attacker)) {
            return;
        }

        // get our violation object
        Violation violation = getProfile(attacker).getViolation(type);

        // get the victim
        Entity victim = event.getEntity();

        double reach = getReachDistance(attacker);

        // if the distance the player is attacking from is greater than reach (squared), we can add a violation
        if (DistanceUtil.getDistanceSq(attacker.getLocation(), victim.getLocation()) > reach * reach) {
            // TODO: surrounded in an if statement if we should cancel if a violation happens
            event.setCancelled(true);

            violation.value += 0.1;

            // TODO: debug should be taken from the config
            debug(attacker, violation);
        }

        // if the violation value is over the threshold
        // TODO: 0.3 should be a config value
        if (violation.value >= 0.3) {
            getProfile(attacker).punish(PunishmentType.KICK, type);
        }
    }

    /**
     * Gets a players reach distance
     * @param player The player
     * @return The reach distance
     */
    private double getReachDistance(Player player) {
        // get our initial reach distance
        // TODO: load from config
        double initial = 3.3;

        // players in creative mode have an extended reach
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            initial = 5.0;
        }

        return initial;
    }
}
