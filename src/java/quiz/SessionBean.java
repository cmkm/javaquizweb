package quiz;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cmkm
 * 
 * external resources used: 
 * http://www.journaldev.com/7252/jsf-authentication-login-logout-database-example
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {
    private String hostname;
    private String username;
    private String password;
    private String message;
    private boolean authenticated;
    private int selectedChapter;

    /**
     * Creates a new instance of SessionBean
     */
    public SessionBean() {
        // HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        // this.hostname = origRequest.getRemoteAddr();
        this.username = "Unregistered User";
        this.authenticated = false;
        this.selectedChapter = 0;
    }
    
    public SessionBean(String username, boolean authenticated, 
            int selectedChapter) {

        this.username = username;
        this.authenticated = authenticated;
        this.selectedChapter = selectedChapter;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public boolean isAuthenticated() {
        return this.authenticated;
    }
    
    public int getSelectedChapter() {
        return this.selectedChapter;
    }
    
    public void setSelectedChapter(int chapterNo) {
        this.selectedChapter = chapterNo;
    }
    
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                        .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                        .getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                        .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null)
                return (String) session.getAttribute("userid");
        else
                return null;
    }
    
    public String validateCredentials() {
        boolean valid = LoginController.validate(username, password);
        if (valid) {
            HttpSession session = getSession();
            this.authenticated = true;
            session.setAttribute("username", username);
            session.setAttribute("selectedChapter", 0);
            HttpServletRequest origRequest = 
                    (HttpServletRequest)FacesContext.getCurrentInstance()
                            .getExternalContext().getRequest();
            session.setAttribute("hostname", origRequest.getRemoteAddr());
            return "authenticated";
        } else {
            
            // flash message to user if bad credentials
            FacesContext.getCurrentInstance()
                .addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        "Bad login credentials", 
                        "Please check your username and password"));
            return "login";
        }
    }
    
    public String logout() {
        HttpSession session = getSession();
        session.invalidate();
        return "login";
    }
    
}
