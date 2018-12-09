import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;

/**/
public class uiui extends JFrame implements ActionListener{

    JPanel panel = new JPanel();
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
    JButton m = new JButton("numbers if plcs");
    JButton n = new JButton("staffNo,name,age,location:age>60");

    public uiui() {
        this.setTitle("Database User Interface");
        this.setSize(400,800);
        this.setLayout(new FlowLayout());

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

        this.add(a);
        this.add(b);
        this.add(c);
        this.add(d);
        this.add(e);
        this.add(f);
        this.add(g);
        this.add(h);
        this.add(i);
        this.add(j);
        this.add(k);
        this.add(l);
        this.add(m);
        this.add(n);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent x) {
        String sql;

        if(x.getSource()==a)
        {
            sql = "select hallId,staffName.name,hall.tel from hall\n" +
                    "join staff\n" +
                    "  on hall.managerId = staff.staffId\n" +
                    "join staffName\n" +
                    "  on staff.staffId = staffName.staffId;";
            select(sql);
        }
        if(x.getSource()==b)
        {
            sql = "select students.studentId,studentName,lease.* from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId;";
            select(sql);
        }
        if(x.getSource()==c)
        {
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
        if(x.getSource()==d)
        {
            sql = "select studentName,pamentDue from students\n" +
                    "left join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "left join lease\n" +
                    "  on students.studentId = lease.studentId\n" +
                    "left join invoice\n" +
                    "  on lease.leaseNo = invoice.leaseNo\n" +
                    "where studentName = 'Fan.A';";
            select(sql);
        }
        if(x.getSource()==e)
        {
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
            select(sql);
        }
        if(x.getSource()==f)
        {
            sql = "select * from inspection\n" +
                    "where satisfactory = 'no';";
            select(sql);
        }
        if(x.getSource()==g)
        {
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
        if(x.getSource()==h)
        {
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
        if(x.getSource()==i)
        {
            sql = "select studentName,students.* from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "where status = 'waiting';";
            select(sql);
        }
        if(x.getSource()==j)
        {
            sql = "select category,count(category) from students\n" +
                    "group by category;";
            select(sql);
        }
        if(x.getSource()==k)
        {
            sql = "select students.studentId,studentName from students\n" +
                    "join stuName\n" +
                    "  on students.studentId = stuName.studentId\n" +
                    "where not exists(\n" +
                    "  select * from kinList\n" +
                    "  where students.studentId = kinList.studentId\n" +
                    "  );";
            select(sql);
        }
        if(x.getSource()==l)
        {
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
        if(x.getSource()==m)
        {
            sql = "select min(rentRate),max(rentRate),avg(rentRate) from allRooms;";
            select(sql);
        }
        if(x.getSource()==n)
        {
            sql = "select staff.staffId,name,timestampdiff(year,birthDate,curdate()) as age,location from staff\n" +
                    "join staffName\n" +
                    "  on staff.staffId = staffName.staffId\n" +
                    "where timestampdiff(year,birthDate,curdate()) >= 60;";
            select(sql);
        }
    }

    public static void main (String[] args) {
        uiui frame = new uiui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void select(String sql){
        Connection conn = getConnection();
        PreparedStatement pstmt;
        try{
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
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
}