import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Eliakah kakou
 * Main.
 * This class runs all of the modules in the right sequential
 * order depending on the input
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the command line arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {

        Map m; //map item
       // String fileName = ""; // file to be used
        String fileName = "C:\\Users\\eliakah\\IdeaProject\\FinalPathFinder\\testFile.kml"; // file to be used
        ArrayList<ArrayList<Point>> list = new ArrayList<>();
        int size = 20;
        boolean check = false;
        KMLGenerator k;
       //Point start = new Point(15.990045, 121.843872);//start point - size = 15
        //Point end = new Point(12.765933, 125.007935);//end point
        Point start = new Point(29.590177, -27.817383);//start point - size = 20 + poly
         Point end = new Point(-0.376279, 8.657227);//end point
        //Point start = new Point(43.050827, 7.163086);//start point - size =15
        //Point end = new Point(39.008513, 15.600586);//end point



        File f = new File(fileName);
        if(f.exists()) {
           check = true;
        }


        if(check) {
            KmlParser parser = new KmlParser(fileName);
            PointChecker checker = new PointChecker();
            list = parser.polygonCoord();
            m = new Map(start, end, size,list); //this will create a graph
        }else{
            m = new Map(start, end, size); //this will create a graph
        }


        System.out.println("Original Map:");
        System.out.println();
        m.drawMap(); //this will draw a representation of the nodes on the map
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < m.nodes.length; i++) {
            for (int j = 0; j < m.nodes.length; j++) {
                nodes.add(m.nodes[j][i]);
            }
        }
         ArrayList<Node> path = m.findPath(); //  list of nodes making the path
        if(path != null) {

            m.drawRes(path);

            if(check){
                k = new KMLGenerator(nodes, start, end, path, list); //passes the graph to the kml generator
            }else{
                k = new KMLGenerator(nodes, start, end, path);
            }

            k.generate(); //generates the kml file
        }else{
            System.out.println("No Path Found.");
        }

    }


}
