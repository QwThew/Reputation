package dev.thew.reputation;

import dev.thew.reputation.databases.DatabaseManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Reputation extends JavaPlugin {

    @Getter
    private static Reputation instance;

    @Override
    public void onEnable() {
        instance = this;

        DatabaseManager.init();

    }

    @Override
    public void onDisable() {
        DatabaseManager.shutDown();
    }
}
