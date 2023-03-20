package Assignment2;

import Assignment1.ReadJson;
import Assignment1.tfIdfCalculator;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application implements ActionListener {
    static SpringLayout layout = new SpringLayout();
    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JLabel label = new JLabel("Enter Restaurant Name: ");
    static JTextField text = new JTextField(50);
    static JButton button = new JButton("Enter");
    static String input;
    static JLabel rec1 = new JLabel("Category: ");
    static JLabel rec2 = new JLabel("Most Similar Key: ");
    static JLabel label1 = new JLabel("");
    static JLabel label2 = new JLabel("");

    public static void main(String[] args) throws IOException, ParseException {

        //initialize JSONParser and create an array of the json objects

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.setSize(80, 25);  //Enter Button
        frame.setSize(1000, 400);   //Frame of GUI
        frame.add(panel);   // panel within Frame containing all text and buttons
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

        panel.add(rec1);  // add fields for recommendations 1 & 2
        panel.add(rec2);

        layout.putConstraint(SpringLayout.WEST, rec1, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rec1, 50, SpringLayout.NORTH, label);
        layout.putConstraint(SpringLayout.WEST, rec2, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rec2, 50, SpringLayout.NORTH, rec1);

        button.addActionListener(new Application());

        panel.add(label1);  //business recommendation 1
        panel.add(label2);  //business recommendation 2

        layout.putConstraint(SpringLayout.WEST, label1, 120, SpringLayout.WEST, rec1);
        layout.putConstraint(SpringLayout.NORTH, label1, 50, SpringLayout.NORTH, label);
        layout.putConstraint(SpringLayout.WEST, label2, 120, SpringLayout.WEST, rec2);
        layout.putConstraint(SpringLayout.NORTH, label2, 50, SpringLayout.NORTH, label1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        
    }
}
