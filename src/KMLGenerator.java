import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Eliakah kakou on 4/21/2016.
 * This class generates a kml file from nodes and parameters passed at initialization
 */
public class KMLGenerator {

    /**
     * The Start.
     */
    Point start;
    /**
     * The End.
     */
    Point end;
    /**
     * The Nodes.
     */
    ArrayList<Node> nodes ;
    /**
     * The Path.
     */
    ArrayList<Node> path ;
    /**
     * The Poly.
     */
    ArrayList< ArrayList<Point>> poly = new ArrayList<>();

    /**
     * Instantiates a new Kml generator.
     *
     * @param nodes the nodes
     * @param start the start
     * @param end   the end
     * @param path  the end
     * @param poly  the poly
     */
    public  KMLGenerator (ArrayList<Node> nodes, Point start, Point end, ArrayList<Node> path,  ArrayList< ArrayList<Point>> poly){
        this.nodes = nodes; // nodes making up graph
        this.start = start; //starting point
        this.end = end; // destination
        this.path = path; //list of nodes making up path
        this.poly = poly; //list of points making up polygon
    }


    /**
     * Instantiates a new Kml generator.
     *
     * @param nodes the nodes
     * @param start the start
     * @param end   the end
     * @param path  the path
     */
    public  KMLGenerator (ArrayList<Node> nodes, Point start, Point end, ArrayList<Node> path){
        this.nodes = nodes; // nodes making up graph
        this.start = start; //starting point
        this.end = end; // destination
        this.path = path; //list of nodes making up path
        this.poly = new ArrayList<>(); //list of points making up polygon, empty
    }

    /**
     * Generate
     * this method generates the file using the created tags including the appropriate information.
     *
     * @throws IOException the io exception
     */
    void generate() throws IOException {
            //creates file
            String filename = (getFileName()); //generates filename and stores it
            File outPutFile = new File(filename); //creates file

            if (outPutFile.createNewFile()) {
                String text = "";
                PrintWriter writer = new PrintWriter(filename, "UTF-8");
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"); //write header to file
                String tag = "  <Document>\n" +
                        "    <name>Paths</name>\n" +
                        "    <description></description>\n" +
                        "    <Style id=\"yellowLineGreenPoly\">\n" +
                        "      <LineStyle>\n" +
                        "        <color>7f00ffff</color>\n" +
                        "        <width>4</width>\n" +
                        "      </LineStyle>\n" +
                        "      <PolyStyle>\n" +
                        "        <color>7f00ff00</color>\n" +
                        "      </PolyStyle>\n" +
                        "    </Style>\n";

                //path - document 1
                tag += createPath(path);

               //nodes - document 2
               /* for (int i = 0; i <nodes.size() ; i++) {
                    if(!(nodes.get(i).walkable == false && !path.contains(nodes.get(i))) )
                       tag += createPlacemark(nodes.get(i).point);
                }*/

                tag += createPlacemark(start);
                tag += createPlacemark(end);

                if(poly.size() > 0){//if there are polygons
                    for (int k = 0; k < poly.size(); k++) {

                        tag += createPolygon(poly.get(k));


                    }
                }


                tag +="</Document>\n</kml>"; //closing tags


                writer.write(tag); //write string to file
                writer.close();
            } else {
                System.out.println("File Creation Unsuccessful!.");
            }
        }

    private String createPolygon(ArrayList<Point> points) {
        String tag = "<Placemark>\n" +
                "    <name>The Pentagon</name>\n" +
                "<Polygon>\n" +
                "      <extrude>1</extrude>\n" +
                "      <altitudeMode>clampToSeaFloor</altitudeMode>\n" +
                "      <outerBoundaryIs>\n" +
                "        <LinearRing>\n" +
                "          <coordinates>\n";
        for (int i = 0; i < points.size(); i++) {
            tag+=points.get(i).longitude +","+points.get(i).latitude+"\n";
        }


        tag+="   </coordinates>\n" +
                "        </LinearRing>\n" +
                "      </outerBoundaryIs>\n" +
                "    </Polygon>\n" +
                "  </Placemark>";

        return tag;
    }


    /**
     * Creates placemark tag as a string.
     *
     * @param point the point
     * @return the string
     */
    public String createPlacemark(Point point) {
            String tag = "";



                tag += "<Placemark>\n<name></name>\n";

                tag += "<description>" + point.latitude+","+point.longitude+ "</description>\n<Point>\n<coordinates>" +  point.longitude+","+point.latitude+"\n";
                tag += "</coordinates>\n</Point>\n</Placemark>\n";

            return tag;
        }

    /**
     * Create path tag as a string.
     *
     * @param p the p
     * @return the string
     */
    public String createPath(ArrayList<Node> p) {

    String tag = "";


            for (int i = 0; i < p.size() - 1; i++) {

                tag += "    <Placemark>\n" +
                        "      <name>Absolute Extruded</name>\n" +
                        "      <description>Transparent green wall with yellow outlines</description>\n" +
                        "      <styleUrl>#yellowLineGreenPoly</styleUrl>\n" +
                        "      <LineString>\n" +
                        "        <extrude>1</extrude>\n" +
                        "        <tessellate>1</tessellate>\n" +
                        "        <altitudeMode>absolute</altitudeMode>\n" +
                        "        <coordinates>";
                tag +=  p.get(i).point.getLongitude() + "," + p.get(i).point.getLatitude() + "\n";
                tag += p.get(i+1).point.getLongitude() + "," + p.get(i+1).point.getLatitude() + "\n";
                tag += "  </coordinates>\n" +
                        "      </LineString>\n" +
                        "    </Placemark>\n";

            }



        return tag;
    }



    /**
     * Generates a timestamp and returns it as a string
     *
     * @return the timeStamp
     */
        private String getFileName(){
            Date date = new Date();
            String timeStamp = ""+date;
            timeStamp = timeStamp.replaceAll(" ", "_").toLowerCase();
            timeStamp = timeStamp.replaceAll(":", "_").toLowerCase();
            timeStamp += timeStamp+".kml";
            return timeStamp;
        }



}
