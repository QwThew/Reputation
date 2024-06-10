package dev.thew.reputation.utils;

import dev.thew.reputation.Reputation;
import org.bukkit.Bukkit;

public class Utils {

    public static void shortAtask(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(Reputation.getInstance(), r);
    }

}
