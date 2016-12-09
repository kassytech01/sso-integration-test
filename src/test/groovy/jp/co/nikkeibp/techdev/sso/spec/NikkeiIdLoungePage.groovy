package jp.co.nikkeibp.techdev.sso.spec
import geb.Page

class NikkeiIdLoungePage extends Page {

	String fqdn

	static at = {
		getDriver().currentUrl.contains(fqdn)
		title == "日経ＩＤ ： 日経ＩＤの確認"
	}

	static content = {
		loginId { $(".idForm") }
		password { $(".pwForm") }
		loginButton { $("form ul li.btn_next input")}
	}
}
