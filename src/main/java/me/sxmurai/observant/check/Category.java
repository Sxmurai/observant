package me.sxmurai.observant.check;

/**
 * Represents a category for a check
 */
public enum Category {
    /**
     * Done upon a combat event
     *
     * This can be used for KillAura, Criticals, etc
     */
    COMBAT,

    /**
     * Done once a player is moving
     *
     * This can be used for Flight, NoSlow, InventoryMove, Jesus, etc
     */
    MOVING,

    /**
     * Done upon a world interaction from a player
     *
     * This can be used for Scaffold, AutoFish, etc
     */
    WORLD
}
