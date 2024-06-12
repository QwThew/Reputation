package dev.thew.reputation.service.interfaces;

import dev.thew.reputation.model.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface UserService extends Listener {

    void init();
    void load(final Player player);
    void unload(final Player player);
    void shutdown();

    User getUser(final Player player);

    @EventHandler
    void setEvent(PlayerQuitEvent event);
    @EventHandler
    void setEvent(PlayerJoinEvent event);

}
