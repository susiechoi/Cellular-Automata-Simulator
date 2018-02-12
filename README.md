# Cell Society Team 17
Collin Brown, Susie Choi, Judith Sanchez Soriano

### Timeline
* Began January 23, finished February 11
* Total estimated hours: 100

### Roles
**Primary Responsibilities in First Sprint**
* Collin: Main (i.e. communication between classes), XML Parsing, SimulationView (i.e. simulation-displaying GUI)
* Susie: Grid, SplashScreen, Segregation simulation
* Judi: Fire, Game-Of-Life, Wator simulations

**Primary Responsibilities in Second Sprint**

See [Sprint2_Roles.md] (doc/Sprint2_Roles.md)

### Resources
* [Week 3-5 Readings] (https://www2.cs.duke.edu/courses/compsci308/spring18/classwork/) 
* StackOverflow and [this post in particular] (https://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string)

### Starting the Project & Notes
* Run Main.java (src/cellsociety_team17/Main.java)
* After choosing a simulation, you will be taken to an options screen. There, click an option for the neighborhood setting first, then the toroidality setting after. The simulation will begin upon your toroidality selection.

### Testing
* Simulations with bare XML (i.e. only a handful of active cells) were used to test the program. 
* The program accounts for Exceptions pertaining to missing data (e.g. the simulation that the user selects does not have a corresponding XML file, if that simulation is being read-in), such as by having a back-up default file path for simulations. 

### Data & Resource Files
* XML files for each simulation, format as follows:
`
<?xml version="1.0" encoding="UTF-8"?>
<data>
	<meta>
		<simulationType>*Simulation name*</simulationType>
		<title>*Simulation title*</title>
		<author>*Author name*</author>
		<height>*Number of rows in grid*</height>
		<width>*Number of columns in grid*</width>
		<probability>*Some attribute associated with the updating behavior of simulation*</probability>
	</meta>
	<grid>
		<row>
			<cell>*State of Cell (integer, usually either 0, 1, or 2 if simulation has 3 states)*</cell>
			<cell>0</cell>
			<cell>1</cell>
			<cell>2</cell>
			<cell>0</cell>
			<cell>1</cell>
			<cell>2</cell>
			<cell>0</cell>
			<cell>1</cell>
			<cell>2</cell>
		</row>
	</grid>
</data>
`
* Properties files for buttons on SplashScreen (indicating simulation options), OptionsScreen (indicating neighborhood shape/type and toroidality options), and SimulationView buttons (indicating png/valid image files) 

### Decisions, assumptions, simplifications 


### Known bugs, crashes, problems 
* In the options screen, users must click on their neighborhood type/shape selection first, followed by their desired toroidality setting, in order for the simulation to begin. The buttons have been organized to encourage this order of clicking, but if this order is violated, the simulation may not begin successfully. 

### Impressions 
It may have been useful to talk about nested classes and invoking methods with Java reflection in lecture during this project. 
Additionally, it would have been useful to have a discussion about designing classes that can be implemented simultaneously. For example, we have a grid class that is important for creating the graphics regarding the cells which meant that it had to be modified in order to achieve several different goals in sprint two. However, this also meant that overlapping work was being done and the way we thought about our code was not ideal. 