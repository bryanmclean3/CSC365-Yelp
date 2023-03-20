package Assignment2;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class throwaway {

    @Test
    public void WriteWithFileChannelUsingRandomAccessFile()
            throws IOException {
        String file = "src/Assignment2/filechannel.bin";
        try ( FileChannel channel = new RandomAccessFile(file, "rw").getChannel()){
            ByteBuffer intbb = ByteBuffer.allocate(Integer.BYTES);
            intbb.putInt(12345);
            intbb.flip();

            ByteBuffer longbb = ByteBuffer.allocate(Long.BYTES);
            longbb.putLong(63524);
            longbb.flip();

            String str = "{ red, blue }";
            byte[] bytes = str.getBytes();
            ByteBuffer set1bb = ByteBuffer.wrap(bytes);

            String str2 = "{ yellow, green }";
            byte[] bytes2 = str2.getBytes();
            ByteBuffer set2bb = ByteBuffer.wrap(bytes2);

            channel.write(intbb);
            channel.write(longbb,4);
            channel.write(set1bb, 15);
            channel.write(set2bb, 30);

            // verify
            RandomAccessFile reader = new RandomAccessFile(file, "r");
            System.out.println(reader.readLine());
            reader.close();
        }
    }
}
