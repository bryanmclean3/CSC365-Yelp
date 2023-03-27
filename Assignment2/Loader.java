package Assignment2;

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
            jsonArray = (JSONArray) parser.parse(new FileReader("src\\Assignment2\\dataset.json"));
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }

        Gson gson = new Gson();
        ExtendibleHashTable hashtable = new ExtendibleHashTable();


        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            Assignment2.Business business = gson.fromJson(jsonObject.toJSONString(), Assignment2.Business.class);
            String str = business.categories;

            String file = "src/Assignment2/data/" + business.business_id + ".txt";
            FileChannel channel = new RandomAccessFile(file, "rw").getChannel();

            hashtable.put(business.name, business.business_id + ".txt");

            assert str != null;
            byte[] bytes = str.getBytes();
            ByteBuffer set1bb = ByteBuffer.wrap(bytes);
            channel.write(set1bb);
            channel.close();
            set1bb.clear();
        }
        String input = Application.input;
        RandomAccessFile reader = new RandomAccessFile("src/Assignment2/data/" + hashtable.get(input), "r");
        String inputCategory = reader.readLine();
        reader.close();
        List<String> catList = List.of(inputCategory.split(", "));
        System.out.println(catList);
        List<List<String>> docs = new ArrayList<>();
        docs.add(catList);

        int position = 0;
        double check = 0;
        tfIdfCalculator calculator = new tfIdfCalculator();
        double currentTfIdf = 0.0;

        List<String> clusters = Arrays.asList("Italian", "Pizza", "American (Traditional)", "Mexican", "Chinese");
        for (int i = 0; i < docs.size(); i++) {
            for (int j = 0; j < clusters.size(); j++) {
                currentTfIdf = calculator.tfIdf(docs.get(i), Application.documents, clusters.get(j));
                System.out.println(currentTfIdf);
                if (currentTfIdf > check) {
                    position = j;
                    check = currentTfIdf;
                }
            }
        }


        label1 = clusters.get(position);
        label2 = clusters.get(position);

    }
}
