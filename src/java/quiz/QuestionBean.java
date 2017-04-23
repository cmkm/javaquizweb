/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author cmkm
 */
@Named(value = "questionBean")
@SessionScoped
public class QuestionBean implements Serializable {
    private int questionNo;
    private int chapterNo;
    private String questionText;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String choiceE;
    private TreeMap<String, String> answers = new TreeMap<>();
    private String answerKey;
    private String hint;
    private String selectedAnswerSingle;
    private ArrayList<String> selectedAnswersMulti = new ArrayList<>();
    private Boolean isCorrect;
    private boolean attempted;

    /**
     * Creates a new instance of QuestionBean
     */
    
    public QuestionBean() {
        
    }
    
    public QuestionBean(int questionNo, String questionText, String choiceA, 
            String choiceB, String choiceC, String choiceD, String choiceE, 
            String answerKey, String hint) {
        this.questionNo = questionNo;
        this.questionText = questionText;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.choiceE = choiceE;
        this.answers.put("A", "A. " + choiceA);
        this.answers.put("B", "B. " + choiceB);
        this.answers.put("C", "C. " + choiceC);
        this.answers.put("D", "D. " + choiceD);
        this.answers.put("E", "E. " + choiceE);
        this.answerKey = answerKey;
        this.hint = hint;
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Bean constructed:\n" + this.toString());
    }
    
    public int getQuestionNo() {
        return this.questionNo;
    }
    
    public int getChapterNo() {
        return this.chapterNo;
    }
    
    public String getQuestionText() {
        return this.questionText;
    }
    
    public String getChoiceA() {
        return this.choiceA;
    }
    
    public String getChoiceB() {
        return this.choiceB;
    }
        
    public String getChoiceC() {
        return this.choiceC;
    }
    
    public String getChoiceD() {
        return this.choiceD;
    }
    
    public String getChoiceE() {
        return this.choiceE;
    }
    
    public Map getAnswers() {
        return this.answers;
    }
    
    public String getSelectedAnswerSingle() {
        return this.selectedAnswerSingle;
    }
    
    public void setSelectedAnswerSingle(String selectedAnswerSingle) {
        this.selectedAnswerSingle = selectedAnswerSingle;
    }
    
    public List getSelectedAnswersMulti() {
        return this.selectedAnswersMulti;
    }
    
    public void setSelectedAnswersMulti(ArrayList selectedAnswersMulti) {
        this.selectedAnswersMulti = selectedAnswersMulti;
    }
    
    public Boolean getIsCorrect() {
        return this.isCorrect;
    }
    
    public String stringifySelectedAnswersMulti() {
        String ans = "";
        for (String s: selectedAnswersMulti) {
            ans += s;
        }
        
        return ans;
    }
    
    public String getAnswerKey() {
        return this.answerKey;
    }
    
    public String getHint() {
        return this.hint;
    }
    
    public boolean getAttempted() {
        return this.attempted;
    }
    
    
    // grade an individual question
    // check submitted answer(s) against answer key and return result
    public boolean gradeQuestion() {
        String selectedAnswers;
        this.attempted = true;
        
        if (this.answerKey.length() > 1) {
            selectedAnswers = stringifySelectedAnswersMulti();
        } else {
            selectedAnswers = this.selectedAnswerSingle;
        }
        
        System.out.println("Answers: " + selectedAnswers);
        System.out.println("Correct ones: " + this.answerKey);
        
        if (selectedAnswers.equalsIgnoreCase(this.answerKey)) {
            this.isCorrect = true;
            System.out.println("Got one!");
            return true;
        } else {
            this.isCorrect = false;
            System.out.println("No bueno");
            return false;
        }
    }
    
    @Override
    public String toString() {
        String str = "";
        
        System.out.println("QuestionBean");
        System.out.println("questionNo: " + this.questionNo);
        System.out.println("questionText: " + this.questionText);
        System.out.println("selectedAnswerSingle: " + this.selectedAnswerSingle);
        System.out.println("selectedAnswersMulti:" + this.selectedAnswersMulti);

        
        return str;
    }
    
}
