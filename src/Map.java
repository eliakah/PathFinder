/**
 * Created by Eliakah kakou
 * Map.
 * This class contains the graph where the main algorithm is ran,
 * it returns the list of Nodes making up the path
 */

import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Map.
 */
public class Map {
    /**
     * holds nodes. first dim represents x-, second y-axis.
     */
    Node[][] nodes;
    /**
     * The Start.
     */
    Point start, /**
     * The End.
     */
    end;
    /**
     * The Size.
     */
    int size;
    /**
     * The File.
     */
    String file;

    /**
     * Instantiates a new Map.
     *
     * @param start the start
     * @param end   the end
     * @param size  the size
     * @throws IOException the io exception
     */
    public Map(Point start, Point end, int size) throws IOException {
        this.start = start;
        this.end = end;
        this.size = size;
        nodes = new Node[size][size];
        initNodes();


    }

    /**
     * Instantiates a new Map.
     *
     * @param start the start
     * @param end   the end
     * @param size  the size
     * @param list  the list
     * @throws IOException the io exception
     */
    public Map(Point start, Point end, int size, ArrayList<ArrayList<Point>> list) throws IOException {
        this.start = start;
        this.end = end;
        this.size = size;
        this.file = file;
        nodes = new Node[size][size];
        initNodes();


        for (int k = 0; k < list.size(); k++) {

            checkForPoly(list.get(k));


        }


    }

