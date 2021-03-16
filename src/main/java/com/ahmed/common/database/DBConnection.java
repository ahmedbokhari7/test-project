package com.ahmed.common.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static Map<String, HikariDataSource> databaseMap = new HashMap<String, HikariDataSource>();
    private static DBConnection dbConnection;
    
    private DBConnection()
    {
    	configureConnections();
    }
    //test
    public static DBConnection getInstance()
    {	
    	if(dbConnection == null)
    	{
    		synchronized (DBConnection.class) 
    		{
    			if(dbConnection == null)
    				dbConnection = new DBConnection();
			}
    		
    	}
        return dbConnection;
    }

    private void configureConnections(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl( "jdbc:mysql://10.10.0.62:3306/accounts" );
        config.setUsername( "refill" );
        config.setPassword( "refill" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        HikariDataSource accountConnection = new HikariDataSource( config );
        databaseMap.put("accounts", accountConnection);

//        config.setJdbcUrl( "jdbc:mysql://10.10.0.62:3306/Refill" );
//        config.setUsername( "refill" );
//        config.setPassword( "refill" );
//        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//        HikariDataSource refillConnection = new HikariDataSource( config );
//        databaseMap.put("refill", refillConnection);
//
//        config.setJdbcUrl( "jdbc:mysql://10.10.0.62:3306/commission" );
//        config.setUsername( "refill" );
//        config.setPassword( "refill" );
//        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//        HikariDataSource commissionConnection = new HikariDataSource( config );
//        databaseMap.put("commission", commissionConnection);
    }

    public Connection getDatabaseConnection(String dbName) throws SQLException {
        if(databaseMap.containsKey(dbName)){
            return databaseMap.get(dbName).getConnection();
        } else
            return null;
    }
}
