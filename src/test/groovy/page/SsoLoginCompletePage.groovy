package page
import geb.Page

class SsoLoginCompletePage extends Page {

	String _title

	static at = {
		title == _title
	}
}
