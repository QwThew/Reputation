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

        Bukkit.getScheduler().runTaskAsynchronously(Reputation.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(this::load));

    }

    public void load(@NonNull final Player player) {
        User user = new User(player, 0, "Нейтрал");

        reputationDatabase.loadUser(user);

        users.put(player, user);
    }

    public void unload(@NonNull final Player player) {
        User user = getUser(player);

        reputationDatabase.saveUser(user);

        users.remove(player);
    }

    @Override
    public void shutdown() {

    }

    public User getUser(@NonNull final Player player) {
        User user = users.get(player);

        return user;
    }

    @EventHandler
    public void setEvent(PlayerJoinEvent event) {
        Utils.shortAtask(() -> load(event.getPlayer()));
    }

    @EventHandler
    public void setEvent(PlayerQuitEvent event) {
        Utils.shortAtask(() -> unload(event.getPlayer()));
    }
}
