# Cell Society Design Exercise (Lab 1/25)
## Cell Society High Level Design 
1. Name of the simulation specified in the XML. 
2. Each cell should have a link to its neighbors. The neighbors which were just affected by a cell will be monitored 
so that they do not update until the next step.
3. Grid is just a 2D array. The grid does not have unique behavior: only "get"-style methods will be invoked on it 
because Cells themselves will manage their behavioral changes. 
4. 
* Name of the kind of simulation 
* Simulation's author
* Settings for global configuration parameters specific to the simulation
	* e.g. Probability of fire propogation 
	* Segregation threshold (when movement can occur) 
	* Step rate 
* Dimensions of the grid
* The initial configuration of the states for the cells in the grid
5. GUI runs through each cell in the grid array and updates color/some external indication to manifest the fact that 
the cell's state has changed.

## Class-Responsibility-Collaborator Cards

* Cell (interface) 
** Method signatures
*** public void setNeighbors(ArrayList<Cell> relevantNeighbors); 
*** public void setState(int state); 
*** public void updateState(int state); // cell will update state of neighbors in ArrayList
*** public int getState();
** Depends on Square shape (especially important in updating colors to indicate updated states)
** Holds data about: Neighbors, behavior of simulation
** Implementations: Game of Life, Fire, Wator classes 

* Grid (extends Group) 
** public void addCell(Cell cell); // will add Squares to Grid Group 
** Holds data about its Cells

* Main 
** Holds an array that holds the cells that are "on fire," i.e. whose neighbors need to be checked 
