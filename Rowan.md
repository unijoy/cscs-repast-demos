# Repast #

### Models ###
  * [Boids](Boids.md) in Java and ReLogo (though the tutorial is only for Java)
  * [NBodies](NBodies.md)
  * [SugarMarket](SugarMarket.md)

### Ideas for future models ###
Here a more ideas that I had for models that I didn't have time to implement, but that I think would be a valuable addition to the Repast library. I leave these here as potential inspiration for future modelers.
  * simple traffic: an emergent behavior would be “phantom traffic” i.e. seemingly random starting and stopping once there are a sufficient number of cars on the highway. The ‘highway’ would have to be a closed track, and features that would probably need to be in place for phantom traffic to emerge would be the possibility of stochasticity in the speed of the vehicles, and traffic feeding onto and off of the highway.
  * simple weather: different conditions like humidity, temperature, particulate count, etc. start on one side of the world and move from left to right, depending on windspeed. For certain weather phenomena like thunderstorms to emerge, the variables and interactions would need to be carefully programmed. For example, for a thunderstorm to emerge, the model would have to be intricate enough to have layers of variables representing different layers of the atmosphere, and a way to recognize when warm air was rising fast enough to create cumulonimbus clouds. This may very well be outside the scope of this summer’s projects.
  * ant foraging: a number of ant foragers explore the map randomly until they encounter a food source. They then follow their trails back to the ant colony, calling gathering ants to follow their trail to the food source to collect food. The gathering ants follow the foraging ant’s path, with random permutations. These random permutations should eventually winnow the path down to the shortest path from the food source to the ant colony. There can be two versions: one simple one with just the ants and the food source, and another with random obstacles.
  * Penguin huddle: a group of penguins huddle together for warmth against biting cold winds. The penguin group as a whole regulates the warmth of each individual penguin, with every penguin spending roughly the same on the warm inside as on the cold outside of the huddle. This would be relatively easy for the penguins to do if each penguin could check the coldness of all of its neighbors, so we may want to assume that is the case for this simulation. A variable that could be introduced is the ‘aggressive’ penguin, who tries to stay on the inside and not take his/her fair turn on the outside of the huddle.
  * simple morphogenesis: it’d be interesting to try a simple activator/inhibitor simulator. The simplest model of morphogenesis would be of some sort of activation/inhibition of cells, and the clumping together of those activated and inhibited cells. My goal would be for a system like this to have emergent scroll shapes.
  * cellular automaton: developing the framework for Conway’s Game of Life shouldn’t be hard. It would be interesting to have the game or another form of cellular automaton program made on Repast.

# Myself #
I'm an undergrad at St. Mary's College of Maryland, a public liberal arts college. I'm doing my undergrad thesis on algorithmic swarm robotics, and using RepastS for modelling purposes. I am also picking up an Art major and Math minor.

email: rowan.copley@gmail.com