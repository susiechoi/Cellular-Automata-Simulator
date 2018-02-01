# Inheritance Review Questions - Susie Choi (sc477) & Calvin Ma (cm298)

## Part 1 
1. Encapsulation
Calvin's Cell class will encapsulate neighbor-checking. My Cell class also has an update neighbors method. 
2. Hierarchies & behavior
Superclass Cell, Superclass for TypeOfGame. I asked why he needed both the cell and type of game superclass: perhaps he could establish the instantiation a different types of cell withou the need for another game-specific superclass. He said it's because each game has specific rules. I explained how our program, instead, handles the rules within each Cell object.
3. Closed & open, polymorphism
Calvin's subclasses are closed to other games. Polymorphism helps them because their superclass can have most of their repeated methods, but within each specific Game class, they can have the rules specific to the game. The Game class pulls the majority of the weight in Calvin's program. I asked him what happens in the Segregation simulation when the neighbor-updating is dependent upon the surrounding segregation threshold: he said that the Game class would use a method to get the value of the current segregation proportion around a given cell, which will then influence the Game method flow. 
4. Exceptions
Calvin said he wants to make sure that everything stays within the bounds of the grid because the Cell class doesn't contain a coordinate location. To fix this, he would either have to give the Cell class a private instance variable indicating a coordinate or add extra methods to indicate to the Game class coordinates of the widths. I talked about how I would throw an exception in the Segregation simulation if there are no empty spots left. This could be resolved by either (1) prompting the user to change the starting proportions with some sliding scale in the UI or (2) alerting the user that the program is decreasing the number of of the starting members to make empty space.
5. Good design 
Calvin thinks his design is very flexible. I questioned the necessity of creating both a Game and Cell class for each new simulation, but he thinks that things will be easy because of the defined Game superclass.

## Part 2
1. Dependencies  
The Game uses methods in the Cell to determine movement. I explained that the Main class depends on the SplashScreen class to get the user input, and that the Main also depends on the Grid class, which depends on the Cell subclass. 
2. Dependencies based on other class' behavior
Calvin's Game class is heavily dependent upon the Cell class because all the Game class does is "move objects around," but the Cell class indicates when that movement is required. 