package spec

import static org.junit.Assert.*

import geb.spock.GebReportingSpec
import page.SsoServiceSitePage

class SsoServiceSiteCommonSpec extends GebReportingSpec {

	def "hogehoge"() {
		given:
		to SsoServiceSitePage
	}
	
//	def "明示的ログイン後にログイン成功画面が表示されること（ts=gateway）"() {
//	}
}
