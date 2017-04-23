package quiz;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author cmkm
 */
@Named(value = "data")
@SessionScoped
public class Data implements Serializable {
    
    private int selectedChapter;
    private static ArrayList<QuestionBean> questions;
    

    /**
     * Creates a new instance of Data
     */
    public Data() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("selectedChapter", selectedChapter);
        sessionMap.put("questions", questions);
    }
    
//    @PostConstruct
//    public void init() {
//        populateQuestions((Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedChapter"));
//    }
    
    public void setSelectedChapter(int chapter) {
        selectedChapter = chapter;
        System.out.println("imhere");
        System.out.println("chapter: " + selectedChapter);
        populateQuestions(chapter);
    }
    
    public int getSelectedChapter() {
        return this.selectedChapter;
    }
    
    public void populateQuestions(int chapter) {
        ArrayList<QuestionBean> fetchedQuestions = new ArrayList<>();
        Connection connection = DatabaseController.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from intro10equiz where chapterNo = ?");
            ps.setString(1, chapter + "");
            ResultSet rs = ps.executeQuery();
                    
            if (rs.next()) {
                fetchedQuestions.add(new QuestionBean(rs.getInt("questionNo"), 
                        rs.getString("question"), rs.getString("choiceA"), 
                        rs.getString("choiceB"), rs.getString("choiceC"), 
                        rs.getString("choiceD"), rs.getString("choiceE"), 
                        rs.getString("answerKey"), rs.getString("hint")));
            }
            
        } catch (SQLException ex) {
            System.out.println("DB error: " + ex.getMessage());
        } finally {
            DatabaseController.close(connection);
        }
        
        questions = fetchedQuestions;
    } 
    
    public void setQuestions(ArrayList<QuestionBean> questions) {
        this.questions = questions;
    }

    public ArrayList<QuestionBean> getQuestions() {
        return questions;
    }
    
        
    // Handle submission of entire chapter at once
    public HashMap submitChapter() {
        int questionsSubmitted = 0;
        int questionsCorrect = 0;
        boolean isCorrect; 
        HashMap<String, Integer> results = new HashMap<>();
        
        for (QuestionBean q: questions) {
            System.out.println(q.toString());
            
            // no response to question, skip it
            if (q.getSelectedAnswerSingle() == null 
                    && q.getSelectedAnswersMulti().isEmpty()) {
                System.out.println("breaking out");
                continue;
            }
            
            System.out.println("bump");
            questionsSubmitted++;
            isCorrect = q.gradeQuestion();
            
            if (isCorrect) {
                questionsCorrect++;
                System.out.println("Correct?: " + isCorrect);
            }
        }
        
        results.put("Total", questionsSubmitted);
        results.put("Correct", questionsCorrect);
        
        return results;
    }
    
}
