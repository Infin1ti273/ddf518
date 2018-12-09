import java.sql.*;

public class Main{
    private static Interface rua = new Interface();
    private static Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/?characterEncoding=utf8&useSSL=false";
        String username = "root";
        String password = "";
        Connection conn = null;
        try {
            Class.forName(driver);                                      //classLoader
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args){
        select();
        rua.InitGUI();
    }

    private static void select(){
        Connection conn = getConnection();
        String sql = "SQL here";
        Statement stmt;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int col = rs.getMetaData().getColumnCount();

            System.out.println("***********************");
            while (rs.next()){
                for(int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
            }
            System.out.println("\n***********************");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
