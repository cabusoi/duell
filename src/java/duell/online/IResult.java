package duell.online;

import java.util.List;
import javax.management.MXBean;

@MXBean
public interface IResult{
    EventType showFilters();
    List<Event> filter(EventType type);
    List<Event> filterAsString(String type);
}
