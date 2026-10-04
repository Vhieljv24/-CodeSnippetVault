import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class CodingTipWorker extends SwingWorker<String, Void> {

    private final JButton sourceButton;
    private final Component parent;

    public CodingTipWorker(JButton sourceButton, Component parent) {
        this.sourceButton = sourceButton;
        this.parent = parent;
    }

    @Override
    protected String doInBackground() {
        return ApiService.fetchProgrammingJoke();
    }

    @Override
    protected void done() {
        sourceButton.setEnabled(true);
        sourceButton.setText("Get Coding Tip");
        try {
            JOptionPane.showMessageDialog(parent, get(), "Coding Tip", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Could not fetch a tip right now.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
