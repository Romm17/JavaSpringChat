package all; /**
 * @author Roman Usik
 */
//STEP 1. Import required packages
import java.io.PrintWriter;
import java.sql.*;

public class FirstExample {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Test";

    //  Database credentials
    static final String USER = "jdbc";
    static final String PASS = "jdbc";

    private static void printTable(ResultSet rs, PrintWriter out)
            throws SQLException {
        rs.beforeFirst();
        while(rs.next()){
            //Retrieve by column name
            int id  = rs.getInt("id");
            String value = rs.getString("value");

            //Display values
            out.print("ID: " + id);
            out.println(", value: " + value);
        }
    }

    public static void run(PrintWriter out, String newValue, int scroll, int update) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            out.println("Creating statement...");
            String sql;
            if (newValue != null) {
                sql = "SELECT id, value FROM femaleNames";
            }
            else {
                sql = "SELECT id, value FROM femaleNames WHERE value = 'Masha'";
            }
            stmt = conn.createStatement(scroll,
                    update);
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set

            if (newValue != null) {
                rs.isBeforeFirst();
                while (rs.next()) {
                    if (rs.getInt("id") == 1) {
                        rs.updateString("value", newValue);
                        rs.updateRow();
                    }
                }
            }
            printTable(rs, out);

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        out.println("Goodbye!");
    }//end main
}//end FirstExample
