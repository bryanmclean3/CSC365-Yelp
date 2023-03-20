package Assignment2;

import Assignment1.Business;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Loader {
    @Test
    public void WriteWithFileChannelUsingRandomAccessFile()
            throws IOException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("src\\Assignment2\\file1.json"));
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }

        Gson gson = new Gson();


        String str = null;

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                Assignment2.Business business = gson.fromJson(jsonObject.toJSONString(), Assignment2.Business.class);
                str = business.categories;

                String file = "src/Assignment2/data/" + business.name + ".txt";
                FileChannel channel = new RandomAccessFile(file, "rw").getChannel();

                assert str != null;
                byte[] bytes = str.getBytes();
                ByteBuffer set1bb = ByteBuffer.wrap(bytes);
                channel.write(set1bb);
                channel.close();
                set1bb.clear();
            }
/*
            // verify
            RandomAccessFile reader = new RandomAccessFile(file, "r");
            System.out.println(reader.readLine());
            reader.close();
        */
    }
}
