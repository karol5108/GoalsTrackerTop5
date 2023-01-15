import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;


public class Tracker implements ActionListener, ChangeListener {

    private JFrame frame;                               //// main menu
    String season, filename;
    private JButton[] players = new JButton[6];
    private JButton replace;

    private Font myFont = new Font("aqua", Font.BOLD, 20);
    private JTextField text;
    private static Player[] p = new Player[5];
    private String[] paramters = new String[30];
    ///////// icons for buttons
    private String namepl = "C:/Users/karol/IdeaProjects/JAVA/GoldenFoot/GoldenFootTracker/pl.png";
    private String namelal = "C:/Users/karol/IdeaProjects/JAVA/GoldenFoot/GoldenFootTracker/laliga.png";
    private String namesa = "C:/Users/karol/IdeaProjects/JAVA/GoldenFoot/GoldenFootTracker/Seriea.png";
    private String namebun = "C:/Users/karol/IdeaProjects/JAVA/GoldenFoot/GoldenFootTracker/Bundes.png";
    private String namel1 = "C:/Users/karol/IdeaProjects/JAVA/GoldenFoot/GoldenFootTracker/ligue1.png";
    private Icon pl = new ImageIcon(namepl);
    private Icon lal = new ImageIcon(namelal);
    private Icon bundes = new ImageIcon(namebun);
    private Icon l1 = new ImageIcon(namel1);
    private Icon seriea = new ImageIcon(namesa);

    /// replacing last player, player from rList
    private JTextField[] rText = new JTextField[6];
    private Player rPlayer;
    private JButton rCheck, rAdd;
    private JComboBox boxOfLeagues;
    private JPanel mgPanel;
    private JSlider gSlider, mSlider;
    private JLabel gLabel, mLabel;
    private String[] leagues = {"PremierLeague", "SerieA", "Ligue1", "BundesLiga", "LaLiga"};
    private String rName, rLastName, rClub, rLeague;
    private String rListName = "C:/Users/karol/IdeaProjects/JAVA/GoldenFoot/GoldenFootTracker/rlist.txt";
    private int rGoals, rMatches;

    ///// player edit
    private JTextField addgoal1, addmatch1, info, stats;
    private JButton add1, back1, confirm1;
    private Player ePlayer;
    private int ng, nm;

    private JLabel check;


