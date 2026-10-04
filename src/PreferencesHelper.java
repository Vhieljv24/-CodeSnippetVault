import java.util.prefs.Preferences;


 
public class PreferencesHelper {

    private static final Preferences prefs = Preferences.userRoot().node("CodeSnippetVault");

    private static final String KEY_LAST_LANGUAGE = "last_language";
    private static final String KEY_LAST_FILTER = "last_filter";

   
    public static void saveLastLanguage(String language) {
        prefs.put(KEY_LAST_LANGUAGE, language);
    }

    public static String getLastLanguage() {
        return prefs.get(KEY_LAST_LANGUAGE, "Java");
    }

    
    public static void saveLastFilter(String filter) {
        prefs.put(KEY_LAST_FILTER, filter);
    }

    public static String getLastFilter() {
        return prefs.get(KEY_LAST_FILTER, "All");
    }
}
