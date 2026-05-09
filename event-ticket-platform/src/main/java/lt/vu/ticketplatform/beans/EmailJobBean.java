package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.EmailJobDAO;
import lt.vu.ticketplatform.entities.EmailJob;

import java.util.List;

@Named
@RequestScoped
public class EmailJobBean {

    @Inject
    private EmailJobDAO emailJobDAO;

    public List<EmailJob> getEmailJobs() {
        return emailJobDAO.findAll();
    }
}