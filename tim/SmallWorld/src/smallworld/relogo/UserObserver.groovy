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
 * Watts, D. J., & Strogatz, S. H. (1998). Collective dynamics of Ôsmall-worldÕ networks. Nature 393, 440Ð442.
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
			userMessage("ERROR:  # Nodes does not exceed 2*(Initial Node Degree).")
		}
		else if (initialNodeDegree <= ln(numNodes)) {
			// Graph more likely to not be connected after rewiring
			userMessage("ERROR:  Initial Node Degree does not exceed ln(# Nodes).")
		}
	}
	
	// Rewire one node
	def rewireOne() {
		if (nextNode < numNodes)
			ask (nodes().with({who == nextNode})) {
				rewire()
			}
		else
			userMessage("No more nodes to rewire")
	}
	
	// Rewire all nodes
	def rewireAll() {
		if (nextNode < numNodes)
			ask (sort(nodes())) {
				rewire()
			}
		else
			userMessage("No more nodes to rewire")
	}
	
	// Calculate and report average path length and clustering coefficient (with ratios relative to
	//   original graph in parentheses)
	def reportStats() {
		def n = numNodes
		def k = initialNodeDegree
		def nkterm = ceiling((n-1)/k)
		calculatePathLengths()
		ask (nodes()) {
			calculateClusterCoeff()
		}
		def globalPL = mean({avgPathLength}.of(nodes()))
		def globalCC = mean({nodeClusterCoeff}.of(nodes()))
		def initialPL = (k*nkterm*(nkterm+1)/2-nkterm*mod(1-n, k))/(n-1)
		def initialCC = 3*k*(k/2-1)/(2*k*(k-1))
		userMessage("Average Path Length:  "+globalPL+" ("+globalPL/initialPL+")\n"+
			"Clustering Coefficient:  "+globalCC+" ("+globalCC/initialCC+")")
	}
	
	// Calculate path lengths between each node pair
	def calculatePathLengths() {
		def sortedNodes = sort(nodes())
		def minWho = min({who}.of(nodes()))
		ask (nodes()) {
			pathLengths = []
		}
		// Implement Floyd-Warshall algorithm for finding all-pairs shortest paths
		ask (sortedNodes) {
			def i = self()
			ask (sortedNodes) {
				def pl = numNodes
				if (who == {who}.of(i))
					pl = 0
				else if (linkNeighborQ(i)) {
					pl = 1
				}
				ask (i) {
					pathLengths = lput(pl, pathLengths)
				}
			}
		}
		ask (sortedNodes) {
			def k = self()
			ask (sortedNodes) {
				def i = self()
				ask (sortedNodes) {
					def j = self()
					def dist = item({who}.of(k)-minWho, {pathLengths}.of(i))+item(who-minWho, {pathLengths}.of(k))
					if (dist < item(who-minWho, {pathLengths}.of(i)))
						ask (i) {
							pathLengths = replaceItem({who}.of(j)-minWho, pathLengths, dist)
						}
				}
			}
		}
		// Calculate average path length for each node
		// NOTE:  mean(pathLengths) is the average path length from the node to any node (including itself),
		//   so multiplying by numNodes/(numNodes-1) gives the average path length to any other node
		ask (nodes()) {
			avgPathLength = mean(pathLengths)*numNodes/(numNodes-1)
		}
	}

}