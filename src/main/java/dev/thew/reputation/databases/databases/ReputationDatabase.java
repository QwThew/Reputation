package dev.thew.reputation.databases.databases;

import dev.thew.reputation.databases.Database;
import dev.thew.reputation.model.User;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

public class ReputationDatabase extends Database {

    public ReputationDatabase(String databaseName) {
        super(databaseName);
    }

    @Override
    public void checkTables() {
        push("CREATE TABLE IF NOT EXISTS reputation(name varchar(255), reputation int, UNIQUE(name))", true);
    }

    @SneakyThrows
    public void loadUser(@NonNull final User user) {
        int reputation = 0;
        Player player = user.getPlayer();
        String name = player.getName();

        ResultSet input = pushWithReturn("SELECT reputation FROM reputation WHERE name=?", name);
        assert input != null;
        if (input.next()) reputation = input.getInt("reputation");

        input.getStatement().close();

        user.setReputation(reputation);
    }

    public void saveUser(@NonNull final User user) {

        Player player = user.getPlayer();
        String name = player.getName();
        int count = user.getReputation();

        push("INSERT INTO reputation(name, reputation) VALUES(?, ?) ON DUPLICATE KEY UPDATE reputation = ?;", true, name, count, count);
    }
}