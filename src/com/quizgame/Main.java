package com.quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Main extends JFrame implements ActionListener {
    Font font = new Font("Arial", Font.BOLD, 16);
    JPanel panel;
    JLabel difficultyLabel;
    JLabel questionNum;
    JLabel categoryLabel;
    JLabel wait;
    JTextField questionNumInput;
    JButton startQuizBtn;
    String[] difficultyChoice = {"easy", "normal", "hard"};
    String[] categoryName = {"a", "b", "c"};
    int[] categoryIndex = {1, 2, 3, 4};
    JComboBox difficultySelector = new JComboBox(difficultyChoice);
    ;
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

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            URL url = null;
            try {
                url = new URL("https://opentdb.com/api.php?amount=10");
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
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
            System.out.println(content);


//            try {
//                Thread.sleep(600);
//            } catch (InterruptedException x) {
//                x.printStackTrace();
//            }
//            finally {
//               // System.exit(0);
//            }

        }
    }
}

// Java program to read JSON from a file
//
//import java.io.FileReader;
//        import java.util.Iterator;
//        import java.util.Map;
//
//        import org.json.simple.JSONArray;
//        import org.json.simple.JSONObject;
//        import org.json.simple.parser.*;
//
//public class JSONReadExample
//{
//    public static void main(String[] args) throws Exception
//    {
//        // parsing file "JSONExample.json"
//        Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
//
//        // typecasting obj to JSONObject
//        JSONObject jo = (JSONObject) obj;
//
//        // getting firstName and lastName
//        String firstName = (String) jo.get("firstName");
//        String lastName = (String) jo.get("lastName");
//
//        System.out.println(firstName);
//        System.out.println(lastName);
//
//        // getting age
//        long age = (long) jo.get("age");
//        System.out.println(age);
//
//        // getting address
//        Map address = ((Map)jo.get("address"));
//
//        // iterating address Map
//        Iterator<Map.Entry> itr1 = address.entrySet().iterator();
//        while (itr1.hasNext()) {
//            Map.Entry pair = itr1.next();
//            System.out.println(pair.getKey() + " : " + pair.getValue());
//        }
//
//        // getting phoneNumbers
//        JSONArray ja = (JSONArray) jo.get("phoneNumbers");
//
//        // iterating phoneNumbers
//        Iterator itr2 = ja.iterator();
//
//        while (itr2.hasNext())
//        {
//            itr1 = ((Map) itr2.next()).entrySet().iterator();
//            while (itr1.hasNext()) {
//                Map.Entry pair = itr1.next();
//                System.out.println(pair.getKey() + " : " + pair.getValue());
//            }
//        }
//    }
//}

