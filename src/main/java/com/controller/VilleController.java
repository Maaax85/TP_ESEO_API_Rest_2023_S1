package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.VilleBLO;

@RestController
public class VilleController {

	@Autowired
	VilleBLO villeBLOService;

	@RequestMapping(value = "/ville", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestParam(required = false, value = "codePostal") String codePostal) {
		System.out.println("get : " + codePostal);
		
		return villeBLOService.getInfoVilles(codePostal);
	}

}
