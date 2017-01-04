SSO Integration Test Project
=============

SSO認証システムのインテグレーションテストを自動で行うためのプロジェクトです。  
SSO認証クライント経由で、SSO認証システムのサーバサイド処理をテストするため、  
モックアプリであるsso-servicesite-mockプロジェクトに対して期待値の検証を行うことを前提にしています。

Description
------

このテストプロジェクトは、クロスプラットフォーム（OS）、クロスブラウザでのUIテストを行うことが可能です。  
ツールやフレームワークはメンテナンス性を考慮して、`Gradle`、`Geb`、及び`Spock`を採用しています。  
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
    - ＋上記「すべてのプラットフォーム」

+   Windows(10)
    - Edge
    - ＋上記「すべてのプラットフォーム」

+   Mac OS
    - Safari
    - ＋上記「すべてのプラットフォーム」

### テスト対象の指定
テストクラスを指定して実行します。（ワイルドカード指定も可能）

    ./gradlew firefoxTest -Ptarget=SsoServiceSiteCommonSpec
    ./gradlew firefoxTest -Ptarget=*ServiceSite*

また、何も指定しない場合は、すべてのテストケースを対象にテストを実行します。

    ./gradlew firefoxTest

尚、テストスイートを指定して実行することも可能です。

    ./gradlew firefoxTest -Ptarget=TestSuite
    ./gradlew firefoxTest -Ptarget=*Suite*

テストスイートは、`src/test/groovy`直下にテストスイートクラスを作成して指定してください。（ワイルドカード指定も可能）  
尚、実装方法は、`src/test/groovy/TestSuite.groovy`を参考にしてください。

### Env指定
実行環境毎に設定やIDを切り替えたい場合は、`env`を指定して実行します。

    ./gradlew firefoxTest -Penv=it

+   Default : `it`

| -Penv        | Description             |
|:------------:|:------------------------|
| dev          |ローカル開発環境用の設定<br>（現状、ローカル開発環境は存在しない為、t2環境を指定）|
| ct           |t2環境用の設定           |
| it           |t環境用の設定            |
| production   |本番環境用の設定         |

指定された`env`によって、読み込まれるGradleタスク用の設定ファイル`{$env}.gradle`が切り替わります。  

### プロキシの指定
WebDriverからプロキシを経由してアクセスする場合、基本的には、Env指定によりプロキシが動的に切り替わります。  
例えば、`-Penv=it`を指定した場合は、`gradle/env/it.gradle`に設定された`proxys.nikkeibp.co.jp`が指定されます。  
しかし、場合によっては手動でプロキシを変更したいケースも出てくると思います。  
その場合には、以下のようにプロキシを手動で指定することが可能です。

    ./gradlew firefoxTest -PproxyUrl=proxy2.nikkeibp.co.jp -PproxyPort=80

※`-PproxyUrl`と`-PproxyPort`は、必ず両方指定する必要があります。


Environment Settings
------

### IEの場合

__※IEでのテストを実行する為には、以下の設定を手動で行う必要があります。__

WebDriverでのIEのテスト実行は、色々と不具合が多く、可能な限りビルド設定で吸収できるようにしましたが、
以下は、手動で設定する必要があります。  
（ローカルの開発環境ではIE以外のブラウザでテストを行い、最終的にCI等でのインテグレーションテスト環境で
IEを使ったテストを行うなどの開発プロセスを検討してください。）
（IE以外のブラウザでは、特に事前設定は必要ありません。）

#### IEのセキュリティ設定
1. IEを開き設定アイコンから「インターネットオプション」を選択する。
2. `セキュリティ`タブの4つのゾーンの`保護モードを有効にする`を統一する。
（セキュリティレベルを保つ為に基本的には`有効`に設定してください。）

#### プロキシ設定
IE以外のWebDriverでは、ドライバインスタンス生成時にプロキシの設定を適用することができますが、
IEだけなぜかNoProxy（除外設定）が効かないため、テストを実施したい環境に合わせてシステムのプロキシ設定を手動で切り替える必要があります。

