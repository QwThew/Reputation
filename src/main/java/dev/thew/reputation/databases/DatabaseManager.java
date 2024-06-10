package dev.thew.reputation.databases;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.reflections.Reflections;


import java.util.HashMap;
import java.util.logging.Level;

public class DatabaseManager {

    private DatabaseManager() {
        throw new IllegalStateException("DatabaseManager class");
    }

    private static final HashMap<String, Database> databases = new HashMap<>();

    @SneakyThrows
    public static void init() {
        Package currentPackage = DatabaseManager.class.getPackage();
        String databasesPackageName = currentPackage.getName() + ".databases";

        Reflections reflections = new Reflections(databasesPackageName);

        for (Class<? extends Database> databaseClass : reflections.getSubTypesOf(Database.class))
            registerDatabase(databaseClass);
    }

    private static <T extends Database> void registerDatabase(Class<T> databaseClass) throws Exception {

        Database database = databaseClass.getDeclaredConstructor().newInstance();
        databases.put(databaseClass.getSimpleName(), database);

        Bukkit.getLogger().log(Level.INFO, "Database '" + databaseClass.getSimpleName() + "' registred!");
    }

    @SneakyThrows
    public static void shutDown() {

        for (Database database : databases.values())
            database.close();


        databases.clear();
    }

    public static <T extends Database> T getDatabase(Class<T> databaseClass) {

        String name = databaseClass.getSimpleName();

        if (!databases.containsKey(name))
            throw new RuntimeException("Database '" + name + "' not found!");

        Database database = databases.get(name);
        return databaseClass.cast(database);
    }
}
