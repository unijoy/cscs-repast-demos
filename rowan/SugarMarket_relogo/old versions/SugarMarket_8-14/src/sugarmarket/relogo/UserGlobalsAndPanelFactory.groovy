package sugarmarket.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		/**
		 * Uncomment by removing "//" below to include ReLogo Tick Display
		 */
		//addReLogoTickCountDisplay()
		
		
		  		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
				addButtonWL("go","Go Once")
		  		addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
  			    addSliderWL("numTraders", "Number of Sugar Traders", 0, 1, 100, 25)
//				addButtonWL("sellSugar","Sell Sugar From Bank")
//				addSliderWL("bankSugarPrice","Bank's Specified Sugar Sell Price",0.0,0.1,10.0,5.0)
//				addSliderWL("bankSugarQuant","Bank's Sugar Quantity per Sell",1,1,20,5)
				addMonitorWL("averageSellPrice","av sell price",1)
//				addMonitorWL("averageBuyPrice","asd;fasdf",1)
//				addMonitorWL("richestTraderMoney","Richest Trader's Money",1)
//				addMonitorWL("poorestTraderMoney","Poorest Trader's Money",1)
//				addMonitorWL("bankSugarForSale","Bank Sugar for Sale",1)
				addMonitor("demand",1)
				addMonitor("largestSugarStockpile",1)
				addMonitor("smallestSugarStockpile",1)
				addMonitor("sugarSoldThisTick",1)
				
		  /**		addToggleButton("action2")		// Toggle Button without label  ( method name )
		  		addGlobal("globalVariable1")	// Globally accessible variable ( variable name)
		  		// Slider with label ( variable name, slider label, minimum value, increment, maximum value, initial value )
		        addSliderWL("sliderVariable", "Slider Variable", 0, 1, 10, 5)
		        // Slider without label ( variable name, minimum value, increment, maximum value, initial value )
		        addSlider("sliderVariable2", 0.2, 0.1, 0.8, 0.5)
		        // Chooser with label  ( variable name, chooser label, list of choices , zero-based index of initial value )
		        addChooserWL("chooserVariable", "Chooser Variable", ["yes","no","maybe"], 2)
		        // Chooser without label  ( variable name, list of choices , zero-based index of initial value )
		        addChooser("chooserVariable2", [1, 66, "seven"], 0)
		 */

	}
}