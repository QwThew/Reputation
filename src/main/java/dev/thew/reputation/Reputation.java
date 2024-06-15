package dev.thew.reputation;

import dev.thew.reputation.interfaces.RNService;
import dev.thew.reputation.interfaces.ReputationAPI;
import dev.thew.reputation.service.Service;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Reputation extends JavaPlugin {

    @Getter
    private static Reputation instance;
    @Getter
    private static final RNService rnService = new Service();

    @Override
    public void onEnable() {
        instance = this;
        FileConfiguration config = getConfig();

        rnService.loadConfiguration(config);
        rnService.loadDatabase();
        rnService.loadUsers();
        rnService.loadPlaceholders();

    }

    @Override
    public void onDisable() {
        rnService.shutdownUsers();
        rnService.shutdownDatabase();
    }

    public static ReputationAPI getReputationAPI() {
        return rnService.getReputationAPI();
    }
}
