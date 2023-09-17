package org.bookstore.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final int MAX_CONNECTIONS = 10;

    private static ConnectionPool connectionPool;
    private final BlockingQueue<Connection> blockingQueue;

    private ConnectionPool() {
        blockingQueue = new ArrayBlockingQueue<>(MAX_CONNECTIONS);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                blockingQueue.put(createConnection());
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ConnectionPool getConnectionPool() {
        synchronized (ConnectionPool.class) {
            if (connectionPool == null) {
                connectionPool = new ConnectionPool();
            }
            return connectionPool;
        }
    }

    public synchronized Connection acquireConnection() {
        // Take (remove and return) a connection from the queue
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        // Offer (add) the connection back to the queue
        try {
            blockingQueue.put(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
