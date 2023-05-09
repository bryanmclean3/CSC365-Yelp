package Assignment3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static Assignment3.Application.*;

public class Loader {

    String label1;
    String label2;

    public void WriteWithFileChannelUsingRandomAccessFile()
            throws IOException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("src\\Assignment3\\file1.json"));
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }

        Gson gson = new Gson();
        List<Node> italianHashtable = new ArrayList<Node>();
        List<Node> pizzaHashtable = new ArrayList<Node>();
        List<Node> sushiHashtable = new ArrayList<Node>();
        List<Node> mexicanHashtable = new ArrayList<Node>();
        List<Node> chineseHashtable = new ArrayList<Node>();
        List<String> clusters = Arrays.asList("Italian", "Pizza", "Sushi Bars", "Mexican", "Chinese");
        ExtendibleHashTable hashtable = new ExtendibleHashTable();


        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            Business business = gson.fromJson(jsonObject.toJSONString(), Business.class);
            String str = business.categories;

            String file = "src/Assignment3/data/" + business.business_id + ".txt";
            FileChannel channel = new RandomAccessFile(file, "rw").getChannel();

            hashtable.add(business.name, business.business_id + ".txt");

            assert str != null;
            byte[] bytes = str.getBytes();
            ByteBuffer set1bb = ByteBuffer.wrap(bytes);
            channel.write(set1bb);
            channel.close();
            set1bb.clear();
        }

        String input = Application.input;
        RandomAccessFile reader = new RandomAccessFile("src/Assignment3/data/" + hashtable.get(input), "r");
        String inputCategory = reader.readLine();
        reader.close();
        List<String> catList = List.of(inputCategory.split(", "));
        List<List<String>> docs = new ArrayList<>();
        docs.add(catList);

        float lat = 0;
        float lon = 0;
        List<String> nameList = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Business business = gson.fromJson(jsonObject.toJSONString(), Business.class);

            if(business.name.equals(input)) {
                lat = business.latitude;
                lon = business.longitude;
            } else {
                nameList.add(business.name);
            }
        }

        Haversine hav = new Haversine();
        List<Double> list = new ArrayList<>();
        Double dist = 0.0;
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Business business = gson.fromJson(jsonObject.toJSONString(), Business.class);

            dist = hav.distance(lat, lon, business.latitude, business.longitude);
            if (dist != 0.0) {
                list.add(dist);
            }
        }


        double smallest = Double.MAX_VALUE;
        double secondSmallest = Double.MAX_VALUE;
        double thirdSmallest = Double.MAX_VALUE;
        double fourthSmallest = Double.MAX_VALUE;
        String smallestName = null;
        String smallestName2 = null;
        String smallestName3 = null;
        String smallestName4 = null;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < smallest) {
                fourthSmallest = thirdSmallest;
                thirdSmallest = secondSmallest;
                secondSmallest = smallest;
                smallest = list.get(i);
                smallestName = nameList.get(i);
            } else if (list.get(i) < secondSmallest) {
                fourthSmallest = thirdSmallest;
                thirdSmallest = secondSmallest;
                secondSmallest = list.get(i);
                smallestName2 = nameList.get(i);
            } else if (list.get(i) < thirdSmallest) {
                fourthSmallest = thirdSmallest;
                thirdSmallest = list.get(i);
                smallestName3 = nameList.get(i);
            } else if (list.get(i) < fourthSmallest) {
                fourthSmallest = list.get(i);
                smallestName4 = nameList.get(i);
            }
        }

        int position = 0;
        double check = 0;
        tfIdfCalculator calculator = new tfIdfCalculator();
        double currentTfIdf = 0.0;

        for (int j = 0; j < documents.size(); j++) {
            ArrayList<Double> tfs = new ArrayList<>();
            String cat = null;
            for (int i = 0; i < clusters.size(); i++) {
                double tfIdf = calculator.tfIdf(documents.get(j), documents, clusters.get(i));
                tfs.add(tfIdf);
                cat = names.get(j);
            }
            int indexOfMax = 0;
            for (int i = 1; i < tfs.size(); i++) {
                Double value = tfs.get(i);

                if (value > tfs.get(indexOfMax))
                    indexOfMax = i;
            }
            switch (clusters.get(indexOfMax)) {
                case "Italian" -> {
                    if (tfs.get(indexOfMax) != 0.0 ) {italianHashtable.add(new Node(italianHashtable.size(), cat, tfs.get(indexOfMax)));}}
                case "Pizza" -> {
                    if (tfs.get(indexOfMax) != 0.0 ) {pizzaHashtable.add(new Node(pizzaHashtable.size(), cat, tfs.get(indexOfMax)));}}
                case "Sushi Bars" -> {
                    if (tfs.get(indexOfMax) != 0.0 ) {sushiHashtable.add(new Node(sushiHashtable.size(), cat, tfs.get(indexOfMax)));}}
                case "Mexican" -> {
                    if (tfs.get(indexOfMax) != 0.0 ) {mexicanHashtable.add(new Node(mexicanHashtable.size(), cat, tfs.get(indexOfMax)));}}
                case "Chinese" -> {
                    if (tfs.get(indexOfMax) != 0.0 ) {chineseHashtable.add(new Node(chineseHashtable.size(), cat, tfs.get(indexOfMax)));}}
            }
        }

        for (int i = 0; i < docs.size(); i++) {
            for (int j = 0; j < clusters.size(); j++) {
                currentTfIdf = calculator.tfIdf(docs.get(i), Application.documents, clusters.get(j));
                if (currentTfIdf > check) {
                    position = j;
                    check = currentTfIdf;
                }
            }
        }
        String inputCluster = clusters.get(position);
        List<Node> usedCluster = new ArrayList<Node>();

        if (inputCluster.equals("Italian")) {
            usedCluster = italianHashtable;
        } else if (inputCluster.equals("Pizza")) {
            usedCluster = pizzaHashtable;
        } else if (inputCluster.equals("Sushi Bars")) {
            usedCluster = sushiHashtable;
        } else if (inputCluster.equals("Mexican")) {
            usedCluster = mexicanHashtable;
        } else if (inputCluster.equals("Chinese")) {
            usedCluster = chineseHashtable;
        }

        int source = 0;
        for(int i = 0; i < usedCluster.size(); i ++) {
            if(usedCluster.get(i).name.equals(input)) {
                source = i;
            }
        }

        ArrayList<String> pathNames = new ArrayList<>();
        Dijkstra dijkstra = new Dijkstra(usedCluster.size());
        dijkstra.algo_dijkstra(usedCluster, source);
        for (int i = 0; i < dijkstra.dist.length; i++) {
            pathNames.add(dijkstra.adj_list.get(i).name);
            System.out.println(0 + " \t\t " + i + " \t\t " + dijkstra.dist[i] + "       " + dijkstra.adj_list.get(i).name);
        }
        
        path.setText(String.valueOf(pathNames));

        double k = 0.0;
        double mostSim = 0.0;
        String c = null;
        double p = 0;

        for (Bucket bucket : ExtendibleHashTable.directory) {
            for (Bucket.Node pair : bucket.map) {
                RandomAccessFile filereader = new RandomAccessFile("src/Assignment3/data/" + pair.value, "r");
                String category = filereader.readLine();
                reader.close();
                List<String> categoryList = List.of(category.split(", "));
                mostSim = calculator.tfIdf(categoryList, Application.documents, clusters.get(position));
                k = Math.abs(mostSim - currentTfIdf);
                if (k > p) {
                    p = k;
                    c = pair.key;
                }
            }
        }

        label1 = inputCluster;
        label2 = c;
        close1.setText(smallestName);
        close2.setText(smallestName2);
        close3.setText(smallestName3);
        close4.setText(smallestName4);
    }

}
