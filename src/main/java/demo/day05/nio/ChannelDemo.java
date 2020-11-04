package demo.day05.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("");
        FileChannel inChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("");
        FileChannel outChannel = fos.getChannel();

        int length = -1;

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while ( (length = inChannel.read(buf)) != -1) {
            buf.flip();
        }
    }
}
