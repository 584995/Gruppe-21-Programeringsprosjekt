package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static int MARGIN = 50;
	
	private static int WIDTH = 3;
	private static int BARHEIGHT = 200; // assume no speed above 200 km/t

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	
	}

	public void run() {

		int N = gpscomputer.speeds().length; // number of data points
		
		makeWindow("Speed profile", 2 * MARGIN + WIDTH * N, 2 * MARGIN + (int) Math.round(GPSUtils.findMax(gpscomputer.speeds())));
		
		showSpeedProfile(MARGIN + (int) Math.round(GPSUtils.findMax(gpscomputer.speeds())), N);
		
	}
	
	public void showSpeedProfile(int ybase, int N) {
		
		System.out.println("Angi tidsskalering i tegnevinduet ...");
		int timescaling = Integer.parseInt(getText("Tidsskalering"));
		int aspeed = (int) Math.round(GPSUtils.speed(gpspoints[0], gpspoints[1]));
		
		setColor(0, 255, 0);
		int line = drawLine(MARGIN, ybase - aspeed, MARGIN + WIDTH * gpscomputer.speeds().length, ybase - aspeed);
		setColor(0, 0, 255);
		
		for (int i = 1; i < gpspoints.length; i++) {
			
				drawLine(MARGIN + WIDTH * i, ybase, MARGIN + WIDTH * i, (ybase - (int) Math.round(GPSUtils.speed(gpspoints[i - 1], gpspoints[i]))));
				pause(timescaling);
			
			double tdist = 0;
			
			for (int p = 0; p < i; p++) {
				
				double temp = GPSUtils.distance(gpspoints[p], gpspoints[p + 1]);
				
				tdist += temp;
				
			}
			
			moveLine(line, MARGIN, ybase - (int) Math.round((tdist / 1000.0) / ((gpspoints[i].getTime() - gpspoints[0].getTime()) / 3600.0)));
			
		}
	
	}
	
}
