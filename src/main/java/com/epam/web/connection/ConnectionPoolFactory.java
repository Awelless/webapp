package com.epam.web.connection;

import com.epam.web.util.ResourcesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPoolFactory {

    private static final String PROPERTIES_FILENAME = "database/database.properties";
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private int poolSize;

    /*package-private*/ ConnectionPoolFactory() {
    }

    /*package-private*/ ConnectionPool create() {

        try {
            initializeProperties();
            List<ProxyConnection> connections = initializeConnections();

            return new ConnectionPool(poolSize, connections);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            LOGGER.fatal(e.getMessage(), e);
            throw new ConnectionPoolException("Connection pool hasn't started properly", e);
        }
    }

    private void initializeProperties() throws IOException, ClassNotFoundException {

        Properties properties = new ResourcesLoader().loadProperties(PROPERTIES_FILENAME);

        String dbDriver = properties.getProperty("database.driver");
        Class.forName(dbDriver);

        dbUrl = properties.getProperty("database.url");
        dbUsername = properties.getProperty("database.username");
        dbPassword = properties.getProperty("database.password");

        poolSize = Integer.parseInt(properties.getProperty("database.connection.pool_size"));
    }

    private List<ProxyConnection> initializeConnections() throws SQLException {

        List<ProxyConnection> connections = new ArrayList<>();

        for (int i = 0; i < poolSize; ++i) {
            Connection connection = DriverManager.getConnection(
                    dbUrl, dbUsername, dbPassword);

            ProxyConnection proxyConnection = new ProxyConnection(connection);

            connections.add(proxyConnection);
        }

        return connections;
    }
}
