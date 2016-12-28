SSO Integration Test Project
=============

SSO認証システムのインテグレーションテストを自動で行うためのプロジェクトです。  
SSO認証クライント経由で、SSO認証システムのサーバサイド処理をテストするため、  
モックアプリであるsso-servicesite-mockプロジェクトに対して期待値の検証を行うことを前提にしています。

Description
------

このテストプロジェクトは、クロスプラットフォーム（OS）、クロスブラウザのUIテストを行うことが可能です。  
ツールやフレームワークは保守性を考慮して、`Gradle`、`Geb`、及び`Spock`を採用しています。  
尚、現状はテスト環境でのインテグレーションテストのみを対象にしていますが、最終的には、本番環境の動作確認にも使用できるように拡張する予定です。  
（例えば、本番サーバ1台に対するリリース後の動作確認などに使用することを考えています。）

* サポートプラットフォーム
    - Windows
    - Mac
    - Linux

* サポートブラウザ
    - Chrome
    - Firefox
    - PhantomJs　（※ヘッドレス=画面のないブラウザ）
    - Internet Exproler
    - Safari
    - Edge　（※MicrosoftWebDriverの`Ver.Release 14393`時点で、原因不明のエラーにより正常動作を確認できていません。）

##### Gebとは
> Gebは、Luke Daley氏を中心に開発されているWebアプリケーション向けの機能テストを自動化するためのライブラリです。Internet Explorer, FireFox, Chrome などのブラウザを操作することが可能なSeleniumのWebDriverとJQueryライクな記述でコンテンツの操作や検査を可能にするAPIを組み合わせ、Groovyの豊かな表現力により簡潔なDSLでテストスクリプトを記述することができます。

>また、JUnitやSpockなどのテストフレームワークと統合することも可能なため、TDDやBDDなどの開発プロセスに取り入れやすいですし、使用するブラウザによってはテスト中の画面をキャプチャして出力する機能も提供されていますので、開発者が機能テストやシナリオテストを効率良く行うことができます。

