# Design Plan

## Intro
Our team seeks to create a platform for Cellular Automata (CA), simulations of units/cells that update based on a fixed set of rules governing inter-cell interactions, typically to model natural and complex phenomena like the spreading of fire ([source: CS308 assignment page](https://www2.cs.duke.edu/courses/compsci308/spring18/assign/02_cellsociety/index.php)). The platform will ensure sufficient flexibility so that models of varying behaviors can be representable by the program. Thus, the program will be open for extension by new simulations with unique behaviors, but closed for modification to the functionalities needed to run all CA simulations; these common functionalities include the ability to get information about any cell's state and accordingly update the state of that cell's neighbors. 

## Overview
The program will include a Main class, a Grid class, and a Cell class. 

The Grid class allows the adding of Cells as (JavaFX) Square Shapes to a (JavaFX) Group object. In Main, a display of the simulation will be produced by adding this Group object to a (JavaFX) Stage object, and then "show()"ing that Stage object.

Cell objects will represent the cell units within each simulation. As such, the Cell class will operate as an interface whose implementations will be the various possible CA simulations. The Cell class will contain methods to set the (int) state of each Cell (e.g. "no tree," "non-burning tree," or "burning tree" in the [Fire simulation](http://nifty.stanford.edu/2007/shiflet-fire/)) and set the Cell's relevant neighbors as an ArrayList of other Cells. Relevant neighbors are those whose states are dependent upon/connected to the Cell, whether those be to the left/right/above/below that Cell, or on that Cell's diagonals, depending on the behavior that the CA simulation manifests. 

The Cell class will also include a method to update Cell state. The update method will evaluate the state of a Cell and accordingly update the state of its neighbors. For example, if a Cell in the Fire simulation reaches the "burning" state in a time step, then some of te Cells in its ArrayList of relevant neighbors will also necessarily enter a "burning" state.

*TODO: Add picture of how components relate, likely via CRC cards*

## User Interface
