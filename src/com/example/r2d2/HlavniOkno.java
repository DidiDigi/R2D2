package com.example.r2d2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;
import net.sevecek.util.swing.*;

public class HlavniOkno extends JFrame {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    JLabel labRobot;
    JLabel labDarthVader;
    JLabel labDarthVader2;
    JLabel labDarthVader3;
    JLabel labYoda;
    JLabel labCil;
    JLabel label1;
    JLabel label5;
    JLabel label7;
    JLabel label9;
    JLabel label10;
    JLabel label8;
    JLabel label12;
    JLabel label13;
    JLabel label14;
    JLabel label6;
    JLabel label15;
    JLabel label16;
    JLabel label17;
    JLabel label18;
    JLabel label19;
    JLabel label21;
    JLabel label22;
    JLabel label23;
    JLabel label24;
    JLabel label25;
    JLabel label26;
    JLabel label27;
    JLabel label28;
    JLabel label29;
    JLabel label20;
    JLabel label30;
    JLabel label31;
    JLabel label32;
    JLabel label33;
    JLabel label34;
    JLabel label35;
    JLabel label36;
    JLabel label11;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    JPanel contentPane;
    Image jduVpravo;
    Image jduVlevo;
    int zivoty = 3;
    boolean konecHry = false;
    private JKeyboard klavesnice;
    private static final int SIRKA_ZDI = 15;
    Point puvodniSouradniceRobot;
    Timer casovac1;
    Timer casovac2;
    Timer casovac3;
    private final int RYCHLOST1 = 10;
    private final int RYCHLOST2 = -15;
    private final int RYCHLOST3 = 5;
    private ArrayList<JLabel> seznamZdi;
    private ArrayList<JLabel> seznamNepratel;
    public HlavniOkno() {
        initComponents();
    }


    public static final int DELTA = 10;

    private void ztrataZivota() {
        zivoty = zivoty - 1;
        if (zivoty == 0) {
            gameOver();
        }
        labRobot.setLocation(0,245);
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(null, "Game Over!",
                "Game Over", JOptionPane.CLOSED_OPTION);
        konecHry = true; // neumožní další pohyb ve hře
        casovac1.stop();
        casovac2.stop();
        casovac3.stop();
    }

    private void vitezstvi () {
        labCil.setVisible(false);
        konecHry = true;  // neumožní další pohyb ve hře
        JOptionPane.showMessageDialog(null, "Congratulation!",
                "Congratulation!", JOptionPane.CLOSED_OPTION);
    }

    private void priStisknutiKlavesy(KeyEvent e) {
        //this.setTitle(Integer.toString(keyCode));

        if (konecHry) { return; }

        if (klavesnice.isKeyDown(KeyEvent.VK_ESCAPE)) {
            gameOver();
        }

        pohybRobota();
        kolizeCil();
        kolizeYoda();
        kolizeNepritel();
        
    }

    private void kolizeYoda() {
        if (detekceKolize(labYoda, labRobot)) {
            labCil.setBackground(Color.yellow);
            labYoda.setVisible(false);
        }
    }

    private void kolizeCil() {
        if (detekceKolize(labCil, labRobot)) {
            if (labCil.getBackground() == (Color.yellow)) {
                vitezstvi();
            }
        }
    }

    private void kolizeNepritel() {
        for (JLabel labNepritel : seznamNepratel)
        if (detekceKolize(labNepritel, labRobot)) {
            if (labNepritel.isVisible()) {
                ztrataZivota();
                labNepritel.setVisible(false);
            }
        }
    }

    private boolean kolizeZed (JLabel objekt) {
        for (JLabel labZed : seznamZdi) {
            if (detekceKolize(labZed, objekt)) { return true; }
        }
        return false;
    }