##### プロキシとテスト環境

+    t環境:   `proxys.nikkeibp.co.jp`
+    t2環境:  `proxy2.nikkeibp.co.jp`


#### その他の不具合
* IEの拡大サイズを100%にする必要がある（IEWebDriverに`InternetExplorerDriver.IGNORE_ZOOM_SETTING`を設定することで回避）
* 64bit版のWebDriverを使用するとtextへの文字入力が異常に遅い（OSのビットに関わらず、すべて32bit版を使用することで回避）


### レポーティングの画面サイズ設定
Gebのレポーティング機能により、テストケース実行時の画面をキャプチャすることができます。  
もし、画面サイズを調整したい場合には、`src/test/resources/GebConfig.groovy`に設定されているWebDriver毎に設定値を変更してください。

```groovy
d.manage().window().size = new Dimension(1280, 1024)
```


Directory Layout
------

```
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
```

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

### gradle/env
実行環境毎に読み込まれるGradleタスク用の設定ファイルが格納されています。

### gradle/wrapper
Gradleのラッパースクリプトが格納されています。

### src/test/groovy
テストコードが格納されています。
テストケースやテストスイートの追加・修正は、当該ディレクト内のファイルに対して実施します。
テストスイートは当該ディレクトリ直下、テストケースは`/spec`、PageObjectデザインパターンのPageに該当するコードは`/page`で管理します。

### src/test/resources
GebConfig.groovyが管理されています。
Gebに対する設定やWebDriverの初期化を行います。


Coding Guide
------

テストコーディングのガイドラインを以下に記載します。

### Geb-Spock
Gebは、Javaの言語仕様をベースとしているGroovyにてテストコードを記述するため、Javaプログラマにとっては比較的早く実装に慣れることが可能です。  
また、PageObjectデザインパターンを採用しており、画面変更に強いテストコーディングを行うことが可能なこともGebの特徴です。  
さらに、テストフレームワークのSpockは、`Given-When-Then`構造でシナリオベースのテストコードを記述することにより、可読性が高くなるだけでなく、
テストコードを見るだけで、プロダクトコードの仕様を理解することができるようになります。  
これは、追加改修によって発生するテストコードメンテナンスの生産性を高めるだけでなく、開発から保守への運用の引き継ぎをスムーズにするという効果もあります。

具体的な実装方法については、実際のテストコードを見て動かして理解してみてください。  
Groovyの言語仕様を知らなくても、Geb-Spockの力によってすぐに実装できるレベルに到達できるはずです。

### Refarence
コーディングのサンプルやプラクティスは、下記を参照してください。

