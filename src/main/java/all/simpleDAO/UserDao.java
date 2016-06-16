package all.simpleDAO;

import model.User;

import java.sql.*;

/**
 * @author Roman Usik
 */
public class UserDao extends Dao<User> {

    private static String staticTableName = "users";
    private static String tableCreationSQL = "CREATE TABLE users " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " login TEXT, " +
            " pass TEXT, " +
            " PRIMARY KEY ( id ))";
    private static UserDao dao;

    {
        tableName = "users";
    }

    private UserDao() {}

    public static UserDao connect() {
        if (dao != null) {
            return dao;
        }

        try {
            dao = new UserDao();
            Class.forName(JDBC_DRIVER);
            dao.conn = DriverManager.getConnection(
                    DB_URL + DB_NAME,
                    USER,
                    PASS
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                dao.conn.close();
                createDatabase().close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            dao.conn = DriverManager.getConnection(
                    DB_URL + DB_NAME,
                    USER,
                    PASS
            );
            DatabaseMetaData databaseMetaData = dao.conn.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, staticTableName, null);
            if (!resultSet.next())
                createTable(dao.conn, tableCreationSQL);
        } catch (SQLException e) {
            System.out.println("***\n\n\nBIda\n\n\n****");
            e.printStackTrace();
        }

        return dao;
    }

    @Override
    protected int getId(User object) {
        return object.getId();
    }

    @Override
    protected void setId(User object, int id) {
        object.setId(id);
    }

    @Override
    protected User parseObject(ResultSet resultSet) throws SQLException {
        User u = new User(
                resultSet.getString(2),
                resultSet.getString(3)
        );
        u.setId(resultSet.getInt(1));
        return u;
    }

    @Override
    protected String getSimpleSelectSQL() {
        return "SELECT * FROM users ";
    }

    @Override
    protected String getFieldNamesAsString() {
        return "login, pass";
    }

    @Override
    protected String getFieldValuesAsString(User object) {
        return "'" + object.getLogin() + "', '" + object.getPass() + "'";
    }

    @Override
    protected String getUpdateNameValueString(User object) {
        return "login = '" + object.getLogin()
                + "', pass = '" + object.getPass() + "'";
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = (UserDao) UserDao.connect();

        userDao.addObject(new User("vova", "123"));

        for (Object o : userDao.getAll()) {
            System.out.println(o);
        }

        userDao.close();

    }

}
