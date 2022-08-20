package n_builderPattern;

public class Post implements Builder {
    private String title;
    private String description;

    public Post() {

    }

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public Post build() {
        return new Post(title, description);
    }

    @Override
    public Post title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Post description(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
