import java.awt.*;
import java.awt.event.*;

public class GameMaster extends Canvas implements KeyListener {

    private Image buf;
    private Image backgroundImage;
    private Image FighterImage;
    private Image EnemyBImage;
    private Graphics buf_gc;
    private Dimension    d;
    private int imgW, imgH;

    private int enmyAnum = 20;
    private int enemyBnum = 1;
    private int ftrBltNum = 30;
    private int enemyBltNum = 1;
    private int mode = -1;
    private int i, j;
    private int killedEnemies = 0;
    private int Bullettype = 0;

    private Item item;
    private double itemDropProbability = 0.1; // アイテムドロップ確率

    private Fighter ftr;
    private FighterBullet ftrBlt[] = new FighterBullet[ftrBltNum];
    private EnemyA enmyA[] = new EnemyA[enmyAnum];
    private EnemyB enemyB[] = new EnemyB[enemyBnum];
    private EnemyBullet enemyBlt[] = new EnemyBullet[enemyBltNum];

    GameMaster(int imgW, int imgH) {
        this.imgW = imgW;
        this.imgH = imgH;
        setSize(imgW, imgH);

        addKeyListener(this);

        FighterImage = Toolkit.getDefaultToolkit().getImage("img\\Fighter.png");

        EnemyBImage = Toolkit.getDefaultToolkit().getImage("img\\EnemyB.png");

        backgroundImage = Toolkit.getDefaultToolkit().getImage("img\\publicdomainq-0003379wgupft.jpg");

        item = new Item();

        ftr = new Fighter(imgW, imgH);
        for (i = 0; i < ftrBltNum; i++) {
            ftrBlt[i] = new FighterBullet();
        }                                                                                                       
        for (i = 0; i < enmyAnum; i++) {
            enmyA[i] = new EnemyA(imgW, imgH);
        }
        for (i = 0; i < enemyBnum; i++) {
            enemyB[i] = new EnemyB(imgW, imgH);
        }
        for (i = 0; i < enemyBltNum; i++) {
            enemyBlt[i] = new EnemyBullet();
        }
    }

    public void addNotify() {
        super.addNotify();
        buf = createImage(imgW, imgH);
        buf_gc = buf.getGraphics();
    }

