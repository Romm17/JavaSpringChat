package all.simpleDAO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Roman Usik
 */
public abstract class Dao<E> {

    // JDBC driver name and database URL
    protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String DB_URL = "jdbc:mysql://localhost/";
    protected static final String DB_NAME = "SmartChat";
    protected String tableName = null;

    //  Database credentials
    protected final static String USER = "root";
    protected final static String PASS = "root";

    protected Connection conn = null;

    protected abstract int getId(E object);

    protected abstract void setId(E object, int id);

    protected abstract E parseObject(ResultSet resultSet) throws SQLException;

    protected abstract String getSimpleSelectSQL();

    protected abstract String getFieldNamesAsString();

    protected abstract String getFieldValuesAsString(E object);

    protected abstract String getUpdateNameValueString(E object);

    public E get(String fieldName, String value) {
        String sql = getSimpleSelectSQL() + "WHERE " + fieldName + "='" + value + "'";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return parseObject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Object[] getAll() {
        String sql = getSimpleSelectSQL();
        Statement stmt = null;
        List<E> result = new LinkedList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(parseObject(rs));
            }
            Object[] resArr = new Object[result.size()];
            result.toArray(resArr);
            return resArr;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void addObject(E object) {
        String sql = "INSERT INTO " + tableName
                + " (" + getFieldNamesAsString()
                + ") VALUES (" + getFieldValuesAsString(object) + ")";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Failed to add message");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    setId(object, rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeObject(E object) {
        String sql = "DELETE FROM " + tableName + " WHERE id = " + getId(object);
        executeQuery(sql);
    }

    public void updateObject(E object) {
        String sql = "UPDATE " + tableName
                + " SET " + getUpdateNameValueString(object)
                + " WHERE id = " + getId(object);
        executeQuery(sql);
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static Connection createDatabase() throws SQLException {
        System.out.println("Creating database");
        String sql = "CREATE DATABASE " + DB_NAME;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    protected static void createTable(Connection connection, String tableCreationSQL) throws SQLException{
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String sql = tableCreationSQL;

            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                if(stmt != null)
                    stmt.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
        }
    }

    protected void executeQuery(String sql) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
