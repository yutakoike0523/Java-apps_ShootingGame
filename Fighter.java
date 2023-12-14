import java.awt.*;

class Fighter extends MovingObject {
    boolean lflag;
    boolean rflag;
    boolean uflag;
    boolean dflag;
    boolean sflag;
    int delaytime;

    int currentBulletType;  

    Fighter(int apWidth, int apHeight) {
        x = (int) (apWidth / 2);
        y = (int) (apHeight * 0.9);
        dx = 5;
        dy = 5;
        w = 10;
        h = 10;
        lflag = false;
        rflag = false;
        uflag = false;
        dflag = false;
        sflag = false;
        delaytime = 5;
        currentBulletType = 0;
    }

    void revive(int apWidth, int apHeight,int dx,int dy) {
    }

    void move(Graphics buf, int apWidth, int apHeight) {
        buf.setColor(Color.black);
        if (lflag && !rflag && x > w)
            x = x - dx;
        if (rflag && !lflag && x < apWidth - w)
            x = x + dx;
        if (uflag && !dflag && y > h)
            y = y - dy;
        if (dflag && !uflag && y < apHeight - h)
            y = y + dy;
    }
    void switchBulletType() {
        currentBulletType = (currentBulletType + 1) % 3;  // Cycle through bullet types
    }
}

