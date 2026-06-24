package OOPE;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class ColorCustomizer extends JFrame implements ChangeListener {
    private JSlider redSlider, greenSlider, blueSlider;
    private JPanel colorDisplayPanel;
    private JLabel colorValueLabel;

    public ColorCustomizer() {
        setTitle("RGB Palette Synthesizer");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sliders configuration setup bounded up to 255
        redSlider = new JSlider(0, 255, 128);
        greenSlider = new JSlider(0, 255, 128);
        blueSlider = new JSlider(0, 255, 128);

        // Structural UI display configurations
        JPanel controlPanel = new JPanel(new GridLayout(6, 1));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.add(new JLabel("Red Axis:"));
        controlPanel.add(redSlider);
        controlPanel.add(new JLabel("Green Axis:"));
        controlPanel.add(greenSlider);
        controlPanel.add(new JLabel("Blue Axis:"));
        controlPanel.add(blueSlider);

        colorDisplayPanel = new JPanel();
        colorDisplayPanel.setBackground(new Color(128, 128, 128)); // Mid-gray baseline start
        colorValueLabel = new JLabel("R: 128  |  G: 128  |  B: 128", SwingConstants.CENTER);
        colorValueLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        
        JPanel displayContainer = new JPanel(new BorderLayout());
        displayContainer.add(colorDisplayPanel, BorderLayout.CENTER);
        displayContainer.add(colorValueLabel, BorderLayout.SOUTH);

        setLayout(new GridLayout(1, 2, 10, 10));
        add(controlPanel);
        add(displayContainer);

        // Hooking ChangeListeners onto the adjustment tracks
        redSlider.addChangeListener(this);
        greenSlider.addChangeListener(this);
        blueSlider.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int r = redSlider.getValue();
        int g = greenSlider.getValue();
        int b = blueSlider.getValue();

        // Dynamically alter color metrics values
        colorValueLabel.setText(String.format("R: %-3d | G: %-3d | B: %-3d", r, g, b));
        colorDisplayPanel.setBackground(new Color(r, g, b));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorCustomizer().setVisible(true));
    }
}