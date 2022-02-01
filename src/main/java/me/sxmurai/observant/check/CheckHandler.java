package me.sxmurai.observant.check;

import me.sxmurai.observant.check.impl.combat.CombatCriticals;
import me.sxmurai.observant.check.impl.combat.CombatReach;
import me.sxmurai.observant.check.impl.combat.CombatWalls;
import me.sxmurai.observant.check.impl.movement.InventoryMove;
import me.sxmurai.observant.check.impl.movement.Jesus;
import me.sxmurai.observant.check.impl.movement.NoSlow;
import me.sxmurai.observant.util.internal.Wrapper;
import org.bukkit.event.HandlerList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CheckHandler implements Wrapper {
    private final Map<CheckType, Check> checks = new ConcurrentHashMap<>();

    public CheckHandler() {
        // combat
        checks.put(CheckType.COMBAT_REACH, new CombatReach());
        checks.put(CheckType.COMBAT_RAYTRACE, new CombatWalls());
        checks.put(CheckType.COMBAT_CRITCALS, new CombatCriticals());

        // moving
        checks.put(CheckType.MOVEMENT_INVENTORY, new InventoryMove());
        checks.put(CheckType.MOVEMENT_JESUS, new Jesus()); // TODO: finish
        checks.put(CheckType.MOVEMENT_NOSLOW, new NoSlow()); // TODO: broken

        // world

        // check if entries should be enabled/disabled.
        checks.forEach((type, check) -> {
            if (!check.getCheckConfig().getBoolean("enabled")) {
                disable(type);
                getAC().getLogger().info("Disabled check " + type.name() + ".");
            } else {
                enable(type);
                getAC().getLogger().info("Enabled check " + type.name() + ".");
            }
        });
    }

    public void enable(CheckType type) {
        Check check = checks.getOrDefault(type, null);
        if (check == null) {
            return;
        }

        getAC().getServer().getPluginManager().registerEvents(check, getAC());
    }

    public void disable(CheckType type) {
        Check check = checks.getOrDefault(type, null);
        if (check == null) {
            return;
        }

        HandlerList.unregisterAll(check);
    }

    public Map<CheckType, Check> getChecks() {
        return checks;
    }
}
