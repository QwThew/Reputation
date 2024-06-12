package dev.thew.reputation.service.api;

import dev.thew.reputation.service.interfaces.ReputationAPI;
import dev.thew.reputation.service.IUserService;
import org.bukkit.entity.Player;

public final class IReputationAPI implements ReputationAPI {

    private static IUserService userService ;

    @Override
    public int getReputationOfPlayer(Player player) {
        return 0;
    }
}
