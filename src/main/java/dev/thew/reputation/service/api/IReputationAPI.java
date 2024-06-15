package dev.thew.reputation.service.api;

import dev.thew.reputation.interfaces.ReputationAPI;
import dev.thew.reputation.interfaces.UserService;
import dev.thew.reputation.model.User;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class IReputationAPI implements ReputationAPI {

    private final UserService userService;

    public IReputationAPI(UserService userService) {
        this.userService = userService;
    }

    @Override
    public int getReputation(Player player) {
        return userService.getUser(player).getReputation();
    }

    @Override
    public int getReputation(@NonNull User user) {
        return user.getReputation();
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public String getStatus(Player player) {
        return userService.getUser(player).getStatus();
    }

    @Override
    public User getUser(Player player) {
        return userService.getUser(player);
    }

    @Override
    public String getStatus(@NonNull User user) {
        return user.getStatus();
    }
}
