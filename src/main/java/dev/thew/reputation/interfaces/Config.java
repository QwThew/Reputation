package dev.thew.reputation.interfaces;

import dev.thew.reputation.model.Level;
import dev.thew.reputation.model.Status;
import dev.thew.reputation.utils.LocalDatabase;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public interface Config {

    void loadDatabase(final FileConfiguration config);
    void loadStatus(final FileConfiguration config);

    int getLimitReputation();

    int getMaxStatus();
    int getMinStatus();

    LocalDatabase getLocalDatabase();
    Status getStatus();

    @Getter
    class IConfig implements Config {

        private LocalDatabase localDatabase;
        private final Status status;
        private int limitReputation;

        public IConfig() {
            status = new Status();
        }

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
        public void loadStatus(FileConfiguration config) {

            limitReputation = config.getInt("defaultmaximum");

            ConfigurationSection levelsSection = config.getConfigurationSection("levels");
            assert levelsSection != null;

            List<Level> levels = new ArrayList<>();
            for (String level : levelsSection.getKeys(false)) levels.add(new Level(level, config.getString(level)));

            levels.sort(Comparator.comparingInt(Level::getRating).reversed());

            for (Level level : levels) status.addLevel(level);
        }

        @Override
        public int getLimitReputation() {
            return limitReputation;
        }

        @Override
        public int getMaxStatus() {
            int[] array = getArray();
            return IntStream.of(array).max().orElse(Integer.MIN_VALUE);
        }

        @Override
        public int getMinStatus() {
            int[] array = getArray();
            return IntStream.of(array).min().orElse(Integer.MAX_VALUE);
        }

        public int[] getArray() {
            int[] array = new int[status.getLevels().size()];
            for (int i = 0; i < array.length; i++) array[i] = status.getLevels().get(i).getRating();
            return array;
        }

    }
}




