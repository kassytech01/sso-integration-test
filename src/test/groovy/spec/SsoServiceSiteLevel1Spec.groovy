package spec

import static org.junit.Assert.*

import geb.spock.GebReportingSpec
import page.NikkeiIdLoungePage
import page.SsoServiceSitePage

class SsoServiceSiteLevel1Spec extends GebReportingSpec {

	/**
	 * 環境設定値をレポーティングする。
	 */
	def setupSpec() {
		given: "_環境設定確認"
		to SsoServiceSitePage
	}

	def "自動認証Cookie(ssoa)が存在しない場合、未認証状態（レベル1）になること"() {
		when: "NBOを開く"
		to SsoServiceSitePage, "nbo"

		then: "ssoa認証クッキーが保持されていないこと"
		requestCookieState("ssoa").text().matches("None")

		when: "自動認証を実行する"
		link("自動認証").click()

		then: "ログイン状態が未認証状態（レベル1）であること"
		waitFor("quick"){
			loginState.text().matches("未認証状態（レベル1）")
		}
	}

	def "明示的ログインを行い、サイト側でレベル1認証状態になること"() {
		when: "NBOを開く"
		to SsoServiceSitePage, "nbo"

		and: "明示的ログインを実行する"
		link("明示的ログイン").click()

		then: "日経IDラウンジに遷移すること"
		waitFor{
			at (new NikkeiIdLoungePage(fqdn: "id.dev.nikkei.com"))
		}

		when: "日経IDラウンジでIDとPWを入力して、ログインする"
		login("tkasuga.bp.sso+100@gmail.com","bptest01")

		then: "NBOに戻りリダイレクトすること"
		waitFor("slow"){ at SsoServiceSitePage }

		and: "NBOでログイン状態が'レベル1認証状態'かつ'2:利用可'であること"
		loginState.text().matches("レベル1認証状態")
		serviceState.text().matches("2:利用可")

		and: "ssoa認証クッキーが保持されていること"
		requestCookieState("ssoa").text() ==~ /\d{14}|\d{1,10}|\d{14}.*/
	}

	//	def "明示的ログインを行い、サイト側でレベル1認証状態になることa"() {
	//		given: "NBOを開く"
	//		to SsoServiceSiteLevel1Page, "nbo"
	//
	//		when: "明示的ログインを実行する"
	//		link("明示的ログイン").click()
	//
	//		then: "日経IDラウンジに遷移すること"
	//		waitFor{
	//			at (new NikkeiIdLoungePage(fqdn: "id.dev.nikkei.com"))
	//		}
	//
	//		when: "日経IDラウンジでIDとPWを入力して、ログインする"
	//		login("tkasuga.bp.sso+100@gmail.com","bptest01")
	//
	//		then: "NBOに戻りリダイレクトすること"
	//		waitFor("slow"){ at SsoServiceSiteLevel1Page }
	//
	//		and: "NBOでログイン状態が'レベル1認証状態'かつ'2:利用可'であること"
	//		loginState.text().matches("レベル1認証状態")
	//		serviceState.text().matches("2:利用可")
	//	}
}
