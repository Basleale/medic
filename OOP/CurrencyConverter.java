package OOPE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class CurrencyConverter extends JFrame {
    private JTextField usdField;
    private JComboBox<String> currencyBox;
    private JRadioButton toForeignRadio;
    private JRadioButton toUsdRadio;
    private JButton resetButton;
    private JLabel resultLabel;

    public CurrencyConverter() {
        setTitle("Global Exchange Desk");
        setSize(480, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Task 1.1: Bi-directional Functionality
        toForeignRadio = new JRadioButton("USD to Foreign", true);
        toUsdRadio = new JRadioButton("Foreign to USD");
        ButtonGroup directionGroup = new ButtonGroup();
        directionGroup.add(toForeignRadio);
        directionGroup.add(toUsdRadio);

        ActionListener updateListener = e -> calculateConversion();
        toForeignRadio.addActionListener(updateListener);
        toUsdRadio.addActionListener(updateListener);

        usdField = new JTextField();
        // Task 1.3: Real-time Live Conversion
        usdField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculateConversion(); }
            public void removeUpdate(DocumentEvent e) { calculateConversion(); }
            public void changedUpdate(DocumentEvent e) { calculateConversion(); }
        });

        String[] currencies = {"EUR (Euros)", "GBP (Pounds)", "JPY (Yen)"};
        currencyBox = new JComboBox<>(currencies);
        currencyBox.addActionListener(updateListener);

        // Task 1.2: Reset Capability
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            usdField.setText("");
            currencyBox.setSelectedIndex(0);
            toForeignRadio.setSelected(true);
            resultLabel.setForeground(UIManager.getColor("Label.foreground"));
            resultLabel.setText("Converted Value: --");
        });

        resultLabel = new JLabel("Converted Value: --", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(new JLabel("Conversion Direction:"));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.add(toForeignRadio);
        radioPanel.add(toUsdRadio);
        panel.add(radioPanel);

        panel.add(new JLabel("Enter Amount:"));
        panel.add(usdField);
        panel.add(new JLabel("Target Currency:"));
        panel.add(currencyBox);
        panel.add(new JLabel("")); // Visual blank placeholder
        panel.add(resetButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    private void calculateConversion() {
        if (usdField.getText().trim().isEmpty()) {
            resultLabel.setForeground(UIManager.getColor("Label.foreground"));
            resultLabel.setText("Converted Value: --");
            return;
        }

        try {
            double amount = Double.parseDouble(usdField.getText().trim());
            int choice = currencyBox.getSelectedIndex();
            double result = 0;
            String symbol = "";
            double rate = 1.0;

            if (choice == 0) { rate = 0.92; symbol = " EUR"; } 
            else if (choice == 1) { rate = 0.79; symbol = " GBP"; } 
            else { rate = 155.40; symbol = " JPY"; }

            // Task 1.1 logic implementation (division for reverse)
            if (toForeignRadio.isSelected()) {
                result = amount * rate;
            } else {
                result = amount / rate;
                symbol = " USD";
            }

            resultLabel.setForeground(new Color(0, 102, 204));
            resultLabel.setText(String.format("Converted Value: %.2f%s", result, symbol));
        } catch (NumberFormatException ex) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Error: Invalid Numeric Input");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverter().setVisible(true));
    }
}