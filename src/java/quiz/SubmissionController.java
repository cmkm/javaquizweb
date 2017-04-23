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
@Named(value = "submissionController")
@Dependent
public class SubmissionController {

    /**
     * Creates a new instance of SubmissionController
     */
    public SubmissionController() {
    }
    
    // Handle submission of each question, one at a time
    public boolean submitQuestion(QuestionBean question, String answer) {
        return true;
    }
    
    // Handle submission of entire chapter at once
    public String submitChapter() {
        return "foo";
    }
    
}
