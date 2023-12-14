/**
 *  アニメーション（その１）
 *  ダブルバッファ処理無し
 * @author H.Fukai
 */
import java.awt.*;

// Anim01_SingleBuffer は Frame クラスを継承し，Runnable インターフェースを実装する
public class Anim01_SingleBuffer extends Frame implements Runnable {
  // ■ フィールド変数
  MyCanvas mc;  // キャンバス
  int x = 0;    // 円のx座標用の変数
  Thread th;    // スレッドクラスのオブジェクトを宣言

  // ■ メインメソッド（スタート地点）
  public static void main(String [] args) {
    Anim01_SingleBuffer sc = new Anim01_SingleBuffer(); 
  }

  // ■ コンストラクタ
  Anim01_SingleBuffer() {
    super("Animation 01");

    // フレームの初期設定
    this.setSize(640, 480); 
    this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));

    // キャンバスの初期設定
    mc = new MyCanvas();   // キャンバス生成
    mc.setSize(610, 450);  // キャンバスサイズ取得
    add(mc);               // 登録

    // フレームを可視化
    this.setVisible(true);

    // スレッドの初期設定
    th = new Thread(this); // スレッドオブジェクトの新規作成
    th.start();            // スレッドの開始
  }

  // スレッドのメソッド
  // Runnable インターフェースを使うと必ずこれを再定義しなければならない
  public void run() {  // スレッドで実行する内容 (Thread クラスのメソッド)
    try {              // 例外処理用ブロック開始
      while(true) {    // 無限ループ
	th.sleep(100); // 再描画の前に一定時間休止する
	mc.repaint();  // キャンバスに再描画を要求
      }
    }
    catch(Exception e) {   //　エラー処理
      System.err.println( "エラーが発生しました: " + e );
    }
  }
}

class MyCanvas extends Canvas {
  int x=0;
  public void paint(Graphics g) { // th.sleep() で指定した時間毎に呼び出される
    Dimension d = getSize();
    g.fillOval(x, d.height/4, 50, 50); // 塗りつぶした円を直接描画する
    x += 5;                // x座標を増やす
    if (x > d.width)       // ウィンドウの幅を越えたら初期化
      x=0;
    System.out.println("x="+x);
  }
}