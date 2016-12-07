package jp.co.nikkeibp.techdev.sso.spec
import geb.Module

class LinkModule extends Module {

    static content = {
        links { $('.uri-list li a') }
    }
}
