package sim;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEvent;

import java.util.ArrayList;


public class FCFSDatacenterBroker extends DatacenterBroker
{
    public FCFSDatacenterBroker(String name) throws Exception {
        super(name);
    }

    public void scheduleTasksToVms()
    {
        ArrayList<Cloudlet> clist = new ArrayList<Cloudlet>();

        for (Cloudlet cloudlet : getCloudletSubmittedList()) {
            clist.add(cloudlet);
        }

        setCloudletReceivedList(clist);
    }
}