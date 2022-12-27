package com.group31.scms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group31.scms.model.CasePaper;

public interface CasePaperRepository extends JpaRepository<CasePaper, Long> {
    List<CasePaper> findByCasePaperNumberContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String casePaperNumber,String firstName, String lastName,Pageable pageable);
    
    List<CasePaper> findByCasePaperNumberContainingIgnoreCase(String casePaperNumber);
    
    @Query(value = "SELECT MONTH(created_on) month,YEAR(created_on) year,COUNT(SYS_CASE_PAPER_ID)  count FROM      case_paper  WHERE     YEAR(created_on) in (extract(year from Current_date),(extract(year from \r\n"
    		+ " Current_date)-1))  GROUP BY  YEAR(created_on),MONTH(created_on) order by YEAR(created_on) desc,MONTH(created_on) desc",nativeQuery = true)
    List<CasePaperCount> getCasePapersCount();
    
    @Query(value = "select DATE_FORMAT(created_on, 'MM-DD-YYYY') date,count(SYS_CASE_PAPER_ID) count from case_paper\r\n"
    		+ "where created_on >= Current_date-7\r\n"
    		+ "group by DATE_FORMAT(created_on, 'MM-DD-YYYY')\r\n"
    		+ "order by DATE_FORMAT(created_on, 'MM-DD-YYYY') desc",nativeQuery = true)
    List<WeeklyPaperCount> getWeeklyPapersCount();
    
    @Query(value = "select age, count(SYS_CASE_PAPER_ID) count from case_paper\r\n"
    		+ "group by age\r\n"
    		+ "order by age desc",nativeQuery = true)
    List<CasePaperByAge> getCasePapersByAge();
    
}
