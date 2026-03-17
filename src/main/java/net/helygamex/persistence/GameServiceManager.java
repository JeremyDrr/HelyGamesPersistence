package net.helygamex.persistence;

import net.helygamex.persistence.datamanager.DatabaseManager;

public class GameServiceManager {

    private DatabaseManager databaseManager;

    public GameServiceManager(String url, String name,  String password, int minPoolSize, int maxPoolSize) {
        this.databaseManager = DatabaseManager.getInstance(url, name, password, minPoolSize, maxPoolSize);
    }
}