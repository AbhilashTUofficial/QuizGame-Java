package com.quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Main extends JFrame implements ActionListener {
    Font font = new Font("Arial", Font.BOLD, 16);
    Font font2 = new Font("Arial", Font.BOLD, 24);
    int score=0;
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
    String[] selectedOption;
    int questionIndex;
    Random random = new Random();
    Object _difficulty;
    Object _question;
    Object _correct_answer;
    Object _incorrect_answer;
    HashMap<Integer, String> quizQuestionDifficultyArray = new HashMap<Integer, String>();
    HashMap<Integer, String> quizQuestionArray = new HashMap<Integer, String>();
    HashMap<Integer, String> quizQuestionCorrectAnswersArray = new HashMap<Integer, String>();
    HashMap<Integer, String[]> quizAnswerChoices = new HashMap<Integer, String[]>();


    JTextArea question;
    JLabel currentQuestion;
    JCheckBox optionALabel;
    JCheckBox optionBLabel;
    JCheckBox optionCLabel;
    JCheckBox optionDLabel;
    JButton nextBtn;
    JLabel scoreBoard;
    JButton nextQuizBtn;
    JButton tryAgainBtn;


    JComboBox difficultySelector = new JComboBox(difficultyChoice);
    JComboBox categorySelector = new JComboBox(categoryName);

    Main() {
        setBounds(100, 100, 600, 400);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
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

    int[] quizPanel(String _question, HashMap<Integer, String[]> quizAnswerChoices, int index) {

        // Quiz Panel
        String[] choice = quizAnswerChoices.get(index);

        question = new JTextArea();
        question.setBounds(80, 20, 440, 100);
        question.setText(_question);
        question.setForeground(Color.white);
        question.setFont(font2);
        question.setEditable(false);
        question.setLineWrap(true);
        question.setBackground(Color.darkGray);

        currentQuestion = new JLabel();
        currentQuestion.setBounds(460, 330, 200, 34);
        currentQuestion.setText(index + 1 + " out of " + quizQuestionArray.size());
        currentQuestion.setForeground(Color.lightGray);
        currentQuestion.setBackground(Color.darkGray);
        currentQuestion.setFont(font);
        currentQuestion.setVisible(true);

        optionALabel = new JCheckBox();
        optionBLabel = new JCheckBox();
        optionCLabel = new JCheckBox();
        optionDLabel = new JCheckBox();

        optionALabel.setBounds(160, 120, 400, 34);
        optionALabel.setText(choice[0]);
        optionALabel.setBackground(Color.darkGray);
        optionALabel.setForeground(Color.white);
        optionALabel.setFocusable(false);
        optionALabel.setFont(font);
        optionALabel.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                String[] _chosenAnswerArray=quizAnswerChoices.get(index);
                String _chosenAnswer=_chosenAnswerArray[0];
                System.out.println(_chosenAnswer+"!!!");
                optionBLabel.setSelected(false);
                optionCLabel.setSelected(false);
                optionDLabel.setSelected(false);
            }
        });


        optionBLabel.setBounds(160, 162, 400, 34);
        optionBLabel.setText(choice[1]);
        optionBLabel.setBackground(Color.darkGray);
        optionBLabel.setForeground(Color.white);
        optionBLabel.setFocusable(false);
        optionBLabel.setFont(font);
        optionBLabel.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                optionALabel.setSelected(false);
                optionCLabel.setSelected(false);
                optionDLabel.setSelected(false);
            }
        });


        optionCLabel.setBounds(160, 204, 400, 34);
        optionCLabel.setText(choice[2]);
        optionCLabel.setBackground(Color.darkGray);
        optionCLabel.setForeground(Color.white);
        optionCLabel.setFocusable(false);
        optionCLabel.setFont(font);
        optionCLabel.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                optionBLabel.setSelected(false);
                optionALabel.setSelected(false);
                optionDLabel.setSelected(false);
            }
        });

        optionDLabel.setBounds(160, 246, 400, 34);
        optionDLabel.setText(choice[3]);
        optionDLabel.setBackground(Color.darkGray);
        optionDLabel.setForeground(Color.white);
        optionDLabel.setFocusable(false);
        optionDLabel.setFont(font);
        optionDLabel.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                optionBLabel.setSelected(false);
                optionCLabel.setSelected(false);
                optionALabel.setSelected(false);
            }
        });


        nextBtn = new JButton();
        nextBtn.setBounds(240, 300, 140, 34);
        nextBtn.setText("Next");
        nextBtn.setFocusable(false);
        nextBtn.setName(String.valueOf(index));
        nextBtn.addActionListener(this);



        panel.add(question);
        panel.add(currentQuestion);
        panel.add(optionALabel);
        panel.add(optionBLabel);
        panel.add(optionCLabel);
        panel.add(optionDLabel);
        panel.add(nextBtn);
        if(optionALabel.isSelected()){
            return new int[]{index + 1, 0};
        }else if(optionBLabel.isSelected()){
            return new int[]{index + 1, 1};
        }else if(optionCLabel.isSelected()){
            return new int[]{index + 1, 2};
        }else if(optionDLabel.isSelected()){
            return new int[]{index + 1, 3};
        }
        return new int[]{index + 1, 0};
    }

    void quizCompleted() {
        scoreBoard = new JLabel();
        nextQuizBtn = new JButton();
        tryAgainBtn = new JButton();

        scoreBoard.setText("Score: " + score);
        scoreBoard.setFont(font2);
        scoreBoard.setForeground(Color.white);
        scoreBoard.setBounds(240, 120, 200, 50);

        nextQuizBtn.setText("Next Quiz");
        nextQuizBtn.setBounds(340, 260, 160, 34);
        nextQuizBtn.addActionListener(this);
        nextQuizBtn.setFocusable(false);

        tryAgainBtn.setText("Try Again");
        tryAgainBtn.setBounds(120, 260, 160, 34);
        tryAgainBtn.addActionListener(this);
        tryAgainBtn.setFocusable(false);

        panel.add(scoreBoard);
        panel.add(nextQuizBtn);
        panel.add(tryAgainBtn);
    }

    public URL generateUrl(int category, String difficulty, int amount) {
        URL url = null;
        try {
            //https://opentdb.com/api.php?amount=10&category=17&difficulty=easy
            url = new URL("https://opentdb.com/api.php?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty + "&type=multiple");
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
                getOptions(_correct_answer.toString(), _incorrect_answer.toString(), i);
                i++;
            }
            selectedOption=new String[quizQuestionArray.size()];
            questionIndex = quizPanel(quizQuestionArray.get(0), quizAnswerChoices, 0)[0];
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void getOptions(String _correct_answer, String _incorrect_answer, int i) {

        String[] options = new String[4];
        String input = _incorrect_answer.substring(2, _incorrect_answer.length() - 2);
        input = input.replace("\",\"", ",");
        String opt1 = input.substring(0, input.indexOf(","));
        input = input.substring(input.indexOf(",") + 1, input.length());
        String opt2 = input.substring(0, input.indexOf(","));
        input = input.substring(input.indexOf(",") + 1, input.length());
        String opt3 = input;
        String[] temp = {opt1, opt2, opt3};
        int k = 0;

        options[random.nextInt(4)] = _correct_answer;
        for (int j = 0; j < 4; j++) {
            if (options[j] == null) {
                options[j] = temp[k];
                k++;
            }
        }
        quizAnswerChoices.put(i, options);

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
        }
        if (e.getSource() == nextBtn) {
            panel.remove(question);
            panel.remove(currentQuestion);
            panel.remove(optionALabel);
            panel.remove(optionBLabel);
            panel.remove(optionCLabel);
            panel.remove(optionDLabel);
            panel.remove(nextBtn);
            panel.repaint();
            try {
                questionIndex = quizPanel(quizQuestionArray.get(questionIndex), quizAnswerChoices, questionIndex)[0];
                int _chosenAnswerIndex = quizPanel(quizQuestionArray.get(questionIndex), quizAnswerChoices, questionIndex)[1];
                System.out.println(_chosenAnswerIndex);
                if(_chosenAnswerIndex!=9){
                    System.out.println(quizAnswerChoices.get(questionIndex)[_chosenAnswerIndex]);
                }
            } catch (Exception endOfQuiz) {
                quizCompleted();
            }
        }
        if (e.getSource() == tryAgainBtn) {
            panel.remove(scoreBoard);
            panel.remove(tryAgainBtn);
            panel.remove(nextQuizBtn);
            panel.repaint();
            questionIndex = quizPanel(quizQuestionArray.get(0), quizAnswerChoices, 0)[0];
        }
        if (e.getSource() == nextQuizBtn) {
            panel.remove(scoreBoard);
            panel.remove(tryAgainBtn);
            panel.remove(nextQuizBtn);
            panel.repaint();
            panel.add(difficultyLabel);
            panel.add(difficultySelector);
            panel.add(categoryLabel);
            panel.add(categorySelector);
            panel.add(questionNum);
            panel.add(questionNumInput);
            panel.add(startQuizBtn);
        }
    }
}
