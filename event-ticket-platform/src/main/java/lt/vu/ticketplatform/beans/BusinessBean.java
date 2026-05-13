package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.BusinessDAO;
import lt.vu.ticketplatform.entities.Business;

@Named
@RequestScoped
public class BusinessBean {

    @Inject
    private BusinessDAO businessDAO;

    private Business business;

    @PostConstruct
    public void init() {
        business = businessDAO.findAll().stream().findFirst().orElse(null);
    }

    public Business getBusiness() {
        return business;
    }
}
