import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridLayout;
 
import javax.swing.JFrame;
import javax.swing.JLabel;

import jdk.nashorn.internal.ir.CatchNode;

import java.util.*;
import java.io.*;
 
public class WOTK {
    static JLabel[] label = new JLabel[5];
    static boolean answerMode = false;
    static boolean answerDifficulty = false; // false = easy, true = hard
    static List<String[]> answersEasy = new ArrayList<>();
    static List<String[]> answersHard = new ArrayList<>();
 
    public static void main(String args[]) {
        JFrame frame = new JFrame("The Wrath of the Kilobyte");
        frame.setLayout(new GridLayout(5, 1));
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
                //System.out.println("Char : " + event.getKeyChar());
                frame.revalidate();
                frame.repaint();
            }
            @Override
            public void keyReleased(KeyEvent event) {
            }
            @Override
            public void keyTyped(KeyEvent event) {
            }
        };
       
        for (int i = 0; i < label.length; i++) {
            label[i] = new JLabel();
        }
        label[0].setText("Welcome to 'The Wrath of the Kilobyte'!");
        label[2].setText("This is the questions to the game.");
        label[3].setText("Simply select your question difficulty by pressing the letter 'E' or 'H'.");
        label[4].setText("Finally enter your answer to the question by pressing the correlating key\n and the system will return with the correct one!");
        for (int i = 0; i < label.length; i++) {
            frame.add(label[i]);
        }
        frame.addKeyListener(listener);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        readCSV("answersEasy.csv");
        readCSV("answersHard.csv");
        frame.setVisible(true); 
    }

    public static void askEasyQuestion() {
        answerMode = true;
        System.out.println("Easy Question!!!!"); // testing dynamics and they work
        String[] question = answersEasy.get(0);
        label[0].setText(question[0]);
        // question to be selected at random
        // display selected question (instruction to press corresponding key can be left in label[5]??)
    }

    public static void askHardQuestion() {
        answerMode = true;
        System.out.println("Hard Question!!!!"); // testing dynamics and they work
        // question to be selected at random
        // display selected question (instruction to press corresponding key can be left in label[5]??)
    }

    public static void checkAnswer(char selectedAnswer) {
        answerMode = false;
        System.out.println("answer: " + selectedAnswer); // testing dynamics and they work
        // check if answer is correct
        // display and state press e or h for next question
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