package jp.co.nikkeibp.techdev.sso.spec
import static org.junit.Assert.*

import geb.spock.GebReportingSpec

class SsoServiceSiteLevel1Spec extends GebReportingSpec {

	def "自動認証Cookie(ssoa)が存在しない場合、未認証状態（レベル1）となる"() {
		given:
		to SsoServiceSiteLevel1Page

		when: //hover over to expand the menu
		links[0].click()

		then: //first link is for the current manual
		waitFor{
			loginState.text().matches("未認証状態（レベル1）")
		}

//		when:
//		manualsMenu.links[0].click()
//
//		then:
//		at TheBookOfGebPage
	}
}
