package com.Polodz.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.springframework.core.serializer.Deserializer;


public class CustomTelnetDeserializer implements Deserializer<String> {

    /**
     * Deserilize stream by socket Capacity to String
     * 
     */
    public String deserialize(InputStream inputStream) throws IOException {
        return handshakeStreamToString(inputStream);
    }

    private String handshakeStreamToString(InputStream inputStream) throws IOException {
        final StringBuilder builder = new StringBuilder();

        final ReadableByteChannel inChannel = Channels.newChannel(inputStream);
        final ByteBuffer buffer = ByteBuffer.allocate(1);

        boolean flag = true;
        double endCount = 0.0;
        while (flag) {
            if (inputStream.available() == 0) {
                try {
                    Thread.sleep((long) (45 * (1 - endCount)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (endCount > 0.5) {
                    flag = false;
                }
                endCount += 0.1;
            } else {
                endCount = 0.0;
                inChannel.read(buffer);
                buffer.flip();
                builder.append((char) buffer.get());
                buffer.clear();
            }
        }

        return builder.toString();
    }
}
