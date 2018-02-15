## DESIGN GOALS
The high level design goals of the project were to collaborate on flexible, readable, and functional code to simulate cell societies. 

By flexible, we mean that we wanted the addition and manipulation of features to be as easy as possible for team members that may not have worked on the bulk of the foundation pieces for a particular component. This means that we intended for all of our classes to be able to interact with each other in such a way that variables and other crucial information were both possible and easy to access. This being said, we had to be careful not to cross the line from flexibility to vulnerability. We had to ensure that although any group member could access every element that they needed for their assigned features, we did not write code that was easy to break with unintentional errors. 

As for readability, we tried to all write code that was as simple as possible for others to understand. This was especially crucial as the focus of this project was on teamwork and communication. Without each member properly understanding interactions in the project, new features would be very difficult to implement. So we were careful in our naming conventions, we tried not to use magic numbers without explanations, and we tried to have very organized structures. 

## ADDING NEW FEATURES
There are different types of features that can be added to our project. These groups include new simulations, new types of neighborhoods, new visual effects, new user manipulations.

New simulations can be added by creating a representative subclass of the abstract Cell class that follows the rules of the simulation. Then, the Main Class needs to be updated to include that simulation in that it has a method to create the Cells of the simulation for that particular type. The option to create these Cells needs to be added to createCells method in the Main Class as another case. Finally, the new simulation need to be added to the Simulations.properties file. 

New neighborhood types can be added directly into the Grid Class. This Class contains an abstract Class called NeighborhoodMaker. Several subclasses extend this abstract class with their own conditional statements for which Cells to include in the neighborhood of a particular Cell. 

Visual features can be added to the main screen where a simulation runs by accessing the SimulationView Class. This class currently chooses to dispaly a header, the grid, a control bar, and a slide bar. For example, all of the buttons like HOME, RESTART, STEP, etc. are included in "myControlsContainer." In order to add user manipulation features like another button or slider, one would need to include this button or slider in one of the current container. Another option would be to create a new container and to increase the height of the Scene to acomodate the addition. In this Class, it would also be possible to add visualy appealing borders for the grid or other such front-end visual deesign features. 
However, to change the look of the Cells in the Grid themselves, one would need to access the specific Cell Class. The Cell class contains an instance variable called myShape. This object can change size, color, or even the type of shape. 



## DESIGN CHOICES 
*Justifies major design choices, including trade-offs (i.e., pros and cons), made in your project

## ASSUMPTIONS 
*States any assumptions or decisions made to simplify or resolve ambiguities in your the project's functionality

One assumption that was made often to simplify the ambiguity of the project's functionality was that each simulation type was assumed to only have up to a certain number of Cell states. Usually this number of possible states was either 2 or 3. 

Another assumption was that the size of the grid was final after the simulation is selected from the options bar. This means that the grid cannot currently shrink or grow mid- simulation. 
