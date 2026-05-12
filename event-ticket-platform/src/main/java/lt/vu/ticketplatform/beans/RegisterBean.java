package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.services.AuthService;

@Named
@RequestScoped
public class RegisterBean {

    @Inject
    private AuthService authService;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String confirmPassword;

    public String register() {
        if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Passwords do not match.",
                            null
                    )
            );

            return null;
        }

        AuthService.RegistrationResult result = authService.register(
                name.trim(),
                surname.trim(),
                email.trim(),
                password
        );

        if (result == AuthService.RegistrationResult.EMAIL_ALREADY_USED) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "An account with this email already exists.",
                            null
                    )
            );

            return null;
        }

        return "/login.xhtml?faces-redirect=true&registered=true";
    }

    public boolean isRegistered() {
        String registered = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("registered");

        return "true".equals(registered);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}