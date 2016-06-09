import java.util.HashMap;
import java.util.Map;

/**
 * Created by eliakah on 3/31/2016.
 * This class is the class of points holding a lat and a long value
 * it also corrects its lat and long if they go off bound
 */
public class Point {
    /**
     * The Maxlat.
     */
    final Double MAXLAT = 85.0;
    /**
     * The Minlat.
     */
    final Double MINLAT = -85.05115;
    /**
     * The Maxlng.
     */
    final Double MAXLNG = 180.0;
    /**
     * The Minlng.
     */
    final Double MINLNG = -180.0;

    /**
     * The Edges.
     */
    Map<Point, Double> edges = new HashMap<>();
    /**
     * The Latitude.
     */
    double latitude, /**
     * The Longitude.
     */
    longitude;

    /**
     * Instantiates a new Point.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.longitude = correctCoord(longitude, MINLNG, MAXLNG);
        this.latitude = correctCoord(latitude, MINLAT, MAXLAT);
    }

    private double correctCoord(double coord, double min, double max) {
        if (coord < min) {
            coord = coord + (-min);
            coord = max + coord;
        }

        if (coord > max) {
            coord = coord - max;
            coord = min + coord;
        }

        return coord;

    }


    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Get coordinate string.
     *
     * @return the string
     */
    public String getCoordinate() {
        return ("" + latitude + "," + longitude);
    }

    /**
     * Add edge.
     *
     * @param p the p
     * @param w the w
     */
    public void addEdge(Point p, double w) {
        if (!edges.containsKey(p)) {
            edges.put(p, w);
            p.addEdge(new Point(latitude, longitude), w);
        }

    }

    /**
     * Get edges map.
     *
     * @return the map
     */
    public Map<Point, Double> getEdges() {
        return edges;
    }


}
