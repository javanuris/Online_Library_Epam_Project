package nuris.epam.connection;

import nuris.epam.connection.exception.ConnectionException;
import nuris.epam.connection.exception.PropertiesException;
import nuris.epam.connection.exception.ResourcesException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс хранит список инициализированных соеденений к БД.
 *
 * @author Kalenov Nurislam
 */
public class ConnectionPool {
    /**
     * Поле  - пользователь от БД.
     */
    private String user;
    /**
     * Поле  - пароль от БД.
     */
    private String password;
    /**
     * Поле  - путь к драйверу.
     */
    private String driver;
    /**
     * Поле  - путь к БД.
     */
    private String url;
    /**
     * Поле  - тип БД.
     */
    private String type;
    /**
     * Поле  - количество инициаилизированных соеденеий.
     */
    private int poolSize;
    /**
     * Поле  - Время ожидание пользователя до освобождения соедение.
     */
    private int timeOut;
    /**
     * Поле  - список для хранеиние инициализированных соеденеий.
     */
    private ResourcesQueue<Connection> connections = null;

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        try {
            init();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Инициализирует N-ое количество соедений и добавляет их в список.
     *
     * @throws ConnectionException
     */
    private void init() throws ConnectionException {
        try {
            loadProperties();
            connections = new ResourcesQueue<Connection>(poolSize, timeOut);
            while (connections.size() < poolSize) {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException e) {
                   throw  new ConnectionException("Cant find driver for JDBC MySql" , e);
                }
                Connection connection = DriverManager.getConnection(url, user, password);
                connections.addResource(connection);
            }
        } catch (SQLException | PropertiesException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загружает данные о БД с помощью файла .properties и инициализирует поля.
     * {@link ConnectionPool#user}
     * {@link ConnectionPool#password}
     * {@link ConnectionPool#url}
     * {@link ConnectionPool#type}
     * {@link ConnectionPool#poolSize}
     * {@link ConnectionPool#timeOut}
     *
     * @throws PropertiesException
     */
    private void loadProperties() throws PropertiesException {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getResourceAsStream("/db.properties"));
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            type = properties.getProperty("type");
            poolSize = Integer.parseInt(properties.getProperty("pool_size"));
            timeOut = Integer.parseInt(properties.getProperty("timeout"));
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            throw new PropertiesException("Not found properties file with connecting settings", e);
        }
    }

    /**
     * Берет из списка пулов соедениий одно соеденение к БД.
     * {@link ConnectionPool#type}
     *
     * @return возвращяет соедение к БД.
     *
     * @throws ResourcesException
     */
    public Connection getConnection() throws ResourcesException {
        try {
            return connections.takeResource();
        } catch (ResourcesException e) {
            throw new ResourcesException("Error in a getConnection() , don't available connect", e);
        }
    }

    /**
     * Возвращяет коннект в список пулов соеденений.
     */
    public void returnConnection(Connection connection) {
        connections.returnResource(connection);
    }

    /**
     * Создает экземпляр класса.
     *
     * @return возвращяет единсвенный экземпляр класса.(Pattern Singleton).
     */
    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    /**
     * Закрывает все коннекты
     *
     * @throws ConnectionException
     */
    public void closeAllConnections() throws ConnectionException {
        for (Connection connection : connections.getResources()) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionException("Cannot close all connections", e);
            }
        }
    }

    /**
     * Получает значение поля.
     *
     * @return возвращяет тип соеденений к БД.
     */
    public String getType() {
        return type;
    }

    public int size(){
        return connections.size();
    }
}
