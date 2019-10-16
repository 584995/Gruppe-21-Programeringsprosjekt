package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		
		this.gpspoints = gpspoints;
		
	}
	
	public GPSPoint[] getGPSPoints() {
		
		return gpspoints;
		
	}
	
	public double totalDistance() {

		double sum = 0;
		
		for (int i = 1; i < gpspoints.length; i++) {
			
			sum += GPSUtils.distance(gpspoints[i - 1], gpspoints[i]);
			
		}

		return sum;
		
	}

	public double totalElevation() {

		double[] elevations = GPSUtils.getElevations(gpspoints);
		double sum = 0;

		for (int i = 1; i < elevations.length; i++) {
			
			if (elevations[i - 1] < elevations[i]) {
				
				sum += elevations[i] - elevations[i-1];
				
			}
			
		}
		
		return sum;

	}

	public int totalTime() {

		return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();

	}
	
	public int totalTimeFlex(int i) {

		return gpspoints[i].getTime() - gpspoints[0].getTime();

	}

	public double[] speeds() {
		
		double[] speeds = new double[gpspoints.length - 1];
		
		for (int i = 1; i < gpspoints.length; i++) {
			
			speeds[i - 1] = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);
			
		}
		
		return speeds;

	}
	
	public double maxSpeed() {
		
		return GPSUtils.findMax(speeds());
		
	}

	public double averageSpeed() {

		return (totalDistance() / 1000.0) / (totalTime() / 3600.0);
		
	}

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	public double kcal(double weight, int secs, double speed) {

		double mph = speed * MS;
		double met = 0;
		
		// MET: Metabolic equivalent of task angir (kcal x kg^-1 x h^-1)
		
		if (mph < 10) {met = 4.0;} else
		if (mph < 12) {met = 6.0;} else
		if (mph < 14) {met = 8.0;} else
		if (mph < 16) {met = 10.0;} else
		if (mph < 20) {met = 12.0;} else {met = 16.0;}
		
		return met / Math.pow(weight, -1) / Math.pow(secs / 3600.0, -1);
		
		
	}

	public double totalKcal(double weight) {
		
		double sum = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			
			sum += kcal(weight, gpspoints[i].getTime() - gpspoints[i - 1].getTime(), GPSUtils.speed(gpspoints[i - 1], gpspoints[i]));
			
		}
		
		return sum;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		int i = 1, j = 0;
		
		if (totalTime() >= 100 * 3600) {	
			
			i = 0;
			j = 99 * 3600 + 99 * 60 + 99;
				
		}
		
		System.out.println("==============================================");
		System.out.println("Total Time     : " + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance : " + GPSUtils.formatDouble(totalDistance() / 1000.0) + " km");
		System.out.println("Total elevation: " + GPSUtils.formatDouble(totalElevation()) + " m");
		System.out.println("Max speed      : " + GPSUtils.formatDouble(maxSpeed()) + " km/t");
		System.out.println("Average speed  : " + GPSUtils.formatDouble(averageSpeed()) + " km/t");
		System.out.println("Energy         : " + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal");
		System.out.println("==============================================");
		
		
	}
	
	public String getStatline(int p) {
		
		if (p == 1) {return "==============================================";}
		if (p == 2) {return "Total Time     : " + GPSUtils.formatTime(totalTime());}
		if (p == 3) {return "Total distance : " + GPSUtils.formatDouble(totalDistance() / 1000.0) + " km";}
		if (p == 4) {return "Total elevation: " + GPSUtils.formatDouble(totalElevation()) + " m";}
		if (p == 5) {return "Max speed      : " + GPSUtils.formatDouble(maxSpeed()) + " km/t";}
		if (p == 6) {return "Average speed  : " + GPSUtils.formatDouble(averageSpeed()) + " km/t";}
		if (p == 7) {return "Energy         : " + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal";}
		if (p == 8) {return "==============================================";}
		
		return null;
		
	}
	
	public double[] climbs() {
		
		double[] climbs = new double[gpspoints.length - 1];
		
		for (int i = 0; i < climbs.length; i++) {
			
			climbs[i] = ((gpspoints[i + 1].getElevation() - gpspoints[i].getElevation()) / GPSUtils.distance(gpspoints[i], gpspoints[i + 1])) * 100;
		
		}
		
		return climbs;
		
	}
	
	public double maxClimbs() {
		
		return GPSUtils.findMax(climbs());
		
	}

}
