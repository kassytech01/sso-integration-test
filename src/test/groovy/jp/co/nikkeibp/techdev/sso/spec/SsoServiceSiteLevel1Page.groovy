package jp.co.nikkeibp.techdev.sso.spec
import geb.Page

class SsoServiceSiteLevel1Page extends Page {

	static url = "nbo"

	static at = { title == "Service Site Mock" }

	static content = {
		links { $(".uri-list a") }
		loginState { $("#login-state p", text: "Login: ").children("span") }
	}
}
