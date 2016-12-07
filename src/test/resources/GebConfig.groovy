/*
 This is the Geb configuration file.
 See: http://www.gebish.org/manual/current/#configuration
 */


import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.CapabilityType

waiting { timeout = 2 }

environments {

	// run via “./gradlew chromeTest”
	// See: http://code.google.com/p/selenium/wiki/ChromeDriver
	chrome {
		driver = { new ChromeDriver() }
	}

	// run via “./gradlew firefoxTest”
	// See: http://code.google.com/p/selenium/wiki/FirefoxDriver
	firefox {
		driver = {
			def PROXY = "proxys.nikkeibp.co.jp:80"
			def NO_PROXY = "localhost.nikkeibp.co.jp"
			def proxy = new org.openqa.selenium.Proxy()
			proxy.setHttpProxy(PROXY)
					.setFtpProxy(PROXY)
					.setSslProxy(PROXY)
					.setNoProxy(NO_PROXY)
			def cap = new DesiredCapabilities()
			cap.setCapability(CapabilityType.PROXY, proxy)
			new FirefoxDriver(cap)
		}
	}

	phantomJs {
		driver = { new PhantomJSDriver() }
	}

}

// To run the tests with all browsers just run “./gradlew test”

baseUrl = "http://localhost.nikkeibp.co.jp:8080/nbo/"
