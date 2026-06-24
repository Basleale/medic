package OOPE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserProfileCreator extends JFrame implements ActionListener {
    private JRadioButton radUndergrad, radPostgrad, radPro;
    private JCheckBox chkJava, chkPython, chkCpp;
    private JButton submitButton;
    private JTextArea summaryArea;

    public UserProfileCreator() {
        setTitle("Developer Registry");
        setSize(400, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        // UI Panel assemblies
        JPanel radioPanel = new JPanel(new GridLayout(3, 1));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Academic / Career Status"));
        radioPanel.add(radUndergrad);
        radioPanel.add(radPostgrad);
        radioPanel.add(radPro);

        JPanel checkPanel = new JPanel(new GridLayout(3, 1));
        checkPanel.setBorder(BorderFactory.createTitledBorder("Core Core Skillsets"));
        checkPanel.add(chkJava);
        checkPanel.add(chkPython);
        checkPanel.add(chkCpp);

        JPanel upperPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        upperPanel.add(radioPanel);
        upperPanel.add(checkPanel);

        submitButton = new JButton("Generate Summary Profile");
        summaryArea = new JTextArea(6, 30);
        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(summaryArea);

        setLayout(new BorderLayout(10, 10));
        add(upperPanel, BorderLayout.NORTH);
        add(submitButton, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String level = "";
        if (radUndergrad.isSelected()) level = "an Undergraduate Student";
        if (radPostgrad.isSelected()) level = "a Postgraduate Student";
        if (radPro.isSelected()) level = "an Industry Professional";

        StringBuilder skills = new StringBuilder();
        if (chkJava.isSelected()) skills.append("Java ");
        if (chkPython.isSelected()) skills.append("Python ");
        if (chkCpp.isSelected()) skills.append("C++ ");

        if (skills.length() == 0) {
            skills.append("[No Languages Selected]");
        }

        String summary = "The registered profile is currently " + level 
                + " demonstrating proficiency stack options in: " + skills.toString().trim() + ".";
        summaryArea.setText(summary);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserProfileCreator().setVisible(true));
    }
}