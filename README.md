# marsRovers
JAVA implementation of the mars-rovers example 


MARS ROVERS
A squad of robotic rovers are to be landed by NASA on a plateau on Mars.
This plateau, which is curiously rectangular, must be navigated by the rovers so that their on-board cameras 
can get a complete view of the surrounding terrain to send back to Earth. 
A rover's position and location is represented by a combination of x and y co-ordinates and a letter 
representing one of the four cardinal compass points. The plateau is divided up into a grid to simplify 
navigation. There are squares that rover cannot pass through, and they are marked on the plateau.
An example position might be 0, 0, N, which means the rover is in the bottom left corner and facing North.
Two rovers cannot occupy the same position.
In order to control a rover, NASA can send two types of commands to a rover
1. The string of letters – possible letters are 'L', 'R' and 'M'. 'L' and 'R' makes the rover spin 90 degrees 
left or right respectively, without moving from its current spot. 'M' means move forward one grid 
point, and maintain the same heading.
When this command is received, the rover moves according to the given commands. After its 
movement is complete, rover returns the coordinates of its final position. 
If some of the required moves cannot be performed, rover returns an error and remains at its 
current position.
2. The coordinates of a point at plateau.
When rover receives this command, it computes the current shortest passable path from its current 
point to the given point and returns the string of letters which would realize the movement along 
that path.
Assume that the square directly North from (x, y) is (x, y+1).
TASK:
Your task is to design a rover control library which will enable the simulation, as well as the REST-full web 
application that will expose the API for simulating the described situation. 
INPUT AND OUTPUT:
You need to have methods for setting the map dimensions, deploying and removing the rover to the plateau, 
execution of the available commands, getting the information about the specific rover position, as well as 
about all of the currently deployed rovers.
The dimensions of the plateau are integers, the lower-left coordinates are assumed to be (0,0). Dimensions 
are not larger than 1000.
The position is made up of two integers and a letter separated by spaces, corresponding to the x and y coordinates and the rover's orientation.
The string of commands is the series of characters ‘L’, ‘R’, ‘M’.
