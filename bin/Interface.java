package bin;
import javax.swing.*;
import java.awt.*;
/*fgd*/

public class Interface extends JFrame {

    void InitGUI() throws HeadlessException {
        JFrame frame = new JFrame("Interface");
        frame.setContentPane(new Interface().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(360,360);
        frame.setVisible(true);

    }

    public JPanel panel1;
    public JList list1;


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
