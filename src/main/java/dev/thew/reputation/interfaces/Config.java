package dev.thew.reputation.interfaces;

import dev.thew.reputation.model.Level;
import dev.thew.reputation.utils.LocalDatabase;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public interface Config {

    void loadDatabase(final FileConfiguration config);
    void loadLevels(final FileConfiguration config);
    LocalDatabase getLocalDatabase();

    @Getter
    class IConfig implements Config {

        private LocalDatabase localDatabase;
        private final List<Level> levels = new ArrayList<>();

        @Override
        public void loadDatabase(final FileConfiguration config) {

            ConfigurationSection databaseSection = config.getConfigurationSection("database");
            assert databaseSection != null;

            String port = databaseSection.getString("port");
            String database = databaseSection.getString("db");
            String user = databaseSection.getString("user");
            String password = databaseSection.getString("password");

            localDatabase = LocalDatabase.builder()
                    .user(user)
                    .database(database)
                    .port(port)
                    .password(password)
                    .build();
        }

        @Override
        public void loadLevels(FileConfiguration config) {

            ConfigurationSection levelsSection = config.getConfigurationSection("levels");
            assert levelsSection != null;

            for (String level : levelsSection.getKeys(false)) levels.add(new Level(level, config.getString(level)));
        }


    }
}




