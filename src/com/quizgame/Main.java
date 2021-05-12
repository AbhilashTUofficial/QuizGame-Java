package com.quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Main extends JFrame implements ActionListener {
    Font font = new Font("Arial", Font.BOLD, 16);
    Font font2 = new Font("Arial", Font.BOLD, 24);


    JPanel panel;
    JLabel difficultyLabel;
    JLabel questionNum;
    JLabel categoryLabel;
    JLabel wait;
    JTextField questionNumInput;
    JButton startQuizBtn;
    String[] difficultyChoice = {"easy", "medium", "hard"};
    String[] categoryName = {"Science & Nature", "Comics", "Anime", "General Knowledge", "Television", "Video Games", "Computers", "Sports", "Geography", "History", "Animals", "Vehicles"};
    int[] categoryIndex = {17, 29, 31, 9, 14, 15, 18, 21, 22, 23, 27, 28};


    Object _difficulty;
    Object _question;
    Object _correct_answer;
    Object _incorrect_answer;
    HashMap<Integer, String> quizQuestionDifficultyArray = new HashMap<Integer, String>();
    HashMap<Integer, String> quizQuestionArray = new HashMap<Integer, String>();
    HashMap<Integer, String> quizQuestionCorrectAnswersArray = new HashMap<Integer, String>();
    HashMap<Integer, String[]> quizQuestionIncorrectAnswersArray = new HashMap<Integer, String[]>();
    HashMap<Integer, String> quizQuestionIncorrectAnswers = new HashMap<Integer, String>();


    JTextArea question;
    JLabel currentQuestion;
    JCheckBox optionACheckBox;
    JCheckBox optionBCheckBox;
    JCheckBox optionCCheckBox;
    JCheckBox optionDCheckBox;
    JButton nextBtn;


    JComboBox difficultySelector = new JComboBox(difficultyChoice);
    JComboBox categorySelector = new JComboBox(categoryName);

    Main() {
        setBounds(100, 100, 600, 400);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel());
        setVisible(true);
    }

    JPanel panel() {

        // Menu Panel

        difficultyLabel = new JLabel();
        categoryLabel = new JLabel();
        questionNum = new JLabel();
        questionNumInput = new JTextField();
        startQuizBtn = new JButton();
        wait = new JLabel();

        panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setSize(600, 400);
        panel.setVisible(true);
        panel.setLayout(null);


        categoryLabel.setBounds(80, 60, 200, 34);
        categoryLabel.setText("CATEGORY ");
        categoryLabel.setFont(font);
        categoryLabel.setForeground(Color.white);

        difficultyLabel.setBounds(80, 120, 200, 34);
        difficultyLabel.setText("DIFFICULTY ");
        difficultyLabel.setFont(font);
        difficultyLabel.setForeground(Color.white);

        questionNum.setBounds(80, 180, 300, 34);
        questionNum.setText("HOW MANY QUESTIONS ?");
        questionNum.setFont(font);
        questionNum.setForeground(Color.white);

        wait.setBounds(180, 140, 400, 34);
        wait.setText("Fetching Questions..");
        wait.setFont(new Font("Arial", Font.BOLD, 24));
        wait.setForeground(Color.white);

        categorySelector.setBounds(220, 60, 200, 34);
        categorySelector.setForeground(Color.white);
        categorySelector.setBackground(Color.darkGray);
        categorySelector.setFont(font);

        difficultySelector.setBounds(220, 120, 200, 34);
        difficultySelector.setForeground(Color.white);
        difficultySelector.setBackground(Color.darkGray);
        difficultySelector.setFont(font);

        questionNumInput.setBounds(320, 180, 100, 34);
        questionNumInput.setText("10");
        questionNumInput.setForeground(Color.white);
        questionNumInput.setBackground(Color.darkGray);
        questionNumInput.setFont(font);


        startQuizBtn.setBounds(220, 240, 200, 34);
        startQuizBtn.setText("START QUIZ");
        startQuizBtn.setFocusable(false);
        startQuizBtn.addActionListener(this);


        panel.add(difficultyLabel);
        panel.add(difficultySelector);
        panel.add(categoryLabel);
        panel.add(categorySelector);
        panel.add(questionNum);
        panel.add(questionNumInput);
        panel.add(startQuizBtn);

        return panel;
    }

    void quizPanel(HashMap<Integer, String> quizQuestionArray, int index) {
        // Quiz Panel
        question = new JTextArea();
        question.setBounds(80, 20, 440, 100);
        question.setText(quizQuestionArray.get(index));
        question.setForeground(Color.white);
        question.setFont(font2);
        question.setEditable(false);
        question.setLineWrap(true);
        question.setBackground(Color.darkGray);

        currentQuestion = new JLabel();
        currentQuestion.setBounds(460, 330, 200, 34);
        currentQuestion.setText(index + " out of " + quizQuestionArray.size());
        currentQuestion.setForeground(Color.lightGray);
        currentQuestion.setBackground(Color.darkGray);
        currentQuestion.setFont(font);
        currentQuestion.setVisible(true);

        optionACheckBox = new JCheckBox();
        optionACheckBox.setBounds(140, 120, 400, 34);
        optionACheckBox.setText("Option A");
        optionACheckBox.setBackground(Color.darkGray);
        optionACheckBox.setForeground(Color.white);
        optionACheckBox.setFocusable(false);
        optionACheckBox.setFont(font);

        optionBCheckBox = new JCheckBox();
        optionBCheckBox.setBounds(140, 162, 400, 34);
        optionBCheckBox.setText("Option B");
        optionBCheckBox.setBackground(Color.darkGray);
        optionBCheckBox.setForeground(Color.white);
        optionBCheckBox.setFocusable(false);
        optionBCheckBox.setFont(font);

        optionCCheckBox = new JCheckBox();
        optionCCheckBox.setBounds(140, 204, 400, 34);
        optionCCheckBox.setText("Option C");
        optionCCheckBox.setBackground(Color.darkGray);
        optionCCheckBox.setForeground(Color.white);
        optionCCheckBox.setFocusable(false);
        optionCCheckBox.setFont(font);

        optionDCheckBox = new JCheckBox();
        optionDCheckBox.setBounds(140, 246, 400, 34);
        optionDCheckBox.setText("Option D");
        optionDCheckBox.setBackground(Color.darkGray);
        optionDCheckBox.setForeground(Color.white);
        optionDCheckBox.setFocusable(false);
        optionDCheckBox.setFont(font);

        nextBtn = new JButton();
        nextBtn.setBounds(240, 300, 140, 34);
        nextBtn.setText("Next");
        nextBtn.setFocusable(false);
        nextBtn.setName(String.valueOf(index));
        nextBtn.addActionListener(this);


        panel.add(question);
        panel.add(currentQuestion);
        panel.add(optionACheckBox);
        panel.add(optionBCheckBox);
        panel.add(optionCCheckBox);
        panel.add(optionDCheckBox);
        panel.add(nextBtn);
    }

    public URL generateUrl(int category, String difficulty, int amount) {
        URL url = null;
        try {
            //https://opentdb.com/api.php?amount=10&category=17&difficulty=easy
            url = new URL("https://opentdb.com/api.php?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public StringBuffer fetchJSON(URL url) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        }
        try {
            int status = con.getResponseCode();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        String inputLine = null;
        StringBuffer content = new StringBuffer();
        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            content.append(inputLine);
        }
        try {
            in.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return content;
    }

    public void writeJSONToFile(StringBuffer content) {

        System.out.println(content);
        try {
            File writeToFile = new File("quiz_data.json");
            if (writeToFile.createNewFile()) {
                System.out.println("File created: " + writeToFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("quiz_data.json");
            myWriter.write(String.valueOf(content));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException exx) {
            System.out.println("An error occurred.");
            exx.printStackTrace();
        }
    }

    public void readJSONFromFile() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("quiz_data.json"));

            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray questionList = (JSONArray) jsonObject.get("results");

            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = questionList.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                JSONObject map = iterator.next();
                _difficulty = map.get("difficulty");
                _question = map.get("question");
                _correct_answer = map.get("correct_answer");
                _incorrect_answer = map.get("incorrect_answers");
                quizQuestionDifficultyArray.put(i, _difficulty.toString());
                quizQuestionArray.put(i, _question.toString());
                quizQuestionCorrectAnswersArray.put(i, _correct_answer.toString());
                quizQuestionIncorrectAnswers.put(i, _incorrect_answer.toString());
                i++;
            }
            for (int j = 0; j < quizQuestionArray.size(); j++) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        quizPanel(quizQuestionArray, 0);

    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int cat = categoryIndex[categorySelector.getSelectedIndex()];
        int amt = Integer.parseInt(questionNumInput.getText());
        String diff = (String) difficultySelector.getSelectedItem();
        if (e.getSource() == startQuizBtn) {
            panel.remove(difficultyLabel);
            panel.remove(difficultySelector);
            panel.remove(categoryLabel);
            panel.remove(categorySelector);
            panel.remove(questionNum);
            panel.remove(questionNumInput);
            panel.remove(startQuizBtn);
            panel.repaint();
            panel.add(wait);
            panel.remove(wait);

            URL url = generateUrl(cat, diff, amt);
            StringBuffer content = fetchJSON(url);
            writeJSONToFile(content);
            readJSONFromFile();


//            try {
//                Thread.sleep(600);
//            } catch (InterruptedException x) {
//                x.printStackTrace();
//            }
//            finally {
//               // System.exit(0);
//            }

        }
        if (e.getSource() == nextBtn) {
            panel.remove(question);
            panel.remove(currentQuestion);
            panel.remove(optionACheckBox);
            panel.remove(optionBCheckBox);
            panel.remove(optionCCheckBox);
            panel.remove(optionDCheckBox);
            panel.remove(nextBtn);
            panel.repaint();
            quizPanel(quizQuestionArray, Integer.parseInt(nextBtn.getName()) + 1);
            System.out.println(Integer.parseInt(nextBtn.getName()) + 1);

        }
    }
}
