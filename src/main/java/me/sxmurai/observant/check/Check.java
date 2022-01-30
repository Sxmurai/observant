package me.sxmurai.observant.check;

import me.sxmurai.observant.profile.Profile;
import me.sxmurai.observant.profile.punishment.PunishmentType;
import me.sxmurai.observant.profile.violation.Violation;
import me.sxmurai.observant.util.internal.Wrapper;
import org.bukkit.ChatColor;
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
        String prefix = ChatColor.RED + "[Observant] " + ChatColor.RESET;
        player.sendMessage(prefix + "(" + violation.getCheckName() + "): {amount=" + violation.value + ", flags=" + String.join(",", violation.getTags()) + "}");
    }
}
