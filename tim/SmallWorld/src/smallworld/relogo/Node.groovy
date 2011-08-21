package smallworld.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * 
 * @author Tim Sweda
 *
 */

class Node extends BaseTurtle {
	
	def nodeClusterCoeff, pathLengths, avgPathLength
	
	def initialize() {
		size = 1/3+(200-numNodes)/300
		color = white()
		def r = 0.45*min([worldHeight(), worldWidth()])
		setxy(r*cos(360/numNodes*who), r*sin(360/numNodes*who))
		pathLengths = []
	}
	
	// Rewire this node
	def rewire() {
		def me = self()
		def newMe
		// Create new node; this will be the rewired node and will replace the existing node
		hatch(1) {
			initialize()
			color = rewiredColor
			newMe = self()
		}
		ask (linkNeighbors()) {
			def neighbor = self()
			// If link is eligible and selected to be rewired, rewire the link
			if (who < numNodes && randomFloat(1) < rewireProb) {
				def potentialNeighbors = nodes().with({!(linkNeighborQ(me) || linkNeighborQ(newMe)) && mod(who, numNodes) != {who}.of(me)})
				ask (newMe) {
					def newNeighbor = oneOf(potentialNeighbors)
					createLinkWith(newNeighbor) {
						color = rewiredColor
					}
				}
			}
			// Else, color link (to indicate that it has been considered) but do not rewire it
			else
				ask (newMe) {
					createLinkWith(neighbor) {
						color = rewiredColor
					}
				}
		}
		nextNode++
		// Remove old node since it has been replaced with rewired node
		die()
	}
	
	// Calculate clustering coefficient for this node
	def calculateClusterCoeff() {
		def neighbors = linkNeighbors()
		// If this node has 0 or 1 neighbors, its clustering coefficient is undefined 
		// (but usually set equal to 0 in practice)
		if (count(neighbors) < 2)
			nodeClusterCoeff = 0
		// Else, compute clustering coefficient as number of links among neighbors of this 
		// node divided by number of potential links among neighbors of this node
		else {
			def m = 0
			def n = count(neighbors)*(count(neighbors)-1)
			ask (neighbors) {
				ask (linkNeighbors()) {
					if (neighbors.contains(it))
						m++
				}
			}
			nodeClusterCoeff = m/n
		}
	}

}