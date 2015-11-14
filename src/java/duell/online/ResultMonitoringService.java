package duell.online;

import java.lang.management.ManagementFactory;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

@Singleton
@Startup
public class ResultMonitoringService {

private MBeanServer mbs;

@PostConstruct
private void setupJMXServer(){
   mbs = ManagementFactory.getPlatformMBeanServer(); 
}

public String registerResult(Object object){
    try {
        ObjectName name = new ObjectName("gameduell:type="+object.getClass().getSimpleName());
        mbs.registerMBean(object, name);
        return name.getCanonicalName();
    } catch (MalformedObjectNameException|InstanceAlreadyExistsException
            |MBeanRegistrationException|NotCompliantMBeanException    ex) {
        throw new RuntimeException(ex);
    }
}
}
