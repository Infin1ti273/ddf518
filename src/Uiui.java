
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**/
public class Uiui extends JFrame implements ActionListener {

    GridLayout l1 = new GridLayout(2,1);
    JPanel j1 = new JPanel(new GridLayout(14,1));
    JPanel j2 = new JPanel(new FlowLayout());
    static JLabel tx = new JLabel();
    JButton a = new JButton("hallinfo:mng'name+tel");
    JButton b = new JButton("lease detail:name+studNo");
    JButton c = new JButton("lease detail;summer semester");
    JButton d = new JButton("total rent paid of a given stud");
    JButton e = new JButton("studs:not paid invoice+given date");
    JButton f = new JButton("inspections:unsatisfied");
    JButton g = new JButton("name+studID:given roomNo+plcNo in hall");
    JButton h = new JButton("stud:waiting");
    JButton i = new JButton("total number of stud in each cata");
    JButton j = new JButton("name+studNo:have no kin");
    JButton k = new JButton("name+intertel of advisor:a given stud");
    JButton l = new JButton("min,max,ave Mrent of hall");
    JButton m = new JButton("Total number of places in each hall");
    JButton n = new JButton("staffNo,name,age,location:age>60");

    private Uiui() {
        this.setTitle("Database User Interface");
        this.setSize(1000, 800);
        this.setLayout(l1);
        this.add(j2);
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
            sql = "select hallId,staffName.name,hall.tel from hall\n" +
                    "join staff\n" +
                    "  on hall.managerId = staff.staffId\n" +
                    "join staffName\n" +
                    "  on staff.staffId = staffName.staffId;";
            header="Hall_ID Manager Hall_Tel";
            select(header,sql);
        }
        if (x.getSource() == b) {
            sql = "select students.studentId,studentName,leaseNo, duration, placeNo, enterDate, leaveDate from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId;";
            header="Student_No Name Lease_No Duration PlaceNo EnterDate LeaveDate";
            select(header,sql);
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
            header="Lease_No Student_No Duration PlaceNo EnterDate LeaveDate";
            select(header,sql);
        }
        if (x.getSource() == d) {
            sql = "select studentName,pamentDue from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId\n" +
                    "left join invoice\n" +
                    "  on lease.leaseNo = invoice.leaseNo\n";
            header="Student_Name TotalPayment";
            select(header,sql);
        }
        if (x.getSource() == e) {
            sql = "select studentName,leaveDate from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId\n" +
                    "left join invoice\n" +
                    "  on lease.leaseNo = invoice.leaseNo\n" +
                    "where status = 'waiting'\n" +
                    "  and lease.leaseNo is not null\n" +
                    "      and paidDate is null;";
            header="Student_Name Last_Payment_Date";
            select(header,sql);
        }
        if (x.getSource() == f) {
            sql = "select * from inspection\n" +
                    "where satisfactory = 'no';";
            header="Inspection_No Staff_No Date Satisfactory Comment";
            select(header,sql);
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
            header="Name Student_No PlaceNo RoomNo";
            select(header,sql);
        }

        if (x.getSource() == h) {
            sql = "select studentName,students.* from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "where status = 'waiting';";
            header="Name Student_No BirthDate Sex Nationality Category Smoker Status SpecialNeed Comments";
            select(header,sql);
        }
        if (x.getSource() == i) {
            sql = "select category,count(category) from students\n" +
                    "group by category;";
            header="Category Students";
            select(header,sql);
        }
        if (x.getSource() == j) {
            sql = "select students.studentId,studentName from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "where not exists(\n" +
                    "  select * from kinList\n" +
                    "  where students.studentId = kinList.studentId\n" +
                    "  );";
            header="Student_No Name";
            select(header,sql);
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
            header="Student_Name Advisor Internal_Tel";
            select(header,sql);
        }
        if (x.getSource() == l) {
            sql = "select min(rentRate),max(rentRate),avg(rentRate) from allRooms;";
            header="Minimal_Rent Maximum_rent Average_Rent";
            select(header,sql);
        }
        if (x.getSource() == m){
            sql = "select hall.hallId,count(hall.hallId) from hall\n" +
                    "left join hallList\n" +
                    "  on hall.hallId = hallList.hallId\n" +
                    "group by hall.hallId";
            header="Hall_No Total_Room(s)";
            select(header,sql);
        }
        if (x.getSource() == n) {
            sql = "select staff.staffId,name,timestampdiff(year,birthDate,curdate()) as age,location from staff\n" +
                    "join staffName\n" +
                    "  on staff.staffId = staffName.staffId\n" +
                    "where timestampdiff(year,birthDate,curdate()) >= 60;";
            header="Staff_No Name Age location";
            select(header,sql);
        }
    }


    private static void select(String in,String sql) {
        String[] header = in.split(" ");
        tx.setText(
                "<html>" +
                "<body>" +
                        "<table border=1>"
        );
        tx.setText(tx.getText() + "<tr>");
        int j = 0;
        while(j<header.length) {
            System.out.println(header[j] + "\t");//
            tx.setText(tx.getText() + "<th>" + header[j] + "</th>");
            j++;
        }
        System.out.print("\n");//
        tx.setText(tx.getText() + "</tr>");
        Connection conn = getConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                tx.setText(tx.getText() + "<tr>");
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");//
                    tx.setText(tx.getText() + "<td>" + rs.getString(i) + "</td>");
                    //if ((i == 2) && (rs.getString(i).length() < 8)) {
                    //    System.out.print("\t");//
                    //}
                }
                System.out.print("\n");//
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