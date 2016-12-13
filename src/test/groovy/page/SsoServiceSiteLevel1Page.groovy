package page
import geb.Page

class SsoServiceSiteLevel1Page extends Page {

	static at = {
		title == "Service Site Mock"
	}

	static content = {
		link { linkText -> $(".uri-list a", text: linkText) }
		loginState { $("#login-state p", text: startsWith("Login:")).children("span") }
		requestCookieState { cookie -> $("#request-cookie-state p", text: startsWith(cookie)).children("span") }
	}
}
