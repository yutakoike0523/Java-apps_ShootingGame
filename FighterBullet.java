import java.awt.*;

class FighterBullet extends MovingObject {
    FighterBullet() {
        w = h = 3;
        dx = 0;
        dy = -6;
        hp = 0;
    }

    FighterBullet(int dx,int dy){
        w = h = 3;
        hp = 0;
        this.dx = dx;
        this.dy = dy;
    }

    void move(Graphics buf, int apWidth, int apHeight) {
        if (hp > 0) {
            buf.setColor(Color.white);
            buf.fillOval(x - w, y - h, 2 * w, 2 * h);
            if (y > 0 && y < apHeight && x > 0 && x < apWidth){
                y = y + dy;
                x = x + dx;
            }
            else{
                hp = 0;
            }
        }
    }

    void revive(int x, int y,int dx,int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        hp = 1;
    }
}
