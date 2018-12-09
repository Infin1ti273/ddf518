import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
    public void actionPerformed(ActionEvent e) {

    }
    public static void main (String[] args) {
        uiui frame = new uiui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}