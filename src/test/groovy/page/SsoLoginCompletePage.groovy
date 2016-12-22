package page
import geb.Page

class SsoLoginCompletePage extends Page {

	String _title

	static content = {
		title == "ログイン成功　：　日経BP社"
		headLineText { $(".headline .txt") }
	}

	static at = {
		title == "ログイン成功　：　日経BP社"
		headLineText.text() == "ログイン成功"
	}
}
