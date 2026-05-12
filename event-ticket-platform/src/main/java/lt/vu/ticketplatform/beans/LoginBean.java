package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.entities.User;
import lt.vu.ticketplatform.services.AuthService;

import java.util.Optional;

@Named
@RequestScoped
public class LoginBean {

    @Inject
    private AuthService authService;

    @Inject
    private CurrentUserBean currentUserBean;

    private String email;
    private String password;

    public String login() {
        Optional<User> authenticatedUser = authService.authenticate(email, password);

        if (authenticatedUser.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Incorrect email or password.",
                            null
                    )
            );

            return null;
        }

        currentUserBean.logIn(authenticatedUser.get());

        return "/index.xhtml?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}