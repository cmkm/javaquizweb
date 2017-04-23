/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author cmkm
 */
@Named(value = "questionBean")
@Dependent
public class QuestionBean {
    private int questionNo;
    private String questionText;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String choiceE;
    private String answerKey;
    private String hint;

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
        this.answerKey = answerKey;
        this.hint = hint;
    }
    
    public int getQuestionNo() {
        return this.questionNo;
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
    
    public String getAnswerKey() {
        return this.answerKey;
    }
    
    public String getHint() {
        return this.hint;
    }
    
    public boolean gradeQuestion(String answers) {
        return (answers.equals(this.answerKey));
    }
    
}
