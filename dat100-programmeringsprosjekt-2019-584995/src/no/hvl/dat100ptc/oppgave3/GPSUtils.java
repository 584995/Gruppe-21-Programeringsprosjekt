package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max = da[0]; 
		
		for (double d : da) {
			
			if (d > max) {
				
				max = d;
			
			}
		
		}
		
		return max;
		
	}

	public static double findMin(double[] da) {

		double min = da[0]; 
		
		for (double d : da) {
			
			if (d < min) {
				
				min = d;
				
			}
			
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latitudes = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			
			latitudes[i] = gpspoints[i].getLatitude();
			
		}
		
		return latitudes;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitudes = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			
			longitudes[i] = gpspoints[i].getLongitude();
			
		}
		
		return longitudes;

	}
	
	public static double[] getElevations(GPSPoint[] gpspoints) {

		double[] elevations = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			
			elevations[i] = gpspoints[i].getElevation();
			
		}
		
		return elevations;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double latitude1 = Math.toRadians(gpspoint1.getLatitude());
		double latitude2 = Math.toRadians(gpspoint2.getLatitude());
		
		double latdif = Math.toRadians(gpspoint2.getLatitude() - gpspoint1.getLatitude());
		double longdif = Math.toRadians(gpspoint2.getLongitude() - gpspoint1.getLongitude());
		
		double a = Math.pow(Math.sin(latdif / 2), 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(longdif / 2), 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		return R * c;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double km = distance(gpspoint1, gpspoint2) / 1000.0;
		double hr = (gpspoint2.getTime() - gpspoint1.getTime()) / 3600.0;
		
		return Math.round((km / hr) * 100) / 100.0;

	}

	public static String formatTime(int secs) {
		
		return 	"  " +
				String.format("%02d:", secs / 3600) + 
				String.format("%02d", (secs / 60) % 60) +
				String.format(":%02d", secs % 60);

	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		
		String q = String.format("%" + TEXTWIDTH + ".2f", d);
		
		return q.substring(0, 7) + "." + q.substring(8, 10);
		
	}
}
