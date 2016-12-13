package page
import geb.Module

class LinkModule extends Module {

    static content = {
        links { $('.uri-list li a') }
    }
}
