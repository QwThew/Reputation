package dev.thew.reputation.interfaces;

import dev.thew.reputation.model.User;
import org.bukkit.entity.Player;

public interface ReputationAPI {

    int getReputationOfPlayer(final Player player);
    User getUserOfPlayer(final Player player);

}
