/**
 * アニメーション（その２）
 * ダブルバッファを用いてちらつきをなくしたバージョン
 * @author H.Fukai
 */

import java.awt.*;

// Anim02_DoubleBuffer は Frame クラスを継承し，Runnable インターフェースを実装する
public class Anim02_DoubleBuffer extends Frame implements Runnable {
  // ■ フィールド変数
  MyCanvas mc;  // キャンバス
  int x = 0;    // 円のx座標用の変数
  Thread th;    // スレッドクラスのオブジェクトを宣言

  // ■ メインメソッド（スタート地点）
  public static void main(String [] args) {
    Anim02_DoubleBuffer sc = new Anim02_DoubleBuffer(); 
  }

  // ■ コンストラクタ
  Anim02_DoubleBuffer() {
    // 親クラスのコンストラクタ呼び出し
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
	mc.repaint();  // キャンバスに再描画を要求 (paint()が呼ばれる)
      }
    }
    catch(Exception e) {   //　エラー処理
      System.err.println( "エラーが発生しました: " + e );
    }
  }
}

// Canvas を継承した描画用クラス
class MyCanvas extends Canvas {
  int      x=0;       // 描く円の x 座標
  Image    buffer;    // 仮の画用紙 (buffer) として利用する Image クラスのオブジェクト
  Graphics buffer_gc; // バッファ用のグラフィックスコンテキスト (gc) 
  Dimension d;        // フレーム画面のサイズを入れる
  
  // ■ メソッド
  // コンストラクタ内で createImage を行うと peer の関連で 
  // nullpointer exception が返ってくる問題を回避するために必要
  public void addNotify(){
    super.addNotify();
    d = getSize();
    buffer = createImage(d.width, d.height); // buffer を画面と同サイズで作成
    buffer_gc = buffer.getGraphics();
  }

  // 画用紙 buffer に描画する
  public void paint(Graphics main_gc) {
    d = getSize();

    // 仮の画用紙に円を描く
    buffer_gc.setColor(Color.white);          // gc の色を白に
    buffer_gc.fillRect(0,0,d.width,d.height); // gc を使って白の四角を描く（クリア）
    buffer_gc.setColor(Color.black);          // gc の色を黒に
    buffer_gc.fillOval(x, d.height/4, 50,50); // gc を使って円を描く

    // 表の画用紙 (this) に裏の画用紙 (buffer) の内容を貼り付ける
    main_gc.drawImage(buffer, 0, 0, this);
	
    x += 2;          // x 座標を増やす
    if (x > d.width) // 円が端まで到達したら
      x=0;         // x を初期化する
  }
}