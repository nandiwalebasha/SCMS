package com.group31.scms.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group31.scms.model.CasePaper;
import com.group31.scms.service.CasePaperService;

@RestController
public class CasePaperController {

    @Autowired
    private CasePaperService casePaperService;

    @PostMapping("/casepaper/savecasepaper")
    public CasePaper saveCasePaper(@RequestBody CasePaper casePaper) {
        System.out.println("in controller");
        return casePaperService.saveCasePaper(casePaper);
    }

    @PostMapping("/casepaper/getallcasepapers")
    public Map<String,Object> getAllCasePapers(@RequestParam("searchParams") String searchParams) {
    	JSONObject obj = new JSONObject(searchParams);
    	
    	Map<String,Object> result= new HashMap<>();
    	result.put("data", casePaperService.getAllCasePapers(obj));
    	result.put("count", casePaperService.getCasePapersCount());
        return result;
    }

    @PostMapping("/casepaper/getcasepaperbyid/{id}")
    public CasePaper getCasePaperByID(@PathVariable("id") Long caseID) {
        return casePaperService.getCasePaperByID(caseID);
    }

    @PostMapping("/casepaper/downloadcasepaperbyid/{id}")
    public String downloadCasePaperByID(@PathVariable("id") Long caseID) {
        casePaperService.downloadCasePaperByID(caseID);
        return "file downloaded successfully";
    }
    
    @PostMapping("/casepaper/getdashboardvalues")
    public Map<String,Object> getDashboardValues(@RequestParam("user") String user) {
    	Map<String,Object> result= new HashMap<>();
    	result.put("data", casePaperService.getDashboardValues());
        return result;
    }
}
