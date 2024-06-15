package dev.thew.reputation.service.api;

import dev.thew.reputation.interfaces.ReputationAPI;
import dev.thew.reputation.interfaces.UserService;
import dev.thew.reputation.model.User;
import org.bukkit.entity.Player;

public final class IReputationAPI implements ReputationAPI {

    private UserService userService;

    public IReputationAPI(UserService userService) {
        this.userService = userService;
    }

    @Override
    public int getReputationOfPlayer(Player player) {
        return 0;
    }

    @Override
    public User getUserOfPlayer(Player player) {
        return userService.getUser(player);
    }
}
