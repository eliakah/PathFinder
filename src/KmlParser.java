import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by eliakah kakou on 6/1/2016.
 * This class takes scans the kml and extracts the polygon
 * coordinates to be returned as a list of points
 */
public class KmlParser {
    /**
     * The File.
     */
    File file; //kml file in question
    /**
     * The Polygon coord.
     */
    ArrayList<String> polygonCoord = new ArrayList<>(); //holds coordinates of each polygon

    /**
     * Instantiates a new Kml parser.
     *
     * @param fileName the file name
     */
    public KmlParser(String fileName){
        this.file = new File(fileName);//initializing file
    }

    /**
     * Polygon coord array list.
     *
     * @return the array list
     * @throws IOException the io exception
     */
    ArrayList< ArrayList<Point>> polygonCoord() throws IOException {
        //get and print each polygon
        ArrayList<String> list;
        ArrayList<String> outer = new ArrayList<>();
        ArrayList<String> coord = new ArrayList<>();
        ArrayList< ArrayList<String>> res = new ArrayList<>();
        ArrayList< ArrayList<Point>> coordinates = new ArrayList<>();
        String temp;
        list = getTagContent("Polygon");


        for (int i = 0; i <list.size() ; i++) {
            temp ="";
            temp = getStringTag("outerBoundaryIs", list.get(i));
            if(temp !=""){
                outer.add(temp);
            }
        }


        for (int i = 0; i <outer.size() ; i++) {
            temp ="";
            temp = getStringTag("coordinates", outer.get(i));
            if(temp !=""){
                coord.add(temp);
            }
        }

        for (int i = 0; i <coord.size() ; i++) {
          res.add(toArrayList(coord.get(i)));
        }


        for (int j = 0; j <res.size() ; j++) {
         coordinates.add(toPointList(res.get(j)));
        }


        for (int j = 0; j <res.size() ; j++) {
            System.out.println(j+": "+coordinates.get(j));
            System.out.println("\n");

        }


        return coordinates;

    }



    /**
     * To point list array list.
     *
     * @param string the string
     * @return the array of strings into array of points
     * @throws IOException the io exception
     */
    ArrayList<Point> toPointList  ( ArrayList<String> string) throws IOException {
        ArrayList<Point> list = new ArrayList<>();
        String str;
        for (int i = 0; i <string.size(); i++) {
            str = string.get(i);
            String[] coordinates  =str.split(",");
            list.add(new Point(Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[0])));
            System.out.println(coordinates[1]+","+ coordinates[0]);
        }



        return list;
    }

    /**
     * To array list array list.
     *
     * @param string the string
     * @return the arraylist of strings
     * @throws IOException the io exception
     */
    ArrayList<String> toArrayList  (String string) throws IOException {
        Scanner s = new Scanner(string);
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();
        return list;
    }


    /**
     * Gets tag content.
     *returns content between tags or returns empty string if tag not contained
     * @param tag the tag
     * @return the tag content
     * @throws IOException the io exception
     */
    ArrayList<String> getTagContent  (String tag) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        String text = "";
        boolean check = false;
        String lineFromFile = "";
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            //get it there
            text = "";
            lineFromFile = scanner.nextLine(); //puts line in String
            if (lineFromFile.contains("<" + tag + ">")) { //check if line contains tag
                check = true;
                while (scanner.hasNextLine()) {
                    lineFromFile = scanner.nextLine();
                    if (lineFromFile.contains("</" + tag + ">")) {
                        break;
                    }else{

                    text +="\n"+lineFromFile;}
                }
                list.add(text);


            }


        }


        return list;
    }

    /**
     * Gets string tag.
     *returns content between tags or returns empty string if tag not contained
     * @param tag    the tag
     * @param string the string
     * @return the string tag
     * @throws IOException the io exception
     */
    String getStringTag  (String tag, String string) throws IOException {
        String text = "";
        boolean check = false;
        String lineFromFile = "";
        Scanner scanner = new Scanner(string);
        while (scanner.hasNextLine()) {
            //get it there
            lineFromFile = scanner.nextLine(); //puts line in String
            if (lineFromFile.contains("<" + tag + ">")) { //check if line contains tag
                check = true;
                while (scanner.hasNextLine()) {
                    lineFromFile = scanner.nextLine();
                    if (lineFromFile.contains("</" + tag + ">")) {
                        break;
                    }else{

                    text +="\n"+lineFromFile;}
                }
                //list.add(text);


            }


        }


        return text;
    }
}


