package me.sxmurai.observant.check;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Register {
    /**
     * The name of the check
     * @return The check name
     */
    CheckType name();

    /**
     * The check's category
     * @return The category
     */
    Category category();

    /**
     * Get the path to this check in the config
     * @return A dot notated path to the setting
     */
    String configPath();
}
