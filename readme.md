SSO Integration Test Project
=============

SSO認証システムのインテグレーションテストを自動で行うためのプロジェクトです。  
SSO認証クライント経由で、SSO認証システムのサーバサイド処理をテストするため、  
モックアプリであるsso-servicesite-mockプロジェクトに対して期待値の検証を行うことを前提にしています。

## Description

このテストプロジェクトは、クロスプラットフォーム（OS）、かつクロスブラウザでの検証に対応しています。  
各種ツールやフレームワークは保守性を考慮して、`Gradle`、`Geb`、及び`Spock`を採用しています。  
尚、現状はテスト環境でのインテグレーションテストのみを対象にしていますが、最終的には、本番リリース後の動作確認にも使用できるように拡張する予定です。  
（例えば、サーバ1台に対するリリース後の動作確認に使用できる想定です。）


* サポートプラットフォーム
    - Windows
    - Mac
    - Linux


* サポートブラウザ
    - Chrome
    - Firefox
    - PhantomJs（ヘッドレス=画面のないブラウザ）
    - Internet Exproler
    - Safari
    - Edge（MicrosoftWebDriverのVer.Release 14393時点で、原因不明のエラーにより正常動作を確認できていません。）

##### Gebとは
> Gebは、Luke Daley氏を中心に開発されているWebアプリケーション向けの機能テストを自動化するためのライブラリです。Internet Explorer, FireFox, Chrome などのブラウザを操作することが可能なSeleniumのWebDriverとJQueryライクな記述でコンテンツの操作や検査を可能にするAPIを組み合わせ、Groovyの豊かな表現力により簡潔なDSLでテストスクリプトを記述することができます。

>また、JUnitやSpockなどのテストフレームワークと統合することも可能なため、TDDやBDDなどの開発プロセスに取り入れやすいですし、使用するブラウザによってはテスト中の画面をキャプチャして出力する機能も提供されていますので、開発者が機能テストやシナリオテストを効率良く行うことができます。

>＜引用元＞：[GebではじめるWebテスト 〜第1回 導入編〜](http://beta.mybetabook.com/showpage/4f27c8cc0cf26106dca875c8)


## Usage

次のコマンドで、各ブラウザのテストが実行されます。

    ./gradlew chromeTest
    ./gradlew firefoxTest
    ./gradlew phantomJsTest
    ./gradlew ieTest
    ./gradlew safariTest
    ./gradlew edgeTest

また、各プラットフォームで実行可能なすべてのブラウザでのテストを起動するには、以下のコマンドを実行します。

    ./gradlew test

このコマンドを実行した場合、各環境ごとに以下のブラウザにてテストが実行されます。

+   すべてのプラットフォーム:
    - Chrome
    - Firefox
    - PhantomJs

+   Windows(10以外)
    - ie(InternetExproler)

+   Windows(10)
    - Edge

+   Mac OS
    - Safari


※Windows環境の場合、上記の`./gradlew`を`gradlew.bat`に置き換えて実行してください。


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



### ChromeでCSSセレクタの検証
F12を押下してディベロッパーツールを表示
* [Console]を選択
- cssセレクターの実験は $$(css_selector);
- （因みに）xpathの実験は $x(xpath_to_element);
