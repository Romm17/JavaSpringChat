package all.simpleDAO;

import model.Message;
import model.User;

import java.sql.*;

/**
 * @author Roman Usik
 */
public class MessageDao extends Dao<Message> {

    private static String staticTableName = "messages";
    private static String tableCreationSQL = "CREATE TABLE messages " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " userId INTEGER not NULL, " +
            " text TEXT, " +
            " PRIMARY KEY ( id ))";
    private static MessageDao dao;
    private static UserDao userDao;

    {
        tableName = "messages";
    }

    private MessageDao() {}

    public static MessageDao connect() {
        if (dao != null) {
            return dao;
        }

        try {
            dao = new MessageDao();
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
        userDao = UserDao.connect();
        return dao;
    }

    @Override
    protected int getId(Message object) {
        return object.getId();
    }

    @Override
    protected void setId(Message object, int id) {
        object.setId(id);
    }

    @Override
    protected Message parseObject(ResultSet resultSet) throws SQLException {
        return new Message(
                userDao.get("id", "" + resultSet.getInt(2)),
                resultSet.getString(3)
        );
    }

    @Override
    protected String getSimpleSelectSQL() {
        return "SELECT messages.id, users.login, text FROM messages INNER JOIN users ON userId = users.id ";
    }

    @Override
    protected String getFieldNamesAsString() {
        return "userId, text";
    }

    @Override
    protected String getFieldValuesAsString(Message object) {
        return "'" + object.getUser().getId() + "', '" + object.getText() + "'";
    }

    @Override
    protected String getUpdateNameValueString(Message object) {
        return "userId = '" + object.getUser().getId() + "', text = '" + object.getText() + "'";
    }

    public static void main(String[] args) throws SQLException {
        MessageDao messageDao = MessageDao.connect();

        UserDao userDao = (UserDao) UserDao.connect();
        User u = userDao.get("login", "roma");
        if (u == null) {
            u = new User("roma", "123");
            userDao.addObject(u);
        }

        messageDao.addObject(new Message(u, "message"));

        for (Object o : messageDao.getAll())
            System.out.println(o);

        messageDao.close();

    }

}
