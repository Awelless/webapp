package com.epam.web.connection;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final AtomicReference<ConnectionPool> INSTANCE = new AtomicReference<>();

    private static final Lock INSTANCE_LOCK = new ReentrantLock();

    private final Queue<ProxyConnection> availableConnections = new ArrayDeque<>();
    private final Set<ProxyConnection> inUseConnections = new HashSet<>();

    private final Lock connectionsLock = new ReentrantLock();
    private final Semaphore connectionsSemaphore;

    public static ConnectionPool getInstance() {

        if (INSTANCE.get() == null) {

            try {
                INSTANCE_LOCK.lock();
                if (INSTANCE.get() == null) {
                    ConnectionPoolFactory connectionPoolFactory = new ConnectionPoolFactory();
                    ConnectionPool pool = connectionPoolFactory.create();
                    INSTANCE.getAndSet(pool);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }

        return INSTANCE.get();
    }

    /*package-private*/ ConnectionPool(int poolSize) {
        connectionsSemaphore = new Semaphore(poolSize);
    }

    /*package-private*/ void initializeConnections(List<ProxyConnection> connections) {
        availableConnections.addAll(connections);
    }

    public ProxyConnection getConnection() {

        try {
            connectionsSemaphore.acquire();
            connectionsLock.lock();

            ProxyConnection connection = availableConnections.poll();
            inUseConnections.add(connection);

            return connection;

        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);

        } finally {
            connectionsLock.unlock();
        }
    }

    public void returnConnection(ProxyConnection connection) {

        try {
            connectionsLock.lock();

            if (inUseConnections.contains(connection)) {
                inUseConnections.remove(connection);
                availableConnections.add(connection);

                connectionsSemaphore.release();
            }

        } finally {
            connectionsLock.unlock();
        }
    }
}
