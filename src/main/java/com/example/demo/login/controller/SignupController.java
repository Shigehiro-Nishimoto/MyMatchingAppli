package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

@Controller
public class SignupController {

	private Map <String, String> radioSex;
	private Map <String, String> initRadioSex() {
		
	Map <String, String > radio = new LinkedHashMap<>();

	radio. put(" 男", "true");
	radio. put(" 女", "false");
	
	return radio;
	
	}
}