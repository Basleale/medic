package OOPE;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class ColorCustomizer extends JFrame implements ChangeListener {
    private JSlider redSlider, greenSlider, blueSlider;
    private JPanel colorDisplayPanel;
    private JLabel colorValueLabel;
    private JLabel rVal, gVal, bVal;

    public ColorCustomizer() {
        setTitle("RGB Palette Synthesizer");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sliders configuration setup bounded up to 255
        redSlider = new JSlider(0, 255, 128);
        greenSlider = new JSlider(0, 255, 128);
        blueSlider = new JSlider(0, 255, 128);

        // Task 4.1 Setup: Synchronize Real-time Numerical Text Labels
        rVal = new JLabel("128", SwingConstants.CENTER);
        gVal = new JLabel("128", SwingConstants.CENTER);
        bVal = new JLabel("128", SwingConstants.CENTER);

        // Structural UI display configurations
        JPanel controlPanel = new JPanel(new GridLayout(6, 1));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel rLabelPanel = new JPanel(new BorderLayout());
        rLabelPanel.add(new JLabel("Red Axis:"), BorderLayout.WEST);
        rLabelPanel.add(rVal, BorderLayout.EAST);
        controlPanel.add(rLabelPanel);
        controlPanel.add(redSlider);
        
        JPanel gLabelPanel = new JPanel(new BorderLayout());
        gLabelPanel.add(new JLabel("Green Axis:"), BorderLayout.WEST);
        gLabelPanel.add(gVal, BorderLayout.EAST);
        controlPanel.add(gLabelPanel);
        controlPanel.add(greenSlider);
        
        JPanel bLabelPanel = new JPanel(new BorderLayout());
        bLabelPanel.add(new JLabel("Blue Axis:"), BorderLayout.WEST);
        bLabelPanel.add(bVal, BorderLayout.EAST);
        controlPanel.add(bLabelPanel);
        controlPanel.add(blueSlider);

        colorDisplayPanel = new JPanel();
        colorDisplayPanel.setBackground(new Color(128, 128, 128)); 
        
        // Task 4.3 Setup
        colorValueLabel = new JLabel("R: 128 | G: 128 | B: 128 | Hex: #808080", SwingConstants.CENTER);
        colorValueLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        colorValueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Task 4.2 Setup: Implement Preset Color Buttons
        JPanel presetPanel = new JPanel(new FlowLayout());
        JButton btnMatchRed = new JButton("Match Red");
        JButton btnMatchGreen = new JButton("Match Green");
        JButton btnMatchBlue = new JButton("Match Blue");

        btnMatchRed.addActionListener(e -> { redSlider.setValue(255); greenSlider.setValue(0); blueSlider.setValue(0); });
        btnMatchGreen.addActionListener(e -> { redSlider.setValue(0); greenSlider.setValue(255); blueSlider.setValue(0); });
        btnMatchBlue.addActionListener(e -> { redSlider.setValue(0); greenSlider.setValue(0); blueSlider.setValue(255); });

        presetPanel.add(btnMatchRed);
        presetPanel.add(btnMatchGreen);
        presetPanel.add(btnMatchBlue);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(presetPanel, BorderLayout.NORTH);
        bottomPanel.add(colorValueLabel, BorderLayout.SOUTH);

        JPanel displayContainer = new JPanel(new BorderLayout());
        displayContainer.add(colorDisplayPanel, BorderLayout.CENTER);
        displayContainer.add(bottomPanel, BorderLayout.SOUTH);

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

        // Task 4.1 Update
        rVal.setText(String.valueOf(r));
        gVal.setText(String.valueOf(g));
        bVal.setText(String.valueOf(b));

        // Task 4.3: Add Hexadecimal String Translation
        String hexString = String.format("#%02X%02X%02X", r, g, b);

        // Dynamically alter color metrics values
        colorValueLabel.setText(String.format("R: %-3d | G: %-3d | B: %-3d | Hex: %s", r, g, b, hexString));
        colorDisplayPanel.setBackground(new Color(r, g, b));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorCustomizer().setVisible(true));
    }
}