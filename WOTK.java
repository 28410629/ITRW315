import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
// get minimal libraries, current a quick fix
import java.util.*;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.*;

public class WOTK {
    static JLabel[] label = new JLabel[6];
    /*
    0 - easy or hard question
    1 - answer a
    2 - answer b
    3 - answer c
    4 - answer d
    5 - correct answer
     */
    static boolean answerMode = false;
    static boolean answerDifficulty = false; // false = easy, true = hard
    static List<String[]> answersEasy = new ArrayList<>();
    static List<String[]> answersHard = new ArrayList<>();
    static String[] question; // question currently asked
    static Random random = new Random(); // random question selection

    public static void main(String args[]) {
        JFrame frame = new JFrame("The Wrath of the Kilobyte");
        ImageIcon img = new ImageIcon("favicon.png");
        frame.setIconImage(img.getImage());
        frame.setLayout(new GridLayout(6, 1));
        frame.setBackground(Color.black);

        //starts music
        try
        {
          Clip clip = AudioSystem.getClip();
          clip.open(AudioSystem.getAudioInputStream(new File("song.wav").toURL()));
          clip.start();
          clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
          System.out.println("Error in audio.");
        }

        KeyListener listener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (answerMode) {
                    switch (event.getKeyChar()) {
                        case 'a':
                            checkAnswer('a');
                            break;
                        case 'b':
                            checkAnswer('b');
                            break;
                        case 'c':
                            checkAnswer('c');
                            break;
                        case 'd':
                            checkAnswer('d');
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (event.getKeyChar()) {
                        case 'e':
                            answerDifficulty = false;
                            askEasyQuestion();
                            break;
                        case 'h':
                            answerDifficulty = true;
                            askHardQuestion();
                            break;
                        default:
                            break;
                    }
                }
                // useful for debug:
                // System.out.println("Char : " + event.getKeyChar());
                frame.revalidate();
                frame.repaint();
            }
            @Override
            public void keyReleased(KeyEvent event) {
                // leave blank, event not used, but overwritten
            }
            @Override
            public void keyTyped(KeyEvent event) {
                // leave blank, event not used, but overwritten
            }
        };

        // create labels
        for (int i = 0; i < label.length; i++)
        {
            label[i] = new JLabel();
            label[i].setFont(new Font("Lucida Console", Font.PLAIN, 11));
            label[i].setForeground(Color.white);
            label[i].setOpaque(true);
            label[i].setBackground(Color.BLACK);
        }
        // intro, can maybe be changed to messagebox with ok button
        label[0].setText("Welcome to 'The Wrath of the Kilobyte'!");
        label[2].setText("This is the questions to the game.");
        label[3].setText("Simply select your question difficulty by pressing the letter 'E' or 'H'.");
        label[4].setText("Finally enter your answer to the question by pressing the correlating key\n and the system will return with the correct one!");
        // add them to frame
        for (int i = 0; i < label.length; i++) {
            frame.add(label[i]);
        }
        // frame properties
        frame.addKeyListener(listener);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 250);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        // load answer csv before showing frame
        readCSV("answersEasy.csv");
        readCSV("answersHard.csv");
        // show frame
        frame.setVisible(true);
    }

    public static void askEasyQuestion() {
        answerMode = true;
        question = answersEasy.get(random.nextInt(answersEasy.size()));
        displayQuestion();
    }

    public static void askHardQuestion() {
        answerMode = true;
        question = answersHard.get(random.nextInt(answersHard.size()));
        displayQuestion();
    }

    public static void displayQuestion() {
        label[0].setText(question[0]);
        label[1].setText("a) " + question[1]);
        label[2].setText("b) " + question[2]);
        label[3].setText("c) " + question[3]);
        label[4].setText("d) " + question[4]);
        label[5].setText("Please select your answer by pressing corresponding keyboard key.");
    }

    public static void checkAnswer(char selectedAnswer) {
        answerMode = false;
        if (question[5].contains(selectedAnswer + "")) {
            label[5].setText("<html><font color='green'>The answer is: " + question[5] + ".</font>");
        } else {
            label[5].setText("<html><font color='red'>The answer is: " + question[5] + ".</font>");
        }
    }

    public static void readCSV(String filename) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (filename == "answersEasy.csv") {
                    answersEasy.add(values);
                } else {
                    answersHard.add(values);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error occured reading CSV!");
        }
    }
}
