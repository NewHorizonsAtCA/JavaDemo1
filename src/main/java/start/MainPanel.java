/*
John Bunk
First test client project
*/
package start;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPanel implements ActionListener, Runnable {
    public JLabel tempLabel;
    
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel aPanel = new JPanel();
        aPanel.setLayout(new BoxLayout(aPanel,BoxLayout.Y_AXIS));

        // add the main window components
        JLabel label = new JLabel("Camarillo Temperature");
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));         
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        aPanel.add(label);

        JButton jsonBtn = new JButton("Get Temp");
        jsonBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        aPanel.add(jsonBtn);
        
        tempLabel = new JLabel("Temp = ???");
        tempLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));         
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        aPanel.add(tempLabel);

        frame.getContentPane().add(aPanel);

        jsonBtn.addActionListener(this);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }    
    
    @Override
    public void run() {
        //System.out.println("Hello from thread: " + Thread.currentThread().getName());
        TemperatureRequest tr = new TemperatureRequest();
        String response = tr.Request();
        if (response != "Error") {
            String temperature = tr.ParseTemperature(response);
            SwingUtilities.invokeLater(() -> {
                tempLabel.setText("Temp = " + temperature + " Deg C");
            });
        }
    }
    
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "Get Temp":
                Thread thread = new Thread(this);
                thread.start();
            break;
        }
    }
}
