package jp.co.nikkeibp.techdev.sso.spec
import geb.Page

class GebishOrgHomePage extends Page {

    static at = { title == "Geb - Very Groovy Browser Automation" }

    static content = {
        manualsMenu { $("#header-content ul li", 0).module(MenuModule) }
    }
}
