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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author cmkm
 */
@Named(value = "data")
@ManagedBean(name = "data")
@SessionScoped
public class Data implements Serializable {
    private int selectedChapter;
    private ArrayList<QuestionBean> questions;

    @ManagedProperty(value="#{login.username}")
    private String username;
    
    private String hostname;


    
//    @PostConstruct
//    public void init() {
//        populateQuestions((Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedChapter"));
//    }
    
    public void setSelectedChapter(int chapter) {
        selectedChapter = chapter;
        System.out.println("chapter: " + selectedChapter);
        populateQuestions(chapter);
    }
    
    public int getSelectedChapter() {
        return this.selectedChapter;
    }
    
    // populate list of questions based on selected chapter
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
    
    public String getUsername() {
        return this.username;
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
                continue;
            }
            
            questionsSubmitted++;
            isCorrect = q.gradeQuestion();
            recordAttempt(q);
            
            if (isCorrect) {
                questionsCorrect++;
            }
        }
        
        results.put("Total", questionsSubmitted);
        results.put("Correct", questionsCorrect);
        
        return results;
    }
    
    public void recordAttempt(QuestionBean question) {
        Connection connection = DatabaseController.getConnection();
        
        try {
            // check if there is an existing attempt to update
            PreparedStatement checkps = connection.prepareStatement(
                    "select * from intro10e where chapterNo = ? and " + 
                    "questionNo = ? and username = ?");
            // insert a new attempt
            PreparedStatement newps = connection.prepareStatement(
                    "insert into intro10e (chapterNo, questionNo, isCorrect, time, " + 
                    "hostName, answerA, answerB, answerC, answerD, answerE, " + 
                    "username) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            // update an existing attempt
            PreparedStatement updateps = connection.prepareStatement(
                    "update intro10e set isCorrect = ?, time = ?, hostName = ?, " + 
                    "answerA = ?, answerB = ?, answerC = ?, answerD = ?, " + 
                    "answerE = ? where chapterNo = ? and questionNo = ? and username = ?");
            
            checkps.setInt(1, getSelectedChapter());
            checkps.setInt(2, question.getQuestionNo());
            checkps.setString(3, getUsername());
            ResultSet checkrs = checkps.executeQuery();
            Date date = new Date();
            String answerSingle = question.getSelectedAnswerSingle();
            List<String> answersMulti = question.getSelectedAnswersMulti();
            String answersString = answersMulti.isEmpty() ? answerSingle : question.stringifySelectedAnswersMulti();
            Timestamp time = new Timestamp(date.getTime());
                    
            if (checkrs.next()) {
                // update
                updateps.setBoolean(1, question.getIsCorrect());
                updateps.setTimestamp(2, time);
                updateps.setString(3, hostname);
                updateps.setInt(9, selectedChapter);
                updateps.setInt(10, question.getQuestionNo());
                updateps.setString(11, username);
                
                if (answersString.length() == 1) {
                    // single
                    updateps.setBoolean(4, answerSingle.equalsIgnoreCase("A"));
                    updateps.setBoolean(5, answerSingle.equalsIgnoreCase("B"));
                    updateps.setBoolean(6, answerSingle.equalsIgnoreCase("C"));
                    updateps.setBoolean(7, answerSingle.equalsIgnoreCase("D"));
                    updateps.setBoolean(8, answerSingle.equalsIgnoreCase("E"));

                } else {
                    // multi
                    updateps.setBoolean(4, answersString.contains("A"));
                    updateps.setBoolean(5, answersString.contains("B"));
                    updateps.setBoolean(6, answersString.contains("C"));
                    updateps.setBoolean(7, answersString.contains("D"));
                    updateps.setBoolean(8, answersString.contains("E"));
                }

                System.out.println(updateps);
                int updaters = updateps.executeUpdate();

            } else {
                // insert
                newps.setInt(1, selectedChapter);
                newps.setInt(2, question.getQuestionNo());
                newps.setBoolean(3, question.getIsCorrect());
                newps.setTimestamp(4, time);
                newps.setString(5, hostname);
                newps.setString(11, username);
                
                if (answersString.length() == 1) {
                    // single
                    newps.setBoolean(4, answerSingle.equalsIgnoreCase("A"));
                    newps.setBoolean(5, answerSingle.equalsIgnoreCase("B"));
                    newps.setBoolean(6, answerSingle.equalsIgnoreCase("C"));
                    newps.setBoolean(7, answerSingle.equalsIgnoreCase("D"));
                    newps.setBoolean(8, answerSingle.equalsIgnoreCase("E"));
                
                } else {
                    // multi        
                    newps.setBoolean(6, answersString.contains("A"));
                    newps.setBoolean(7, answersString.contains("B"));
                    newps.setBoolean(8, answersString.contains("B"));
                    newps.setBoolean(9, answersString.contains("C"));
                    newps.setBoolean(10, answersString.contains("D"));
                }
                
                System.out.println(newps);
                int newrs = newps.executeUpdate();

            }
        } catch (SQLException ex) {
            System.out.println("DB issue: " + ex.getMessage());
        } finally {
            DatabaseController.close(connection);
        }
    }
    
}
