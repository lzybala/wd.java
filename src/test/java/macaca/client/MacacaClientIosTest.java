package macaca.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class MacacaClientIosTest {

	MacacaClient driver = new MacacaClient();
	// the default mock data of request
	JSONObject defaultMockData = new JSONObject();
	
	@Before
	public void setUp() throws Exception {
		defaultMockData.put("value", "");
		defaultMockData.put("status", 0);
		defaultMockData.put("sessionId", "123456");

//		stubFor(any(urlPathMatching(".*/session.*"))
//			.willReturn(aResponse()
//				.withStatus(200)
//				.withHeader("content-type", "application/json")
//				.withBody(defaultMockData.toString())));

		JSONObject desiredCapabilities = new JSONObject();
		desiredCapabilities.put("platformName", "ios");
		desiredCapabilities.put("deviceName", "iPhone");
		desiredCapabilities.put("platformVersion", "10.3.1");
		desiredCapabilities.put("udid", "45feaea59d2908c6c1457469cf692a0ff71bfaf1");
		desiredCapabilities.put("bundleId", "com.nq.mdm");
		desiredCapabilities.put("autoAcceptAlerts", "true");
		driver.initDriver(desiredCapabilities);
	}
	
	@Test
	public void test_case_1() {
		System.out.println("test case #1");
	}


	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
