package sim;


import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerVm;
import org.cloudbus.cloudsim.Log;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;

public class SchedulerBase
{
    protected static List<Cloudlet> cloudletList;
    protected static List<PowerVm> vmList;
    protected static Datacenter[] datacenter;

    protected static List<PowerVm> createVm(int userId, int vms, double mips[], boolean unified)
    {
        LinkedList<PowerVm> list = new LinkedList<PowerVm>();

        /* VM Parameters */
        long size = 10000; /* VM image size [MB] */
        int ram = 512; /* VM memory [MB] */
        long bw = 1000;
        int pesNumber = 1; /* number of VM cpus */
        String vmm = "Xen"; /* VM name */

        PowerVm[] pvm = new PowerVm[vms];

        for (int i = 0; i < vms; ++i) {
            double currMips = unified ? 1000 : mips[i];
            pvm[i] = new PowerVm(datacenter[i].getId(), userId, currMips, pesNumber, ram, bw, size, 1, vmm, new CloudletSchedulerSpaceShared(), 1);
            list.add(pvm[i]);
        }

        return list;
    }

    protected static List<Cloudlet> createCloudlet(int userId, int cloudlets, int idShift)
    {
        LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

        long fileSize = 300;
        long outputSize = 300;
        int pesNumber = 1;

        UtilizationModel utilizationModel = new UtilizationModelFull();

        Cloudlet[] cloudlet = new Cloudlet[cloudlets];

        for (int i = 0; i < cloudlets; ++i) {
            Random r = new Random();
            // long low = 10;
            // long high = 15000;
            // long length = r.nextInt(high-low) + low;
            long length = r.nextInt(15001);
            cloudlet[i] = new Cloudlet(idShift + i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            /* Setting the owner of these cloudlets */
            cloudlet[i].setUserId(userId);
            // cloudlet[i].setVmId(???); /* TODO */
			list.add(cloudlet[i]);
        }

        return list;
    }

    protected static void printCloudletList(List<Cloudlet> list)
	{
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");

		for (int i = 0; i < size; ++i) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}
	}

}
