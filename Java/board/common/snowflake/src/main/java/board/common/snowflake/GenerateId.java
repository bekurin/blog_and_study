package board.common.snowflake;

public class GenerateId {
    private static final Snowflake snowflake = new Snowflake();

    public static Long nextId() {
        return snowflake.nextId();
    }
}
