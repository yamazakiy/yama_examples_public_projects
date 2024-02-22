# yama_examples_public_projects
公開用のサンプルプロジェクト置き場です。おそらくリファクタリングしていない状態のものがあるのでご容赦ください。


## project_yama_examples_beta
Eclipse プロジェクトです。<br>
いろいろ思い付きで作っています。


### ゆらゆらペイント
物体落下みたいなの作ろうと始めたら、最終的にゆらゆらペイントになりました。<br>

https://github.com/yamazakiy/yama_examples_public_projects/assets/73773018/a814d3f8-9b04-43ea-b7ca-4bec740bf961

```
起動クラス：yama.CreatePointsStartup
起動パラメータ：なし
```
> このアプリケーションの動作には「即時リリース」と「一括リリース」のふたつのモードがあります。<br>
> このリリースは描画したオブジェクトの落下を意味します。<br>
> 即時リリースの場合は mouseReleased、一括リリースの場合は 上部の release ボタンを押下したタイミングで描画したオブジェクトを落下させます。<br>

## project_yama_bingo
Eclipse プロジェクトです。<br>
会社の忘年会用に作成したビンゴサービスです。


### ビンゴサービス
会社の忘年会用に作成したビンゴサービスです。<br>

https://github.com/yamazakiy/yama_examples_public_projects/assets/73773018/caedd7d4-f558-4f25-811f-7fe4daf68f94

```
起動クラス：yama.bingo.BingoServiceStartup
起動パラメータ：VIEW_TYPE_GUI_STANDARD - ビュータイプ／標準ビュー
起動パラメータ：VIEW_TYPE_GUI_MOCKUP - ビュータイプ／モックアップ
起動パラメータ：VIEW_TYPE_CUI - ビュータイプ／CUI
起動パラメータ：~temp.bingo.0000000000000~.data - 読み込みファイル
```
> パラメータなしで起動すれば、標準で起動します。<br>
> 途中でサービスが落ちた場合、直前のファイルを起動パラメータに指定することで状態を復元することができます。<br>
> （ただ、復元した場合に設定ファイルの内容が変わっているとおかしくなるので注意！ 個人用なのでここまでは対応せず）<br>
