package smallworld.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * Adaptation of small-world network model found in
 * 
 * Watts, D. J. & Strogatz, S. H. (1998). Collective dynamics of Ôsmall-worldÕ networks. Nature 393, 440Ð442.
 * 
 * @author Tim Sweda
 *
 */

class UserObserver extends BaseObserver{

	def setup() {
		clearAll()
		setDefaultShape(Node, "circle")
		nextNode = 0
		rewiredColor = green()
		if (numNodes > 2*initialNodeDegree && initialNodeDegree > ln(numNodes)) {
			// Setup initial ring lattice
			createNodes(numNodes) {
				initialize()
			}
			ask (nodes()) {
				def k = who+initialNodeDegree/2
				createLinksWith(nodes().with({mod(k-who, numNodes) < initialNodeDegree/2}))
			}
		}
		else if (numNodes <= 2*initialNodeDegree) {
			// May not be able to rewire nodes properly
			userMessage("ERROR:  numNodes does not exceed 2*initialNodeDegree")
		}
		else if (initialNodeDegree <= ln(numNodes)) {
			// May have too many isolated nodes after rewiring
			userMessage("ERROR:  initialNodeDegree does not exceed ln(numNodes)")
		}
	}
	
	// Rewire one node
	def rewireOne() {
		if (nextNode < numNodes) {
			ask (nodes().with({who == nextNode})) {
				rewire()
			}
		}
		else {
			userMessage("No more nodes to rewire")
		}
	}
	
	// Rewire all nodes
	def rewireAll() {
		if (nextNode < numNodes) {
			ask (sort(nodes())) {
				rewire()
			}
		}
		else {
			userMessage("No more nodes to rewire")
		}
	}
	
	// Calculate and report average path length and clustering coefficient
	def reportStats() {
		calculatePathLengths()
		ask (nodes()) {
			calculateClusterCoeff()
		}
		def pl = mean({avgDistance}.of(nodes()))
		def cc = mean({nodeClusterCoeff}.of(nodes()))
		userMessage("Average Path Length:  "+pl+"\nClustering Coefficient:  "+cc)
	}
	
	// Calculate path lengths between each node pair
	def calculatePathLengths() {
		def sortedNodes = sort(nodes())
		def minWho = min({who}.of(nodes()))
		ask (nodes()) {
			distance = []
		}
		// Implement Floyd-Warshall algorithm for finding all-pairs shortest paths
		ask (sortedNodes) {
			def i = self()
			ask (sortedNodes) {
				def initialDist = numNodes
				if (who == {who}.of(i)) {
					initialDist = 0
				}
				else if (linkNeighborQ(i)) {
					initialDist = 1
				}
				ask (i) {
					distance = lput(initialDist, distance)
				}
			}
		}
		ask (sortedNodes) {
			def k = self()
			ask (sortedNodes) {
				def i = self()
				ask (sortedNodes) {
					def j = self()
					def dist = item({who}.of(k)-minWho, {distance}.of(i))+item(who-minWho, {distance}.of(k))
					if (dist < item(who-minWho, {distance}.of(i))) {
						ask (i) {
							distance = replaceItem({who}.of(j)-minWho, distance, dist)
						}
					}
				}
			}
		}
		// Calculate average path length for each node
		ask (nodes()) {
			avgDistance = mean(distance)
		}
	}

}