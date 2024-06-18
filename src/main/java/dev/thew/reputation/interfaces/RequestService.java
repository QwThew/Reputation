package dev.thew.reputation.interfaces;


import dev.thew.reputation.model.enums.EventType;
import dev.thew.reputation.model.Request;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public interface RequestService {

    void loadRequests(FileConfiguration config);
    <T> void checkEvent(Player player, EventType eventType, T data);

    Request[] getRequests();
    UserService getUserService();

}
