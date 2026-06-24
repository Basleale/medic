package OOPE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CurrencyConverter extends JFrame implements ActionListener {
    private JTextField usdField;
    private JComboBox<String> currencyBox;
    private JButton convertButton;
    private JLabel resultLabel;

    public CurrencyConverter() {
        setTitle("Global Exchange Desk");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Core containment structuring
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usdField = new JTextField();
        String[] currencies = {"EUR (Euros)", "GBP (Pounds)", "JPY (Yen)"};
        currencyBox = new JComboBox<>(currencies);
        convertButton = new JButton("Convert Amount");
        resultLabel = new JLabel("Converted Value: --", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(new JLabel("Enter USD Amount:"));
        panel.add(usdField);
        panel.add(new JLabel("Target Currency:"));
        panel.add(currencyBox);
        panel.add(new JLabel("")); // Visual blank placeholder
        panel.add(convertButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        convertButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double usd = Double.parseDouble(usdField.getText().trim());
            int choice = currencyBox.getSelectedIndex();
            double result = 0;
            String symbol = "";

            if (choice == 0) { // EUR
                result = usd * 0.92;
                symbol = " EUR";
            } else if (choice == 1) { // GBP
                result = usd * 0.79;
                symbol = " GBP";
            } else { // JPY
                result = usd * 155.40;
                symbol = " JPY";
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