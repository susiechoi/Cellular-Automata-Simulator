# Duplication Refactoring

There were only three instances of duplicated code, one of which was in Grid.java. No changes were made here, as doing so would have led to null-pointer exceptions. The code duplication that SonarQube pointed out was for-loop duplication, in which we looped through the 2D array of Cells twice, one "for" loop after another. This was because the first for loop initializes the cells, while the second one sets cell neighbors. Completing both of this functionality in a single loop would have been problematic because some neighbors would not yet be initialized. 

# Checklist refactoring 

We chose to address "Communication" issues in the Segregation and Grid classes by decomposing 1-line "if" statements into 2-line decompositions of the "if" with the action in curly braces. We also removed unused imports in Grid, SplashScreen, and SegregationCell. All of the "Communication" changes were considered major and required little discussion by the team. 

There were no notable errors in flexibility. There were a few errors under modularity regarding the STATE_COLORS array holding the colors for each state of cell in the simulations. We removed the duplication by creating a single Cell colors array in the Cell superclass. There were also a few errors regarding our use of ArrayLists in method parameters and returns instead of the more general List. We fixed these mistakes to make our code more flexible to varying data structures.

One notable highlight was the "No manager classes" check, which indicated that our Main had too many dependencies. The team had a hard time envisioning how we would delegate the role of screen transitions without creating dependencies with the screen transition objects/classes within Main, so this will be a subject for future discussion. However, we believe that the refatoring of Main may be too coordinate to implement at this point in time. 

# General refactoring

We tried to make detailed error messages in each "catch" block of the try/catches in the Grid class, most of which relate to the use of Java reflection. These were targeted so that a programmer would be better able to identify the issue and respond appropriately, especially since many of the same exceptions can be thrown in a single Java reflection method invocation. 