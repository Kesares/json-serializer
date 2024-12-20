package kesares.serializer;

import java.nio.file.Path;
import java.util.List;

public class Main {

    private static final Path PATH = Path.of("src/main/resources/tests.json");

    public static void main(String[] args) {
        List<Test> tests = getTests();

        try (JsonWriter writer = new JsonWriter(PATH, true)) {
            writer.writeList(tests);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Test> getTests() {
        Test test1 = new Test(
                (byte) 1,
                (short) 100,
                1000,
                10000L,
                10.5f,
                100.99,
                'A',
                true,
                "Test-String 1",
                Level.ERROR,
                new Position(1, 2),
                new Position[] {
                        null,
                        new Position(7, 8),
                        new Position(6, 5),
                },
                new int[] {5, 1, 4, 2, 3}
        );
        Test test2 = new Test(
                (byte) 2,
                (short) 200,
                2000,
                20000L,
                20.5f,
                200.99,
                'B',
                false,
                "Test-String 2",
                Level.WARN,
                new Position(3, 4),
                new Position[] {
                        new Position(10, 9),
                        new Position(7, 8),
                        null,
                },
                new int[] {1, 2, 3, 4, 5}
        );
        Test test3 = new Test(
                (byte) 3,
                (short) 300,
                3000,
                30000L,
                30.5f,
                300.99,
                'C',
                true,
                "Test-String 3",
                Level.LOG,
                new Position(5, 6),
                null,
                new int[] {5, 4, 3, 2, 1}
        );
        return List.of(test1, test2, test3);
    }
}
