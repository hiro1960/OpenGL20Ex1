OpenGL20Ex1
===========

OpenGL ES 2.0用サンプルプログラム　ロボットが回転する。（ネットにあったサンプルが元ネタ）

ロボットのモデルはWavefrontのobj形式（*.objと*.mtlファイル）で読み込む。
注意として、モデルは以下の条件を満たすこと。
（１）objファイルの終端は"usemtl default"で終わること。
    →面を定義しているf行の終了を別の行の始まりでプログラムが検知している。
    　そのため、何か害のないキーワードでファイルを終了する必要がある。
    　Blenderのobj形式出力がf行で終了しているため、最後のオブジェクトが表示されないことから判明した。
（２）オブジェクトにマテリアルを定義して、「面」（含む色）を定義すること。
    →これがないと法線ベクトルを定義できない。