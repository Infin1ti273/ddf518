import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**/
public class Uiui extends JFrame implements ActionListener {

    private static JLabel tx = new JLabel();
    private JButton a = new JButton("Hall info");
    private JButton b = new JButton("Lease detail for every student");
    private JButton c = new JButton("lease detail;summer semester");
    private JButton d = new JButton("total rent paid of a given stud");
    private JButton e = new JButton("studs:not paid invoice+given date");
    private JButton f = new JButton("inspections:unsatisfieconfigurationd");
    private JButton g = new JButton("name+studID:given roomNo+plcNo in hall");
    private JButton h = new JButton("stud:waiting");
    private JButton i = new JButton("total number of stud in each cata");
    private JButton j = new JButton("name+studNo:have no kin");
    private JButton k = new JButton("name+intertel of advisor:a given stud");
    private JButton l = new JButton("min,max,ave Mrent of hall");
    private JButton m = new JButton("Total number of places in each hall");
    private JButton n = new JButton("staffNo,name,age,location:age>60");

    static class Sub_ui extends JFrame implements ActionListener {
        JTextArea input = new JTextArea();
        JButton ex = new JButton("SQL Search");
        GridLayout l2 = new GridLayout(2,1);
        private Sub_ui(){
            this.setTitle("Extra Searching");
            this.setSize(400,400);
            this.setLayout(l2);
            this.add(input);
            this.add(ex);
            ex.addActionListener(this);
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Uiui.select(input.getText());
            input.setText("");
        }
    }
    private Uiui() {
        this.setTitle("Database User Interface");
        this.setSize(1000, 800);
        GridLayout l1 = new GridLayout(2, 1);
        this.setLayout(l1);
        JPanel j2 = new JPanel(new FlowLayout());
        this.add(j2);
        JPanel j1 = new JPanel(new GridLayout(14, 1));
        this.add(j1);

        a.addActionListener(this);
        b.addActionListener(this);
        c.addActionListener(this);
        d.addActionListener(this);
        e.addActionListener(this);
        f.addActionListener(this);
        g.addActionListener(this);
        h.addActionListener(this);
        i.addActionListener(this);
        j.addActionListener(this);
        k.addActionListener(this);
        l.addActionListener(this);
        m.addActionListener(this);
        n.addActionListener(this);

        j1.add(a);
        j1.add(b);
        j1.add(c);
        j1.add(d);
        j1.add(e);
        j1.add(f);
        j1.add(g);
        j1.add(h);
        j1.add(i);
        j1.add(j);
        j1.add(k);
        j1.add(l);
        j1.add(m);
        j1.add(n);
        j2.add(tx);
        tx.setSize(1000,400);
        this.setResizable(false);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        Uiui frame = new Uiui();
        new Sub_ui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/db?characterEncoding=utf8&useSSL=false";
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

    @Override
    public void actionPerformed(ActionEvent x) {
        String sql;
        String header;
        if (x.getSource() == a) {
            sql = "select hallId,staffName.name as Manager,hall.tel as Hall_Tel from hall\n" +
                    "join staff\n" +
                    "  on hall.managerId = staff.staffId\n" +
                    "join staffName\n" +
                    "  on staff.staffId = staffName.staffId;";
            select(sql);
        }
        if (x.getSource() == b) {
            sql = "select students.studentId as Student_No,studentName,leaseNo,duration,placeNo,enterDate,leaveDate from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId;";
            select(sql);
        }
        if (x.getSource() == c) {
            sql = "select * from lease\n" +
                    "  where (\n" +
                    "    (\n" +
                    "      year(enterDate) = year(leaveDate)\n" +
                    "      and month(leaveDate) = 3\n" +
                    "      )\n" +
                    "    or\n" +
                    "    (\n" +
                    "      year(enterDate) != year(leaveDate)\n" +
                    "      and (\n" +
                    "        month(enterDate) = 3\n" +
                    "      or\n" +
                    "        year(leaveDate) - year(enterDate) > 1\n" +
                    "        )\n" +
                    "     )\n" +
                    "    );";
            select(sql);
        }
        if (x.getSource() == d) {
            sql = "select studentName,pamentDue as TotalPayment from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId\n" +
                    "left join invoice\n" +
                    "  on lease.leaseNo = invoice.leaseNo\n";
            select(sql);
        }
        if (x.getSource() == e) {
            sql = "select studentName,leaveDate as Last_Payment_Date from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId\n" +
                    "left join invoice\n" +
                    "  on lease.leaseNo = invoice.leaseNo\n" +
                    "where status = 'waiting'\n" +
                    "  and lease.leaseNo is not null\n" +
                    "      and paidDate is null;";
            select(sql);
        }
        if (x.getSource() == f) {
            sql = "select * from inspection\n" +
                    "where satisfactory = 'no';";
            select(sql);
        }
        if (x.getSource() == g) {
            sql = "select studentName,students.studentId,allRooms.placeNo,roomNo from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "join lease\n" +
                    "  on students.studentId = lease.studentId\n" +
                    "join allRooms\n" +
                    "  on lease.placeNo = allRooms.placeNo\n" +
                    "join hallRoom\n" +
                    "  on allRooms.placeNo = hallRoom.placeNo;\n";
            select(sql);
        }
        if (x.getSource() == h) {
            sql = "select studentName,students.* from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "where status = 'waiting';";
            select(sql);
        }
        if (x.getSource() == i) {
            sql = "select category,count(category) as Students from students\n" +
                    "group by category;";
            select(sql);
        }
        if (x.getSource() == j) {
            sql = "select students.studentId,studentName from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "where not exists(\n" +
                    "  select * from kinList\n" +
                    "  where students.studentId = kinList.studentId\n" +
                    "  );";
            select(sql);
        }
        if (x.getSource() == k) {
            sql = "select studentName,stuAdvisor.advisor,officeRoom.internalTel from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "join stuAdvisor\n" +
                    "  on students.studentId = stuAdvisor.studentId\n" +
                    "join advisor\n" +
                    "  on stuAdvisor.advisor = advisor.name\n" +
                    "join officeRoom\n" +
                    "  on advisor.roomNo = officeRoom.roomNo;";
            select(sql);
        }
        if (x.getSource() == l) {
            sql = "select min(rentRate) as Minimal_Rent,max(rentRate) as Maximum_rent,avg(rentRate) as Average_Rent from allRooms;";
            select(sql);
        }
        if (x.getSource() == m){
            sql = "select hall.hallId as hallNo,count(hall.hallId) as Room_Total from hall\n" +
                    "left join hallList\n" +
                    "  on hall.hallId = hallList.hallId\n" +
                    "group by hall.hallId";
            select(sql);
        }
        if (x.getSource() == n) {
            sql = "select staff.staffId,name,timestampdiff(year,birthDate,curdate()) as age,location from staff\n" +
                    "join staffName\n" +
                    "  on staff.staffId = staffName.staffId\n" +
                    "where timestampdiff(year,birthDate,curdate()) >= 60;";
            select(sql);
        }
    }


    private static void select(String sql) {
        tx.setText(
                "<html>" +
                "<body>" +
                        "<table border=1>"
        );
//      String[] header = in.split(" ");
//      tx.setText(tx.getText() + "<tr>");
//      int j = 0;
//      while(j<header.length) {
//          System.out.println(header[j] + "\t");//
//          tx.setText(tx.getText() + "<th>" + header[j] + "</th>");
//          j++;
//      }
//      System.out.print("\n");
//      tx.setText(tx.getText() + "</tr>");
        Connection conn = getConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int col = rs.getMetaData().getColumnCount();
            tx.setText(tx.getText() + "<tr>");
            for (int k = 0;k < col; k++){
                String colname = rs.getMetaData().getColumnName(k+1);
                tx.setText(tx.getText() + "<th>" + colname + "</th>");
                //System.out.print(colname + "\t");
            }
            tx.setText(tx.getText() + "</tr>");

            while (rs.next()) {
                tx.setText(tx.getText() + "<tr>");
                for (int i = 1; i <= col; i++) {
                    //System.out.print(rs.getString(i) + "\t");
                    tx.setText(tx.getText() + "<td>" + rs.getString(i) + "</td>");
                    //if ((i == 2) && (rs.getString(i).length() < 8)) {
                    //    System.out.print("\t");//
                    //}
                }
                //System.out.print("\n");//
                tx.setText(tx.getText() + "</tr>");
            }
            tx.setText(
                    tx.getText() +
                            "</table>"+
                            "</body>" +
                            "</html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


}