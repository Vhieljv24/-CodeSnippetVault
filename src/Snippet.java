
public class Snippet {

    private final int id;
    private final String title;
    private final String language;
    private final String code;
    private final String dateCreated;

    public Snippet(int id, String title, String language, String code, String dateCreated) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.code = code;
        this.dateCreated = dateCreated;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public String getCode() { return code; }
    public String getDateCreated() { return dateCreated; }
}
