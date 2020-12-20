package com.fo.view;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTransientException;

public class ConnectionPool {

    public static volatile HikariDataSource hikariCPSource = null;

    public static Connection getConnection() throws SQLException {
    	return ConnectionPool.getConnection(true);
    }

    public static Connection getConnection(boolean autoCommit) throws SQLException {
        try {
        	if (hikariCPSource == null) {
                hikariCPSource = getDataSource();
            }

            Connection con = hikariCPSource.getConnection();
            con.setNetworkTimeout(null, 30000);
            if (!autoCommit) con.setAutoCommit(false);
            return con;
        } catch (SQLTransientException e) {
            throw e;
        } catch (Exception e) {
            if (hikariCPSource != null) try {
                hikariCPSource.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            hikariCPSource = null;
            throw e;
        }
    }

    private synchronized static HikariDataSource getDataSource() {
        if (hikariCPSource != null) return hikariCPSource;

        HikariConfig jdbcConfig = new HikariConfig();
        jdbcConfig.setPoolName("DBPOOL");
        jdbcConfig.setIdleTimeout(30000); // 30 sec
        jdbcConfig.setConnectionTimeout(5000); // 5 sec

        jdbcConfig.addDataSourceProperty("cachePrepStmts", "true");
        jdbcConfig.addDataSourceProperty("prepStmtCacheSize", "250");

        jdbcConfig.setMinimumIdle(10);
        	jdbcConfig.setMaximumPoolSize(25);

        jdbcConfig.setJdbcUrl("jdbc:postgresql://ofalkobase.cp0c6tu903e4.eu-central-1.rds.amazonaws.com/postgres?ApplicationName=base");

        jdbcConfig.setUsername("postgres");
        jdbcConfig.setPassword("0Fa1ko04");

        HikariDataSource dataSource = new HikariDataSource(jdbcConfig);

        return dataSource;
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
            	if (!con.getAutoCommit()) {
            		con.commit();
            	}
                con.close();
            } catch (SQLException ignored) {}
        }
    }

    public static void closeConnection(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection(con);
    }

    public static void closeDBConnection() {
        /* HikariCP */
        try {
            if (ConnectionPool.hikariCPSource != null) {
                ConnectionPool.hikariCPSource.close();
                ConnectionPool.hikariCPSource = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
