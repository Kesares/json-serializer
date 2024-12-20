package kesares.serializer;

public enum Level {

    ERROR(AnsiColor.RED),
    WARN(AnsiColor.YELLOW),
    DEBUG(AnsiColor.GREEN),
    LOG(AnsiColor.RESET);

    private final String color;

    Level(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}