# Design Plan

## Specification

### Intro
Our team seeks to create a platform for Cellular Automata (CA), simulations of units/cells that update based on a fixed set of rules governing inter-cell interactions, typically to model natural and complex phenomena like the spreading of fire ([source: CS308 assignment page](https://www2.cs.duke.edu/courses/compsci308/spring18/assign/02_cellsociety/index.php)). The platform will ensure sufficient flexibility so that models of varying behaviors can be representable by the program. Thus, the program will be open for extension by new simulations with unique behaviors, but closed for modification to the functionalities needed to run all CA simulations; these common functionalities include the ability to get information about any cell's state and accordingly update the state of that cell's neighbors. 

### Overview
The program will include a Main class, a Grid class, and a Cell class. 

The Grid class allows the adding of Cells as (JavaFX) Rectangle Shapes to a (JavaFX) Group object. In Main, a display of the simulation will be produced by adding this Group object to a (JavaFX) Stage object, and then "show()"ing that Stage object.

Cell objects will represent the cell units within each simulation. As such, the Cell class will operate as an interface whose implementations will be the various possible CA simulations. The Cell class will require methods to set the (int) state of each Cell (e.g. in the [Fire simulation](http://nifty.stanford.edu/2007/shiflet-fire/), "no tree," "non-burning tree," or "burning tree") and set the Cell's relevant neighbors as an ArrayList of other Cells. Relevant neighbors are those whose states are dependent upon/connected to the Cell, whether those be to the left/right/above/below that Cell, or on that Cell's diagonals, depending on the behavior that the CA simulation manifests. 

The Cell class will also include a method to update Cell state. The update method will evaluate the state of a Cell and accordingly update the state of its neighbors. For example, if a Cell in the Fire simulation reaches the "burning" state in a time step, then some of the Cells in its ArrayList of relevant neighbors will also necessarily enter a "burning" state.

*TODO: Add picture of how components relate, likely via CRC cards*

### User Interface
Upon launch, the program will provide a menu of names of available simulations, which users may click to begin watching. In each simulation, each Cell state will be represented by a different color (e.g. in the WaTor predator-prey simulation, red for predator and green for prey), specified by a color legend to the side of the simulation display. While viewing the simulation, users will be able to pause, slow, and reset simulations (like [this Segregation simulation](http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/)) by clicking buttons to the side of the display. A home button will be available for users to return to the home screen and switch which simulation they are viewing.

For certain simulations, users will also need to specify basic rules for the simulation via text input of a decimal between 0 and 1, with users be alerted if they enter an out-of-range number. Empty text input will lead to a randomized simulation being displayed. 

* For the segregation simulation, users will input the starting proportion of each of the one of the two self-segregating groups and the segregation-satisfaction minimum. 
* For the WaTor predator-prey simulation, users will input input the starting proportion of the predator group, similar to the segregation simulation. 
* For the fire simulation, users will input the probability of catching fire. 

*TODO: Add a picture of the interface*

### Design Details 
* Main Class
    * The Main class handles the simulations. This class will contain all the imports for JavaFX, the creation of the timeline and GUI, and manage the reading of .XML files for the creation of different simulations.<br />
        * <B>Instance Variables</B>
            * private stage myPrimaryStage -- the stage which JavaFX will use for the GUI
            * private scene myScene -- the Scene JavaFX will use for the GUI
            * private grid myGrid -- an instance of the Grid class that holds all of the cells in a simulation
            * private int mySimulationType -- the integer corresponding to the type of simulation
            * private ArrayList<cell> activeCells -- cells that need to be checked in each step
        
        * <B>Methods</B><br />
            * public static void main(String[] args) -- calls the launch method of javaFX;
            * Public void Start(Stage PrimaryStage) throws Exception -- sets up the primary stage of JavaFX
            * private scene Step() -- handles everything that happens between two keyframes in the animation
            * private void handleMouseinput(mouseEvent e) -- takes the input of a mouse event
            * private voidStartSimulation(grid G) -- starts the simulation after recieving a starting grid
            * private grid readInput(file f) -- scans the file and returns the corresponding simulation grid
            
* Grid Class<br />
    * The Grid class handles the interaction between the front-end and the backend.<br />
    * <B>Instance Variables</B><br />
        * mySimulationType -- Stores the integer value of the simulation type 
        * MyWidth -- integer value of the number of columns
        * MyHeight -- integer value of the number of rows
        * Cell[][] myCellArray -- array of cells
        
    * <B>Methods</B><br />
        * public Grid(Int height, Int width, int simulationType, ArrayList activePoints) -- constructor given componenents from file
        * public Grid(Int height, Int width) -- empty grid constructor
        * private int getMyWidth -- returns integer value of width
        * private int getMyHeight -- returns integer value of height
        * 
* Cell Class -- extends interface<br />
    * <B>Variables</B><br />
        * myState -- keeps the state of a given cell
        * myType -- keeps the type of simulation
    * <B>Methods</B><br />
        * public int getMyState -- returns the state of an object
        * public void setMyState -- sets the state of an object
        * public void update -- updates this cell and neighboring cells
        * public cell[] getNeighbors -- returns a cell array of all the neighboring cells
    * Fire Cell
    * Wator Cell
    * Predator Cell
    * Game of Life Cell

### Design Considerations
Our team discussed whether the Cell class should be an interface or an abstract class. We decided that an interface would be best because the Cell class in and of itself would have no purpose; it only becomes relevant through implementation through a simulation. 

Our team also discussed whether a Neighborhood class would be beneficial to associate neighboring Cell objects with one another, as opposed to having an ArrayList of Cell neighbors associated with each Cell object. We ultimately decided against the Neighborhood class, preferring the ArrayList of Cell neighbor approach. The Neighborhood class would have embodied an object-oriented spirit by compartmentalizing the state of a single Cell from the behavior/interactions it manifests in relation to other Cells. However, the Neighborhood class would have made designating Neighborhood shapes tedious (i.e. edge vs. middle neighborhoods). Moreover, the Neighborhood class would be so heavily dependent upon state attributes in the Cell class that it would make more sense to keep neighbor-updating responsibilities within each Cell object. 

### Team Responsibilities
The team will develop the Cell interface together so that we have a common understanding of the simulation classes that we will divide amongst ourselves. Collin will write the Main class and the game of life simulation class. Susie will handle the Grid class and the segregation simulation. Judith will take the fire and predator-prey classes. We will reassign as necessary if we see issues with an uneven distribution of work (i.e. one class takes considerably more or less work than anticipated). 

## Use Cases
* Apply the rules to a middle OR edge cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors) OR edge (i.e., with some of its neighbors missing)
    * updateState() will be called on the Cell of interest. Within this method, the ArrayList of the Cell's neighbors will be for-looped through, and if the number of live neighbors is <=1 or >=4, then the Cell's state will be set to dead (likely the int "1" as opposed to the int "0" for alive). The end of the updateState() method will contain an updateColor() method so that state updates can manifest through changes in the Color of each Cell's associated Rectangle Shape. The next step() through the simulation will display these changes.
* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
    * The Main class will call upon the Grid class within Main's step() method. The Grid class will loop through each Cell, repeating updateState() on each of these Cells as elaborated upon the first use case. The next step (i.e. next run through the step() method) will convey these changes graphically. 
* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
    * Upon startup, the simulation display will assume a default value for probCatch specified in the XML file. This XML file will be parsed with a parsing method in Main. The value will be passed to the constructor of the appropriate Fire implementation of the Cell interface. 
* Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
    * A user may click the Return to Home button on Game of Life simulation screen s/he is on, which will be detected by a handleMouseInput() method in the Main class. The user may then select the WaTor option, upon which the XML file associated with WaTor will be parsed so that the new simulation launch may begin.  




>>>>>>> f40614e1f9ad861ab1c49e1b25df3287254a2d88
