package jp.co.nikkeibp.techdev.sso.spec
import geb.Page

class SsoServiceSiteLevel1Page extends Page {

	static at = {
		title == "Service Site Mock"
		requestCookieState.text().matches("None")
	}

	static content = {
		link { linkText -> $(".uri-list a", text: linkText) }
		loginState { $("#login-state p", text: startsWith("Login:")).children("span") }
		requestCookieState { $("#request-cookie-state p", text: startsWith("ssoa:")).children("span") }
	}
}
