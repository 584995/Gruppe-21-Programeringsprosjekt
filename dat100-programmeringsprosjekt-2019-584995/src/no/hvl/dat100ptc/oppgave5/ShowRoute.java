package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 1600;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		playRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		return MAPXSIZE / (Math.abs(GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints)) - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints)))); //MaxLon - MinLon
		
	}

	// antall y-pixels per breddegrad
	public double ystep() {
		
		return MAPYSIZE / (Math.abs(GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints)) - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints)))); //MaxLat - MinLat
		
	}

	public void showRouteMap(int ybase) {
		
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		for (GPSPoint i : gpspoints) {
			
			fillCircle((int) Math.round(MARGIN + (i.getLongitude() - minlon) * xstep()), (int) Math.round(ybase - (i.getLatitude() - minlat) * ystep()), 2);
			
		}
		
	}

	public void playRoute(int ybase) {
		
		System.out.println("Angi tidsskalering i tegnevinduet ...");
		int timescaling = Integer.parseInt(getText("Tidsskalering"));
		setFont("Courier",20);
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		int timer = 0;
		
		setColor(0, 0, 255);
		int me = fillCircle(0, 0, 3);
		moveCircle(me, (int) Math.round(MARGIN + (gpspoints[0].getLongitude() - minlon) * xstep()), (int) Math.round(ybase - (gpspoints[0].getLatitude() - minlat) * ystep()));
		
		for (int i = 0; i < gpscomputer.speeds().length; i++) {
			
			int timeBetween = (gpspoints[i + 1].getTime() - gpspoints[i].getTime());
			int x1 = (int) Math.round(MARGIN + (gpspoints[i].getLongitude() - minlon) * xstep());
			int x2 = (int) Math.round(MARGIN + (gpspoints[i + 1].getLongitude() - minlon) * xstep());
			int y1 = (int) Math.round(ybase - (gpspoints[i].getLatitude() - minlat) * ystep());
			int y2 = (int) Math.round(ybase - (gpspoints[i + 1].getLatitude() - minlat) * ystep());
			
			//-------------------------------------------------------------------------------------------------------------------
			
			for (int j = 0; j < timeBetween; j++) { //Sekund-teller
				
				pause(1000 / timescaling);
				timer++;
				
				setColor(255, 255, 255);
				fillRectangle(MAPXSIZE - MARGIN + 25, MARGIN - 13, 94, 13);
				setColor(0, 0, 0);
				drawString(GPSUtils.formatTime(timer), MAPXSIZE - MARGIN, MARGIN);
				
				moveCircle(me, x1 + ((x2 - x1) * j / timeBetween), y1 + ((y2 - y1) * j / timeBetween));
				
			}
			
			//-------------------------------------------------------------------------------------------------------------------
			
			double elevation = gpspoints[i + 1].getElevation() - gpspoints[i].getElevation(); //Oppover/nedover bakke
			
			if (elevation > 0) {
				
				setColor(255, 0, 0);
				
			} else {
				
				setColor(0, 255, 0);
				
			}
			
			drawLine(x1, y1, x2, y2);
			
			//-------------------------------------------------------------------------------------------------------------------
			
		}
		
	}
	
	public void showStatistics() {

		int TEXTDISTANCEY = 20;
		int TEXTDISTANCEX = 25;

		setColor(0,0,0);
		setFont("Courier",12);
		
		for (int i = 1; i < 9; i++) {
		
		drawString(gpscomputer.getStatline(i), TEXTDISTANCEX, TEXTDISTANCEY * i);
		
		}
	}

}
