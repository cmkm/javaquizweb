/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author cmkm
 */
@Named(value = "chapters")
@Dependent
public class Chapters {
    
    private List<ChapterBean> chapters = new ArrayList<ChapterBean>();

    /**
     * Creates a new instance of Chapters
     */
    public Chapters() {
        this.chapters.add(new ChapterBean(1, "Test"));
    }
    
    private void setupChapters() {
        
    }
    
    public List<ChapterBean> getChapters() {
        return chapters;
    }
    
    public void setChapters(List<ChapterBean> chapters) {
        this.chapters = chapters;
    }
    
}
