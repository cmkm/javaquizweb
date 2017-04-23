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
@Named(value = "questions")
@Dependent
public class Questions {
    
    public List<QuestionBean> questions = new ArrayList<>();

    /**
     * Creates a new instance of Questions
     */
    public Questions() {
        this.questions.add(new QuestionBean(1, "test questoin?", "foo", "bar", 
            "baz", "foo2", "bar2", "A", "this is a hint"));
        this.questions.add(new QuestionBean(2, "hello world?", "foo", "bar", 
            "baz", "foo2", "bar2", "A", "this is a hint"));
    }
    
    public List<QuestionBean> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<QuestionBean> questions) {
        this.questions = questions;
    }
    
}
