import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Runs the given action every time the search field's text changes
 * (typing, pasting, or deleting), so the snippet list can filter live
 * as the user types.
 */
public class SearchDocumentListener implements DocumentListener {

    /** Small functional interface so this can be built with a method reference. */
    public interface Action {
        void run();
    }

    private final Action action;

    public SearchDocumentListener(Action action) {
        this.action = action;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        action.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        action.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        action.run();
    }
}
