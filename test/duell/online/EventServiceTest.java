package duell.online;

import java.lang.management.ManagementFactory;
import javax.ejb.embeddable.EJBContainer;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class EventServiceTest {
    
private MBeanServer mbs;
    
    @Before
    public void setup(){
       mbs = ManagementFactory.getPlatformMBeanServer(); 
    }
    
    @Test
    public void test() throws Exception {
        final int PAGE_SIZE=10;
        Event event;
    EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer() ;
    EventService service = (EventService)container.getContext().lookup("java:global/classes/EventService");
        
        for (int i=0;i<2*PAGE_SIZE;i++){
            event=new Event();
            event.setName("Named Event: "+i);
            event.setType(i%2==1?EventType.GAMEEVENT:EventType.USEREVENT);
            
            Long result = service.store(event);
            assertTrue(result>0);
        }
        
        String resultName = service.listing(1, PAGE_SIZE);
        assertNotNull(resultName);
        
        ObjectName name=new ObjectName(resultName);
        ObjectInstance oi = mbs.getObjectInstance(name);
        assertNotNull(oi);
        
        if (mbs.isInstanceOf(name, IResult.class.getName())){
            EventType eventType=EventType.GAMEEVENT; 
            Object[] args = new Object[]{eventType};
            String[] sign = new String[]{"gameduell.online.EventType"};
            try{
                Object retList = mbs.invoke(name, "filter", args, sign);
                fail("expecting JMX MBeanServer.invoke issue");
            }catch(ReflectionException ex){}

            args = new Object[]{eventType.toString()};
            sign = new String[]{"java.lang.String"};
            Object retList = mbs.invoke(name, "filterAsString", args, sign);
            
    
            Object retFilter = mbs.invoke(name, "showFilters", new Object[0], new String[0]);
            assertTrue(eventType.toString()==retFilter.toString());
        }
    }
    }

