package me.sxmurai.observant.check.impl.combat;

import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;
import me.sxmurai.observant.profile.move.MovementData;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

@Register(name = CheckType.COMBAT_CRITCALS, category = Category.MOVING, configPath = "checks.moving.criticals")
public class CombatCriticals extends Check {
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

        // get our movement data
        MovementData data = getAC().getMovementManager().getMovementData(attacker);
        if (data == null) {
            return;
        }

        // get our fall distance
        float fallDistance = attacker.getFallDistance() + (float) Math.max(0.0, data.lastTickPos.getY() - attacker.getLocation().getY());

        // calculate if this was a critical hit
        // this will figure out if the jump via fall distance is a critical
        if (fallDistance > 0.0f && !attacker.isOnGround() && !attacker.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            Violation violation = getProfile(attacker).getViolation(type);

            if (!isCritical(attacker)) {
                // correct damage
                event.setDamage(event.getFinalDamage() / 1.5f);

                // add a violation
                violation.value += 0.1;
                debug(attacker, violation);
            } else {
                // reward player for a legit critical
                violation.value -= 0.05;
            }

            // if we're over our violation threshold
            if (violation.value >= 0.2) {
                getProfile(attacker).punish(PunishmentType.KICK, type);
            }
        }
    }

    /**
     * Checks if it is a critical based off of the velocity.
     *
     * NOT MY METHOD, got from a forum from 2016 lol
     * https://bukkit.org/threads/detect-a-critical-hit.274601/
     *
     * @param player The player
     * @return if the hit was actually a critical
     */
    public boolean isCritical(Player player) {
        return player.getVelocity().getY() + 0.0784000015258789 <= 0.0;
    }
}
