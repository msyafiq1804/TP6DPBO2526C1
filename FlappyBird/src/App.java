import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        Image menuBg = new ImageIcon(App.class.getResource("assets/background.png")).getImage();

        JPanel menuPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (menuBg != null) {
                    g.drawImage(menuBg, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };

        JButton startBtn = new JButton("Start Game");
        JButton exitBtn = new JButton("Exit");

        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(startBtn);
        btnPanel.add(exitBtn);
        menuPanel.add(btnPanel);

        Logic logika = new Logic();
        View tampilan = new View(logika);
        logika.setView(tampilan);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(tampilan, "Game");

        startBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Game");
            tampilan.requestFocus();
            logika.startGame();
        });
        exitBtn.addActionListener(e -> System.exit(0));

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}