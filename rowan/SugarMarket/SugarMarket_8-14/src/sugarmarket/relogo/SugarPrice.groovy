package sugarmarket.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ReLogoModel;

class SugarPrice {
	double lastSugarPrice
	def prices = new ArrayList<Double>()
	int bankSugar
	double bankPrice
	int bankSugarSoldThisTick
	int totalTicks
	int totalSugarSoldThisTick
	int demandLastTick
	boolean demandUp
	
	def setup(){
		lastSugarPrice = randomFloat(8.0)+1.0
		prices.clear()
		prices.add(0)
		bankSugar = 0
		bankPrice = 0
		bankSugarSoldThisTick = 0
		totalTicks = 0
		totalSugarSoldThisTick = 0
		demandLastTick = 0
		demandUp = false
	}
	
	def newSugarPrice(double price){
		lastSugarPrice = price
		prices.add(price)
	}
	
	def newDemandCount(int demand){
		if(demand > demandLastTick) demandUp = true
		else demandUp = false
		demandLastTick = demand
	}
	
	def getPriceAtTick(int tick){
		return prices.get(tick)
	}
	
	def bankSugarForSale(){
		return bankSugar
	}
	
	def getNetChangeOverNTicks(int ticks){
		if(ticks >= prices.size()){
			return lastSugarPrice
		}else{
			return lastSugarPrice - prices.get((int)(totalTicks - ticks))
		}
	}
}
