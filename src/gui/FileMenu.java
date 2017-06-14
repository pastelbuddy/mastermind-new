package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FileMenu {

    private MainGUIPanel mainPanel;
    private Options optionPopup;

    public JMenuBar makeFileMenu(MainGUIPanel gui) {

        this.mainPanel = gui;
        optionPopup = new Options(mainPanel);

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            mainPanel.newGame();
            optionPopup.newGame();
        });

        JMenuItem options = new JMenuItem("Options");
        options.addActionListener(new OptionsListen());

        JMenuItem viewSolution = new JMenuItem("View Solution");
        viewSolution.addActionListener(new SolutionListener());

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new QuitListener());

        file.add(newGame);
        file.add(options);
        file.add(viewSolution);
        file.add(quit);

        menuBar.add(file);

        return menuBar;
    }

    class OptionsListen implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            optionPopup.setVisible(true);
        }

    }

    class SolutionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainPanel.getSolutionPanel().setVisible(true);
        }
    }

    class QuitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
