import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
                   
public class TripPoint {
	private double lat;
	private double lon;
	private int time;
	private static ArrayList<TripPoint>trip;
	
	public TripPoint(int time, double lat, double lon) {
		this.time = time;
		this.lat = lat;
		this.lon = lon;
	}

	public static void readFile(String filename) {
		trip = new ArrayList<TripPoint>();
		String csv = "triplog.csv";
		String line = "";
		String other = ",";
		
		trip.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split(other);
            	
                int time = Integer.parseInt(parts[0]);
                double latitude = Double.parseDouble(parts[1]);
                double longitude = Double.parseDouble(parts[2]);
                TripPoint tripPoint = new TripPoint(time, latitude, longitude);
                trip.add(tripPoint);
                System.out.println(tripPoint);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
	}

	public Integer getTime() {
		return time;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	public static ArrayList<TripPoint> getTrip() {
		ArrayList<TripPoint> newTrip = new ArrayList<TripPoint>(trip);
		return newTrip;
	}

	public static double haversineDistance(TripPoint a, TripPoint b) {
		final double R = 6371;
		double lat1 = Math.toRadians(a.getLat());;
		double lon1 = Math.toRadians(a.getLon());;
		double lat2 = Math.toRadians(b.getLat());;
		double lon2 = Math.toRadians(b.getLon());;
		
		 double dLat = lat2 - lat1;
		 double dLon = lon2 - lon1;
		 
		 double a1 = Math.sin(dLat / 2) * Math.sin(dLat / 2)
		            + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

		 double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));

		 double distance = R * c;
		 
		 return distance;
	}

	public static double totalDistance() {
		double totalDistance = 0;
        for(int i = 0; i < trip.size()-1; i++) {
            totalDistance += haversineDistance(trip.get(i), trip.get(i+1));
        }
		return totalDistance;
	}

	public static double totalTime() {
		return (trip.get(trip.size()-1).getTime())/60.0;
	}

	public static double avgSpeed(TripPoint a, TripPoint b) {
		 double c = (a.getTime()/60.0)-(b.getTime()/60.0);
		 double d = haversineDistance(a,b);
		 double average = Math.abs(d/c);
		 return average;
	}
}
