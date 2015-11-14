package duell.online;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Stateless
public class EventService {
    
    @PersistenceContext
    private EntityManager pc;

    public Long store(@NotNull Event event) {
        pc.persist(event);
        return event.getId();
    }

    @EJB
    private ResultMonitoringService monitoringService;
    
    public String listing(@Min(0) int pageNumber, @Min(1)int pageSize) {
        TypedQuery<Event> query = pc.createNamedQuery("findAll", Event.class);
        List<Event> eventsPage = query
                .setFirstResult(pageSize * pageNumber).setMaxResults(pageSize)
                .getResultList();
        return monitoringService.registerResult(new Result(eventsPage));
    }
}