    public void paint(Graphics g) {
        
        buf_gc.setColor(Color.white);
        buf_gc.fillRect(0, 0, imgW, imgH);
        buf_gc.drawImage(backgroundImage, 0, 0, imgW , imgH , this);
        buf_gc.drawImage(FighterImage, ftr.x-10, ftr.y,30,30, this);
        buf_gc.drawImage(EnemyBImage, enemyB[0].x, enemyB[0].y,30,30, this);
        switch (mode) {
            case -2:
                buf_gc.setColor(Color.white);
                buf_gc.drawString("   == Game over ==   ", imgW / 2 - 80, imgH / 2 - 20);
                buf_gc.drawString("    Hit SPACE key    ", imgW / 2 - 80, imgH / 2 + 20);
                break;
            case -1:
                buf_gc.setColor(Color.white);
                buf_gc.drawString(" == Shooting Game Title == ", imgW / 2 - 80, imgH / 2 - 20);
                buf_gc.drawString("Hit SPACE bar to start game", imgW / 2 - 80, imgH / 2 + 20);
                break;
            default:
                makeEnmy: if (Math.random() < 0.1) {
                    for (i = 0; i < enmyAnum; i++) {
                        if (enmyA[i].hp == 0) {
                            enmyA[i].revive(imgW, imgH,(int) Math.round(Math.random() * 50 - 10),5);
                            break makeEnmy;
                        }
                    }
                }
                makeEnmy: if (Math.random() < 0.1) {
                    for (i = 0; i < enemyBnum; i++) {
                        if (enemyB[i].hp == 0) {
                            enemyB[i].revive(imgW, imgH,(int) Math.round(Math.random() * 5 - 10),(int) Math.round(Math.random() * 5 - 10));
                            break makeEnmy;
                        }
                    }
                }
                

                if (ftr.sflag == true && ftr.delaytime == 0) {
                    if(Bullettype==0){
                        for (i = 0; i < ftrBltNum; i++) {
                            if (ftrBlt[i].hp == 0) {
                                ftrBlt[i].revive(ftr.x,ftr.y,0,-6);  
                                ftr.delaytime = 5;
                                break;
                            }
                        }
                    }
                    else if(Bullettype == 1){
                        for (i = 0; i < ftrBltNum; i++) {
                            if (ftrBlt[i].hp == 0) {
                                //バレットを３方向に射出
                                if(i%3 == 0){
                                    ftrBlt[i].revive(ftr.x,ftr.y,0,-6);  
                                    break;
                                }
                                else if(i%3 ==1){
                                    ftrBlt[i].revive(ftr.x,ftr.y,3,-6);
                                    break;  
                                }
                                else{
                                    ftrBlt[i].revive(ftr.x,ftr.y,-3,-6); 
                                    ftr.delaytime = 3;
                                    break;
                                }
                            }
                        }
                    }
                }else if (ftr.delaytime > 0)
                    ftr.delaytime--;

                for(i = 0; i < enemyBltNum;i++){
                    if (enemyBlt[i].hp == 0) {
                        enemyBlt[i].shootTowards(ftr,enemyB[0]);
                        enemyBlt[i].revive(enemyB[0].x,enemyB[0].y,enemyBlt[i].dx, enemyBlt[i].dy); 
                        break;
                    }
                }
                

                for (i = 0; i < enmyAnum; i++)
                    if (enmyA[i].hp > 0) {
                        ftr.collisionCheck(enmyA[i]);

                        for (j = 0; j < ftrBltNum; j++) {
                            if (ftrBlt[j].hp > 0) {
                                ftrBlt[j].collisionCheck(enmyA[i]);
                            }
                        }
                        if (enmyA[i].hp == 0) {
                            enemyKilled();
                            if (Math.random() < itemDropProbability) {
                                dropItem(enmyA[i].x, enmyA[i].y,0,3);
                            }
                        }
                    }

                 


                for (i = 0; i < enemyBnum; i++)
                    if (enemyB[i].hp > 0) {
                        ftr.collisionCheck(enemyB[i]);

                        for (j = 0; j < enemyBltNum; j++) {
                            if (enemyBlt[j].hp > 0) {
                                ftr.collisionCheck(enemyBlt[i]);
                                ftrBlt[j].collisionCheck(enemyB[i]);
                            }
                        }
                        if (enemyB[i].hp == 0) {
                            enemyKilled();
                            if (Math.random() < itemDropProbability) {
                                dropItem(enemyB[i].x, enemyB[i].y,0,3);
                            }
                        }
                    }

                if (ftr.hp < 1)
                    mode = -2;

                for (i = 0; i < enmyAnum; i++)
                    enmyA[i].move(buf_gc, imgW, imgH);
                for (i = 0; i < enemyBnum; i++)
                    enemyB[i].move(buf_gc, imgW, imgH);
                for (i = 0; i < ftrBltNum; i++)
                    ftrBlt[i].move(buf_gc, imgW, imgH);
                for (i = 0; i < enemyBltNum; i++)
                    enemyBlt[i].move(buf_gc, imgW, imgH);
                ftr.move(buf_gc, imgW, imgH);

                drawHp(buf_gc);
                drawKilledEnemies(buf_gc);

                item.move(buf_gc, imgW, imgH);
                if (ftr.collisionCheck(item)) {
                    Bullettype = 1;
                    break;
                }
        }
        g.drawImage(buf, 0, 0, this);
    }

    public void update(Graphics gc) {
        paint(gc);
    }    

    public void keyTyped(KeyEvent ke) {

    }

    public void keyPressed(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {
            case KeyEvent.VK_LEFT:
                ftr.lflag = true;
                break;
            case KeyEvent.VK_RIGHT:
                ftr.rflag = true;
                break;
            case KeyEvent.VK_UP:
                ftr.uflag = true;
                break;
            case KeyEvent.VK_DOWN:
                ftr.dflag = true;
                break;
            case KeyEvent.VK_SPACE:
                ftr.sflag = true;
                if (mode != 1) {
                    mode++;
                    ftr.hp = 10;
                }
                break;
        }
    }

    public void keyReleased(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {
            case KeyEvent.VK_LEFT:
                ftr.lflag = false;
                break;
            case KeyEvent.VK_RIGHT:
                ftr.rflag = false;
                break;
            case KeyEvent.VK_UP:
                ftr.uflag = false;
                break;
            case KeyEvent.VK_DOWN:
                ftr.dflag = false;
                break;
            case KeyEvent.VK_SPACE:
                ftr.sflag = false;
                break;
        }
    }

    public void drawHp(Graphics buf) {
        buf.setColor(Color.white);
        buf.drawString("HP: " + ftr.hp, 10, 20);
    }

    public void enemyKilled() {
        killedEnemies++;
    }

    public void drawKilledEnemies(Graphics buf) {
        buf.setColor(Color.white);
        //敵撃破数の表示
        buf.drawString("Killed Enemies: " + killedEnemies, 10, 40);
    }

    private void dropItem(int x, int y,int dx,int dy) {
        for (i = 0; i < ftrBltNum; i++) {
            if (item.hp == 0) {
                item.revive(x, y, dx, dy);
                break; 
            }
        }
    }
}