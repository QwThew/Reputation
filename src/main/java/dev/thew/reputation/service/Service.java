package dev.thew.reputation.service;

import dev.thew.reputation.databases.DatabaseManager;
import dev.thew.reputation.interfaces.Config;
import dev.thew.reputation.interfaces.RNService;
import dev.thew.reputation.interfaces.UserService;
import dev.thew.reputation.service.api.IReputationAPI;
import dev.thew.reputation.interfaces.ReputationAPI;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public final class Service implements RNService {

    private final UserService userService = new IUserService();
    private final ReputationAPI reputationAPI = new IReputationAPI(userService);
    private final Config config = new Config.IConfig();
    private final DatabaseManager databaseManager = new DatabaseManager();

    @Override
    public void loadConfiguration(final FileConfiguration config){
        this.config.loadDatabase(config);
        this.config.loadLevels(config);
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

}
