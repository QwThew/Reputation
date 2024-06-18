package dev.thew.reputation.service;

import dev.thew.reputation.databases.DatabaseManager;
import dev.thew.reputation.interfaces.*;
import dev.thew.reputation.service.api.IReputationAPI;
import dev.thew.reputation.utils.Placeholders;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public final class Service implements RNService {

    private final UserService userService = new IUserService();
    private final ReputationAPI reputationAPI = new IReputationAPI(userService);
    private final RequestService requestService = new IRequestService(userService);
    private final Config config = new Config.IConfig();
    private final DatabaseManager databaseManager = new DatabaseManager();

    @Override
    public void loadFromConfiguration(final FileConfiguration config) {
        this.config.loadDatabase(config);
        this.config.loadStatus(config);
        this.requestService.loadRequests(config);
    }

    @Override
    public void loadDatabase(){
        databaseManager.load();
    }

    @Override
    public void shutdownDatabase(){
        databaseManager.shutDown();
    }

    @Override
    public void loadUsers() {
        userService.init();
    }

    @Override
    public void shutdownUsers() {
        userService.shutdown();
    }

    @Override
    public void loadPlaceholders() {
        boolean isSet = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        if (isSet) new Placeholders().register();
    }

}
