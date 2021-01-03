import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

public class Main {

	/**
	 *
	 * @author darkzgothic
	 */
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Can't load native look. Sad face!");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new TankGame().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(TankGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
