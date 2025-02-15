/*
John Bunk
First test client project
*/
package start;

public class HelloWorldSwing {
    private static MainPanel m_MainPanel;

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                m_MainPanel = new MainPanel();
                m_MainPanel.createAndShowGUI();
            }
        });
    }
}
