package dev.thew.reputation.utils;

import dev.thew.reputation.Reputation;
import dev.thew.reputation.interfaces.Config;
import dev.thew.reputation.model.Status;
import dev.thew.reputation.model.User;
import org.bukkit.Bukkit;

public class Utils {

    public static void shortTask(Runnable r, boolean async) {
        if (async) Bukkit.getScheduler().runTaskAsynchronously(Reputation.getInstance(), r);
        else Bukkit.getScheduler().runTask(Reputation.getInstance(), r);
    }

    public static void loadStatusUser(final User user) {
        Config config = Reputation.getRnService().getConfig();
        Status status = config.getStatus();

        user.setStatus(status.getStatus(user));
    }

}
