package board.article.util;

import board.common.snowflake.Snowflake;

public class GenerateId {
    private static final Snowflake snowflake = new Snowflake();

    public static Long nextId() {
        return snowflake.nextId();
    }
}
