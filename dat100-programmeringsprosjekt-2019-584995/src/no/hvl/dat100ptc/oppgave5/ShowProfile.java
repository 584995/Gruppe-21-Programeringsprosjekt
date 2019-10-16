package no.hvl.dat100ptc.oppgave5;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowProfile extends EasyGraphics {

	private static int MARGIN = 50;		// margin on the sides 
	
	private static int WIDTH = 3; 	// width between datapoints
	
	private GPSPoint[] gpspoints;

	public ShowProfile() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		GPSComputer gpscomputer =  new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}

	public static void main(String[] args) {
		
		launch(args);
	
	}

	public void run() {

		int N = gpspoints.length; // number of data points

		makeWindow("Height profile", 2 * MARGIN + WIDTH * N, 2 * MARGIN + (int) Math.round(GPSUtils.findMax(GPSUtils.getElevations(gpspoints))));

		showHeightProfile(MARGIN + (int) GPSUtils.findMax(GPSUtils.getElevations(gpspoints))); // top margin + height of drawing area
	}

	public void showHeightProfile(int ybase) {

		for (int i = 0; i < gpspoints.length; i++) {
			
			if (gpspoints[i].getElevation() > 0) {
			
				drawLine(MARGIN + WIDTH * i, ybase, MARGIN + WIDTH * i, (ybase - (int) Math.round(gpspoints[i].getElevation())));
			
			}
			
		}
		
	}
	
}
