package page
import geb.Page

class SsoLoginCompletePage extends Page {

	String _title

	static content = {
		title == "ログイン成功　：　日経BP社"
		headLineText { $(".headline .txt") }
		gotoServiceButton { $("form ul li.btn_next input")}
	}

	static at = {
		title == "ログイン成功　：　日経BP社"
		headLineText.text() == "ログイン成功"
	}
}
