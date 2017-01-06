package spec

import static org.junit.Assert.*

import geb.spock.GebReportingSpec
import page.NikkeiIdLoungePage
import page.SsoLoginCompletePage
import page.SsoServiceSitePage

/**
 * SSO認証クライントのスペックを検証するテストクラスです。
 * <p>
 * 新認証サーバのログイン後のスペックを記載します。
 * </p>
 * @author tkasuga
 *
 */
class SsoClientAfterNewSsoLoggedInSpec extends GebReportingSpec {

	/**
	 * 環境設定値をレポーティングする。
	 */
	def setupSpec() {
		given: "_環境設定確認"
		to SsoServiceSitePage
	}

	/**
	 * IT_005-01-1<br>
	 * <p>
	 * 新認証サーバでログイン済みの状態で、
	 * ターゲットサービスIDに新認証サーバの対象となるものを指定し、サービス自動ログインを実施し、正常にステータスが返却される。
	 * 同一ブラウザでターゲットサービスIDに新旧問い合わせ対象となるもののみを指定し、サービス自動ログインを実施し、正常にステータスが返却される。
	 * 同一ブラウザでターゲットサービスIDに旧認証サーバの対象となるもののみを指定し、サービス自動ログインを実施し、Cookieの復号に失敗する。
	 * </p>
	 */
	def "新認証サーバで認証後、1.新認証、2.新旧問い合わせ、3.旧認証の自動ログインができること"() {
		when: "新認証サーバ対象のtsのサービスサイトに遷移する"
		to SsoServiceSitePage, "gateway"

		and: "明示的ログインを実行する"
		link("明示的ログイン").click()

		then: "日経IDラウンジに遷移すること"
		waitFor{
			at (new NikkeiIdLoungePage(fqdn: "id.dev.nikkei.com"))
		}

		when: "日経IDラウンジでIDとPWを入力して、ログインする"
		login("tkasuga.bp.sso+100@gmail.com","bptest01")

		then: "ログインに成功すること"
		waitFor("slow"){ at SsoLoginCompletePage }

		when: "新認証サーバ対象のtsのサービスサイトに遷移する(ログインしたtsとは別)"
		to SsoServiceSitePage, "nbo"

		and: "自動認証を実行する"
		link("自動認証").click()

		then: "ログイン状態であること"
		waitFor {
			loginState.text().matches("レベル1認証状態")
			serviceState.text().matches("2:利用可")
			requestCookieState("ssoa").text() ==~ /\d{14}|\d{1,10}|\d{14}.*/
		}

		when: "新旧問い合わせ対象のtsのサービスサイトに遷移する"
		to SsoServiceSitePage, "nfm_en"

		and: "自動認証を実行する"
		link("自動認証").click()

		then: "ログイン状態であること"
		waitFor {
			loginState.text().matches("レベル1認証状態")
			serviceState.text().matches("2:利用可")
			requestCookieState("ssoa").text() ==~ /\d{14}|\d{1,10}|\d{14}.*/
		}

		//TODO:sso-servicesite-mockにNMOを追加する？
		//		when: "旧認証サーバ対象のtsのサービスサイトに遷移する"
		//		to SsoServiceSitePage, "nmo"
		//
		//		and: "自動認証を実行する"
		//		link("自動認証").click()
		//
		//		then: "ログイン状態であること"
		//		waitFor {
		//			loginState.text().matches("レベル1認証状態")
		//			serviceState.text().matches("2:利用可")
		//			requestCookieState("ssoa").text() ==~ /\d{14}|\d{1,10}|\d{14}.*/
		//		}
	}
	
	def "パスポートに存在しないユーザで、新旧問い合わせを行うと認証サーバエラーが発生する"() {
		when: "新認証サーバ対象のtsのサービスサイトに遷移する"
		to SsoServiceSitePage, "gateway"

		and: "明示的ログインを実行する"
		link("明示的ログイン").click()

		then: "日経IDラウンジに遷移すること"
		waitFor{
			at (new NikkeiIdLoungePage(fqdn: "id.dev.nikkei.com"))
		}

		when: "日経IDラウンジでIDとPWを入力して、ログインする"
		login("tkasuga.bp.sso+101@gmail.com","bptest01")

		then: "ログインに成功すること"
		waitFor("slow"){ at SsoLoginCompletePage }

		when: "新旧問い合わせ対象のtsのサービスサイトに遷移する"
		to SsoServiceSitePage, "nfm_en"

		and: "自動認証を実行する"
		link("自動認証").click()

		then: "ログイン状態であること"
		waitFor {
			loginState.text().matches("その他認証クライアント障害")
			requestCookieState("ssoa").text() ==~ /\d{14}|\d{1,10}|\d{14}.*/
		}
	}
}
