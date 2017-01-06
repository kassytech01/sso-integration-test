package spec

import static org.junit.Assert.*

import geb.spock.GebReportingSpec
import page.NikkeiIdLoungePage
import page.SsoLoginCompletePage
import page.SsoServiceSitePage

class SsoServiceSiteCommonSpec extends GebReportingSpec {

	/**
	 * 環境設定値をレポーティングする。
	 */
	def setupSpec() {
		given: "_環境設定確認"
		to SsoServiceSitePage
	}

	/**
	 * sif/〜/move.txtに'redirect.time ='と指定した場合、リダイレクト出口からの戻りリダイレクトは発生しない
	 */
	def "明示的ログイン後にログイン成功画面が表示されリダイレクトされないこと（ts=gateway）"() {
		when: "Commonを開く"
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

		when: "「ご利用のサービスへ」ボタンを押下する"
		gotoServiceButton.click()

		then: "Commonに戻ること"
		at SsoServiceSitePage
		getDriver().currentUrl.endsWith("/gateway/")
	}

	def "設定したtsの場合、GWフロントのリダイレクト出口で、認証サーバの画面をスキップする"(){
		//		bp_shoten

	}
}
