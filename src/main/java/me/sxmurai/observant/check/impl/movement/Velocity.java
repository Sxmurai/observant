package me.sxmurai.observant.check.impl.movement;

import me.sxmurai.observant.check.Category;
import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.check.Register;

@Register(name = CheckType.MOVEMENT_VELOCITY, category = Category.MOVING, configPath = "checks.moving.velocity")
public class Velocity extends Check {

}
