package dev.thew.reputation.interfaces;

import dev.thew.reputation.Reputation;
import dev.thew.reputation.model.User;
import org.bukkit.entity.Player;

public interface ReputationAPI {

    int getReputation(final Player player);
    int getReputation(final User user);
    UserService getUserService();

    String getStatus(final Player player);
    String getStatus(final User user);

    User getUser(final Player player);

    static ReputationAPI get() {
        return Reputation.getReputationAPI();
    }

}
