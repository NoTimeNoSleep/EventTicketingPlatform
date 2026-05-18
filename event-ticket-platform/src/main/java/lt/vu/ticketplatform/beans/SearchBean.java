package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.SearchDAO;
import lt.vu.ticketplatform.entities.Event;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@RequestScoped
public class SearchBean {

    @Inject
    private SearchDAO searchDAO;

    private String query;
    private List<Event> events = new ArrayList<>();

    @PostConstruct
    public void init() {
        events = new ArrayList<>();
    }

    public String search() {
        if (query == null || query.trim().isEmpty()) {
            return "/searchResults.xhtml?faces-redirect=true&query=";
        }

        String encodedQuery = URLEncoder.encode(query.trim(), StandardCharsets.UTF_8);
        return "/searchResults.xhtml?faces-redirect=true&query=" + encodedQuery;
    }

    public void loadResults() {
        if (query != null && !query.trim().isEmpty()) {
            events = searchDAO.searchEvents(query.trim());
        } else {
            events = Collections.emptyList();
        }
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Event> getEvents() {
        return events;
    }
}
