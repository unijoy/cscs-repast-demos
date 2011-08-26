package sugarmarket.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class SugarTrader extends BaseTurtle {
	
	double sellPrice = randomFloat(10);
	double sugar = 0;
	double money = 0;
	double cheapThreshold = randomFloat(2.5)+2.5
	boolean soldThisTick = false
	boolean boughtThisTick = false
	boolean selling = true
	boolean buying = true
	int ticksSinceLastPurchase = 0
	int ticksSinceLastSell = 0
	int preferredPurchaseFrequency = random(14)+2
	int lookBackNTicks = random(10)+2
	SugarPrice sugarPrice
	
	def setup(SugarPrice sp){
		sugarPrice = sp
	}
	
	def step(){
		soldThisTick = false
		buying = buying()
		if(buying){
//			decideBuyPrice()
			checkMarket()
		}
		decideSellPrice()
		if(!boughtThisTick){ticksSinceLastPurchase++}
		else{ticksSinceLastPurchase = 0}
		if(!soldThisTick){ticksSinceLastSell++}
		else{ticksSinceLastSell = 0}
	}
	
	boolean buying(){
		if(money == 0){
			return false
		}
		//add in bank clause
		if(sugarPrice.lastSugarPrice < cheapThreshold) return true
		if(ticksSinceLastPurchase > preferredPurchaseFrequency){
			return true
		}else{
			if(sugarPrice.getNetChangeOverNTicks(lookBackNTicks) < -3){//basically, if prices have dropped a lot then 
				return true											   //buying is a very good idea
			}
			return false
		}
	}
	
	/**
	 * Sees if there are sellers selling at the rate at which the buyer is willing to buy.
	 * @return
	 */
	def checkMarket(){
		def lowestSeller = minOneOf(sugarTradersWithSugar()){sellPrice}
		
		if(sugarPrice.bankSugar > 0 && sugarPrice.bankPrice < lowestSeller.sellPrice){
			if(money >= sugarPrice.bankPrice){
				buyFromBank()
			}
		}else if(money >= lowestSeller.sellPrice && 
		   lowestSeller.getSugar() > 0){
		   	  buy(lowestSeller)
//			  print("sold!")
		}
	}
	
	/**
	 * Decides what the SugarTrader is willing to sell a unit of sugar for.
	 * @return
	 */
	def decideSellPrice(){
		sellPrice = sugarPrice.lastSugarPrice
		if(sugarPrice.demandLastTick > 0){//if there was more demand than supply
			sellPrice += randomFloat(1.0) //then this turn the trader will increase their asking price
		}
		if(sugarPrice.demandUp){
			sellPrice += randomFloat(1.0)
		}
		
		/*
		if(soldThisTick){
			sellPrice += randomFloat(1.0)
		}
		if(bullMarket()){
			sellPrice += sugarPrice.getNetChangeOverNTicks(lookBackNTicks) / 2
		}else{
			if(random(1))sellPrice += sugarPrice.getNetChangeOverNTicks(lookBackNTicks)
			else sellPrice -= sugarPrice.getNetChangeOverNTicks(lookBackNTicks)
		}
		*/
		sellPrice += (randomFloat(1.0)-0.5)//this adds a small random change
		if(sellPrice > 10) sellPrice = (9.0 + randomFloat(1.0)) 
		if(sugar == 0 || sellPrice < 0 || sugarPrice.lastSugarPrice < cheapThreshold){
			sellPrice = 99999
			selling = false
		}else selling = true
	}
	/*
	boolean bullMarket(){
		if(pricesGoingUp())return true
		else return false
	}
	*/
	boolean pricesGoingUp(){
		if(sugarPrice.getNetChangeOverNTicks(lookBackNTicks) > 0){
			return true
		}else return false
	}
	
	def buy(trader){
		trader.sugar--
		sugar++
		money = money - trader.sellPrice
		trader.money = trader.money + trader.sellPrice
		trader.soldThisTick = true
		boughtThisTick = true
	}
	
	def buyFromBank(){
		sugarPrice.bankSugar--
		sugar++
		money = money - sugarPrice.bankPrice
		boughtThisTick = true
		sugarPrice.bankSugarSoldThisTick++
	}
	
	def sugarTradersWithSugar(){
		def traders = sugarTraders()
//		traders.clear()
//		traders = sugarTraders()
		/*hmm... should work... check boids
		for(SugarTrader trader : traders){
			if(trader.selling){
				traders.remove(trader)
			}
		}*/
		traders.remove(this)
		return traders
	}
}