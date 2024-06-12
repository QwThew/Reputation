package dev.thew.reputation.databases.databases;

import dev.thew.reputation.databases.Database;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class ReputationDatabase extends Database {

    public ReputationDatabase(String databaseName) {
        super(databaseName);
    }

    @Override
    public void checkTables() {
        push("CREATE TABLE IF NOT EXISTS reputation(name varchar(255), reputation int, UNIQUE(name))", false);
    }

    @SneakyThrows(Exception.class)
    public int getFrunks(String name) {
        int coins = 0;

        ResultSet input = pushWithReturn("SELECT coins FROM coins WHERE name=?", name);
        assert input != null;
        if (input.next()) coins = input.getInt("coins");

        input.getStatement().close();

        return coins;
    }

    public void removeFrunks(String name, int count) {
        push("INSERT INTO coins(name, coins) VALUES(?, ?) ON DUPLICATE KEY UPDATE coins = coins - ?;", true, name, count, count);
    }
}