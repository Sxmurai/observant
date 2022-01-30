package me.sxmurai.observant.profile.violation;

import me.sxmurai.observant.check.CheckType;

import java.util.ArrayList;

/**
 * Represents an anticheat violation
 *
 * This is contained in a Map of violations in the Profile object
 *
 * This also contains tags, which tell you specifically what they are being flagged for in the check
 */
public class Violation {
    private final CheckType checkName;
    private final ArrayList<String> tags = new ArrayList<>();

    /**
     * The violation value.
     *
     * In the config, this value can be set to be anything.
     *
     * Once that value in the config is greater than this message, the anticheat will preform whatever the config told it to do
     */
    public double value = 0.0;

    public Violation(CheckType checkName) {
        this.checkName = checkName;
    }

    public CheckType getCheckName() {
        return checkName;
    }

    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public boolean containsFlag(String tag) {
        return tags.contains(tag);
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "checkName=" + checkName +
                '}';
    }
}
