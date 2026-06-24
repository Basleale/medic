package OOPE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserProfileCreator extends JFrame implements ActionListener {
    private JTextField nameField;
    private JRadioButton radUndergrad, radPostgrad, radPro;
    private JCheckBox chkJava, chkPython, chkCpp;
    private JButton submitButton;
    private JTextArea summaryArea;
    private JLabel skillCountLabel;

    public UserProfileCreator() {
        setTitle("Developer Registry");
        setSize(450, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Task 2.2: Add a Text Field for Name Integration
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Full Name: "));
        nameField = new JTextField(20);
        namePanel.add(nameField);

        // Grouping mutual exclusion buttons
        radUndergrad = new JRadioButton("Undergraduate", true);
        radPostgrad = new JRadioButton("Postgraduate");
        radPro = new JRadioButton("Industry Professional");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(radUndergrad);
        statusGroup.add(radPostgrad);
        statusGroup.add(radPro);

        // Multi-selection checkboxes
        chkJava = new JCheckBox("Java");
        chkPython = new JCheckBox("Python");
        chkCpp = new JCheckBox("C++");

        // Task 2.3 logic
        ItemListener counterListener = e -> updateSkillCount();
        chkJava.addItemListener(counterListener);
        chkPython.addItemListener(counterListener);
        chkCpp.addItemListener(counterListener);

        // UI Panel assemblies
        JPanel radioPanel = new JPanel(new GridLayout(3, 1));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Academic / Career Status"));
        radioPanel.add(radUndergrad);
        radioPanel.add(radPostgrad);
        radioPanel.add(radPro);

        JPanel checkPanel = new JPanel(new GridLayout(3, 1));
        checkPanel.setBorder(BorderFactory.createTitledBorder("Core Skillsets"));
        checkPanel.add(chkJava);
        checkPanel.add(chkPython);
        checkPanel.add(chkCpp);

        JPanel upperPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        upperPanel.add(radioPanel);
        upperPanel.add(checkPanel);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(namePanel, BorderLayout.NORTH);
        topContainer.add(upperPanel, BorderLayout.CENTER);

        submitButton = new JButton("Generate Summary Profile");
        summaryArea = new JTextArea(6, 30);
        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(summaryArea);

        // Task 2.3: Selection Counters UI
        skillCountLabel = new JLabel("Skills Selected: 0", SwingConstants.CENTER);
        skillCountLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(skillCountLabel, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(topContainer, BorderLayout.NORTH);
        add(submitButton, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
    }

    private void updateSkillCount() {
        int count = 0;
        if (chkJava.isSelected()) count++;
        if (chkPython.isSelected()) count++;
        if (chkCpp.isSelected()) count++;
        skillCountLabel.setText("Skills Selected: " + count);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Task 2.1: Add Input Validation & Missing Field Warnings
        if (!chkJava.isSelected() && !chkPython.isSelected() && !chkCpp.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select at least one skill!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String level = "";
        if (radUndergrad.isSelected()) level = "an Undergraduate Student";
        if (radPostgrad.isSelected()) level = "a Postgraduate Student";
        if (radPro.isSelected()) level = "an Industry Professional";

        StringBuilder skills = new StringBuilder();
        if (chkJava.isSelected()) skills.append("Java ");
        if (chkPython.isSelected()) skills.append("Python ");
        if (chkCpp.isSelected()) skills.append("C++ ");

        String name = nameField.getText().trim();
        if (name.isEmpty()) name = "[No Name Provided]";

        // Task 2.2 Integration update
        String summary = "The registered profile for " + name + " is currently " + level 
                + " demonstrating proficiency stack options in: " + skills.toString().trim() + ".";
        summaryArea.setText(summary);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserProfileCreator().setVisible(true));
    }
}