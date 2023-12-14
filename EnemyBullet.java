import java.awt.*;

class EnemyBullet extends MovingObject {
int trgdx;
int trgdy;
    EnemyBullet() {
        w = h = 3;
        dx = 0;
        dy = 6;
        hp = 0;
    }

    EnemyBullet(int dx,int dy){
        w = h = 3;
        hp = 0;
        this.dx = dx;
        this.dy = dy;
    }

    void shootTowards(Fighter ftr,EnemyB enemyB ) {
        int Dx = ftr.x - enemyB.x;
        int Dy = ftr.y - enemyB.y;
    
        // ベクトルの大きさを計算
        double magnitude = Math.sqrt(Dx * Dx + Dy * Dy);
    
        // 大きさが1になるように正規化
        double normalizedDx = Dx / magnitude;
        double normalizedDy = Dy / magnitude;

        double speed = 10; // 調整可能な速度
        dx = (int) (speed * normalizedDx);
        dy = (int) (speed * normalizedDy);
        
        System.out.print("dx,dy計算した");
        System.out.print("dx="+dx);
        System.out.print("dy="+dy);
        System.out.println("");
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