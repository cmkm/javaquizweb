package quiz;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cmkm
 *
 * http://stackoverflow.com/a/7354975
 * 
 **/

@FacesConverter(forClass=QuestionBean.class)
public class QuestionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        
        Data data = context.getApplication().evaluateExpressionGet(context, "#{data}", Data.class);
        
        for (QuestionBean question: data.getQuestions()) {
            if (question.getQuestionNo() == Integer.valueOf(value)) {
                return question;
            }
        }
        
        throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to QuestionBean", value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (value instanceof QuestionBean) ? ((QuestionBean) value).getQuestionNo() + "" : null;
    }
    
}
