package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int antall) {

		gpspoints = new GPSPoint[antall];
		
		this.antall = 0;
		
	}

	public GPSPoint[] getGPSPoints() {
		
		return this.gpspoints;
	
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		if (gpspoints.length > antall) {
			
			gpspoints[antall] = gpspoint;
			antall++;
			return true;
			
		} else {
			
			return false;
			
		}
		
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {
		
		if (gpspoints.length > antall) {
			
			gpspoints[antall] = GPSDataConverter.convert(time, latitude, longitude, elevation);
			antall++;
			return true;
			
		} else {
			
			return false;
			
		}
		
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for (GPSPoint i : gpspoints) {
			
			System.out.print(i.toString());
			
		}
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
