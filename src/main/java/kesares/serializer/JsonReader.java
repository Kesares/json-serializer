package kesares.serializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonReader implements AutoCloseable {

    private final BufferedReader reader;

    public JsonReader(Path path) {
        try {
            this.reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String read() throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line.trim());
        }
        return builder.toString();
    }

    @Override
    public void close() throws Exception {
        this.reader.close();
    }
}
