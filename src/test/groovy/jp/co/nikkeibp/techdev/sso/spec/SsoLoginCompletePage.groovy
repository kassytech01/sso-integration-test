package jp.co.nikkeibp.techdev.sso.spec
import geb.Page

class SsoLoginCompletePage extends Page {

	String _title

	static at = {
		title == _title
	}
}
