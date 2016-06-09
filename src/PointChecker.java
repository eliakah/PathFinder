import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by eliakah on 3/31/2016.
 * this class runs a php script that checks google map's
 * pixel at a certain coordinate
 */
public class PointChecker {
    /**
     * Execute int.
     *
     * @param lat the lat
     * @param lng the lng
     * @return the int
     * @throws IOException the io exception
     */


    public int execute(double lat, double lng) throws IOException {
        URL link = new URL("http://localhost/PointChecker/src/com/company/index.php?latLong="+lat+","+lng); //creates the link with the lat and long as arguments for script
        URLConnection connection = link.openConnection();  //runs the php script online
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));//sets up to get output from script
        String inputLine; //string that will contain the returned value from the script

        while ((inputLine = in.readLine()) != null) //grabs the output from the script & puts it in the string
            return Integer.parseInt(inputLine);//returns result as integer
        in.close();
        return 0;
    }


    /**
     * On segment boolean.
     *
     * @param p the p
     * @param q the q
     * @param r the r
     * @return the boolean
     */
    public boolean onSegment(Point p, Point q, Point r)
        {
            if (q.latitude <= Math.max(p.latitude, r.latitude) && q.latitude >= Math.min(p.latitude, r.latitude)
                    && q.longitude <= Math.max(p.longitude, r.longitude) && q.longitude >= Math.min(p.longitude, r.longitude))
                return true;
            return false;
        }

    /**
     * Orientation int.
     *
     * @param p the p
     * @param q the q
     * @param r the r
     * @return the int
     */
    public int orientation(Point p, Point q, Point r)
        {
            int val = (int)((q.longitude - p.longitude) * (r.latitude - q.latitude) - (q.latitude - p.latitude) * (r.longitude - q.longitude));

            if (val == 0)
                return 0;
            return (val > 0) ? 1 : 2;
        }

    /**
     * Do intersect boolean.
     *
     * @param p1 the p 1
     * @param q1 the q 1
     * @param p2 the p 2
     * @param q2 the q 2
     * @return the boolean
     */
    public boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
        {

            int o1 = orientation(p1, q1, p2);
            int o2 = orientation(p1, q1, q2);
            int o3 = orientation(p2, q2, p1);
            int o4 = orientation(p2, q2, q1);

            if (o1 != o2 && o3 != o4)
                return true;

            if (o1 == 0 && onSegment(p1, p2, q1))
                return true;

            if (o2 == 0 && onSegment(p1, q2, q1))
                return true;

            if (o3 == 0 && onSegment(p2, p1, q2))
                return true;

            if (o4 == 0 && onSegment(p2, q1, q2))
                return true;

            return false;
        }

    /**
     * Is inside boolean.
     *
     * @param polygon the polygon
     * @param p       the p
     * @return the boolean
     */
    public boolean isInside(ArrayList<Point> polygon, Point p)
        {
            int n = polygon.size(); 
            int INF = 10000;
            if (n < 3)
                return false;

            Point extreme = new Point(INF, p.longitude);

            int count = 0, i = 0;
            do
            {
                int next = (i + 1) % n;
                if (doIntersect(polygon.get(i), polygon.get(next), p, extreme))
                {
                    if (orientation(polygon.get(i), p, polygon.get(next)) == 0)
                        return onSegment(polygon.get(i), p, polygon.get(next));

                    count++;
                }
                i = next;
            } while (i != 0);

            return (count & 1) == 1 ? true : false;
        }



}
