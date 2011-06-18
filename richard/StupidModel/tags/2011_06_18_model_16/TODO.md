# Revision info #
* $HeadURL$
* $LastChangedDate$
* $LastChangedRevision$
* $LastChangedBy$
* $Id$

# TODO #
This file contains some minor-major current TODO elements.

## General ##

### Possible bugs? ###
* Reinit fails for some reason sometimes: 2-3 init calls are required to display the grid, why?

## Model 4##
* How to probe ValueLayerProbeObject2D? There are 2 options here:
	* The HabitatCells may be probed. This way we have no Value Layers, but HabitatCell objects in the grid
	  The Bug's move action need a bit of update. 
	* We use value layers and see the grass growing

* Error reporting: revert everywhere to MessageCenter's functions (that is the standard Repast S way to do it)

* Commit backwards:
	* Bug#foodConsumption() from Model 3
	* Multi occuppancy from Model 3
	* Cell background should fade from black to green, not whit to green (pre-14)

### FIXMEs ###
* How to infer generic types with `StupidModelContextBuilder#init(Context)`?
