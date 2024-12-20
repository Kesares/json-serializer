package kesares.serializer;

public class Test {

    private byte b;
    private short s;
    private int i;
    private long l;
    private float f;
    private double d;
    private char c;
    private boolean success;
    private String string;
    private Level level;
    private Position position;
    private Position[] positions;
    private int[] nums;

    public Test(byte b, short s, int i, long l, float f, double d, char c, boolean success, String string,
                Level level, Position position, Position[] positions, int[] nums) {
        this.b = b;
        this.s = s;
        this.i = i;
        this.l = l;
        this.f = f;
        this.d = d;
        this.c = c;
        this.success = success;
        this.string = string;
        this.level = level;
        this.position = position;
        this.positions = positions;
        this.nums = nums;
    }
}