>＜引用元＞：[GebではじめるWebテスト 〜第1回 導入編〜](http://beta.mybetabook.com/showpage/4f27c8cc0cf26106dca875c8)


Requirement
------

* Java 1.8


Usage
------

次のコマンドで、各ブラウザのテストが実行されます。  
※Windows環境の場合、下記の`./gradlew`を`gradlew.bat`に置き換えて実行してください。

    ./gradlew chromeTest
    ./gradlew firefoxTest
    ./gradlew phantomJsTest
    ./gradlew ieTest
    ./gradlew safariTest
    ./gradlew edgeTest

また、実行するプラットフォームで動作可能なすべてのブラウザでテストを行うには、以下のコマンドを実行します。

    ./gradlew test

各プラットフォーム毎に以下のブラウザにて、テストが実行されます。

+   すべてのプラットフォーム:
    - Chrome
    - Firefox
    - PhantomJs

+   Windows(10以外)
    - IE(Internet Exproler)
    - ※上記「すべてのプラットフォーム」

+   Windows(10)
    - Edge
    - ※上記「すべてのプラットフォーム」

+   Mac OS
    - Safari
    - ※上記「すべてのプラットフォーム」

### テスト対象の指定
テストスイートを指定して実行します。

    ./gradlew firefoxTest -Ptarget=TestSuite

テストクラスを指定して実行します。（ワイルドカード指定も可能）

    ./gradlew firefoxTest -Ptarget=SsoServiceSiteCommonSpec
    ./gradlew firefoxTest -Ptarget=*ServiceSite*

何も指定しない場合、すべてのテストケースを対象にテストを実行します。

    ./gradlew firefoxTest


Environment Settings
------

__※IEでのテストを実行する為には、以下の設定を手動で行う必要があります。__

WebDriverでのIEのテスト実行は、色々と不具合が多く、可能な限りビルド設定で吸収できるようにしましたが、
以下は、手動で設定する必要があります。  
（ローカルの開発環境ではIE以外のブラウザでテストを行い、最終的にCI等でのインテグレーションテスト環境で
IEを使ったテストを行うなどの開発プロセスを検討してください。）
（IE以外のブラウザでは、特に事前設定は必要ありません。）

### IEのセキュリティ設定
1. IEを開き設定アイコンから「インターネットオプション」を選択する。
2. `セキュリティ`タブの4つのゾーンの`保護モードを有効にする`を統一する。
（セキュリティレベルを保つ為に基本的には`有効`に設定してください。）

### プロキシ設定
IE以外のWebDriverでは、ドライバインスタンス生成時にプロキシの設定を適用することができますが、
IEだけなぜかNoProxy（除外設定）が効かないため、テストを実施したい環境に合わせてシステムのプロキシ設定を手動で切り替える必要があります。

#### プロキシとテスト環境

+    t環境:   `proxys.nikkeibp.co.jp`
+    t2環境:  `proxy2.nikkeibp.co.jp`


### その他の不具合
* IEの拡大サイズを100%にする必要がある（IEWebDriverに`InternetExplorerDriver.IGNORE_ZOOM_SETTING`を設定することで回避）
* 64bit版のWebDriverを使用するとtextへの文字入力が異常に遅い（OSのビットに関わらず、すべて32bit版を使用することで回避）


Directory Layout
------

sso-integration-test
├─build
│  ├─classes
│  │  └─test
│  │      ├─page
│  │      └─spec
│  ├─reports
│  │  ├─firefoxTest
│  │  │  ├─geb
│  │  │  │  └─spec
│  │  │  └─tests
│  │  │      ├─classes
│  │  │      ├─css
│  │  │      ├─js
│  │  │      ├─packages
│  │  │      └─index.html
│  │  └─ieTest
│  │      ├─geb
│  │      │  └─spec
│  │      └─tests
│  │          ├─classes
│  │          ├─css
│  │          ├─js
│  │          ├─packages
│  │          └─index.html
│  ├─resources
│  │  └─test
│  ├─test-results
│  │  ├─firefoxTest
│  │  │  └─binary
│  │  └─ieTest
│  │      └─binary
│  ├─tmp
│  │  ├─compileTestGroovy
│  │  │  └─groovy-java-stubs
│  │  ├─firefoxTest
│  │  └─ieTest
│  └─webdriver
│      ├─geckodriver
│      ├─iedriver
│      ├─geckodriver-v0.11.1-win64.zip
│      └─IEDriverServer_Win32_3.0.0.zip
├─gradle
│  └─wrapper
└─src
    └─test
        ├─groovy
        │  ├─page
        │  └─spec
        └─resources

### build/classes
gradleのbuildタスクによってビルドされたclassファイルが格納されます。

### build/reports/[テストタスク名]/geb
実行されたテストタスク毎のレポート（画面キャプチャ）が格納されます。

### build/reports/[テストタスク名]/tests
実行されたテストタスク毎のテストサマリーが格納されます。

### build/webdriver
各テストタスクの中でダウンロードされたWebDriverの実態が格納されます。

### gradle
直下には、build.gradleから分離したGradleタスク用の設定ファイルが管理されています。
また、`/wrapper`内にGradleのラッパースクリプトが格納されています。

### src/test/groovy
テストコードが格納されています。
テストケースやテストスイートの追加・修正は、当該ディレクト内のファイルに対して実施します。
テストスイートは当該ディレクトリ直下、テストケースは`/sprec`、PageObjectデザインパターンのPageに該当するコードは`/page`で管理します。

### src/test/resources
GebConfig.groovyが管理されています。
Gebに対する設定やWebDriverの初期化を行います。


Coding Guide
------

テストコーディングのガイドラインを以下に記載します。

### Geb-Spock
Gebは、Javaの言語仕様をベースとしているGroovyにてテストコードを記述するため、Javaプログラマにとっては比較的早く実装に慣れることが可能です。  
また、PageObjectデザインパターンを採用しており、画面変更に強いテストコーディングを行うことが可能なこともGebの特徴です。  
さらに、テストフレームワークのSpockは、`Given-When-Then`構造でテストコードを記述することにより、可読性が高くなるだけでなく、
テストコードを見るだけで、プロダクトコードの仕様を理解することができるようになります。  
これは、追加改修によって発生するテストコードメンテナンスの生産性を高めるだけでなく、開発から保守への運用の引き継ぎをスムーズにするという効果もあります。

具体的な実装方法については、実際のテストコードを見て動かして理解してみてください。  
Groovyの言語仕様を知らなくても、Geb-Spockの力によってすぐに実装できるレベルに到達できるはずです。

### Refarence
コーディングのサンプルやプラクティスは、下記を参照してください。

＜公式サイト＞:[The Book Of Geb](http://www.gebish.org/manual/current/)

最初にGebの概要を理解するには、以下のスライドが特徴をシンプルに説明していてわかり易いです。

* [横浜タネマキでGebと握手！ #yokohamagroovy byPoohSunny](http://sssslide.com/speakerdeck.com/poohsunny/heng-bang-tanemakidegebtowo-shou-number-yokohamagroovy)

もう少し詳細に知りたい方は、こちらもご覧ください。

* [脱・独自改造！ GebでWebDriverをもっとシンプルに ](http://www.slideshare.net/hirokotamagawa/20141018-2selenium-40423299)
* [ブラウザテストをサクサク自動化するためのGeb実践入門 #jjug_ccc](http://sssslide.com/speakerdeck.com/poohsunny/burauzatesutowosakusakuzi-dong-hua-surutamefalsegebshi-jian-ru-men-number-jjug-ccc)

Spockに関する概要は、以下がまとまっていてわかり易いです。  
（そもそもgiven-when-thenとは？という方は、「BDD（振る舞い駆動開発）」について調べてみてください。）

* [Spockのドキュメントを読んだよ](http://bufferings.hatenablog.com/entry/2014/01/17/234736)

### Coding Std.

+   テストフレームワーク
    - 保守性を考慮して、JUnitではなく**Spock**を採用
    - テストクラス名のSuffixは、`*Spec`とすること

+   テストケース
    - テストケースの実行順序や状態の依存関係を極力なくすこと（）
    -

+   テストスイート
    -

### Tips

#### Chromeでセレクタの検証
GebはjQueryライクなセレクタで画面要素を取得することができますが、それ自体の検証にいちいちテストを実行してデバッグするのは面倒なので、  
Chromeのディベロッパーツールを使ってその検証を行う方法を紹介します。（Firefoxでも可能です。）

1. Chrome起動後、F12を押下してディベロッパーツールを表示する。
2. [Console]を選択し、下部の入力欄に以下を入力する。
    - cssセレクターの取得検証は $$(css_selector);
    - （因みに）xpathの取得検証は $x(xpath_to_element);




Eclipse
------

### Eclipse-Groovy設定
[ウィンドウ]-[設定]を開き[Goovy]-[エディター]-[フォーマッター]の設定を以下の通りにしてください。
+   Position of the opening braces {:
    - On the same line:
+   Position of the closing braces }:
    - On the next line:
+   折り返しされた行のデフォルト・インデント:
    - 2
+   Length after which list are 'long' (long lists are wrapped):
    - 1
+   Remove unnecessary semicolons
    - checked


### Eclipseを使ったDebug方法
https://softdevbuilttolast.wordpress.com/2015/04/15/debugging-build-gradle-with-eclipse/

    ./gradlew phantomJsTest -Dorg.gradle.debug=true

Eclipse
プロジェクト-右クリック
[デバッグ]-[デバッグの構成]-[リモートJavaアプリケーション]
接続タイプ：標準（ソケット接続）
接続プロパティ
	ホスト：localhost
	ポート：5005
リモートVMの終了を許可：チェック

ただし、sso-servicesite-mockプロジェクトをEclipseにてデバッグ実行している場合、
1つしかデバッグできない可能性がある（未検証だが、そのような記事がありました。）


Others
------

### スローテスト対策
テストを並列実行
