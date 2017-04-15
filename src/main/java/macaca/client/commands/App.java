package macaca.client.commands;

import com.alibaba.fastjson.JSONObject;

import macaca.client.common.DriverCommand;
import macaca.client.common.MacacaDriver;
import macaca.client.common.Utils;

public class App {
	private MacacaDriver driver;
	private Utils utils;

	public App(MacacaDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
	}

	public Object deactivateApp() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sessionId", driver.getSessionId());
		return utils.request("POST", DriverCommand.DEACTIVATEAPP, jsonObject);
	}
}
