/**
 * Created by natalieronson on 2017-03-08.
 */
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
        //This class will instantiate our JPanel subclass and place it inside a JFrame
        public static void main(String[]args) {

            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();

            }

            javax.swing.SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    createAndShowGUI();
                }
            });

        }

        private static void createAndShowGUI() {

            //Create a JFrame that will be the outer container for our GUI
            JFrame frame = new JFrame();
            frame.setLayout(new MigLayout("insets 0"));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JComponent myPanel = new A2();
            myPanel.setOpaque(true);
            frame.setContentPane(myPanel);
            //make GUI visible
            frame.pack();
            frame.setVisible(true);
        }

}






