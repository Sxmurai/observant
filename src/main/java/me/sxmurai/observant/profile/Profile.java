package me.sxmurai.observant.profile;

import me.sxmurai.observant.Observant;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.profile.punishment.Punishment;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a player profile
 *
 * This contains player violations and punishments
 */
public class Profile {
    private final UUID uuid;

    private final Map<CheckType, Violation> violations = new ConcurrentHashMap<>();
    private final List<Punishment> punishments = new ArrayList<>();

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Punishes a player
     * @param action The punishment action (or type)
     * @param type The violation type
     */
    public void punish(PunishmentType action, CheckType type) {
        Punishment punishment = new Punishment(uuid, action, type);
        punishments.add(punishment);

        punishment.dealPunishment();
    }

    /**
     * Gets a violation
     * @param type The check name
     * @return The violation object
     */
    public Violation getViolation(CheckType type) {
        return violations.computeIfAbsent(type, (t) -> new Violation(type));
    }

    /**
     * Gets the player from the server
     * @return The player, or an OfflinePlayer
     */
    public Player getPlayer() {
        return Observant.getInstance().getServer().getPlayer(uuid);
    }
}
