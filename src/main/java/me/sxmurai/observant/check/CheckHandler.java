package me.sxmurai.observant.check;

import me.sxmurai.observant.check.impl.combat.CombatReach;
import me.sxmurai.observant.check.impl.combat.CombatWalls;
import me.sxmurai.observant.check.impl.combat.CombatCriticals;
import me.sxmurai.observant.check.impl.movement.InventoryMove;
import me.sxmurai.observant.check.impl.movement.Jesus;
import me.sxmurai.observant.check.impl.movement.NoSlow;
import me.sxmurai.observant.util.internal.Wrapper;

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

        // register all checks as an event listener
        checks.values().forEach((check) -> getAC().getServer().getPluginManager().registerEvents(check, getAC()));
    }

    public Map<CheckType, Check> getChecks() {
        return checks;
    }
}