＜公式サイト＞:[The Book Of Geb](http://www.gebish.org/manual/current/)

最初にGebの概要を理解するには、以下のスライドが、特徴をシンプルに説明していてわかり易いです。

* [横浜タネマキでGebと握手！ #yokohamagroovy byPoohSunny](http://sssslide.com/speakerdeck.com/poohsunny/heng-bang-tanemakidegebtowo-shou-number-yokohamagroovy)

もう少し詳細に知りたい方は、こちらもご覧ください。

* [脱・独自改造！ GebでWebDriverをもっとシンプルに ](http://www.slideshare.net/hirokotamagawa/20141018-2selenium-40423299)
* [ブラウザテストをサクサク自動化するためのGeb実践入門 #jjug_ccc](http://sssslide.com/speakerdeck.com/poohsunny/burauzatesutowosakusakuzi-dong-hua-surutamefalsegebshi-jian-ru-men-number-jjug-ccc)

Spockに関する概要は、以下がまとまっていてわかり易いです。  
（そもそもgiven-when-thenとは？という方は、「BDD（振る舞い駆動開発）」について調べてみてください。）

* [Spockのドキュメントを読んだよ](http://bufferings.hatenablog.com/entry/2014/01/17/234736)

### Coding Std.

+   テストフレームワーク
    - メンテナンス性を考慮して、JUnitではなく**Spock**を採用
    - テストクラス名のSuffixは、`*Spec`とすること

+   テストケース
    - テストケースの実行順序や状態の依存関係を極力なくすこと（冪等性）

+   ページ実装
    - 画面の部品や構成要素は、PageObjectパターンに準拠し、Pageクラスに実装すること
    - ページクラス名のSuffixは、`*Page`とすること
    - 構成要素が複数ページにまたがって使用される場合は、Moduleクラスに実装すること
    - モジュールクラス名のSuffixは、`*Module`とすること

+   テストスイート
    - 複数のテストクラスをまとめてテスト実行したい場合に使用する。  
      例えば、ログイン機能に関連した機能の改修に伴い、ログイン関連のテストケースをまとめて実行する場合になどに利用する。
    - テストスイートクラス名のSuffixは、`*TestSuite`とすること

### Tips

#### Chromeでセレクタの検証
GebはjQueryライクなセレクタで画面要素を取得することができますが、それ自体の検証にいちいちテストを実行してデバッグするのは面倒なので、Chromeのディベロッパーツールを使ってその検証を行う方法を紹介します。（Firefoxでも可能です。）

1. Chrome起動後、F12を押下してディベロッパーツールを表示する。
2. [Console]を選択し、下部の入力欄に以下を入力する。
    - cssセレクターの取得検証は $$(css_selector);
    - （因みに）xpathの取得検証は $x(xpath_to_element);


Eclipse
------

### Eclipse-Groovy設定
[ウィンドウ]-[設定]を開き[Goovy]-[エディター]-[フォーマッター]の設定を以下の通りにしてください。
+   Position of the opening braces {:
    - checked : On the same line:
+   Position of the closing braces }:
    - checked : On the next line:
+   折り返しされた行のデフォルト・インデント:
    - 2
+   Length after which list are 'long' (long lists are wrapped):
    - 1
+   Remove unnecessary semicolons
    - checked

### Eclipseを使ったDebug方法
Eclipseのリモートデバッグ機能を利用したデバッグ方法です。

#### Eclipse
プロジェクトを右クリックし、[デバッグ]-[デバッグの構成]-[リモートJavaアプリケーション]（未作成の場合は、右クリック-[新規]）の設定を以下の通りにしてください。
+   接続タイプ
    - 標準（ソケット接続）
+   接続プロパティ
    - ホスト：localhost
	  - ポート：5005
+   リモートVMの終了を許可：
    - checked

#### 実行

    ./gradlew phantomJsTest -Dorg.gradle.debug=true

ただし、sso-servicesite-mockプロジェクトをEclipseにてデバッグ実行している場合、1つしかデバッグできない可能性があります（未検証ですが、そのような記事がありました）。

＜参考＞：https://softdevbuilttolast.wordpress.com/2015/04/15/debugging-build-gradle-with-eclipse/


Others
------

### スローテスト対策
将来的にテスト実行時間が長くなり過ぎた場合は、テストを並列実行する必要が出てきます。  
その為、テストケースをまたいでのテストデータ（ログインIDなど）の利用はできるだけ避け、テストケースを独立させるように心掛けてください。  

### 今後対応したいこと
* ログインIDを設定ファイルに逃がして、Env Profileに応じて切り替えられるようにする。（本番とテスト環境のユーザ切り替え）
* Edgeもうまく動作するように調査する。
* MacのSafariに対応する。
* Seleniumが不安定な為に発生するエラー対策としてリトライ処理を実装する。
* [Sahagin](http://blog.trident-qa.com/2015/09/sahagin-with-geb-spock/)を入れてみる。
* UIテストだけではカバレッジを上げられない場合は、ユニットテストの導入を検討する。
* 継続的インテグレーションの実践（Gitと連携して、Jenkinsからテストを自動実行できるようにする）。
