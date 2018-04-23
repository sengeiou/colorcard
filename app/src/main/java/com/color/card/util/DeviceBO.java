package com.ipudong.pdmapi.service.biz;

import org.apache.commons.lang.StringUtils;

import com.ipudong.pdserver.util.ArrayUtil;

public class DeviceBO {
	
	private String deviceId;
	
	private String ip;
	
	private String screen; //分辩率
	
	private String brand; //品牌
	
	private String pattern; //型号
	
	private String domain; //域
	//pdwx||1||375*627||ios||4||5||www.1.cn
	
	public DeviceBO(String code) {
		String[] arr = StringUtils.split(code, "||");
		if (ArrayUtil.isNotEmpty(arr) && arr.length > 3 ) { 
			this.deviceId = arr[0];
			this.ip = arr[1];
			this.screen = arr[2];
			this.brand = arr[3];
			this.pattern = ( arr.length > 4) ? arr[4] : null;
			this.domain=  ( arr.length > 5) ? arr[5] : null;
			
		}
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getIp() {
		return ip;
	}

	public String getScreen() {
		return screen;
	}

	public String getBrand() {
		return brand;
	}

	public String getPattern() {
		return pattern;
	}

	public String getDomain() {
		return domain;
	}

	

}
