/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author cmkm
 */
@Named(value = "chapterBean")
@Dependent
public class ChapterBean {
    
    private int chapterNo;
    private String chapterName;

    /**
     * Creates a new instance of ChapterBean
     */
    public ChapterBean(int chapterNo, String chapterName) {
        this.chapterNo = chapterNo;
        this.chapterName = chapterName;
    }
    
    public int getChapterNo() {
        return chapterNo;
    }
    
    public String getChapterName() {
        return chapterName;
    }
    
}
