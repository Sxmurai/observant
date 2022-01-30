package me.sxmurai.observant;

import me.sxmurai.observant.check.CheckHandler;
import me.sxmurai.observant.profile.ProfileManager;
import me.sxmurai.observant.profile.move.MovementManager;
import me.sxmurai.observant.util.ReflectionUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class Observant extends JavaPlugin {
    public static final String VERSION = "1.0";
    public static final String LISTENER_PACKAGE = "me.sxmurai.observant.listeners";

    private static Observant INSTANCE;

    private CheckHandler checkHandler;
    private ProfileManager profileManager;
    private MovementManager movementManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        getLogger().info("Loading Observant v" + VERSION);


        // dynamically load listeners
        int loaded = loadListeners();
        getLogger().info("Loaded " + loaded + " listeners.");

        // load profile manager
        profileManager = new ProfileManager();
        getLogger().info("Loaded profile manager");

        // load check handling
        checkHandler = new CheckHandler();
        getLogger().info("Loaded check handling. Loaded " + checkHandler.getChecks().size() + " checks.");

        movementManager = new MovementManager();
        getServer().getPluginManager().registerEvents(movementManager, this);
        getLogger().info("Loaded movement manager");
    }

    /**
     * Loads all listeners from the LISTENER_PACKAGE
     * @return the number of listeners loaded
     */
    private int loadListeners() {
        Set<Class<?>> listeners = ReflectionUtil.getClassesInPackage(LISTENER_PACKAGE, Listener.class);

        listeners.forEach((klass) -> {
            try {
                getServer().getPluginManager().registerEvents((Listener) klass.newInstance(), this);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return listeners.size();
    }

    @Override
    public void onDisable() {
        getLogger().info("Unloaded Observant.");

        INSTANCE = null;
    }

    public CheckHandler getCheckHandler() {
        return checkHandler;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public MovementManager getMovementManager() {
        return movementManager;
    }

    public static Observant getInstance() {
        return INSTANCE;
    }
}
