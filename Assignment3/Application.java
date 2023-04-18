package Assignment3;

import Assignment1.HT;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

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
    static JLabel rec1 = new JLabel("Recommendation 1: ");
    static JLabel rec2 = new JLabel("Recommendation 2: ");
    static JLabel clus = new JLabel("Cluster: ");
    static JLabel simil = new JLabel("Most Similar Key: ");
    static JLabel closestNeighbor1 = new JLabel("Closest Geographically: ");
    static JLabel closestNeighbor2 = new JLabel("Closest Geographically: ");
    static JLabel closestNeighbor3 = new JLabel("Closest Geographically: ");
    static JLabel closestNeighbor4 = new JLabel("Closest Geographically: ");
    static JLabel label1 = new JLabel("");
    static JLabel label2 = new JLabel("");
    static JLabel label3 = new JLabel("");
    static JLabel label4 = new JLabel("");
    static JLabel close1 = new JLabel("");
    static JLabel close2 = new JLabel("");
    static JLabel close3 = new JLabel("");
    static JLabel close4 = new JLabel("");
    static List<List<String>> documents = new ArrayList<>();


    public static void main(String[] args) throws IOException, ParseException {

        //initialize JSONParser and create an array of the json objects

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.setSize(80, 25);  //Enter Button
        frame.setSize(1000, 900);   //Frame of GUI
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
        panel.add(clus);
        panel.add(simil);
        panel.add(closestNeighbor1);
        panel.add(closestNeighbor2);
        panel.add(closestNeighbor3);
        panel.add(closestNeighbor4);

        layout.putConstraint(SpringLayout.WEST, rec1, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rec1, 50, SpringLayout.NORTH, label);
        layout.putConstraint(SpringLayout.WEST, rec2, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rec2, 50, SpringLayout.NORTH, rec1);
        layout.putConstraint(SpringLayout.WEST, clus, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, clus, 100, SpringLayout.NORTH, rec2);
        layout.putConstraint(SpringLayout.WEST, simil, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, simil, 50, SpringLayout.NORTH, clus);

        layout.putConstraint(SpringLayout.WEST, closestNeighbor1, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, closestNeighbor1, 100, SpringLayout.NORTH, simil);
        layout.putConstraint(SpringLayout.WEST, closestNeighbor2, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, closestNeighbor2, 50, SpringLayout.NORTH, closestNeighbor1);
        layout.putConstraint(SpringLayout.WEST, closestNeighbor3, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, closestNeighbor3, 50, SpringLayout.NORTH, closestNeighbor2);
        layout.putConstraint(SpringLayout.WEST, closestNeighbor4, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, closestNeighbor4, 50, SpringLayout.NORTH, closestNeighbor3);


        button.addActionListener(new Application());

        panel.add(label1);  //business recommendation 1
        panel.add(label2);  //business recommendation 2
        panel.add(label3);
        panel.add(label4);
        panel.add(close1);
        panel.add(close2);
        panel.add(close3);
        panel.add(close4);


        layout.putConstraint(SpringLayout.WEST, label1, 120, SpringLayout.WEST, rec1);
        layout.putConstraint(SpringLayout.NORTH, label1, 50, SpringLayout.NORTH, label);
        layout.putConstraint(SpringLayout.WEST, label2, 120, SpringLayout.WEST, rec2);
        layout.putConstraint(SpringLayout.NORTH, label2, 50, SpringLayout.NORTH, label1);
        layout.putConstraint(SpringLayout.WEST, label3, 120, SpringLayout.WEST, clus);
        layout.putConstraint(SpringLayout.NORTH, label3, 100, SpringLayout.NORTH, label2);
        layout.putConstraint(SpringLayout.WEST, label4, 120, SpringLayout.WEST, simil);
        layout.putConstraint(SpringLayout.NORTH, label4, 50, SpringLayout.NORTH, label3);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        input = text.getText();

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("src\\Assignment3\\file1.json"));
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }


        Gson gson = new Gson();

        //initialize a hash table that stores the data that is to be parsed
        HT hashTableOne = new HT();        //hashtable that maps name to object
        HT hashTableTwo = new HT();      //hashtable that maps to tf-idf value

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Business business = gson.fromJson(jsonObject.toJSONString(), Business.class);
            hashTableOne.add(business.name, business.categories);  //set appropriate category for key of hashtable
        }

        String userInput = input;
        Object gsonObj = hashTableOne.getValue(userInput); //get category of business inputted by user


        if (gsonObj == null) {  //business is not in JSON file
            label1.setText("Invalid Business!");
            label2.setText("Invalid Business!");

        }
        tfIdfCalculator calculator = new tfIdfCalculator();

        hashTableOne.remove(input);   //remove inputted business and category from hashtables

        String[] queryString = gsonObj.toString().split(", ");
        List<String> inputList = Arrays.asList(queryString);  //categories of input business

        List<String> nextList = null;
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Business business = gson.fromJson(jsonObject.toJSONString(), Business.class);
            nextList = Arrays.asList(business.categories.split(", "));
            documents.add(nextList); //add all business categories

        }

        for (int j = 0; j < documents.size(); j++) {
            double k = 0;
            for (int i = 0; i < inputList.size(); i++) {
                double tfIdf = calculator.tfIdf(documents.get(j), documents, inputList.get(i));
                k = k + tfIdf;
            }
            String doc = String.valueOf(documents.get(j));
            String category = doc.substring(1, doc.length() - 1);  //get rid of brackets
            hashTableTwo.add(category, k);
        }
        hashTableTwo.remove(gsonObj);
        label1.setText(hashTableOne.getKey(hashTableTwo.getHighVal()).toString());  //return highest tf-idf value
        hashTableTwo.remove(hashTableTwo.getHighVal());
        hashTableTwo.remove(hashTableTwo.getValue(hashTableTwo.getHighVal()));
        label2.setText(hashTableOne.getKey(hashTableTwo.getHighVal()).toString()); //return second highest tf-idf value

        Loader loader = new Loader();
        try {
            loader.WriteWithFileChannelUsingRandomAccessFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        label3.setText(loader.label1);
        label4.setText(loader.label2);
    }
}