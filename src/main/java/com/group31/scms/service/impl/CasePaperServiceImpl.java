package com.group31.scms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.group31.scms.model.CasePaper;
import com.group31.scms.print.DownloadUtil;
import com.group31.scms.repository.CasePaperByAge;
import com.group31.scms.repository.CasePaperCount;
import com.group31.scms.repository.CasePaperRepository;
import com.group31.scms.repository.WeeklyPaperCount;
import com.group31.scms.service.CasePaperService;

@Service
public class CasePaperServiceImpl implements CasePaperService {

	@Autowired
	private CasePaperRepository casePaperRepository;

	@Autowired
	private DownloadUtil downloadUtil;

	public CasePaper saveCasePaper(CasePaper casePaper) {
		System.out.println("Casepaper: "+ casePaper.toString());
		if (casePaper.getSysCasePaperId() != null) {
		System.out.println("Casepaper: "+ casePaper.getSysCasePaperId());
			CasePaper casePaperFromDB = getCasePaperByID(casePaper.getSysCasePaperId());
			casePaper.mergeFollowup(casePaperFromDB);
		}
		System.out.println("Casepaper: before save");
		return casePaperRepository.save(casePaper);
	}

	public List<CasePaper> getAllCasePapers(JSONObject obj) {
		
		int start = obj.optInt("start", 0) ;
		int limit = obj.optInt("limit", 0);
		String casePaperNumber = obj.optString("casePaperNumber", "") ;
		String firstName = obj.optString("firstName", "");
		String lastName = obj.optString("lastName", "");

		Pageable sortedByUpdatedOn = PageRequest.of(start / limit, limit, Sort.by("updatedOn").descending());

		return casePaperRepository
				.findByCasePaperNumberContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
						casePaperNumber, firstName, lastName, sortedByUpdatedOn);
	}

	public long getCasePapersCount() {
		return casePaperRepository.count();
	}

	public CasePaper getCasePaperByID(Long caseID) {
		return casePaperRepository.findById(caseID).get();
	}

	public void downloadCasePaperByID(Long caseID) {
		downloadUtil.generatePDF(getCasePaperByID(caseID));
	}

	@Override
	public Map<String,Object> getDashboardValues() {
		
		List<CasePaperCount> casePaperCounts = casePaperRepository.getCasePapersCount();
		
		Map<String,Object> result= new HashMap<>();
		long totalCasePapers = getCasePapersCount();

		result.put("totalCasePapers", "<div style=\"line-height: 160px;\">"+totalCasePapers+"</div>");
		if(casePaperCounts.size()>0) {
			CasePaperCount countForCurrentMonth = casePaperCounts.get(0);
			result.put("casePapersForCurrentMonth", 
					"<div style=\"font-size:25px;font-style: italic;\">"+getMonthName(countForCurrentMonth.getMonth())+" "+countForCurrentMonth.getYear()+"</div><div>"+countForCurrentMonth.getCount()+"</div>");
		}
		
		if(casePaperCounts.size()>1) {
			CasePaperCount countForLastMonth = casePaperCounts.get(1);
			result.put("casePapersForLastMonth",
					"<div style=\"font-size:25px;font-style: italic;\">"+getMonthName(countForLastMonth.getMonth())+" "+countForLastMonth.getYear()+"</div><div>"+countForLastMonth.getCount()+"</div>");
		}
		
		List<CasePaperCount> casePaperForYear = casePaperCounts;
		if(casePaperCounts.size() > 12) {
			casePaperForYear = casePaperCounts.subList(0, 12);
		}
		
		Map<String,Integer> monthlyCasepapers= new HashMap<>();
		for(CasePaperCount casePaperCount : casePaperForYear) {
			monthlyCasepapers.put(casePaperCount.getMonth()+"-1-"+casePaperCount.getYear()
			, casePaperCount.getCount());
		}
		
		result.put("monthlyCasePapers", monthlyCasepapers);
		
		List<WeeklyPaperCount> weeklyPaperCounts = casePaperRepository.getWeeklyPapersCount();
		Map<String,Integer> weeklyPapers= new HashMap<>();
		for(WeeklyPaperCount casePaperCount : weeklyPaperCounts) {
			weeklyPapers.put(casePaperCount.getDate(), casePaperCount.getCount());
		}
		result.put("weeklyCasePapers", weeklyPapers);
		
		
		List<CasePaperByAge> casePapersByAgeCounts = casePaperRepository.getCasePapersByAge();
		Map<String,Double> casePapersByAge= new HashMap<>();
		casePapersByAge.put("< 20 years", 0.0);
		casePapersByAge.put("21 - 40 years", 0.0);
		casePapersByAge.put("41 - 60 years", 0.0);
		casePapersByAge.put("61 - 80 years", 0.0);
		casePapersByAge.put("> 81 years", 0.0);
		
		if(totalCasePapers > 0) {

			for (CasePaperByAge casePaperCount : casePapersByAgeCounts) {
				if (casePaperCount.getAge() <= 20) {
					casePapersByAge.put("< 20 years", casePapersByAge.get("< 20 years") + casePaperCount.getCount());
				} else if (casePaperCount.getAge() >= 21 && casePaperCount.getAge() <= 40) {
					casePapersByAge.put("21 - 40 years", casePapersByAge.get("21 - 40 years") + casePaperCount.getCount());
				} else if (casePaperCount.getAge() >= 41 && casePaperCount.getAge() <= 60) {
					casePapersByAge.put("41 - 60 years", casePapersByAge.get("41 - 60 years") + casePaperCount.getCount());
				} else if (casePaperCount.getAge() >= 61 && casePaperCount.getAge() <= 80) {
					casePapersByAge.put("61 - 80 years", casePapersByAge.get("61 - 80 years") + casePaperCount.getCount());
				} else if (casePaperCount.getAge() >= 81) {
					casePapersByAge.put("> 81 years", casePapersByAge.get("> 81 years") + casePaperCount.getCount());
				}
			}
			casePapersByAge.put("< 20 years", (double) ((casePapersByAge.get("< 20 years")* 100) / totalCasePapers) );
			casePapersByAge.put("21 - 40 years", (double) ((casePapersByAge.get("21 - 40 years")* 100) / totalCasePapers) );
			casePapersByAge.put("41 - 60 years", (double) ((casePapersByAge.get("41 - 60 years")* 100) / totalCasePapers) );
			casePapersByAge.put("61 - 80 years", (double) ((casePapersByAge.get("61 - 80 years")* 100) / totalCasePapers) );
			casePapersByAge.put("> 81 years", (double) ((casePapersByAge.get("> 81 years")* 100) / totalCasePapers));

		}
		
		result.put("casePapersByAge", casePapersByAge);
		System.out.println("result:: " +result.toString());
		return result;
	}
	
	private String getMonthName(int month) {
		String monthName = "";
		switch (month) {
		case 1:
			monthName = "January";
			break;
		case 2:
			monthName = "February";
			break;
		case 3:
			monthName = "March";
			break;
		case 4:
			monthName = "April";
			break;
		case 5:
			monthName = "May";
			break;
		case 6:
			monthName = "June";
			break;
		case 7:
			monthName = "July";
			break;
		case 8:
			monthName = "August";
			break;
		case 9:
			monthName = "September";
			break;
		case 10:
			monthName = "October";
			break;
		case 11:
			monthName = "November";
			break;
		case 12:
			monthName = "December";
			break;
		}
		return monthName;
	}
}