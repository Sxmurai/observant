package me.sxmurai.observant.check.impl.combat;

import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Checks upon an attack event if the they are able to attack an entity
 */
@Register(name = CheckType.COMBAT_RAYTRACE, category = Category.MOVING, configPath = "checks.combat.raytrace")
public class CombatWalls extends Check {
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

        // if the attacker cannot see the victim
        if (!attacker.hasLineOfSight(victim)) {
            // TODO: surrounded in an if statement if we should cancel if a violation happens
            event.setCancelled(true);

            violation.value += 0.1;

            // TODO: debug should be taken from the config
            debug(attacker, violation);
        }

        // TODO: 0.5 should be a config value
        if (violation.value >= 0.5) {
            getProfile(attacker).punish(PunishmentType.KICK, type);
        }
    }
}
