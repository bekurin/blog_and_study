package n_builderPattern;

public interface Builder {
    Post build();

    Post title(String title);

    Post description(String description);
}
