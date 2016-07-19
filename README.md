# PathFinder

ABOUT INDEX.PHP:

This project uses the index.php script in order to check if a point is on land or water. This Script is called by the Pointcheker Class. 
While testing, I made sure that the index.php file was inside of my htdocs folder in Xamp in order for it to run. Making sure that that file can be executed properly is all that is needed for this program to run properly. 

**Make sure that the Pointcheker class has the right path for the index.php file. 


BASIC INFO ABOUT EACH CLASS:

(i) Class Main
			(1)This class executes all of the modules in the appropriate order 
based on the user’s input. 
		(ii)Class Map(start, end, size, list(optional))
		     (1)InitNode()
			(a) Initializes the nodes by checking generating the coordinates of 
each node. It also uses the point checker to determine if a node is walkable(usable) or not. 
		     (2)findPath()
			(a)Uses the A* algorithm to determine if there is a path and what it 
is. It then returns a list of nodes which make up the path found. 
		     (3)drawMap()
			(a)This module prints a text representation if the space where the 
nodes are located on the map. ‘#’ indicates unwalkable nodes, and ‘0’ indicates walkable nodes. 
		     (4)getDistance()
			(a)This method takes two nodes and returns the distance between 
them in double using their coordinates. 
(iii)Class Node
     (1)This class represents a node, it holds a Point object, and 
whether is it a usable node or not. 
(iv)Class Point
     (1)This class represents a point on the map. It holds its latitude and 
longitude. It also corrects the latitude and longitude of the point if 
the coordinates entered are outside the boundaries of the map.
(v)Class PointChecker
execute(lat, lng)
			(a)This module runs a PHP script that checks google maps’ pixels 
at a certain coordinate. 
isInside(polygon, point)
This module checks if the point is inside the polygon, then returns true/false. 
Index.php
This script takes a latitude and longitude parameter which allows it to check for a pixel color a that coordinate. It then returns a 1 if the pixel is black and 0 if it is white. 
This script along with the pointchecker module is in the htdocs in the xampp folder. It runs as if it were on a server. 
(vi)Class KMLParser(fileName)
      (1)polygonCoord()	
     (a)This module reads a kml file then returns the coordinates of 
the points making up the polygon(s) in the file. 	
(viii)Class KMLGenerator(nodes, start, end, path, poly(optional))
     (1)createPolygon(points)
	    (a)This module creates a polygon tag using the list of points 
Inputted and returns a string. 
     (2)createPlacemark(point)
	   (a)This module creates a placemark tag using the point inputted 
and returns a string. 
     (3)createPath(nodes)
	    (a)This module creates a path tag the list of nodes inputted and 
returns a string. 


