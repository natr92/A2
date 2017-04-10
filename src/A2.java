/**
 * Created by natalieronson on 2017-03-08.
 */

/*
I declare that the attached assignment is my own work in accordance
with Seneca Academic Policy. No part of this assignment has been
copied manually or electronically from any other source (including web
sites) or distributed to other students.
Name : Natalie Ronson
Student ID: 109859165
*/
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class A2 extends JPanel implements ActionListener {


    //Unique identifier for the windowing system
    private static final long serialVersionUID = 123456789L;


    // hello there
    //variables
    private JTextArea outputSequence;
    private JTextArea searchSequence;
    private JTextArea seqInfo;

    private Button resetButton;
    private Button searchButton;

    private String[] textLayout = {"40", "50", "60", "70"};

    private JCheckBox setLineFormat;
    private JRadioButton uppercase;
    private JRadioButton lowercase;
    private JComboBox selectTextLayout;
    private String inputText;
    private String originalText;
    private JFrame reset;
    private JScrollPane scrollInfo;

    //constructor
    public A2() {

        //creating container for components using MigLayout
        JPanel controlsArea = new JPanel(new MigLayout());
        //set size of container
        controlsArea.setPreferredSize(new Dimension(800, 700));
        this.add(controlsArea, BorderLayout.SOUTH);

        //label to enter sequence, wrap constraint indicates that the following component goes
        //in the next row
        controlsArea.add(new Label("Input sequence"), "wrap");

        //create a JText area to let the user input a sequence
        searchSequence = new JTextArea();
        searchSequence.setLineWrap(true);
        //text is initially empty
        searchSequence.setText("");
        //add vertical scrollbar
        JScrollPane scrollVertical = new JScrollPane(searchSequence);
        scrollVertical.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //w and h specify the width and height of the component
        controlsArea.add(scrollVertical, "w 100%, h 10%, wrap");

        controlsArea.add(new Label("Select number of characters per line"), "wrap");
        //dropdown box
        selectTextLayout = new JComboBox(textLayout);
        controlsArea.add(selectTextLayout, "wrap");
        //setting the default to 60
        selectTextLayout.setSelectedIndex(2);
        selectTextLayout.addActionListener(this);

        controlsArea.add(new Label("Check box to format in 10 character groups"), "wrap");
        //checkbox
        setLineFormat = new JCheckBox();
        controlsArea.add(setLineFormat, "wrap");

        //radio buttons
        uppercase = new JRadioButton("Uppercase");
        lowercase = new JRadioButton("Lowercase");
        ButtonGroup caseDecision = new ButtonGroup();
        caseDecision.add(uppercase);
        caseDecision.add(lowercase);
        //default to lowercase
        lowercase.setSelected(true);
        controlsArea.add(uppercase, "wrap");
        controlsArea.add(lowercase, "wrap");

        //button to format the sequence
        searchButton = new Button("Format sequence");
        controlsArea.add(searchButton, " wrap");
        searchButton.setActionCommand("search_button_was_pressed");
        searchButton.addActionListener(this);

        //button to reset
        resetButton = new Button("Reset sequence  ");
        controlsArea.add(resetButton, " wrap");
        resetButton.setActionCommand("reset_button_was_pressed");
        resetButton.addActionListener(this);

        //text box for formatted sequence
        controlsArea.add(new Label("Output"), "wrap");
        outputSequence = new JTextArea();
        JScrollPane scrollVerticalOutput = new JScrollPane(outputSequence);
        scrollVerticalOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        controlsArea.add(scrollVerticalOutput, "w 100%, h 10%, wrap");

        //text box for sequence information
        controlsArea.add(new Label("Sequence Information"), "wrap");
        seqInfo = new JTextArea();
        seqInfo.setCaretPosition(0);
        scrollInfo = new JScrollPane(seqInfo);
        scrollInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        controlsArea.add(scrollInfo, "w 100%, h 10%, wrap");



    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //if the format button is pressed
        if (e.getActionCommand() == "search_button_was_pressed") {

            //getting the text from the text area
            inputText = searchSequence.getText();
            originalText = inputText;
            //getting the number of characters to write per line
            int lineNumber = Integer.parseInt(selectTextLayout.getSelectedItem().toString());

            int A = 0;
            int T = 0;
            int G = 0;
            int C = 0;
            int error = 0;

            //calculating number of each base
            for (int i = 0; i < inputText.length(); i++) {

                if (inputText.substring(i, i + 1).matches("[Aa]")) {
                    A += 1;
                }
                else if (inputText.substring(i, i + 1).matches("[Tt]")) {
                    T += 1;
                }
                else if (inputText.substring(i, i + 1).matches("[Gg]")) {
                    G += 1;
                }
                else if (inputText.substring(i, i + 1).matches("[Cc]")) {
                    C += 1;
                }
                //ignoring spaces and new lines
                else if (inputText.substring(i, i + 1).matches(" ")) {

                }
                else if (inputText.substring(i, i + 1).matches("\n")) {

                }

                //determining if other characters are in the sequence
                else {
                    error += 1;
                }
            }
            //error message if there are characters other than ACTG
            if (error > 0) {

                JOptionPane.showMessageDialog(null, "Your sequence must only contain" +
                        " the characters ACTG");
            }

            //format sequence
            else {
                //remove spaces and newlines for formatting
                inputText = inputText.replaceAll("[^ATGCatgc]", "");
                //determine length of the sequence
                int total = inputText.length();


                //base percentages
                double percentA = ((double) A) / total * 100;
                double percentT = ((double) T) / total * 100;
                double percentG = ((double) G) / total * 100;
                double percentC = ((double) C) / total * 100;


                String stats = "Sequence statistics" + '\n' + "Total A: " + A + '\n'
                        + "Total T: " + T + '\n' + "Total C: " + C + '\n' + "Total G: " + G + '\n' + '\n'
                        + "Percent A: " + percentA + "%" + '\n' + "Percent T: " + percentT + "%" + '\n' +
                        "Percent C: " + percentC + "%" + '\n' + "Percent G: " + percentG + "%" + '\n' + '\n'
                        + "Total sequence length: " + total + '\n';

                //if checkbox is selected to format into 10 character blocks
                if (setLineFormat.isSelected()) {
                    StringBuilder tenLines = new StringBuilder();
                    int start = 0;
                    while (start < inputText.length()) {
                        //take the smaller of the values
                        int end = Math.min(start + 10, inputText.length());
                        tenLines.append(inputText.substring(start, end));
                        //add a space every 10 lines
                        tenLines.append(' ');
                        start = end + 1;
                    }

                    inputText = tenLines.toString();



                }
                //convert to uppercase
                if (uppercase.isSelected()) {
                inputText = inputText.toUpperCase();
                    System.out.println("UC" + inputText);


                }

                //convert to lowercase
                if (lowercase.isSelected()) {
                inputText = inputText.toLowerCase();
                    System.out.println("LC" + inputText);

                }

                //formatting based on number of characters per line selected
                StringBuilder lines = new StringBuilder();
                int start = 0;
                int extra = (lineNumber - 1) / 10;


                while (start < inputText.length()) {
                    //adding in extra spaces if the checkbox was checked
                    if (setLineFormat.isSelected()) {
                        int end = Math.min(start + lineNumber + extra, inputText.length());
                        lines.append(inputText.substring(start, end));
                        lines.append('\n');
                        start = end + 1;

                    }

                    //adding new lines at the end of every x number of lines selected
                    else {
                        int end = Math.min(start + lineNumber, inputText.length());
                        lines.append(inputText.substring(start, end));
                        lines.append('\n');
                        start = end + 1;
                    }





            }
                inputText = lines.toString();
                //outputting formatted sequence to a text box
                outputSequence.setText(inputText);
                //outputting sequence statistics to a text box
                seqInfo.setText(stats);


        }

    }

    //resetting sequence
    if (e.getActionCommand() == "reset_button_was_pressed") {

            //button selections
            Object[] options = {"Yes", "No", "Cancel"};
            int n = JOptionPane.showOptionDialog(reset,
                    "Do you want to reset the sequence?",
                    "Reset",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);

            //if the user chooses to reset
            if (n == JOptionPane.YES_OPTION) {
                //set text boxes to empty
                outputSequence.setText("");
                searchSequence.setText("");
                seqInfo.setText("");
                //uncheck box
                setLineFormat.setSelected(false);
                //default to lowercase
                lowercase.setSelected(true);
                //default to 60
                selectTextLayout.setSelectedIndex(2);


            }
        }
    }
}
























