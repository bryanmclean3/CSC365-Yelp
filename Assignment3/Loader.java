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
import java.util.List;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Business business = gson.fromJson(jsonObject.toJSONString(), Business.class);

            if(business.name.equals(input)) {
                lat = business.latitude;
                lon = business.longitude;
                break;
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

        for (double i : list) {
            if (i < smallest) {
                fourthSmallest = thirdSmallest;
                thirdSmallest = secondSmallest;
                secondSmallest = smallest;
                smallest = i;
            } else if (i < secondSmallest) {
                fourthSmallest = thirdSmallest;
                thirdSmallest = secondSmallest;
                secondSmallest = i;
            } else if (i < thirdSmallest) {
                fourthSmallest = thirdSmallest;
                thirdSmallest = i;
            } else if (i < fourthSmallest) {
                fourthSmallest = i;
            }
        }

        System.out.println("The four smallest values are " + smallest + ", " + secondSmallest + ", " + thirdSmallest + ", " + fourthSmallest);


        int position = 0;
        double check = 0;
        tfIdfCalculator calculator = new tfIdfCalculator();
        double currentTfIdf = 0.0;

        List<String> clusters = Arrays.asList("Italian", "Pizza", "Sushi Bars", "Mexican", "Chinese");
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
        
    }

}
