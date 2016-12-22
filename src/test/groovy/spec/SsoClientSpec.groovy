package spec

import static org.junit.Assert.*

import geb.spock.GebReportingSpec
import page.NikkeiIdLoungePage
import page.SsoLoginCompletePage
import page.SsoServiceSitePage

/**
 * SSO認証クライントのスペックを検証するテストクラスです。
 * @author tkasuga
 *
 */
class SsoClientSpec extends GebReportingSpec {

	/**
	 * 環境設定値をレポーティングする。
	 */
	def "_環境設定確認"() {
		given:
		to SsoServiceSitePage
	}

	/**
	 * 
	 */
	def "新認証サーバで認証後、新認証サーバ対象のtsで自動ログインできること"() {
		given: "新認証サーバでログイン済みであること"
		loginAtNewSsoServer()

		when: "新認証サーバ対象のtsで自動ログインする"
		to SsoServiceSitePage, "nbo"

		and: "自動認証を実行する"
		link("自動認証").click()

		then: "NBOでログイン状態が'レベル1認証状態'かつ'2:利用可'であること"
		loginState.text().matches("レベル1認証状態")
		serviceState.text().matches("2:利用可")

		and: "ssoa認証クッキーが保持されていること"
		requestCookieState("ssoa").text() ==~ /\d{14}|\d{1,10}|\d{14}.*/
	}

	def "設定したtsの場合、GWフロントのリダイレクト出口で、認証サーバの画面をスキップする"(){
		//		bp_shoten

	}

	void loginAtNewSsoServer() {
		when: "新認証サーバでログイン済みであること"
		to SsoServiceSitePage, "gateway"

		and: "明示的ログインを実行する"
		link("明示的ログイン").click()

		then: "日経IDラウンジに遷移すること"
		waitFor{
			at (new NikkeiIdLoungePage(fqdn: "id.dev.nikkei.com"))
		}

		when: "日経IDラウンジでIDとPWを入力して、ログインする"
		waitFor { login("tkasuga.bp.sso+100@gmail.com","bptest01") }

		then: "ログインに成功し、リダイレクト出口で戻りリダイレクトされないこと"
		waitFor("slow"){ at SsoLoginCompletePage }
	}
}
