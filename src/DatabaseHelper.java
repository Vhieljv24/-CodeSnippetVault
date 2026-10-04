import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:snippet_vault.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found on classpath: " + e.getMessage());
        }
    }

    public DatabaseHelper() {
        createTable();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS snippets ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, "
                + "language TEXT NOT NULL, "
                + "code TEXT NOT NULL, "
                + "date_created TEXT NOT NULL)";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

   
    public boolean insertSnippet(String title, String language, String code) {
        String sql = "INSERT INTO snippets(title, language, code, date_created) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, language);
            ps.setString(3, code);
            ps.setString(4, java.time.LocalDate.now().toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting snippet: " + e.getMessage());
            return false;
        }
    }

  
    public boolean updateSnippet(int id, String title, String language, String code) {
        String sql = "UPDATE snippets SET title = ?, language = ?, code = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, language);
            ps.setString(3, code);
            ps.setInt(4, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating snippet: " + e.getMessage());
            return false;
        }
    }

    
    public boolean deleteSnippet(int id) {
        String sql = "DELETE FROM snippets WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting snippet: " + e.getMessage());
            return false;
        }
    }

    
    public List<Snippet> getAllSnippets() {
        List<Snippet> list = new ArrayList<>();
        String sql = "SELECT * FROM snippets ORDER BY id DESC";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching snippets: " + e.getMessage());
        }
        return list;
    }

    public List<Snippet> getSnippetsByLanguage(String language) {
        List<Snippet> list = new ArrayList<>();
        String sql = "SELECT * FROM snippets WHERE language = ? ORDER BY id DESC";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, language);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching snippets: " + e.getMessage());
        }
        return list;
    }

    private Snippet mapRow(ResultSet rs) throws SQLException {
        return new Snippet(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("language"),
                rs.getString("code"),
                rs.getString("date_created")
        );
    }
}
