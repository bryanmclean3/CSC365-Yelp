package Assignment1;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    SpringLayout layout = new SpringLayout();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Enter Restaurant Name: ");
    JTextField text = new JTextField(50);
    JButton button = new JButton("Enter");
    String input;
    JLabel rec1 = new JLabel("Recommendation 1: ");
    JLabel rec2 = new JLabel("Recommendation 2: ");
    JLabel label1 = new JLabel("");
    JLabel label2 = new JLabel("");

    public GUI() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.setSize(80, 25);
        frame.setSize(1000, 400);
        frame.add(panel);
        frame.setVisible(true);
        panel.setLayout(layout);
        panel.add(label);
        panel.add(text);
        panel.add(button);

        // Put constraint on components
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, text, 5, SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, text, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.EAST, text);
        layout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, panel);

        panel.add(rec1);
        panel.add(rec2);

        layout.putConstraint(SpringLayout.WEST, rec1, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rec1, 50, SpringLayout.NORTH, label);
        layout.putConstraint(SpringLayout.WEST, rec2, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rec2, 50, SpringLayout.NORTH, rec1);

        button.addActionListener(this);

        panel.add(label1);
        panel.add(label2);

        layout.putConstraint(SpringLayout.WEST, label1, 120, SpringLayout.WEST, rec1);
        layout.putConstraint(SpringLayout.NORTH, label1, 50, SpringLayout.NORTH, label);
        layout.putConstraint(SpringLayout.WEST, label2, 120, SpringLayout.WEST, rec2);
        layout.putConstraint(SpringLayout.NORTH, label2, 50, SpringLayout.NORTH, label1);
    }


    public static void main(String[] args) {
        new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        input = text.getText();
        if ( input.equals("chilis")) {
            label1.setText("applebees");
            label2.setText("red robin");
        }
    }
}
