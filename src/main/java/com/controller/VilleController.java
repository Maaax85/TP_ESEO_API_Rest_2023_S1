package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.VilleBLO;
import com.dto.Ville;

@RestController
public class VilleController {

	@Autowired
	VilleBLO villeBLOService;

	@RequestMapping(value = "/getVille", method = RequestMethod.GET)
	@ResponseBody
	public Object get(@RequestParam(required = false, value = "codePostal") String codePostal, @RequestParam(required = false, value = "codeCommunal") String codeCommunal) {
		System.out.println("get : " + codePostal + " get : " + codeCommunal);

		return villeBLOService.getInfoVilles(codePostal, codeCommunal);
	}

	@RequestMapping(value = "/addVille", method = RequestMethod.POST)
	@ResponseBody
	public Object post(@RequestBody Ville ville) {
		System.out.println("post : " + ville.getNomCommune());

		villeBLOService.addVille(ville);
		return "La ville " + ville.getNomCommune() + " a été ajoutée";
	}

	@RequestMapping(value = "/editVille", method = RequestMethod.PUT)
	@ResponseBody
	public Object put(@RequestBody Ville ville) {
		System.out.println("put : " + ville.getNomCommune());

		villeBLOService.editVille(ville);
		return "La ville " + ville.getNomCommune() + " a été modifiée";
	}

	@RequestMapping(value = "/deleteVille", method = RequestMethod.DELETE)
	@ResponseBody
	public Object delete(@RequestBody Ville ville) {
		System.out.println("delete : " + ville.getNomCommune());

		villeBLOService.deleteVille(ville);
		return "La ville " + ville.getNomCommune() + " a été supprimée";
	}
	
	@RequestMapping(value = "/flagVille", method = RequestMethod.PUT)
	@ResponseBody
	public Object inhib(@RequestBody Ville ville) {
		System.out.println("inhib/désinhib : " + ville.getNomCommune());

		villeBLOService.inhibVille(ville);
		return "La ville " + ville.getNomCommune() + " a été inhibée/désinhibée";
	}

}
