package com.avatarmind.msg.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.avatarmind.msg.entity.Msg;
import com.avatarmind.msg.service.MsgServcie;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/msg")
public class MessageController {

	@Autowired
	private MsgServcie msgServcie;
	
	public boolean flag = false;
	public final String CONSUMES = "application/json";
	public final String CODE_000 = "000";
	public final String CODE_000_MSG = "Success";

	public final String CODE_001 = "001";
	public final String CODE_001_MSG = "Unknown error";
	public final String CODE_002 = "002";
	public final String CODE_002_MSG = "Parameter is not complete";
	public final String CODE_003 = "003";
	public final String CODE_003_MSG = "Account or password error";
	public final String CODE_004 = "004";
	public final String CODE_004_MSG = "Did not find the relevant information";
	public final String CODE_005 = "005";
	public final String CODE_005_MSG = "Can not be added repeatedly";
	public final String CODE_006 = "006";
	public final String CODE_006_MSG = "id conversion failed";
	public final String CODE_007 = "007";
	public final String CODE_007_MSG = "Sn not exist";
	public final String CODE_008 = "008";
	public final String CODE_008_MSG = "Update error";

@RequestMapping("/toLogin.do")
public String toMsg(Model model) {
		model.addAttribute("message", "Hello Spring MVC!!!");  
		return "msg";
	}


@RequestMapping(value="/sendMsg", method= RequestMethod.POST, consumes = CONSUMES)
public void sendMsg(@RequestBody Map<String,String> form,HttpServletResponse response){
  JSONObject json = new JSONObject();
  String phones = form.get("phone");
  String content = form.get("content");
   Msg msg = new Msg();
   
   String[] phoneArr = phones.split(",");
   for(int i=0;i<phoneArr.length;i++){
	  String phone = phoneArr[i];
	  msg.setContent(content);
	  msg.setSendTime(new Date());
	  msg.setPhone(phone);
	  int num = msgServcie.insertMsg(msg);
	    
   }
   
      json.put("resultCode",CODE_003);
      json.put("msg",CODE_000_MSG);
      outputJson(response,json);
      return;
    

   
}

private void outputJson(HttpServletResponse response, JSONObject json) {
  response.setCharacterEncoding("UTF-8");
  response.setContentType("text/javascript;charset=UTF-8");
  response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
  response.addHeader("Cache-Control", "post-check=0, pre-check=0");
  response.setHeader("Pragma", "no-cache");
  try {
    PrintWriter out = response.getWriter();
    try {
      out.write(json.toString());
    } finally {
      out.close();
    }
  } catch (IOException e) {
    e.printStackTrace();
  }
}

public void exceptionOut(JSONObject json,HttpServletResponse response){
	json.put("resultCode", CODE_001);
	json.put("msg",CODE_001_MSG);
	outputJson(response,json);
	return;
}

public void lackParamOut(JSONObject json,HttpServletResponse response){
	json.put("resultCode", CODE_000);
	json.put("msg",CODE_002_MSG);
	outputJson(response,json);
	return;
}
 
}
