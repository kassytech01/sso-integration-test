package jp.co.nikkeibp.techdev.sso.spec
import geb.Page

class NikkeiIdLoungePage extends Page {

	static at = {
		print "url:" + getDriver().currentUrl
//		${url}.text().startsWith(fqdn)
		title == "日経ＩＤ ： 日経ＩＤの確認"
	}

	static content = {
		//		link { linkText -> $(".uri-list a", text: linkText) }
		//		loginState { $("#login-state p", text: startsWith("Login:")).children("span") }
	}
}
