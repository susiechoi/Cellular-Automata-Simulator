## Part 1

* What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
    -Both groups decided to layer the structure so that the cells are the only ones aware of the interaction between objects and their characteristics and the GUI can only see the appearanceo of the cells.
* What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
    -Our groups took different approaches to the heirarchies. My group decided that there should be several different sub classes of a cell superclass that would each have their own physics and interactions. Their group decided that they would not only have subclasses for cells but also have different classes for the different kinds of games which would control the simulation itself.
* What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
    -Their group was allowing a lot of open parts so that regardless of the individual objects that are created, one could access the data of where a cell should be or what type of simulation a game ought to be. This constrasts our choice to keep almost all the data closed with the exception of graphics.
* What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
    - The biggest errors that are likely to occur are in reading in the XML values so its important throw exceptions during the reading of the file to ensure that it is being fed valid input. This can be solved through try/catch statements and throwing exceptions.
* Why do you think your design is good (also define what your measure of good is)?
    -I believe our design is good because there is very little duplicated code and the information is very protected which is a contrast to their design which is open allowing for a lot more interaction between objects and the data.

## Part 2
* How is your area linked to/dependent on other areas of the project?
    -I am handling the GUI and it relies on the other areas of the project to retrieve the necessary data and to run the simulation itself. Moreover, I am in charge of the xml parsing which sets the beginning of the simulation. The other team I spoke with did not have distinct areas they work on but instead are choosing to tackle a different challenge as a team at any given time so that nobody is writing code dependent on a an unimplemented method or class.
* Are these dependencies based on the other class's behavior or implementation?
    Yes! 
* How can you minimize these dependencies?
    Plan ahead! Make sure that you know what interactions you will need.
* Go over one pair of super/sub classes in detail to see if there is room for improvement. 
* Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).

## Part 3

* Come up with at least five use cases for your part (most likely these will be useful for both teams).
    - Import an XML file
    - Correctly Parse the information from an XML file
    - Create a GUI with a working grid and controls
    - Properly handles invalid input 
    - Has a splash screen that enables users to choose their own simulation type
* What feature/design problem are you most excited to work on?
    - I'm really excited about working on the GUI because I like graphic design and I think that there is a lot of challenges getting the GUI and controls to interact with their subclasses.
* What feature/design problem are you most worried about working on?
    -I'm very worried about XML parsing because any mistake on that could kill the entire project and there are such minute details that could slip by and change the functionality of the rest of the program.