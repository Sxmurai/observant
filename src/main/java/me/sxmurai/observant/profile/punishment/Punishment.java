package me.sxmurai.observant.profile.punishment;

import me.sxmurai.observant.check.Check;
import me.sxmurai.observant.check.CheckType;
import me.sxmurai.observant.profile.Profile;
import me.sxmurai.observant.util.internal.Wrapper;
import org.bukkit.BanList;
import org.bukkit.ChatColor;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a punishment
 *
 * This object is used for when a punishment is given to a player once they have exceeded the violation threshold
 * specified in the config file.
 */
public class Punishment implements Wrapper {
    private final UUID uuid;
    private final PunishmentType type;
    private final CheckType checkType;

    public Punishment(UUID uuid, PunishmentType type, CheckType checkType) {
        this.uuid = uuid;
        this.type = type;
        this.checkType = checkType;
    }

    /**
     * Deals a punishment out to a player
     */
    public void dealPunishment() {
        Profile profile = getAC().getProfileManager().get(uuid);
        if (profile == null) {
            getAC().getLogger().info("Profile doesn't exist for UUID? (" + uuid + ")");
            return;
        }

        // if the config specified that we should do nothing, we do nothing.
        if (type.equals(PunishmentType.NONE)) {
            return;
        }

        String reason = "Observant has detected you cheating!\n";
        // TODO: kick type should be hidden if specified in the config
        reason += checkType;

        switch (type) {
            case KICK -> profile.getPlayer().kickPlayer(reason);
            case BAN -> getAC().getServer().getBanList(BanList.Type.NAME).addBan(
                    profile.getPlayer().getName(),
                    reason,
                    new Date(System.currentTimeMillis() + 86400000L), // TODO: get the 86400000 part from the config
                    null
            );
        }

        // TODO: brodcast is set in the config
        getAC().getServer().broadcastMessage(ChatColor.RED + "[Observant] " + ChatColor.RESET + "A player in your server has been detected cheating!");
    }

    public UUID getUuid() {
        return uuid;
    }

    public PunishmentType getType() {
        return type;
    }

    public CheckType getCheckType() {
        return checkType;
    }
}