    private void checkForPoly(ArrayList<Point> list) {
        PointChecker checker = new PointChecker();
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (checker.isInside(list, nodes[j][i].point) == true) {
                    nodes[j][i].walkable = false;
                }

            }
        }

    }

    /**
     * initializes all nodes. Their coordinates will be set correctly.
     */
    private void initNodes() throws IOException {
        double lat = start.latitude;
        double lng = start.longitude;
        double xDistance = (end.longitude - start.longitude) / size;
        double yDistance = (start.latitude - end.latitude) / size;
        double x, y;
        int flag;
        boolean walkable = true;
        PointChecker checker = new PointChecker();
        y = lat;
        for (int i = 0; i < size; i++) {
            x = lng;

            for (int j = 0; j < size; j++) {
                //populate array
                flag = checker.execute(y, x);
                if (flag == 0) {
                    walkable = true;
                } else {
                    walkable = false;
                }
                nodes[j][i] = new Node(new Point(y, x), walkable, getDistance(start, new Point(y, x)), getDistance(new Point(y, x), end));
                System.out.println(flag + " | " + y + ", " + x);
                x = x + xDistance;
            }
            y = y - yDistance;
        }


    }


    /**
     * Find path and returns array list of nodes or Null if path not found.
     *
     * @return the array list
     */
    public ArrayList<Node> findPath() {
        System.out.println("Path finding!");
        nodes[0][0].parent = null;
        int j = 0, i = 0;
        int oj = 0, oi = 0; //old
        int lj = 0, li = 0; //next

        nodes[j][i].walkable = false; //set the start node to un-walkable


        while (true) { //loop start


            if ((nodes[j][i].point == nodes[size - 1][size - 1].point) || (i == size - 1 && j == size - 1)) { //exit if current is destination
                nodes[j][i].walkable = false; //set walkable to false
                break; //exit loop
            }


            //If i can't get past the first node
            if ((
                    ((i <= 0 || j <= 0) || nodes[j - 1][i - 1].walkable != true) && //if top left
                            ((i <= 0 || j >= size - 1) || nodes[j + 1][i - 1].walkable != true) && //top right
                            (i <= 0 || nodes[j][i - 1].walkable != true) && //if top of row
                            (j <= 0 || nodes[j - 1][i].walkable != true) && //if left of col
                            (j >= size - 1 || nodes[j + 1][i].walkable != true) && //right of col
                            ((i >= size - 1 || j <= 0) || nodes[j - 1][i + 1].walkable != true) && //if bottom left
                            ((i >= size - 1 || j >= size - 1) || nodes[j + 1][i + 1].walkable != true) && //if bottom right
                            (i >= size - 1 || nodes[j][i + 1].walkable != true) && nodes[j][i].parent == null)) { //if no more neighbors to explore, go back to parent or break

                return null;

            }


            //if neighbors of the node are NOT available
            if (
                    ((i <= 0 || j <= 0) || nodes[j - 1][i - 1].walkable != true) && //if top left
                            ((i <= 0 || j >= size - 1) || nodes[j + 1][i - 1].walkable != true) && //top right
                            (i <= 0 || nodes[j][i - 1].walkable != true) && //if top of row
                            (j <= 0 || nodes[j - 1][i].walkable != true) && //if left of col
                            (j >= size - 1 || nodes[j + 1][i].walkable != true) && //right of col
                            ((i >= size - 1 || j <= 0) || nodes[j - 1][i + 1].walkable != true) && //if bottom left
                            ((i >= size - 1 || j >= size - 1) || nodes[j + 1][i + 1].walkable != true) && //if bottom right
                            (i >= size - 1 || nodes[j][i + 1].walkable != true)) { //if no more neighbors to explore, go back to parent or break

                //set current to unwalkable
                nodes[j][i].walkable = false;
                //set current to the parent
                j = oj;
                i = oi;
                //set child to 0, 0
                lj = 0;
                li = 0;
            } else {//if we have options, if some ARE available
                //from here we compare each node's h distance and set the lowest to next

                if (!(i <= 0 || j <= 0)) {
                    //top left
                    if (nodes[j - 1][i - 1].walkable == true) {
                        lj = j - 1;
                        li = i - 1;
                    }
                }

                if (!(i <= 0 || j >= size - 1)) {
                    if (nodes[j + 1][i - 1].walkable == true) { //top right
                        if (nodes[lj][li].h > nodes[j + 1][i - 1].h) {
                            lj = j + 1;
                            li = i - 1;
                        }
                    }
                }

                if (!(i <= 0)) {
                    if (nodes[j][i - 1].walkable == true) {
                        if (nodes[lj][li].h > nodes[j][i - 1].h) {
                            lj = j;
                            li = i - 1;
                        }
                    }
                }

                if (!(j <= 0)) {
                    if (nodes[j - 1][i].walkable == true) {
                        if (nodes[lj][li].h > nodes[j - 1][i].h) {
                            lj = j - 1;
                            li = i;
                        }
                    }
                }

                if (!(j >= size - 1)) {
                    if (nodes[j + 1][i].walkable == true) {
                        if (nodes[lj][li].h > nodes[j + 1][i].h) {
                            lj = j + 1;
                            li = i;
                        }
                    }
                }

                if (!(i >= size - 1 || j <= 0)) {
                    if (nodes[j - 1][i + 1].walkable == true) {
                        if (nodes[lj][li].h > nodes[j - 1][i + 1].h) {
                            lj = j - 1;
                            li = i + 1;
                        }
                    }
                }

                if (!(i >= size - 1 || j >= size - 1)) {
                    if (nodes[j + 1][i + 1].walkable == true) {
                        if (nodes[lj][li].h > nodes[j + 1][i + 1].h) {
                            lj = j + 1;
                            li = i + 1;
                        }
                    }
                }

                if (!(i >= size - 1)) {
                    if (nodes[j][i + 1].walkable == true) {
                        if (nodes[lj][li].h > nodes[j][i + 1].h) {
                            lj = j;
                            li = i + 1;
                        }
                    }
                }


                //prioritize left over top
                if (!(i <= 0)) { //if can go up
                    if (!(j <= 0)) { //if can go left
                        if (i - 1 == li && j == lj) { //if next is top
                            if (nodes[j - 1][i].walkable == true) { //if left is walkable
                                //set next to left
                                lj = j - 1;
                                li = i;
                            }
                        }
                    }

                }
                //prioritize left over top right
                if (!(i <= 0 || j >= size - 1)) {//if can go up right
                    if (!(j <= 0)) { //if can go left
                        if (i - 1 == li && j + 1 == lj) { //if next is top
                            if (nodes[j - 1][i].walkable == true) { //if left is walkable
                                //set next to left
                                lj = j - 1;
                                li = i;
                            }
                        }
                    }

                }


            }

            //set current node to un-walkable
            nodes[j][i].walkable = false;
            //set current as parent of next node
            nodes[lj][li].parent = nodes[j][i];
            // set current nodes to old nodes
            oj = j;
            oi = i;
            //set next node as current
            j = lj;
            i = li;
            if (lj == oj && li == oi) {
                return null;
            }

            lj = 0;
            li = 0;

            drawMap(); //draw map
            System.out.println();
        } //end loop

        return nodetoList(nodes[j][i]); //return path
    }


    /**
     * Nodeto list array list.
     *
     * @param n the n
     * @return the array list
     */
    ArrayList<Node> nodetoList(Node n) {
        ArrayList<Node> path = new ArrayList<>();

        path.add(new Node(end));
        path.add(n);
        while (n.parent != null) {
            path.add(n);
            n = n.parent;
        }
        path.add(n);
        return path;
    }


    /**
     * prints map to line
     * walkable node = "0",un-wakable node = "#".
     */
    public void drawMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (nodes[j][i].walkable)
                    print("0\t");
                else
                    print("#\t");
            }
            print("\n");
        }
    }

    /**
     * Draw res.
     *
     * @param pathList the path list
     */
    public void drawRes(ArrayList<Node> pathList) {
        boolean check;
        for (int i = 0; i < size; i++) {
            check = false;
            for (int j = 0; j < size; j++) {
                check = false;

                for (int k = 0; k < pathList.size(); k++) {
                    if (nodes[j][i].point.latitude == pathList.get(k).point.latitude && nodes[j][i].point.longitude == pathList.get(k).point.longitude) {
                        check = true;
                        print(">\t");
                    }
                }
                if (check == false) {
                    if (nodes[j][i].walkable)
                        print("0\t");
                    else
                        print("#\t");
                }
            }
            print("\n");
        }
    }

    /**
     * prints something to sto.
     */
    private void print(String s) {
        System.out.print(s);
    }


    /**
     * Get distance double.
     *
     * @param one the one
     * @param two the two
     * @return the double
     */
    public Double getDistance(Point one, Point two) {
        double R = 6371000; // metres
        double lat1 = (one.latitude * Math.PI) / 180; // convert to toRadians
        double lat2 = (two.latitude * Math.PI) / 180; // convert to toRadians
        double latDiff = ((two.latitude - one.latitude) * Math.PI) / 180; // convert to toRadians
        double longDiff = ((two.longitude - one.longitude) * Math.PI) / 180; // convert to toRadians

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(longDiff / 2) * Math.sin(longDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = R * c;
        return d;
    }


}
