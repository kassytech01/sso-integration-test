import static org.junit.Assert.*

import geb.spock.GebReportingSpec
import page.NikkeiIdLoungePage
import page.SsoLoginCompletePage
import page.SsoServiceSiteLevel1Page

class SsoServiceSiteLevel1Spec extends GebReportingSpec {

	def "自動認証Cookie(ssoa)が存在しない場合、未認証状態（レベル1）になること"() {
		given: "サービスサイトモックのトップページを開く"
		to SsoServiceSiteLevel1Page

		report 'service_site_mock_welcome_page'

		when: "NBOモックサイトを開く"
		to SsoServiceSiteLevel1Page, "nbo"

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
		given: "NBOモックサイトを開く"
		to SsoServiceSiteLevel1Page, "nbo"

		when: "明示的ログインを実行する"
		link("明示的ログイン").click()

		then: "日経IDラウンジに遷移すること"
		waitFor{
			at (new NikkeiIdLoungePage(fqdn: "id.dev.nikkei.com"))
		}

		when: "日経IDラウンジでIDとPWを入力する"
		loginId.value("tkasuga.bp.sso+100@gmail.com")
		password.value("bptest01")

		and: "ログインする"
		loginButton.click()

		then: "NBOモックサイトに戻りリダイレクトすること"
		waitFor("slow"){ at SsoServiceSiteLevel1Page }

		and: "NBOモックサイトでログイン状態がレベル1認証状態であること"
		loginState.text().matches("レベル1認証状態")
	}
}
