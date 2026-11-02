import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.net.URL;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    int pipeVelocityX = -2;

    View view;
    Image bgImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    Player player;
    ArrayList<Pipe> pipes;

    private int score = 0;
    private boolean gameOver = false;
    private Clip bgmClip;

    public Logic() {
        bgImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        // Timer awal kemunculan pipa diatur ke 1700ms (lebih lambat & berjarak di awal)
        pipesCooldown = new Timer(1700, e -> placePipes());
        gameLoop = new Timer(1000 / 60, this);
    }

    public void startGame() {
        gameLoop.start();
        pipesCooldown.start();
        playBGM("bgm.wav");
    }

    public void setView(View view) { this.view = view; }
    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public int getScore() { return score / 2; }
    public boolean isGameOver() { return gameOver; }
    public Image getBgImage() { return bgImage; }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));

        // PENGATURAN KESULITAN 1: Celah vertikal mengecil seiring skor bertambah
        int baseOpening = frameHeight / 3; // Celah awal lebih besar (~213 piksel)
        int decreaseRate = getScore() * 4; // Celah menyempit 4 piksel setiap poin
        int minOpening = 100; // Batas sempit maksimal agar burung tetap bisa lewat
        int dynamicOpeningSpace = Math.max(minOpening, baseOpening - decreaseRate);

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + dynamicOpeningSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void move() {
        if (gameOver) return;

        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            if (!pipe.passed && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                score++;
                pipe.passed = true;

                // PENGATURAN KESULITAN 2: Pipa muncul semakin cepat/rapat seiring tingginya skor
                // Waktu awal 1700ms, berkurang 20ms setiap poin, batas tercepat adalah 900ms
                int newDelay = Math.max(900, 1700 - (getScore() * 20));
                pipesCooldown.setDelay(newDelay);
            }

            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
            if (playerRect.intersects(pipeRect)) {
                gameOver = true;
            }
        }

        if (player.getPosY() > frameHeight - player.getHeight()) {
            gameOver = true;
        }

        if (gameOver) {
            gameLoop.stop();
            pipesCooldown.stop();
            if (bgmClip != null) {
                bgmClip.stop();
            }
            playSound("gameover.wav");
        }
    }

    public void playSound(String soundFileName) {
        try {
            URL url = getClass().getResource("assets/" + soundFileName);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Audio error: " + e.getMessage());
        }
    }

    public void playBGM(String soundFileName) {
        try {
            URL url = getClass().getResource("assets/" + soundFileName);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioIn);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            System.out.println("BGM error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.updateScoreLabel();
            view.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                player.setVelocityY(-10);
                playSound("lompat.wav");
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            player.setPosX(playerStartPosX);
            player.setPosY(playerStartPosY);
            player.setVelocityY(0);
            pipes.clear();
            score = 0;
            gameOver = false;

            // Kembalikan timer kemunculan pipa ke lambat saat restart
            pipesCooldown.setDelay(1700);

            gameLoop.start();
            pipesCooldown.start();
            if (bgmClip != null) {
                bgmClip.setFramePosition(0);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}