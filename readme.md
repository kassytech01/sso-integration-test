# Example Geb and Gradle Project

[![Build Status][build_status]](https://snap-ci.com/geb/geb-example-gradle/branch/master)

## Description

This is an example of incorporating Geb into a Gradle build. It shows the use of Spock and JUnit 4 tests.

The build is setup to work with Firefox, Chrome and PhantomJS. Have a look at the `build.gradle` and the `src/test/resources/GebConfig.groovy` files.

## Usage

The following commands will launch the tests with the individual browsers:

    ./gradlew chromeTest
    ./gradlew firefoxTest
    ./gradlew phantomJsTest

To run with all, you can run:

    ./gradlew test

Replace `./gradlew` with `gradlew.bat` in the above examples if you're on Windows.

Environment Settings
------
__※IEでのテストを実行する為には、以下の設定を手動で実施する必要があります。__

WebDriverでのIE11の実行は、色々と不具合が多く可能な限りビルド設定で吸収できるようにしましたが、
IEのセキュリティ設定とプロキシ設定は、手動で設定する必要があります。（現状回避策なし）

### IEのセキュリティ設定
1. IE11を開き設定アイコンから「インターネットオプション」を選択する。
2. `セキュリティ`タブの4つのゾーンの`保護モードを有効にする`を統一する。
（セキュリティレベルを保つ為に基本的には`有効`に設定してください。）

### プロキシ設定
IE以外のWebDriverでは、ドライバインスタンス生成時にプロキシの設定を適用することができますが、
IEだけなぜかNoProxy（除外設定）が効かないため、テストを実施したい環境に合わせてプロキシ設定を変更する必要があります。

#### プロキシとテスト環境

+    t環境:   `proxys.nikkeibp.co.jp`
+    t2環境:  `proxy2.nikkeibp.co.jp`


### その他の不具合
* IEの拡大サイズを100%にする必要がある（IEWebDriverに`InternetExplorerDriver.IGNORE_ZOOM_SETTING`を設定することで回避）
* 64bit版のWebDriverを使用するとtextへの文字入力が異常に遅い（32bit版を使用することで回避）




Eclipseを使ったDebug方法
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


#### テスト対象を指定して実行
./gradlew firefoxTest -Ptarget=TestSuite
./gradlew firefoxTest -Ptarget=SsoServiceSiteCommonSpec
./gradlew firefoxTest -Ptarget=*ServiceSite*




### ChromeでCSSセレクタの検証
F12を押下してディベロッパーツールを表示
* [Console]を選択
- cssセレクターの実験は $$(css_selector);
- （因みに）xpathの実験は $x(xpath_to_element);


#### Eclipse-Groovy設定
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
