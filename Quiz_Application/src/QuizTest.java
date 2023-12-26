import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Quiz extends JFrame implements ActionListener {
    JPanel panel;
    JRadioButton choice1;
    JRadioButton choice2;
    JRadioButton choice3;
    JRadioButton choice4;
    ButtonGroup bg;
    JLabel lblmess;
    JButton btnext;
    String[][] gpa;
    String[][] gca;
    int gaid;
    HashMap<Integer, String> map;

    Quiz() {
        initializedata();
        setTitle("Quiz Program");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(430, 350);
        setLocation(300, 100);
        setResizable(false);
        Container cont = getContentPane();
        cont.setLayout(null);
        cont.setBackground(Color.GRAY);
        bg = new ButtonGroup();
        choice1 = new JRadioButton("Choice1", true);
        choice2 = new JRadioButton("Choice2", false);
        choice3 = new JRadioButton("Choice3", false);
        choice4 = new JRadioButton("Choice4", false);
        bg.add(choice1);
        bg.add(choice2);
        bg.add(choice3);
        bg.add(choice4);
        lblmess = new JLabel("Choose a correct answer");
        lblmess.setForeground(Color.BLUE);
        lblmess.setFont(new Font("Arial", Font.BOLD, 11));
        btnext = new JButton("Next");
        btnext.setForeground(Color.BLUE);
        btnext.addActionListener(this);
        panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLocation(10, 10);
        panel.setSize(400, 300);
        panel.setLayout(new GridLayout(6, 2));
        panel.add(lblmess);
        panel.add(choice1);
        panel.add(choice2);
        panel.add(choice3);
        panel.add(choice4);
        panel.add(btnext);
        cont.add(panel);
        setVisible(true);
        gaid = 0;
        readqa(gaid);
    }

    public void actionPerformed(ActionEvent e) {
        if (btnext.getText().equals("Next")) {
            if (gaid < 4) {
                map.put(gaid, getSelection());
                gaid++;
                readqa(gaid);
            } else {
                map.put(gaid, getSelection());
                btnext.setText("Show answers");
            }
        } else if (btnext.getText().equals("Show answers")) {
            new Report(this);
        }
    }

    public void initializedata() {
        gpa = new String[5][5];

        gpa[0][0] = "what is the capital of India?";
        gpa[0][1] = "New Delhi";
        gpa[0][2] = "Noida";
        gpa[0][3] = "Lucknow";
        gpa[0][4] = "Ahmedabad";
        
        gpa[1][0] = "What is the national bird of India?";
        gpa[1][1] = "peacock.";
        gpa[1][2] = "parrot.";
        gpa[1][3] = "Crow.";
        gpa[1][4] = "hen.";

        gpa[2][0] = "whta is the national sport of India?";
        gpa[2][1] = "Hockey";
        gpa[2][2] = "Football";
        gpa[2][3] = "Batminton";
        gpa[2][4] = "Criket";

        gpa[3][0] = "Which one is the Movie of RK";
        gpa[3][1] = "Animal";
        gpa[3][2] = "Chak de India";
        gpa[3][3] = "UDta Punjab";
        gpa[3][4] = "Tanu weds MAnu";

        gpa[4][0] = "What are you doing?";
        gpa[4][1] = "nothing";
        gpa[4][2] = "kuch nhi";
        gpa[4][3] = "kantaal";
        gpa[4][4] = "Boring";

        // ... (similar initialization for other questions)

        // qca stores pairs of question and its correct answer
        gca = new String[5][2];
        gca[0][0] = "what is the capital of India?";
        gca[0][1] = "New Delhi";
        

        gca[1][0] = "What is the national bird of India?";
        gca[1][1] = "peacock.";

        gca[2][0] = "whta is the national sport of India?";
        gca[2][1] = "Hockey";

        gca[3][0] = "Which one is the Movie of RK";
        gca[3][1] = "Animal";

        gca[4][0] = "What are you doing?";
        gca[4][1] = "nothing\"";

        // ... (similar initialization for other correct answers)

        // create a map object to store pairs of question and selected answer
        map = new HashMap<>();
    }

    public String getSelection() {
        String selectedChoice = null;
        Enumeration<AbstractButton> buttons = bg.getElements();
        while (buttons.hasMoreElements()) {
            JRadioButton temp = (JRadioButton) buttons.nextElement();
            if (temp.isSelected()) {
                selectedChoice = temp.getText();
            }
        }
        return (selectedChoice);
    }

    public void readqa(int qid) {
        lblmess.setText("  " + gpa[qid][0]);
        choice1.setText(gpa[qid][1]);
        choice2.setText(gpa[qid][2]);
        choice3.setText(gpa[qid][3]);
        choice4.setText(gpa[qid][4]);
        choice1.setSelected(true);
    }

    public void reset() {
        gaid = 0;
        map.clear();
        readqa(gaid);
        btnext.setText("Next");
    }

    public int calCorrectAnswer() {
        int qnum = 10;
        int count = 0;
        for (int qid = 0; qid < qnum; qid++) {
            if (gca[qid][1].equals(map.get(qid))) {
                count++;
            }
        }
        return count;
    }

    public class Report extends JFrame {
        Report(Quiz quiz) {
            setTitle("Answers");
            setSize(850, 550);
            setBackground(Color.WHITE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                    quiz.reset();
                }
            });
            Draw d = new Draw(quiz);
            add(d);
            setVisible(true);
        }

        class Draw extends Canvas {
            Quiz quiz;

            Draw(Quiz quiz) {
                this.quiz = quiz;
            }

            public void paint(Graphics g) {
                int qnum = 10;
                int x = 10;
                int y = 20;
                for (int i = 0; i < qnum; i++) {
                    // print the 1st column
                    g.setColor(Color.BLUE);
                    g.setFont(new Font("Arial", Font.BOLD, 12));
                    g.drawString(i + 1 + ". " + gca[i][0], x, y);
                    y += 30;
                    g.setFont(new Font("Arial", Font.PLAIN, 12));
                    g.drawString("      Correct answer:" + gca[i][1], x, y);
                    y += 30;
                    g.drawString("      Your answer:" + map.get(i), x, y);
                    y += 30;
                    // print the 2nd column
                    if (y > 400) {
                        y = 20;
                        x = 450;
                    }
                }
                // show number of correct answers
                int numc = quiz.calCorrectAnswer();
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("Number of correct answers:" + numc, 300, 500);
            }
        }
    }
}

public class QuizTest {
    public static void main(String args[]) {
        new Quiz();
    }
}