    private void pohybRobota() {
        puvodniSouradniceRobot = labRobot.getLocation();
        Point souradniceRobot = labRobot.getLocation();
        int x = souradniceRobot.x;
        int y = souradniceRobot.y;

        // šipka vpravo
        if (klavesnice.isKeyDown(KeyEvent.VK_RIGHT)) {
            x = x + DELTA;
            y = y;
            labRobot.setIcon(new ImageIcon(jduVpravo));
        }

        // šipka vlevo
        if (klavesnice.isKeyDown(KeyEvent.VK_LEFT)) {
            x = x - DELTA;
            y = y;
            labRobot.setIcon(new ImageIcon(jduVlevo));
        }

        // šipka dolu
        if (klavesnice.isKeyDown(KeyEvent.VK_DOWN)) {
            x = x;
            y = y + DELTA;
        }

        // šipka nahoru
        if (klavesnice.isKeyDown(KeyEvent.VK_UP)) {
            x = x;
            y = y - DELTA;
        }

        souradniceRobot.x = x;
        souradniceRobot.y = y;
        labRobot.setLocation(souradniceRobot);
        if (kolizeZed(labRobot) || (x < 0) || ((x + labRobot.getWidth()) > (contentPane.getWidth()))) {labRobot.setLocation(puvodniSouradniceRobot);}
        
    }

    private boolean detekceKolize(JLabel objekt1, JLabel objekt2) {
        Point souradniceLabel1 = objekt1.getLocation();
        int ax = souradniceLabel1.x;
        int ay = souradniceLabel1.y;

        int bx = ax + objekt1.getWidth();
        int by = ay + objekt1.getHeight();

        Point souradniceLabel2 = objekt2.getLocation();
        int cx = souradniceLabel2.x;
        int cy = souradniceLabel2.y;

        int dx = cx + objekt2.getWidth();
        int dy = cy + objekt2.getHeight();

        if (ax <= dx && ay <= dy && cx <= bx && cy <= by ) {
            return true;
        }

        return false;
    }

    private void pohybVertikalni(JLabel figurka, int rychlost) {
        Point poziceFigurky = figurka.getLocation();
        int deltaX = rychlost;
        int deltaY = rychlost;

        int x = poziceFigurky.x;
        int y = poziceFigurky.y;

        x = x + deltaX;

        if (kolizeZed(figurka)) {
            y = y + deltaY;
            poziceFigurky.y = y;
            figurka.setLocation(poziceFigurky);
        } else {
            poziceFigurky.x = x;
            figurka.setLocation(poziceFigurky);
        }

        if (x < SIRKA_ZDI ) {
            if (deltaX < 0) {
                deltaX = -deltaX;
            }
        }

        if (x + figurka.getWidth() >= (contentPane.getWidth() - SIRKA_ZDI)) {
            if (deltaX > 0) {
                deltaX = -deltaX;
            }

        }

        if (y < SIRKA_ZDI ) {
            if (deltaY < 0) {
                deltaY = -deltaY;
            }
        }

        if (y + figurka.getHeight() >= (contentPane.getHeight() - SIRKA_ZDI)) {
            if (deltaY > 0) {
                deltaY = -deltaY;
            }
        }
    }

    private void pohybHorizontalni(JLabel figurka, int rychlost) {
        Point poziceFigurky = figurka.getLocation();
        int deltaX = rychlost;
        int deltaY = rychlost;
        int x = poziceFigurky.x;
        int y = poziceFigurky.y;
        y = y + deltaY;

        if (kolizeZed(figurka)) {
            x = x + deltaX;
            poziceFigurky.x = x;
            figurka.setLocation(poziceFigurky);
        } else {
            poziceFigurky.y = y;
            figurka.setLocation(poziceFigurky);
        }

        if (x < SIRKA_ZDI ) {
            if (deltaX < 0) {
                deltaX = -deltaX;
            }
        }

        if (x + figurka.getWidth() >= (contentPane.getWidth() - SIRKA_ZDI)) {
            if (deltaX > 0) {
                deltaX = -deltaX;
            }

        }

        if (y < SIRKA_ZDI ) {
            if (deltaY < 0) {
                deltaY = -deltaY;
            }
        }

        if (y + figurka.getHeight() >= (contentPane.getHeight() - SIRKA_ZDI)) {
            if (deltaY > 0) {
                deltaY = -deltaY;
            }
        }


    }

