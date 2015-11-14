package duell.online;

import java.util.ArrayList;
import java.util.List;

public class Result implements IResult{

    private EventType filters;
    private final List<Event> events;
    
    public Result(List<Event> events){
        this.events=events;
    }
    
    @Override
    public EventType showFilters() {
        return filters;
    }

    @Override
    public List<Event> filter(EventType type) {
        filters=type;
        List<Event> afterFilterResult = new ArrayList<>(events.size());
        for(Event e:events){
            if(type==e.getType()){
                afterFilterResult.add(e);
            }
        }
        return afterFilterResult;
    }

    @Override
    public List<Event> filterAsString(String type) {
        return filter(EventType.valueOf(type));
    }
}
