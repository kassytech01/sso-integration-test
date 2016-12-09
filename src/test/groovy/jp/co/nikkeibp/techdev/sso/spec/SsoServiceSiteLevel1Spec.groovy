package jp.co.nikkeibp.techdev.sso.spec
import static org.junit.Assert.*

import geb.spock.GebReportingSpec

class SsoServiceSiteLevel1Spec extends GebReportingSpec {

	def "自動認証Cookie(ssoa)が存在しない場合、未認証状態（レベル1）となる"() {
		given: "NBOのモックサイトを開く"
		to SsoServiceSiteLevel1Page, "nbo"

		when: "自動認証を実行"
		link("自動認証").click()

		then: "ログイン状態が未認証状態（レベル1）であること"
		waitFor{
			loginState.text().matches("未認証状態（レベル1）")
		}

		when: "明示的ログインを実行"
		link("明示的ログイン").click()

		then: "日経IDラウンジに遷移すること"
		waitFor{ at NikkeiIdLoungePage }
	}
}
