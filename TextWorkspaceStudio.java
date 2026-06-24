package OOPE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TextWorkspaceStudio extends JFrame {
    private JTextArea canvasArea;

    public TextWorkspaceStudio() {
        setTitle("MicroPad Text Editor");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        canvasArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(canvasArea);
        add(scrollPane, BorderLayout.CENTER);

        // Building JMenuBar system architecture
        JMenuBar menuBar = new JMenuBar();

        JMenu editMenu = new JMenu("Edit");
        JMenuItem clearItem = new JMenuItem("Clear Canvas");
        JMenuItem timeItem = new JMenuItem("Insert Timestamp");
        editMenu.add(clearItem);
        editMenu.add(timeItem);

        JMenu systemMenu = new JMenu("System");
        JMenuItem exitItem = new JMenuItem("Exit Application");
        systemMenu.add(exitItem);

        menuBar.add(editMenu);
        menuBar.add(systemMenu);
        setJMenuBar(menuBar);

        // Event Processing using Anonymous Inner Classes
        clearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasArea.setText("");
            }
        });

        timeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasArea.append(" [System Timestamp Logged] ");
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();      // Release window framework resources
                System.exit(0); // Safely terminate JVM process execution
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextWorkspaceStudio().setVisible(true));
    }
}