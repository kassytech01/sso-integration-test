/*
 This is the Geb configuration file.
 See: http://www.gebish.org/manual/current/#configuration
 */


import org.openqa.selenium.Dimension
import org.openqa.selenium.Proxy
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

waiting { timeout = 2 }
atCheckWaiting = true

environments {

	// run via “./gradlew chromeTest”
	// See: http://code.google.com/p/selenium/wiki/ChromeDriver
	chrome {
		driver = {
			def d = new ChromeDriver()
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}

	// run via “./gradlew firefoxTest”
	// See: http://code.google.com/p/selenium/wiki/FirefoxDriver
	firefox {
		driver = {
			def PROXY = "proxys.nikkeibp.co.jp:80"
			def NO_PROXY = "localhost.nikkeibp.co.jp"
			def proxy = new Proxy()
			proxy.setHttpProxy(PROXY)
					.setFtpProxy(PROXY)
					.setSslProxy(PROXY)
					.setNoProxy(NO_PROXY)
			def cap = new DesiredCapabilities()
			cap.setCapability(CapabilityType.PROXY, proxy)
			def d = new FirefoxDriver(cap)
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}

	phantomJs {
		driver = {
			def d = new PhantomJSDriver()
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}

}

// To run the tests with all browsers just run “./gradlew test”

baseUrl = "http://localhost.nikkeibp.co.jp:8080/"
