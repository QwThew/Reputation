package dev.thew.reputation.service;

import dev.thew.reputation.databases.DatabaseManager;
import dev.thew.reputation.service.api.IReputationAPI;
import dev.thew.reputation.service.interfaces.ConfigService;
import dev.thew.reputation.service.interfaces.RNService;
import dev.thew.reputation.service.interfaces.ReputationAPI;
import dev.thew.reputation.service.interfaces.UserService;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public final class Service implements RNService {

    @Getter
    private final UserService userService = new IUserService();
    @Getter
    private final ReputationAPI reputationAPI = new IReputationAPI();
    @Getter
    private final ConfigService configService = new IConfigService();
    @Getter
    private final DatabaseManager databaseManager = new DatabaseManager();

    @Override
    public void loadConfiguration(final FileConfiguration config){
        configService.load(config);
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
