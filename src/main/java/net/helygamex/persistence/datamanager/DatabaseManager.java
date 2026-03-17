package net.helygamex.persistence.datamanager;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DatabaseManager {

    public static volatile DatabaseManager instance = null;
    public DataSource dataSource = null;
    private String url;
    private String name;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;

    public DatabaseManager(String url, String name, String password, int minPoolSize, int maxPoolSize) {
        // Super constructor
        super();
        this.url = url;
        this.name = name;
        this.password = password;
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.setupDataSource();
    }

    // Singleton generator
    public final static DatabaseManager getInstance(String url, String name, String password, int minPoolSize, int maxPoolSize) {
        if (DatabaseManager.instance == null) {
            synchronized(DatabaseManager.class) {
                if (DatabaseManager.instance == null) {
                    DatabaseManager.instance = new DatabaseManager(url, name, password, minPoolSize, maxPoolSize);
                }
            }
        }
        return DatabaseManager.instance;
    }

    // Initialize the data source
    public void setupDataSource() {
        // Set a JDBC/MySQL connection
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.name);
        dataSource.setPassword(this.password);
        dataSource.setInitialSize(this.minPoolSize);
        dataSource.setMaxTotal(this.maxPoolSize);
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        // Return the datasource
        //this.getSourcesStats(this.dataSource); // Fixme remove this trace
        return this.dataSource;
    }

    // Get the data sources stats
    public void getSourcesStats(DataSource dataSource) {
        BasicDataSource basicDataSource = (BasicDataSource) dataSource;
        System.out.println("Number of active: " + basicDataSource.getNumActive());
        System.out.println("Number of idle: " + basicDataSource.getNumIdle());
        System.out.println("================================================================================");
    }

    // Shutdown the data source
    public void shutdownDataSource(DataSource dataSource) throws Exception {
        BasicDataSource basicDataSource = (BasicDataSource) dataSource;
        basicDataSource.close();
    }
}