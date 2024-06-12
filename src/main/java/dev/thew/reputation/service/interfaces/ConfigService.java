package dev.thew.reputation.service.interfaces;

import dev.thew.reputation.utils.LocalDatabase;
import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigService {

    void load(final FileConfiguration config);
    LocalDatabase getLocalDatabase();

}
