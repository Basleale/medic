package OOPE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TextWorkspaceStudio extends JFrame {
    private JTextArea canvasArea;

    public TextWorkspaceStudio() {
        setTitle("MicroPad Text Editor");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        canvasArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(canvasArea);
        add(scrollPane, BorderLayout.CENTER);

        // Building JMenuBar system architecture
        JMenuBar menuBar = new JMenuBar();

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E); // Standard Mnemonic
        
        JMenuItem clearItem = new JMenuItem("Clear Canvas");
        JMenuItem timeItem = new JMenuItem("Insert Timestamp");
        
        // Task 3.1: Add Font Stylization Actions
        JMenuItem largeFontItem = new JMenuItem("Make Font Large (24pt)");
        JMenuItem smallFontItem = new JMenuItem("Make Font Small (12pt)");
        
        editMenu.add(clearItem);
        editMenu.add(timeItem);
        editMenu.addSeparator();
        editMenu.add(largeFontItem);
        editMenu.add(smallFontItem);

        // Task 3.2: Integrate a Dark Mode Toggle Item
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V); // Standard Mnemonic
        JCheckBoxMenuItem darkModeItem = new JCheckBoxMenuItem("Dark Mode");
        viewMenu.add(darkModeItem);

        JMenu systemMenu = new JMenu("System");
        systemMenu.setMnemonic(KeyEvent.VK_S); // Standard Mnemonic
        JMenuItem exitItem = new JMenuItem("Exit Application");
        
        // Task 3.3: Add Keyboard Shortcuts (Mnemonics)
        exitItem.setMnemonic(KeyEvent.VK_X);
        systemMenu.add(exitItem);

        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(systemMenu);
        setJMenuBar(menuBar);

        // Event Processing using Anonymous Inner Classes/Lambdas
        clearItem.addActionListener(e -> canvasArea.setText(""));
        timeItem.addActionListener(e -> canvasArea.append(" [System Timestamp Logged] "));
        
        // Task 3.1 Functionality
        largeFontItem.addActionListener(e -> canvasArea.setFont(new Font("Arial", Font.PLAIN, 24)));
        smallFontItem.addActionListener(e -> canvasArea.setFont(new Font("Arial", Font.PLAIN, 12)));
        
        // Task 3.2 Functionality
        darkModeItem.addItemListener(e -> {
            if (darkModeItem.isSelected()) {
                canvasArea.setBackground(Color.DARK_GRAY);
                canvasArea.setForeground(Color.WHITE);
                canvasArea.setCaretColor(Color.WHITE);
            } else {
                canvasArea.setBackground(Color.WHITE);
                canvasArea.setForeground(Color.BLACK);
                canvasArea.setCaretColor(Color.BLACK);
            }
        });

        exitItem.addActionListener(e -> {
            dispose();      
            System.exit(0); 
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextWorkspaceStudio().setVisible(true));
    }
}