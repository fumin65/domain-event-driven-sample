package dev.fumin.sample.eventdriven.infrastructure.persistence.doma;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.sql.DataSource;

public class DomaConfig implements Config {

    private static final DomaConfig INSTANCE;

    private final Dialect dialect;

    private final LocalTransactionDataSource dataSource;

    private final TransactionManager transactionManager;

    static {
        synchronized (DomaConfig.class) {
            INSTANCE = new DomaConfig();
        }
    }

    private DomaConfig() {
        dialect = new MysqlDialect();
        dataSource = createDataSource();
        transactionManager = new LocalTransactionManager(dataSource.getLocalTransaction(getJdbcLogger()));
    }

    public static DomaConfig getInstance() {
        return INSTANCE;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    private LocalTransactionDataSource createDataSource() {
        String appId = System.getenv("GAE_APPLICATION");
        HikariConfig config = new HikariConfig();

        if (appId == null) {
            // for local
            config.setJdbcUrl("jdbc:mysql://localhost:3306/eventdriven_db");
            config.setUsername("myuser");
            config.setPassword("mypassword");
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            // for CloudSQL
            config.setJdbcUrl("set your cloud sql jdbc url");
            config.setUsername("set your db user name");
            config.setPassword("set your db user password");
            config.addDataSourceProperty("socketFactory",
                    "com.google.cloud.sql.mysql.SocketFactory");
            config.addDataSourceProperty("cloudSqlInstance", "set your instance name");
            config.addDataSourceProperty("useSSL", "false");
        }

        return new LocalTransactionDataSource(new HikariDataSource(config));
    }

}
