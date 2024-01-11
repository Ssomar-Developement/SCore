package com.ssomar.score.data;


import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.ssomar.score.config.GeneralConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseOld {

    public Connection getOldConnection() throws SQLException {
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setServerName(GeneralConfig.getInstance().getDbIP());
        dataSource.setPortNumber(GeneralConfig.getInstance().getDbPort());
        dataSource.setDatabaseName(GeneralConfig.getInstance().getDbName());
        dataSource.setUser(GeneralConfig.getInstance().getDbUser());
        dataSource.setPassword(GeneralConfig.getInstance().getDbPassword());
        dataSource.setServerTimezone("UTC");
        dataSource.setUseSSL(false);
        dataSource.setAutoReconnect(true);
        return dataSource.getConnection();
    }
}
