package dev.thew.reputation.service;

import dev.thew.reputation.Reputation;
import dev.thew.reputation.databases.DatabaseManager;
import dev.thew.reputation.databases.databases.ReputationDatabase;
import dev.thew.reputation.interfaces.RNService;
import dev.thew.reputation.interfaces.UserService;
import dev.thew.reputation.model.User;
import dev.thew.reputation.utils.Utils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public final class IUserService implements UserService {

    @Getter
    public static Map<Player, User> users = new HashMap<>();

    private ReputationDatabase reputationDatabase;

    public void init(){
        Bukkit.getPluginManager().registerEvents(this, Reputation.getInstance());

        RNService rnService = Reputation.getRnService();
        DatabaseManager databaseManager = rnService.getDatabaseManager();
        reputationDatabase = databaseManager.getDatabase(ReputationDatabase.class);

        Utils.shortTask(() -> Bukkit.getOnlinePlayers().forEach(this::load), true);
    }

    public void load(@NonNull final Player player) {
        User user = new User(player, 0, "Нейтрал");

        reputationDatabase.loadUser(user);
        Utils.loadStatusUser(user);

        users.put(player, user);
    }

    public void unload(@NonNull final Player player) {
        User user = getUser(player);

        reputationDatabase.saveUser(user);

        users.remove(player);
    }

    @Override
    public void shutdown() {
        Utils.shortTask(() -> Bukkit.getOnlinePlayers().forEach(this::unload), false);
    }

    public User getUser(@NonNull final Player player) {
        return users.get(player);
    }

    @EventHandler
    public void setEvent(PlayerJoinEvent event) {
        Utils.shortTask(() -> load(event.getPlayer()), true);
    }

    @EventHandler
    public void setEvent(PlayerQuitEvent event) {
        Utils.shortTask(() -> unload(event.getPlayer()), true);
    }
}
