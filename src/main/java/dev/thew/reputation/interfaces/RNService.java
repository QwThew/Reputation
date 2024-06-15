package dev.thew.reputation.interfaces;

import dev.thew.reputation.databases.DatabaseManager;
import org.bukkit.configuration.file.FileConfiguration;

public interface RNService {

    ReputationAPI getReputationAPI();
    UserService getUserService();
    Config getConfig();
    DatabaseManager getDatabaseManager();

    void loadConfiguration(final FileConfiguration config);
    void loadDatabase();
    void shutdownDatabase();
    void loadUsers();
    void shutdownUsers();
    void loadPlaceholders();

}
