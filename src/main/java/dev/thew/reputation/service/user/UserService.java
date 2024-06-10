package dev.thew.reputation.service.user;

import dev.thew.reputation.Reputation;
import dev.thew.reputation.model.User;
import dev.thew.reputation.utils.Utils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class UserService implements Listener {

    @Getter
    public static Map<Player, User> users = new HashMap<>();

    public static void init(){
        Bukkit.getPluginManager().registerEvents(new UserService(), Reputation.getInstance());

        Bukkit.getScheduler().runTaskAsynchronously(Reputation.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(UserService::load));

    }

    public static void load(@NonNull final Player player) {


    }

    public static void unload(@NonNull final Player player) {
        User user = getUser(player);

        users.remove(player);

    }

    public static User getUser(@NonNull final Player player) {
        User user = users.get(player);

        return user;
    }

    @EventHandler
    private void setEvent(PlayerJoinEvent event) {
        Utils.shortAtask(() -> load(event.getPlayer()));
    }

    @EventHandler
    private void setEvent(PlayerQuitEvent event) {
        Utils.shortAtask(() -> unload(event.getPlayer()));
    }

}
