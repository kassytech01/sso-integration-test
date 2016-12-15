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

## Questions and issues

Please ask questions on [Geb user mailing list][mailing_list] and raise issues in [Geb issue tracker][issue_tracker].


[build_status]: https://snap-ci.com/geb/geb-example-gradle/branch/master/build_image "Build Status"
[mailing_list]: https://groups.google.com/forum/#!forum/geb-user
[issue_tracker]: https://github.com/geb/issues/issues


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
