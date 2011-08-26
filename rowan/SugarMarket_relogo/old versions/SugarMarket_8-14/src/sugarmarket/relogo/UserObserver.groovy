package sugarmarket.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{
	SugarPrice sp = new SugarPrice()
	
	def setup(){
		clearAll()
		sp.setup()
		createSugarTraders(numTraders){
			setup(sp)
			sugar = 10
			money = 100
			setxy(randomXcor(),randomYcor())
		}
	}
	
	def go(){
		
		ask(sugarTraders()){
			step()
		}
		sp.newSugarPrice(getPriceOfSugar())
		sp.newDemandCount(getBuyers()-sp.totalSugarSoldThisTick)
		sp.totalTicks++
		print(sp.totalTicks)
		Thread.sleep(50)
	}
	
	def sellSugar(){
		sp.bankSugar += bankSugarQuant
		sp.bankPrice = bankSugarPrice
	}
	
	def getPriceOfSugar(){
		def sugarSold = 0
		def price = 0
		def priceOfSugar = sp.lastSugarPrice
		ask(sugarTraders()){
			if(soldThisTick){
				sugarSold++
				price += sellPrice
			}
		}
		sp.totalSugarSoldThisTick = sugarSold
		if(sp.bankSugarSoldThisTick > 0){
			sugarSold += sp.bankSugarSoldThisTick
			price += sp.bankPrice*sp.bankSugarSoldThisTick
		}
		if(sugarSold > 0){
			priceOfSugar = price / sugarSold
		}
		return priceOfSugar
	}
	
	def getSellers(){
		def total = 0
		ask(sugarTraders()){if(it.selling)total++}
		return total
	}
	
	def getBuyers(){
		def total = 0
		ask(sugarTraders()){if(it.buying)total++}
		return total
	}
		
	def averageSellPrice(){
		def total = 0
		def traders = 0
		ask(sugarTraders()){
			if(it.selling){
				total = total + it.sellPrice
				traders++
			}
		}
		if(traders == 0) return 0
		else return total/traders
	}
	/*
	def averageBuyPrice(){
		def total = 0
		def traders = 0
		ask(sugarTraders()){
			if(it.buying){
				total = total + it.buyPrice
				traders++
			}
		}
		if(traders == 0) return 0
		else return total/traders
	}
	*/
	def poorestTraderMoney(){
		def poorest = minOneOf(sugarTraders()){getMoney()}
		return poorest.getMoney()
	}
	
	def richestTraderMoney(){
		def richest = maxOneOf(sugarTraders()){getMoney()}
		return richest.getMoney()
	}
	
	def largestSugarStockpile(){
		def richest = maxOneOf(sugarTraders()){getSugar()}
		return richest.getSugar()
	}
	
	def smallestSugarStockpile(){
		def poorest = minOneOf(sugarTraders()){getSugar()}
		return poorest.getSugar()
	}
	def demand(){
		return sp.demandLastTick
	}
	
	def bankSugarForSale(){
		return sp.bankSugarForSale()
	}
	
	def sugarSoldThisTick(){
		return sp.totalSugarSoldThisTick
	}
}