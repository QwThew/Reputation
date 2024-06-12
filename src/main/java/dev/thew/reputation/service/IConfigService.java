package dev.thew.reputation.service;

import dev.thew.reputation.service.interfaces.ConfigService;
import dev.thew.reputation.utils.LocalDatabase;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public final class IConfigService implements ConfigService {

    private LocalDatabase localDatabase;

    @Override
    public void load(final FileConfiguration config) {
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


}


