package dev.thew.reputation.databases;

import dev.thew.reputation.Reputation;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.*;
import java.util.Arrays;

public abstract class Database {

    private Connection connection;
    private final String[] connectionProperties;
    private final String url;

    @SneakyThrows
    protected Database(String databaseName){

        File file = new File("database.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        this.url = "jdbc:mysql://" + cfg.getString("host") + ":" + cfg.getString("port") + "/" + databaseName + "?characterEncoding=utf8";
        this.connectionProperties = new String[]{cfg.getString("user"), cfg.getString("password")};

        connect();
        checkTables();
    }

    private void connect() throws SQLException {

        if (connectionProperties == null || connectionProperties.length == 0)
            connection = DriverManager.getConnection(url);

        else if (connectionProperties.length == 2)
            connection = DriverManager.getConnection(url, connectionProperties[0], connectionProperties[1]);

        else
            throw new SQLException("Args " + Arrays.toString(connectionProperties) + " not supported in DriverManager.getConnection() method!");
    }

    public abstract void checkTables();

    public void push(String sql, boolean async, Object... values) {

        boolean isSync = Bukkit.isPrimaryThread();

        if (!isSync || !async) pushInThisThread(sql, values);

        else
            Bukkit.getScheduler().runTaskAsynchronously(Reputation.getInstance(), () -> pushInThisThread(sql, values));
    }

    @SneakyThrows
    private void pushInThisThread(String sql, Object... values) {
        PreparedStatement preparedStatement = prepareStatement(sql, values);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ResultSet pushWithReturn(String sql, Object... values) throws Exception {
        PreparedStatement preparedStatement = prepareStatement(sql, values);
        return preparedStatement.executeQuery();
    }

    private PreparedStatement prepareStatement(String sql, Object... values) throws Exception {

        if (connection == null || connection.isClosed()) connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int index = 0; index < values.length; index++)
            setValueInPreparedStatement(preparedStatement, index + 1, values[index]);

        return preparedStatement;
    }

    @SneakyThrows
    private void setValueInPreparedStatement(PreparedStatement preparedStatement, int index, Object value) {

        if (value instanceof String) preparedStatement.setString(index, (String) value);
        else if (value instanceof Integer) preparedStatement.setInt(index, (int) value);
        else if (value instanceof Long) preparedStatement.setLong(index, (long) value);
        else if (value instanceof Double) preparedStatement.setDouble(index, (double) value);
        else if (value instanceof Float) preparedStatement.setFloat(index, (float) value);
        else if (value instanceof Short) preparedStatement.setShort(index, (short) value);

        else
            throw new SQLException("Type '" + value.getClass().getSimpleName() + "' (value: '" + value + "') not supported in preparedStatement values!");
    }

    public void close() throws SQLException {
        connection.close();
    }
}