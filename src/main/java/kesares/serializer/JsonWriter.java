package kesares.serializer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonWriter implements AutoCloseable {

    private final BufferedWriter writer;
    private final boolean isWritingCompactPrimitiveArray;
    private int indentLevel = 0;

    public JsonWriter(Path path, boolean isCompactPrimitivArray) {
        this.isWritingCompactPrimitiveArray = isCompactPrimitivArray;
        try {
            this.writer = Files.newBufferedWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void writeObject(T object) throws IOException, IllegalAccessException {
        if (object == null) return;
        Field[] fields = object.getClass().getDeclaredFields();

        this.writer.append('{');
        this.writer.newLine();
        this.indentLevel++;
        for (int i = 0; i < fields.length; i++) {
            if (this.isIgnorable(fields[i]))
                continue;
            fields[i].setAccessible(true);
            String fieldName = fields[i].getName();
            Object value = fields[i].get(object);
            fields[i].setAccessible(false);

            this.writeIndent();
            this.writer.append('"').append(fieldName).append("\": ");
            this.writeValue(value);

            this.writeComma(i, fields);
        }

        this.indentLevel--;
        this.writeIndent();
        this.writer.write('}');
    }

    public void writeList(List<?> list) throws IOException, IllegalAccessException {
        this.writeArray(list.toArray());
    }

    public <T> void writeArray(T[] array) throws IOException, IllegalAccessException {
        this.writer.append('[');
        this.writer.newLine();
        this.indentLevel++;
        for (int i = 0; i < array.length; i++) {
            this.writeIndent();
            this.writeValue(array[i]);
            this.writeComma(i, array);
        }
        this.indentLevel--;
        this.writeIndent();
        this.writer.write(']');
    }

    public void writePrimitiveArray(Object array) throws IOException, IllegalAccessException {
        this.writer.append('[');
        int length = Array.getLength(array);
        if (this.isWritingCompactPrimitiveArray) {
            for (int i = 0; i < length; i++) {
                Object value = Array.get(array, i);
                this.writeValue(value);
                if (i < length - 1) {
                    this.writer.append(", ");
                }
            }
        } else {
            this.indentLevel++;
            for (int i = 0; i < length; i++) {
                this.writer.newLine();
                this.writeIndent();
                Object value = Array.get(array, i);
                this.writeValue(value);
                if (i < length - 1) {
                    this.writer.append(",");
                }
            }
            this.indentLevel--;
            this.writer.newLine();
            this.writeIndent();
        }
        this.writer.write(']');
    }

    private void writeValue(Object value) throws IOException, IllegalAccessException {
        if (value == null) {
            this.writer.append("null");
        } else if (this.isJsonStringType(value)) {
            this.writer.append('"').append(value.toString()).append('"');
        } else if (value instanceof Boolean || value instanceof Number) {
            this.writer.append(value.toString());
        } else if (value.getClass().isArray()) {
            if (value.getClass().getComponentType().isPrimitive()) {
                this.writePrimitiveArray(value);
            } else {
                this.writeArray((Object[]) value);
            }
        } else {
            this.writeObject(value);
        }
    }

    @Override
    public void close() throws Exception {
        this.writer.close();
    }

    private <T> void writeComma(int index, T[] array) throws IOException {
        if (index < array.length - 1) {
            this.writer.append(',');
        }
        this.writer.newLine();
    }

    private void writeIndent() throws IOException {
        for (int i = 0; i < this.indentLevel; i++) {
            this.writer.write('\t');
        }
    }

    private boolean isIgnorable(Field field) {
        return field.isAnnotationPresent(JsonIgnore.class);
    }

    private boolean isJsonStringType(Object object) {
        return object instanceof String || object instanceof Character || object instanceof Enum<?>;
    }
}
