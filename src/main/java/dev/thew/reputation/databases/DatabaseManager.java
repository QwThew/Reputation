package dev.thew.reputation.databases;

import dev.thew.reputation.Reputation;
import dev.thew.reputation.databases.databases.ReputationDatabase;
import dev.thew.reputation.interfaces.RNService;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;


import java.util.HashMap;
import java.util.logging.Level;

public class DatabaseManager {

    private final HashMap<String, Database> databases = new HashMap<>();

    @SneakyThrows
    public void load() {
        RNService rnService = Reputation.getRnService();
        String database = rnService.getConfig().getLocalDatabase().getDatabase();

        ReputationDatabase reputationDatabase = new ReputationDatabase(database);

        registerDatabase(reputationDatabase);
    }

    @SneakyThrows
    private void registerDatabase(Database databaseClass) {
        databases.put(databaseClass.toString(), databaseClass);

        Bukkit.getLogger().log(Level.INFO, "Database '" + databaseClass + "' registred!");
    }

    @SneakyThrows
    public void shutDown() {

        for (Database database : databases.values())
            database.close();


        databases.clear();
    }

    public <T extends Database> T getDatabase(Class<T> databaseClass) {

        String name = databaseClass.getSimpleName();

        if (!databases.containsKey(name))
            throw new RuntimeException("Database '" + name + "' not found!");

        Database database = databases.get(name);
        return databaseClass.cast(database);
    }
}
