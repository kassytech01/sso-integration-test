import org.junit.runner.RunWith
import org.junit.runners.Suite

import spec.SsoServiceSiteCommonSpec
import spec.SsoServiceSiteLevel1Spec

@RunWith(Suite)
@Suite.SuiteClasses([
//	SsoServiceSiteCommonSpec,
	SsoServiceSiteLevel1Spec,
])

class TestSuite {
}