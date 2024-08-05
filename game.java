import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class TankClient extends Frame implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final int Fram_width = 800; // 
    public static final int Fram_length = 600;
    public static boolean printable = true;
    MenuBar jmb = null;
    Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;
    MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
            jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;
    Image screenImage = null;

    Tank homeTank = new Tank(300, 560, true, Direction.STOP, this);// 
    GetBlood blood = new GetBlood(); 
    Home home = new Home(373, 545, this);

    List<River> theRiver = new ArrayList<River>();
    List<Tank> tanks = new ArrayList<Tank>();
    List<BombTank> bombTanks = new ArrayList<BombTank>();
    List<Bullets> bullets = new ArrayList<Bullets>();
    List<Tree> trees = new ArrayList<Tree>();
    List<CommonWall> homeWall = new ArrayList<CommonWall>(); 
    List<CommonWall> otherWall = new ArrayList<CommonWall>();
    List<MetalWall> metalWall = new ArrayList<MetalWall>();

    public void update(Graphics g) {

        screenImage = this.createImage(Fram_width, Fram_length);

        Graphics gps = screenImage.getGraphics();
        Color c = gps.getColor();
        gps.setColor(Color.GRAY);
        gps.fillRect(0, 0, Fram_width, Fram_length);
        gps.setColor(c);
        framPaint(gps);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void framPaint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.green);

        Font f1 = g.getFont();
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("最高分: ", 200, 70);
        g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        g.drawString("" + tanks.size(), 400, 70);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("得分ֵ: ", 500, 70);
        g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        g.drawString("" + homeTank.getLife(), 650, 70);
        g.setFont(f1);

        if (tanks.size() == 0 && home.isLive() && homeTank.isLive()) {
            Font f = g.getFont();
            g.setFont(new Font("TimesRoman", Font.BOLD, 60)); 
            this.otherWall.clear();
            g.drawString(" ", 310, 300);
            g.setFont(f);
        }

        if (homeTank.isLive() == false) {
            Font f = g.getFont();
            g.setFont(new Font("TimesRoman", Font.BOLD, 40));
            tanks.clear();
            bullets.clear();
            g.setFont(f);
        }
        g.setColor(c);

        for (int i = 0; i < theRiver.size(); i++) {
            River r = theRiver.get(i);
            r.draw(g);
        }

        for (int i = 0; i < theRiver.size(); i++) {
            River r = theRiver.get(i);
            homeTank.collideRiver(r);

            r.draw(g);
        }

        home.draw(g);
        homeTank.draw(g);
        homeTank.eat(blood);

        for (int i = 0; i < bullets.size(); i++) { 
            Bullets m = bullets.get(i);
            m.hitTanks(tanks); 
            m.hitTank(homeTank);
