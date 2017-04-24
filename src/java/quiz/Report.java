/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.util.HashMap;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author cmkm
 */
@Named(value = "report")
@Dependent
public class Report {
    private int chapterNo;
    private HashMap<String, Integer> results;

    /**
     * Creates a new instance of Report
     */
    public Report() {
        
    }
    
    public int getChapterNo() {
        return this.chapterNo;
    }
    
    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }
    
    public HashMap<String, Integer> getResults() {
        return this.results;
    }
    
    public void setResults(HashMap<String, Integer> results) {
        this.results = results;
    }
    
}
