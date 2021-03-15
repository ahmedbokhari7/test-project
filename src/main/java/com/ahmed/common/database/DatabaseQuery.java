package com.ahmed.common.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ahmed.common.utilities.ConfigPropertyReader;
import com.ahmed.common.utilities.HelperClass;


public class DatabaseQuery {
    private static Logger log;
    private static HelperClass helperClass;
    private static DBConnection dbConnection= DBConnection.getInstance();
    
    private static ConfigPropertyReader reader;

    private DatabaseQuery() {
        helperClass = HelperClass.getInstance();
        dbConnection = DBConnection.getInstance();
        log = LogManager.getLogger(DatabaseQuery.class);
        reader = ConfigPropertyReader.getInstance();
        DBConnection.configureConnections();
    }

    /**
     * Creating instance.
     */
    public static DatabaseQuery getInstance() {
        return DatabaseQueryHelper.instance;
    }
    private static class DatabaseQueryHelper {
    	
        private static final DatabaseQuery instance = new DatabaseQuery();
    }
    	
    
    
    public CachedRowSet selectQuery(String query, Connection database) throws SQLException {
    	
        try (Statement statement = database.createStatement(); ResultSet result=statement.executeQuery(query)) {
            
        	//DataSourceUtils.getConnection(dataSource)
        	RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet crs = factory.createCachedRowSet();
            crs.populate(result);
            return crs;
        }
    }
    public String getResellerBalance(String resellerAccountType,String accountId) {
        String resellerBalance = null;
        String query = "select balance from accounts where accountid='" + accountId + "' AND accountTypeId='" + resellerAccountType + "'";
        log.debug("Executing query: " + query);
        
        try (ResultSet rs = selectQuery(query, DBConnection.getDatabaseConnection("accounts"))) {
            while (rs.next()) {
                resellerBalance = rs.getString(1);
            }
            log.debug("Reseller balance: " + resellerBalance);
        } catch (SQLException e) {
            log.error("Error running query: ", e);
        }
        return resellerBalance;
    }

    
}
