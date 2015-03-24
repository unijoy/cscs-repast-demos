# Contents #




---


# Summary #

  * Model        : MarkAndWalk
  * Platform     : [RepastS ReLogo](RepastSReLogo.md)
  * Source Code  : https://cscs-repast-demos.googlecode.com/svn/mudit/MarkAndWalk/
  * Documentation: http://code.google.com/p/cscs-repast-demos/wiki/MarkAndWalkDescription
  * Mentor       : Dr. Eric Tatara
  * Author       : [Mudit Raj Gupta](Mudit.md)


---


# 1. Description #

The model demonstrates a Mark and Walk algorithm. It is an improvement on using RandomWalk Algorithm in area coverage. This can be used to predict how many times a cell is revisited by the agents and how much time an agent colony takes takes for complete Area Coverage

https://cscs-repast-demos.googlecode.com/svn/wiki/Images_Mudit/WSN(Index).JPG


---


# 2. User Control #

The user can assign a "Coverage Range". The number of agents are also adjustable. Standard Setup, Go and Go Once Options are also present. User Panel looks like this :

https://cscs-repast-demos.googlecode.com/svn/wiki/Images_Mudit/WSN(panel).JPG



---


# 3. Output #

Model Outputter is also added. The added model outputter is comma separated values of "Time" and "Area Covered". Another Outputter which is added is between "Time" and "Number of times each cell is visited" Sample output files can be checked [here](https://code.google.com/p/cscs-repast-demos/source/browse/wiki/BiasedRandomWalk/ModelOutput.txt)

**Note:**

  1. This Output can be fed into various external plug-ins available with RepastS like MATLAB and spreadsheet.


---


# 4. Analysis #

Various analysis could be carried out with the model, but for demonstration purpose a graph between, "Number of Visits" on y-axis and "Time" on x-axis is plotted.

![https://cscs-repast-demos.googlecode.com/svn/wiki/Images_Mudit/WSNp2.png](https://cscs-repast-demos.googlecode.com/svn/wiki/Images_Mudit/WSNp2.png)

Another graph of area coverage with time is plotted.

![https://cscs-repast-demos.googlecode.com/svn/wiki/Images_Mudit/WSNp1.png](https://cscs-repast-demos.googlecode.com/svn/wiki/Images_Mudit/WSNp1.png)


---


# 5. Extending #

## 5.1 Adding Constrains ##

Try adding constrains like, at each time all agents should be in n-hop connectivity to the base station located at (0,0) or all agents should move in a single cluster.

## 5.2 Optimization ##

Try to add a few optimization conditions so that the number of time a cell is visited twice or thrice is reduced.


---


# 6. Applications #

## 6.1 Path planning in Robotics ##

The algorithm is used in path planning application for swarm robotics. Specially, in cases of De-mining operations and detecting Gas Leakage.


---