    private void priOtevreniOkna(WindowEvent e) {
        uploadPictures();
        vytvorSeznamZdi();
        vytvorSeznamNepratel();
        nastavCasovac();
        klavesnice = new JKeyboard();
    }
    private void nastavCasovac() {
        casovac1 = new Timer(500, it -> pohybVertikalni(labDarthVader, RYCHLOST1));
        casovac1.start();
        casovac2 = new Timer(400, it -> pohybHorizontalni(labDarthVader2, RYCHLOST2));
        casovac2.start();
        casovac3 = new Timer(200, it -> pohybVertikalni(labDarthVader3, RYCHLOST3));
        casovac3.start();
    }
    private void vytvorSeznamZdi () {
        seznamZdi = new ArrayList<JLabel>();
        seznamZdi.add(label1);
        seznamZdi.add(label5);
        seznamZdi.add(label6);
        seznamZdi.add(label7);
        seznamZdi.add(label8);
        seznamZdi.add(label9);
        seznamZdi.add(label10);
        seznamZdi.add(label11);
        seznamZdi.add(label12);
        seznamZdi.add(label13);
        seznamZdi.add(label14);
        seznamZdi.add(label15);
        seznamZdi.add(label16);
        seznamZdi.add(label17);
        seznamZdi.add(label18);
        seznamZdi.add(label19);
        seznamZdi.add(label20);
        seznamZdi.add(label21);
        seznamZdi.add(label22);
        seznamZdi.add(label23);
        seznamZdi.add(label24);
        seznamZdi.add(label25);
        seznamZdi.add(label26);
        seznamZdi.add(label27);
        seznamZdi.add(label28);
        seznamZdi.add(label29);
        seznamZdi.add(label30);
        seznamZdi.add(label31);
        seznamZdi.add(label32);
        seznamZdi.add(label33);
        seznamZdi.add(label34);
        seznamZdi.add(label35);
        seznamZdi.add(label36);
    }
    private void vytvorSeznamNepratel() {
        seznamNepratel = new ArrayList<JLabel>();
        seznamNepratel.add(labDarthVader);
        seznamNepratel.add(labDarthVader2);
        seznamNepratel.add(labDarthVader3);
    }
    private void uploadPictures() {
        try {
            jduVpravo = ImageIO.read(getClass().getResourceAsStream("/com/example/R2D2/r2d2-vpravo.png"));
        }
        catch (IOException ex) {
            throw new RuntimeException("nepodařilo se nahrát obrázek");
        }
        try {
            jduVlevo = ImageIO.read(getClass().getResourceAsStream("/com/example/R2D2/r2d2-vlevo.png"));
        }
        catch (IOException ex) {
            throw new RuntimeException("nepodařilo se nahrát obrázek");
        }
    }
    private void priZavreniOkna(WindowEvent e) {
       casovac1.stop();
       casovac2.stop();
       casovac3.stop();
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        labRobot = new JLabel();
        labDarthVader = new JLabel();
        labDarthVader2 = new JLabel();
        labDarthVader3 = new JLabel();
        labYoda = new JLabel();
        labCil = new JLabel();
        label1 = new JLabel();
        label5 = new JLabel();
        label7 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        label8 = new JLabel();
        label12 = new JLabel();
        label13 = new JLabel();
        label14 = new JLabel();
        label6 = new JLabel();
        label15 = new JLabel();
        label16 = new JLabel();
        label17 = new JLabel();
        label18 = new JLabel();
        label19 = new JLabel();
        label21 = new JLabel();
        label22 = new JLabel();
        label23 = new JLabel();
        label24 = new JLabel();
        label25 = new JLabel();
        label26 = new JLabel();
        label27 = new JLabel();
        label28 = new JLabel();
        label29 = new JLabel();
        label20 = new JLabel();
        label30 = new JLabel();
        label31 = new JLabel();
        label32 = new JLabel();
        label33 = new JLabel();
        label34 = new JLabel();
        label35 = new JLabel();
        label36 = new JLabel();
        label11 = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("R2D2");
        setType(Window.Type.POPUP);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                priStisknutiKlavesy(e);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                priZavreniOkna(e);
            }
            @Override
            public void windowOpened(WindowEvent e) {
                priOtevreniOkna(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        this.contentPane = (JPanel) this.getContentPane();
        this.contentPane.setBackground(this.getBackground());

        //---- labRobot ----
        labRobot.setIcon(new ImageIcon(getClass().getResource("/com/example/r2d2/r2d2-vpravo.png")));
        contentPane.add(labRobot);
        labRobot.setBounds(new Rectangle(new Point(5, 245), labRobot.getPreferredSize()));

        //---- labDarthVader ----
        labDarthVader.setIcon(new ImageIcon(getClass().getResource("/com/example/r2d2/darth_vader.png")));
        contentPane.add(labDarthVader);
        labDarthVader.setBounds(new Rectangle(new Point(680, 415), labDarthVader.getPreferredSize()));

        //---- labDarthVader2 ----
        labDarthVader2.setIcon(new ImageIcon(getClass().getResource("/com/example/r2d2/darth_vader.png")));
        contentPane.add(labDarthVader2);
        labDarthVader2.setBounds(630, 250, 50, 50);

        //---- labDarthVader3 ----
        labDarthVader3.setIcon(new ImageIcon(getClass().getResource("/com/example/r2d2/darth_vader.png")));
        contentPane.add(labDarthVader3);
        labDarthVader3.setBounds(215, 255, 50, 50);

        //---- labYoda ----
        labYoda.setIcon(new ImageIcon(getClass().getResource("/com/example/r2d2/yoda.png")));
        contentPane.add(labYoda);
        labYoda.setBounds(new Rectangle(new Point(595, 90), labYoda.getPreferredSize()));

        //---- labCil ----
        labCil.setFocusable(false);
        labCil.setOpaque(true);
        labCil.setBackground(new Color(102, 102, 102));
        contentPane.add(labCil);
        labCil.setBounds(870, 230, 15, 90);

        //---- label1 ----
        label1.setFocusable(false);
        label1.setOpaque(true);
        label1.setBackground(new Color(102, 102, 102));
        contentPane.add(label1);
        label1.setBounds(0, 0, 885, 15);

        //---- label5 ----
        label5.setFocusable(false);
        label5.setOpaque(true);
        label5.setBackground(new Color(102, 102, 102));
        contentPane.add(label5);
        label5.setBounds(15, 150, 85, 15);

        //---- label7 ----
        label7.setFocusable(false);
        label7.setOpaque(true);
        label7.setBackground(new Color(102, 102, 102));
        contentPane.add(label7);
        label7.setBounds(190, 15, 15, 75);

        //---- label9 ----
        label9.setFocusable(false);
        label9.setOpaque(true);
        label9.setBackground(new Color(102, 102, 102));
        contentPane.add(label9);
        label9.setBounds(0, 15, 15, 215);

        //---- label10 ----
        label10.setFocusable(false);
        label10.setOpaque(true);
        label10.setBackground(new Color(102, 102, 102));
        contentPane.add(label10);
        label10.setBounds(15, 560, 855, 15);

        //---- label8 ----
        label8.setFocusable(false);
        label8.setOpaque(true);
        label8.setBackground(new Color(102, 102, 102));
        contentPane.add(label8);
        label8.setBounds(100, 95, 15, 130);

        //---- label12 ----
        label12.setFocusable(false);
        label12.setOpaque(true);
        label12.setBackground(new Color(102, 102, 102));
        contentPane.add(label12);
        label12.setBounds(0, 315, 15, 260);

        //---- label13 ----
        label13.setFocusable(false);
        label13.setOpaque(true);
        label13.setBackground(new Color(102, 102, 102));
        contentPane.add(label13);
        label13.setBounds(15, 395, 85, 15);

        //---- label14 ----
        label14.setFocusable(false);
        label14.setOpaque(true);
        label14.setBackground(new Color(102, 102, 102));
        contentPane.add(label14);
        label14.setBounds(100, 315, 15, 165);

        //---- label6 ----
        label6.setFocusable(false);
        label6.setOpaque(true);
        label6.setBackground(new Color(102, 102, 102));
        contentPane.add(label6);
        label6.setBounds(205, 155, 170, 15);

        //---- label15 ----
        label15.setFocusable(false);
        label15.setOpaque(true);
        label15.setBackground(new Color(102, 102, 102));
        contentPane.add(label15);
        label15.setBounds(280, 95, 15, 145);

        //---- label16 ----
        label16.setFocusable(false);
        label16.setOpaque(true);
        label16.setBackground(new Color(102, 102, 102));
        contentPane.add(label16);
        label16.setBounds(205, 390, 170, 15);

        //---- label17 ----
        label17.setFocusable(false);
        label17.setOpaque(true);
        label17.setBackground(new Color(102, 102, 102));
        contentPane.add(label17);
        label17.setBounds(280, 330, 15, 145);

        //---- label18 ----
        label18.setFocusable(false);
        label18.setOpaque(true);
        label18.setBackground(new Color(102, 102, 102));
        contentPane.add(label18);
        label18.setBounds(180, 245, 15, 75);

        //---- label19 ----
        label19.setFocusable(false);
        label19.setOpaque(true);
        label19.setBackground(new Color(102, 102, 102));
        contentPane.add(label19);
        label19.setBounds(380, 250, 15, 70);

        //---- label21 ----
        label21.setFocusable(false);
        label21.setOpaque(true);
        label21.setBackground(new Color(102, 102, 102));
        contentPane.add(label21);
        label21.setBounds(785, 150, 85, 15);

        //---- label22 ----
        label22.setFocusable(false);
        label22.setOpaque(true);
        label22.setBackground(new Color(102, 102, 102));
        contentPane.add(label22);
        label22.setBounds(770, 95, 15, 130);

        //---- label23 ----
        label23.setFocusable(false);
        label23.setOpaque(true);
        label23.setBackground(new Color(102, 102, 102));
        contentPane.add(label23);
        label23.setBounds(785, 370, 85, 15);

        //---- label24 ----
        label24.setFocusable(false);
        label24.setOpaque(true);
        label24.setBackground(new Color(102, 102, 102));
        contentPane.add(label24);
        label24.setBounds(770, 315, 15, 130);

        //---- label25 ----
        label25.setFocusable(false);
        label25.setOpaque(true);
        label25.setBackground(new Color(102, 102, 102));
        contentPane.add(label25);
        label25.setBounds(495, 155, 170, 15);

        //---- label26 ----
        label26.setFocusable(false);
        label26.setOpaque(true);
        label26.setBackground(new Color(102, 102, 102));
        contentPane.add(label26);
        label26.setBounds(570, 95, 15, 145);

        //---- label27 ----
        label27.setFocusable(false);
        label27.setOpaque(true);
        label27.setBackground(new Color(102, 102, 102));
        contentPane.add(label27);
        label27.setBounds(570, 330, 15, 145);

        //---- label28 ----
        label28.setFocusable(false);
        label28.setOpaque(true);
        label28.setBackground(new Color(102, 102, 102));
        contentPane.add(label28);
        label28.setBounds(495, 390, 170, 15);

        //---- label29 ----
        label29.setFocusable(false);
        label29.setOpaque(true);
        label29.setBackground(new Color(102, 102, 102));
        contentPane.add(label29);
        label29.setBounds(690, 240, 15, 75);

        //---- label20 ----
        label20.setFocusable(false);
        label20.setOpaque(true);
        label20.setBackground(new Color(102, 102, 102));
        contentPane.add(label20);
        label20.setBounds(390, 305, 85, 15);

        //---- label30 ----
        label30.setFocusable(false);
        label30.setOpaque(true);
        label30.setBackground(new Color(102, 102, 102));
        contentPane.add(label30);
        label30.setBounds(475, 250, 15, 70);

        //---- label31 ----
        label31.setFocusable(false);
        label31.setOpaque(true);
        label31.setBackground(new Color(102, 102, 102));
        contentPane.add(label31);
        label31.setBounds(695, 15, 15, 75);

        //---- label32 ----
        label32.setFocusable(false);
        label32.setOpaque(true);
        label32.setBackground(new Color(102, 102, 102));
        contentPane.add(label32);
        label32.setBounds(180, 485, 15, 75);

        //---- label33 ----
        label33.setFocusable(false);
        label33.setOpaque(true);
        label33.setBackground(new Color(102, 102, 102));
        contentPane.add(label33);
        label33.setBounds(430, 15, 15, 75);

        //---- label34 ----
        label34.setFocusable(false);
        label34.setOpaque(true);
        label34.setBackground(new Color(102, 102, 102));
        contentPane.add(label34);
        label34.setBounds(435, 485, 15, 75);

        //---- label35 ----
        label35.setFocusable(false);
        label35.setOpaque(true);
        label35.setBackground(new Color(102, 102, 102));
        contentPane.add(label35);
        label35.setBounds(695, 485, 15, 75);

        //---- label36 ----
        label36.setFocusable(false);
        label36.setOpaque(true);
        label36.setBackground(new Color(102, 102, 102));
        contentPane.add(label36);
        label36.setBounds(870, 315, 15, 260);

        //---- label11 ----
        label11.setFocusable(false);
        label11.setOpaque(true);
        label11.setBackground(new Color(102, 102, 102));
        contentPane.add(label11);
        label11.setBounds(870, 15, 15, 215);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(900, 615);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
