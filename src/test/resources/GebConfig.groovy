/*
 This is the Geb configuration file.
 See: http://www.gebish.org/manual/current/#configuration
 */


import org.openqa.selenium.Dimension
import org.openqa.selenium.Proxy
import org.openqa.selenium.Proxy.ProxyType
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

waiting {
	timeout = 5
	retryInterval = 0.3
}

waiting {
	presets {
		slow {
			timeout = 10
			retryInterval = 1
		}
		quick { timeout = 1 }
	}
}

atCheckWaiting = false

environments {

	def PROXY = "proxy2.nikkeibp.co.jp"
	def PROXY_PORT = 80
	def NO_PROXY = "localhost.nikkeibp.co.jp"

	// run via “./gradlew chromeTest”
	// See: http://code.google.com/p/selenium/wiki/ChromeDriver
	chrome {
		driver = {
			Proxy proxy = new Proxy()
			proxy.setHttpProxy(PROXY + ":" + PROXY_PORT)
					.setFtpProxy(PROXY + ":" + PROXY_PORT)
					.setSslProxy(PROXY + ":" + PROXY_PORT)
					.setNoProxy(NO_PROXY)

			DesiredCapabilities capabilities = DesiredCapabilities.chrome()
			capabilities.setCapability(CapabilityType.PROXY, proxy)

			def d = new ChromeDriver(capabilities)
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}

	// run via “./gradlew firefoxTest”
	// See: http://code.google.com/p/selenium/wiki/FirefoxDriver
	firefox {
		driver = {
			FirefoxProfile firefoxProfile = new FirefoxProfile()
			firefoxProfile.setPreference("network.proxy.type", 1)
			firefoxProfile.setPreference("network.http", PROXY)
			firefoxProfile.setPreference("network.http_port", PROXY_PORT)
			firefoxProfile.setPreference("network.proxy.ssl", PROXY)
			firefoxProfile.setPreference("network.proxy.ssl_port", PROXY_PORT)
			firefoxProfile.setPreference("network.proxy.no_proxies_on", NO_PROXY)

			def d = new FirefoxDriver(firefoxProfile)
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}

	phantomJs {
		driver = {
			Proxy proxy = new Proxy()
			proxy.setHttpProxy(PROXY + ":" + PROXY_PORT)
					.setFtpProxy(PROXY + ":" + PROXY_PORT)
					.setSslProxy(PROXY + ":" + PROXY_PORT)
					.setNoProxy(NO_PROXY)

			DesiredCapabilities capabilities = DesiredCapabilities.phantomjs()
			capabilities.setCapability(CapabilityType.PROXY, proxy)

			def d = new PhantomJSDriver(capabilities)
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}

	ie {
		driver = {
			// IE11では、何をやってもNoProxyが効かない（DesiredCapabilitiesで渡っているがNoProxyだけが認識されない）為、システム設定を利用する。
			Proxy proxy = new Proxy()
			proxy.setProxyType(ProxyType.SYSTEM)
			//proxy.setHttpProxy(PROXY + ":" + PROXY_PORT)
			//	.setFtpProxy(PROXY + ":" + PROXY_PORT)
			//	.setSslProxy(PROXY + ":" + PROXY_PORT)
			//	.setNoProxy(NO_PROXY)

			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer()
			// この設定を入れないとIEのZoom設定が100%じゃないと正しく動かないため、Zoom設定自体を無視する。
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true)
			capabilities.setCapability(InternetExplorerDriver.IE_USE_PRE_PROCESS_PROXY, true)
			capabilities.setCapability(CapabilityType.PROXY, proxy)

			def d = new InternetExplorerDriver(capabilities)
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}

	edge {
		driver = {
			Proxy proxy = new Proxy()
			proxy.setHttpProxy(PROXY + ":" + PROXY_PORT)
					.setFtpProxy(PROXY + ":" + PROXY_PORT)
					.setSslProxy(PROXY + ":" + PROXY_PORT)
					.setNoProxy(NO_PROXY)
			DesiredCapabilities capabilities = DesiredCapabilities.edge()
			capabilities.setCapability(CapabilityType.PROXY, proxy)

			def d = new EdgeDriver(capabilities)
			d.manage().window().size = new Dimension(1280, 1024)
			d
		}
	}
}

// To run the tests with all browsers just run “./gradlew test”

baseUrl = "http://localhost.nikkeibp.co.jp:8080/"
