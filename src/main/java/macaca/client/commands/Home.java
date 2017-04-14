package macaca.client.commands;

import com.alibaba.fastjson.JSONObject;

import macaca.client.common.DriverCommand;
import macaca.client.common.MacacaDriver;
import macaca.client.common.Utils;

public class Home {
	
	private MacacaDriver driver;
	private Utils utils;

	public Home(MacacaDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
	}

	public Object backToHome() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sessionId", driver.getSessionId());
		return utils.request("POST", DriverCommand.Home, jsonObject);
	}
}
