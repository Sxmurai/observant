package me.sxmurai.observant.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the profile system
 *
 * Profiles are used to contain individual players violation data and puishment data
 *
 * These are usually loaded from a file containing player data punishments
 */
public class ProfileManager {
    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    /**
     * UUIDs that are exempt from all anticheat checks
     */
    private final List<UUID> exempt = new ArrayList<>();

    /**
     * Adds a profile to the manager
     * @param uuid The players UUID
     */
    public void addProfile(UUID uuid) {
        profiles.put(uuid, new Profile(uuid));
    }

    /**
     * Removes a profile from the manager
     * @param uuid The players UUID
     */
    public void removeProfile(UUID uuid) {
        profiles.remove(uuid);
    }

    /**
     * Gets a player's profile
     * @param uuid The players UUID
     * @return The profile object
     */
    public Profile get(UUID uuid) {
        return profiles.computeIfAbsent(uuid, (u) -> new Profile(uuid));
    }

    /**
     * If a player is exempt from anticheat checks
     * @param uuid The UUID
     * @return what do you think
     */
    public boolean isExempt(UUID uuid) {
        return exempt.contains(uuid);
    }
}
