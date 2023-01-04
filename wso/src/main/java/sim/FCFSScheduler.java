package sim;

import sim.SchedulerBase;
// import sim.DatacenterCreator;

import java.util.Calendar;
// import java.text.DecimalFormat;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerVm;
import org.cloudbus.cloudsim.Log;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;


public class FCFSScheduler extends SchedulerBase
{
    public static void main(String[] args) {
		Log.printLine("Starting CloudSimExample8...");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

            /* Create datacenters */
            datacenter = new Datacenter[6];
            for (int i = 0; i < 6; ++i) {
                datacenter[i] = DatacenterCreator.createDatacenter("Datacenter_" + i);
            }

            /* Create broker */
			FCFSDatacenterBroker broker = createBroker("Broker_0");
			int brokerId = broker.getId();

			//Fourth step: Create VMs and Cloudlets and send them to broker
			double mips[] = {500, 1000, 1500, 2000, 2500, 3000};
			boolean un = true;
			vmList = createVm(brokerId, 6, mips, un); //creating 5 vms
			cloudletList = createCloudlet(brokerId, 10, 0); // creating 10 cloudlets

			broker.submitVmList(vmList);
			broker.submitCloudletList(cloudletList);

			// Fifth step: Starts the simulation
			CloudSim.startSimulation();

			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
			// newList.addAll(globalBroker.getBroker().getCloudletReceivedList());

			CloudSim.stopSimulation();

			printCloudletList(newList);

			Log.printLine("CloudSimExample8 finished!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	private static FCFSDatacenterBroker createBroker(String name) throws Exception
	{
		return new FCFSDatacenterBroker(name);
	}
}