    Tracker(String season, String filename) {
        this.season = season;
        this.filename = filename;

        int i = 0;
        try {
            FileInputStream list = new FileInputStream(filename);
            Scanner sc = new Scanner(list);
            while (sc.hasNextLine()) {
                paramters[i] = sc.nextLine();
                i++;
            }
            sc.close();
            i = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int k = 0; k < paramters.length; k += 6) {
            Player player = new Player(paramters[k], paramters[k + 1], paramters[k + 2], paramters[k + 3], Integer.parseInt(paramters[k + 4]), Integer.parseInt(paramters[k + 5]));
            p[i] = player;
            i++;
        }

        sort(p);

        frame = new JFrame("GoldenFootTracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(null);

        text = new JTextField();
        text.setBounds(75, 25, 550, 30);
        text.setText("Goals Tracker | TOP 5 Strikers | Season: " + season);
        text.setFont(myFont);
        text.setEditable(false);

        int y = 100;
        for (i = 0; i < 5; i++) {
            JButton button = new JButton(setIcon(p[i]));
            button.setText(p[i].toString());
            button.setFont(myFont);
            button.setHorizontalTextPosition(AbstractButton.LEADING);
            players[i] = button;
            players[i].setBounds(50, y, 500, 100);
            players[i].addActionListener(this);
            frame.add(players[i]);
            y += 100;
        }

        replace = new JButton("REPLACE LAST");
        replace.setBounds(125, 650, 350, 100);
        replace.addActionListener(this);

        frame.add(replace);
        frame.add(text);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //replace last
        if (e.getSource() == replace) {
            frame.remove(replace);
            frame.remove(text);

            for (int i = 0; i < 5; i++) {
                frame.remove(players[i]);
            }
            frame.setVisible(false);

            String[] strings = {"Name", "Lastname", "C:"};

            int y = 25;
            for (int j = 0; j < 3; j++) {
                JTextField tf = new JTextField();
                tf.setText(strings[j]);
                rText[j] = tf;
                rText[j].setBounds(125, y, 350, 80);
                rText[j].setEditable(true);
                frame.add(rText[j]);
                y += 80;
            }


            boxOfLeagues = new JComboBox();
            boxOfLeagues.addActionListener(this);
            for(int k=0; k<5; k++){
            boxOfLeagues.addItem(leagues[k]);
            }
            boxOfLeagues.setBounds(125,265,350,80);
            frame.add(boxOfLeagues);

            mgPanel = new JPanel();
            gLabel = new JLabel();
            gSlider = new JSlider((p[4].getNumbersOfGoals()+1), 50,(p[4].getNumbersOfGoals()+1));
            gSlider.setPreferredSize(new Dimension(300,100));
            gSlider.setPaintTicks(true);
            gSlider.setMinorTickSpacing(1);
            gSlider.setPaintTrack(true);
            gSlider.setMajorTickSpacing(4);
            gSlider.setPaintLabels(true);
            gSlider.addChangeListener(this);
            gLabel.setText("G: " + gSlider.getValue());

            mLabel = new JLabel("M: ");
            mSlider = new JSlider(0,50);
            mSlider.setPreferredSize(new Dimension(300,100));
            mSlider.setPaintTicks(true);
            mSlider.setMinorTickSpacing(1);
            mSlider.setPaintTrack(true);
            mSlider.setMajorTickSpacing(4);
            mSlider.setPaintLabels(true);
            mSlider.addChangeListener(this);
            mLabel.setText("M: " + gSlider.getValue());

            mgPanel.add(gSlider);
            mgPanel.add(gLabel);
            mgPanel.add(mSlider);
            mgPanel.add(mLabel);
            mgPanel.setBounds(75,350,450,250);
            frame.add(mgPanel);

            rCheck = new JButton("Check");
            rCheck.setBounds(50, 700, 200, 50);
            rCheck.addActionListener(this);

            rAdd = new JButton("ADD");
            rAdd.setBounds(350, 700, 200, 50);
            rAdd.addActionListener(this);


            frame.add(rCheck);
            frame.setVisible(true);

        }

        if (e.getSource() == rCheck) {
            String rNameCheck = rText[0].getText() + rText[1].getText();
            String rcheck;
            System.out.println(rNameCheck);
            boolean onList = false;
            for (int i = 0; i < 5; i++) {
                rcheck = p[i].getName() + p[i].getLastName();
                System.out.println(rcheck);
                if (rNameCheck.equals(rcheck)==true){
                    rText[0].setText(p[i].getName() + p[i].getLastName()+ " is TOP 5");
                    rText[1].setText("ADD OTHER!");
                    onList = true;
                    break;
                }
            }
                if(onList == false){

                    try {
                        FileInputStream rlist = new FileInputStream(rListName);
                        Scanner sc = new Scanner(rlist);
                        System.out.println(rNameCheck);
                        while (sc.hasNextLine()) {
                            if (sc.nextLine().equals(rNameCheck)) {
                                rName = rText[0].getText();
                                rLastName = rText[1].getText();
                                System.out.println("Dziala");
                                rText[0].setText("Player: " + rNameCheck + " found");
                                rText[0].setHorizontalAlignment(JTextField.CENTER);
                                rText[0].setFont(myFont);
                                rText[0].setEditable(false);
                                rText[1].setText("ADD INFO & STATS!");
                                rText[1].setHorizontalAlignment(JTextField.CENTER);
                                rText[1].setFont(myFont);
                                rText[1].setEditable(false);

                                frame.add(rAdd);
                                frame.setVisible(true);
                            }
                            else{
                                    rText[0].setForeground(Color.red);
                                    rText[1].setForeground(Color.red);
                                }

                        }
                        sc.close();
                    }
                    catch (IOException exception) {
                        exception.printStackTrace();
                    }

                }

        }

            if (e.getSource() == rAdd) {
                boolean ok = true;

                if (rText[2].getText().length() < 3 || rText[2].getText().length() > 15) {
                    rText[2].setText("Enter good club!");
                    rText[2].setForeground(Color.RED);
                    ok = false;
                } else {
                    rClub = rText[2].getText();
                }

                rGoals = gSlider.getValue();
                rMatches = mSlider.getValue();
                rLeague = boxOfLeagues.getSelectedItem().toString();
                System.out.println(rGoals);

                if (ok == true) {
                    frame.remove(rAdd);
                    frame.remove(rCheck);
                    for (int i = 0; i < 3; i++) {
                        frame.remove(rText[i]);
                    }
                    frame.remove(mgPanel);
                    frame.remove(boxOfLeagues);
                    frame.setVisible(false);

                    rPlayer = new Player(rName, rLastName, rClub, rLeague, rGoals, rMatches);
                    p[4] = rPlayer;
                    players[4] = new JButton(setIcon(rPlayer));
                    players[4].setText(rPlayer.toString());
                    players[4].setFont(myFont);
                    players[4].setBounds(50, 500, 500, 100);
                    players[4].addActionListener(this);
                    players[4].setHorizontalTextPosition(AbstractButton.LEADING);
                    frame.add(players[4]);

                    sort(p);
                    clearFile(filename);
                    saveFile(filename);

                    for (int i = 0; i < p.length; i++) {
                        players[i].setText(p[i].toString());
                        players[i].setIcon(setIcon(p[i]));
                        frame.add(players[i]);
                    }
                    frame.add(replace);
                    frame.add(text);
                    frame.setVisible(true);
                }
            }
            if(e.getSource() == boxOfLeagues){
                System.out.println(boxOfLeagues.getSelectedItem());

            }

            ///// actions for players
            // player info
            for (int i = 0; i < 5; i++) {
                if (e.getSource() == players[i]) {
                    for (int j = 0; j < p.length; j++) {
                        frame.remove(players[j]);
                    }
                    frame.remove(replace);
                    frame.remove(text);
                    frame.setVisible(false);

                    frame.setSize(500, 300);

                    info = new JTextField();
                    info.setBounds(10, 50, 450, 50);
                    info.setText(p[i].info());
                    info.setEditable(false);


                    stats = new JTextField();
                    stats.setBounds(10, 100, 450, 50);
                    stats.setText(p[i].stats());
                    stats.setEditable(false);

                    add1 = new JButton("ADD OR SUB GOALS AND MATCHES");
                    add1.setBounds(10, 150, 450, 50);
                    add1.addActionListener(this);

                    back1 = new JButton("RETURN");
                    back1.setBounds(10, 200, 450, 50);
                    back1.addActionListener(this);

                    ePlayer = p[i];

                    frame.add(info);
                    frame.add(stats);
                    frame.add(add1);
                    frame.add(back1);
                    frame.setVisible(true);
                }
            }
            // add goal, matches
            if (e.getSource() == add1) {
                frame.remove(info);
                frame.remove(stats);
                frame.remove(add1);
                frame.remove(back1);
                frame.setVisible(false);

                addgoal1 = new JTextField();
                addgoal1.setBounds(50, 20, 350, 50);
                addgoal1.setText("ADD OR SUB GOALS!");
                addgoal1.setEditable(true);

                addmatch1 = new JTextField();
                addmatch1.setBounds(50, 75, 350, 50);
                addmatch1.setText("ADD OR SUB MATCHES!");
                addmatch1.setEditable(true);

                confirm1 = new JButton("CONFIRM");
                confirm1.setBounds(150, 200, 200, 50);
                confirm1.addActionListener(this);

                frame.add(addgoal1);
                frame.add(addmatch1);
                frame.add(confirm1);
                frame.setVisible(true);
            }
            ///return to main
            if (e.getSource() == back1) {
                frame.remove(info);
                frame.remove(stats);
                frame.remove(add1);
                frame.remove(back1);
                frame.setVisible(false);

                frame.setSize(600, 800);

                for (int i = 0; i < p.length; i++) {
                    frame.add(players[i]);
                }
                frame.add(replace);
                frame.add(text);

                frame.setVisible(true);
            }
            ////// confirm goals, matches
            if (e.getSource() == confirm1) {
                if (isNumbers(addgoal1.getText(), addmatch1.getText()) == true) {
                    ng = Integer.parseInt(addgoal1.getText());
                    nm = Integer.parseInt(addmatch1.getText());
                    ePlayer.setGoals(ng);
                    ePlayer.setMatches(nm);
                    ePlayer.setAvrg();
                    sort(p);
                    clearFile(filename);
                    saveFile(filename);

                    for (int i = 0; i < p.length; i++) {
                        players[i].setText(p[i].toString());
                        players[i].setIcon(setIcon(p[i]));
                    }
                    frame.remove(addgoal1);
                    frame.remove(addmatch1);
                    frame.remove(confirm1);
                    frame.setVisible(false);

                    frame.setSize(600, 800);

                    for (int i = 0; i < p.length; i++) {
                        frame.add(players[i]);
                    }
                    frame.add(replace);
                    frame.add(text);

                    frame.setVisible(true);
                } else {
                    addgoal1.setText("Enter NUMBER !!");
                    addmatch1.setText("Enter NUMBER !!");
                }
            }

        }

        @Override
        public void stateChanged(ChangeEvent e){
            gLabel.setText("G: " + gSlider.getValue());
            mLabel.setText("M: " + mSlider.getValue());

        }

        //// sort players
        public static void sort (Player[]players){
            for (int i = 0; i < players.length; i++) {
                for (int j = i + 1; j < players.length; j++) {
                    Player player = new Player();
                    if (players[i].getNumbersOfGoals() < players[j].getNumbersOfGoals()) {
                        player = players[i];
                        players[i] = players[j];
                        players[j] = player;
                    }
                }
            }
        }

        //// make lista.txt empty to save new lista.txt
        public static void clearFile (String filename) {
            try {
                FileWriter fw = new FileWriter(filename, false);
                PrintWriter pw = new PrintWriter(fw, false);
                pw.flush();
                pw.close();
                fw.close();
            } catch (Exception exception) {
                System.out.println("Exception have been caught");
            }
        }

        /////  save new lista.txt
        public static void saveFile (String filename) {
            try {
                FileWriter fw = new FileWriter(filename, true);
                for (int i = 0; i < p.length; i++) {
                    fw.write(p[i].getName() + "\n");
                    fw.write(p[i].getLastName() + "\n");
                    fw.write(p[i].getClub() + "\n");
                    fw.write(p[i].getLeague() + "\n");
                    fw.write(p[i].getNumbersOfGoals() + "\n");
                    fw.write(p[i].getMatchesPlayed() + "\n");
                }
                fw.close();
            } catch (Exception exception) {
                System.out.println("Exception have been caught");
            }
        }

        public boolean isNumbers (String g, String m){
            boolean isg, ism;
            try {
                Integer.parseInt(g);
                isg = true;
            } catch (NumberFormatException ex) {
                isg = false;
            }

            try {
                Integer.parseInt(m);
                ism = true;
            } catch (NumberFormatException ex) {
                ism = false;
            }

            if (isg == true && ism == true)
                return true;
            else
                return false;
        }

        public Icon setIcon (Player player){
            String league = player.getLeague();
            if (league.equals("PremierLeague")) {
                return this.pl;
            } else if (league.equals("SerieA")) {
                return this.seriea;
            } else if (league.equals("BundesLiga")) {
                return this.bundes;
            } else if (league.equals("Ligue1")) {
                return this.l1;
            } else if (league.equals("LaLiga")) {
                return this.lal;
            } else{
                return null;
            }

        }

    }



