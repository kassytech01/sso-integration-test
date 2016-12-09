package jp.co.nikkeibp.techdev.sso.spec
import static org.junit.Assert.*

import geb.spock.GebReportingSpec

class SsoServiceSiteLevel1Spec extends GebReportingSpec {

	def "自動認証Cookie(ssoa)が存在しない場合、未認証状態（レベル1）になること"() {
		given: "NBOモックサイトを開く"
		to SsoServiceSiteLevel1Page, "nbo"

		when: "自動認証を実行する"
		link("自動認証").click()

		then: "ログイン状態が未認証状態（レベル1）であること"
		waitFor{
			loginState.text().matches("未認証状態（レベル1）")
		}
	}

	def "明示的ログインを行い、サイト側でレベル1認証状態になること"() {
		given: "NBOモックサイトを開く"
		to SsoServiceSiteLevel1Page, "nbo"

		when: "明示的ログインを実行"
		link("明示的ログイン").click()

		then: "日経IDラウンジに遷移すること"
		at (new NikkeiIdLoungePage(fqdn: "id.dev.nikkei.com"))

		when: "日経IDラウンジでIDとPWを入力し、ログインする"
		loginId.value("tkasuga.bp.sso+100@gmail.com")
		password.value("bptest01")
		loginButton.click()

		then: "NBOモックサイトでログイン状態がレベル1認証状態であること"
		waitFor{
			loginState.text().matches("レベル1認証状態")
		}
	}
}
