package me.sxmurai.observant.check;

import me.sxmurai.observant.Observant;
import me.sxmurai.observant.profile.Profile;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import me.sxmurai.observant.util.internal.Wrapper;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Check implements Wrapper, Listener {
    protected final CheckType type;
    protected final String configPath;
    protected final Category category;

    public Check() {
        Register register = getClass().getDeclaredAnnotation(Register.class);

        this.type = register.name();
        this.configPath = register.configPath();
        this.category = register.category();
    }

    /**
     * Gets a players profile
     * @param player The player
     * @return A profile
     */
    public Profile getProfile(Player player) {
        return getAC().getProfileManager().get(player.getUniqueId());
    }

    /**
     * Adds a punishment to a player
     */
    public void addPunishment(Player player) {
        Profile profile = getAC().getProfileManager().get(player.getUniqueId());
        if (profile == null) {
            getAC().getLogger().info("Tried to punish an unknown player " + player.getName());
            return;
        }

        profile.punish(PunishmentType.KICK, null);
    }

    /**
     * Sends a debug statement to the player
     * @param player The player
     * @param violation The violation
     */
    public void debug(Player player, Violation violation) {
        if (!Observant.getInstance().shouldDebug()) {
            return;
        }

        String prefix = ChatColor.RED + "[Observant] " + ChatColor.RESET;
        player.sendMessage(prefix + "(" + violation.getCheckName() + "): {amount=" + violation.value + ", flags=" + String.join(",", violation.getTags()) + "}");
    }

    /**
     * Get this check's config path
     * @return a ConfigurationSection that contains the configuration for this check
     */
    public ConfigurationSection getCheckConfig() {
        return Observant.getInstance().getConfig().getConfigurationSection(configPath);
    }

    /**
     * Get the max amount of violations set in the config
     * @return the violation max amount
     */
    public double getMaxViolation() {
        return getCheckConfig().getDouble("violations");
    }

    /**
     * If we should cancel an event upon a flag
     * @return if we should cancel
     */
    public boolean shouldCancel() {
        return Observant.getInstance().getConfig().getBoolean("punishment.cancelOnFlag");
    }
}
