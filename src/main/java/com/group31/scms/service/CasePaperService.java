package com.group31.scms.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.group31.scms.model.CasePaper;

public interface CasePaperService {

	CasePaper saveCasePaper(CasePaper casePaper);

    List<CasePaper> getAllCasePapers(JSONObject obj);
    
    long getCasePapersCount();

    CasePaper getCasePaperByID(Long caseID);

    void downloadCasePaperByID(Long caseID);
    
    Map<String,Object> getDashboardValues();
}
