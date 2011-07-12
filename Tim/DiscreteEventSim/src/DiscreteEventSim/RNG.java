package DiscreteEventSim;

import java.util.Random;

public class RNG extends Random {
	
	public RNG() {
		
	}
	
	// Initialize random number generator with a seed value (useful for comparing results across runs)
	public RNG(long seed) {
		setSeed(seed);
	}
	
	
	// DISCRETE DISTRIBUTIONS
	
	public int nextBernoulli(double p) {
		/*
		 *  p = probability of success
		 */
		if (nextDouble() < p)
			return 1;
		else
			return 0;
	}
	
	public int nextBinomial(int n, double p) {
		/*
		 *  n = number of trials
		 *  p = probability of success in each trial
		 */
		int x = 0;
		for (int i=0; i<n; i++)
			x += nextBernoulli(p);
		return x;
	}
	
	public int nextGeometric(double p) {
		/*
		 *  p = probability of success
		 */
		return (int)(Math.log(nextDouble())/Math.log(1-p));   // for support on {0,1,2,...}
		//return 1+(int)(Math.log(nextDouble())/Math.log(1-p)); // for support on {1,2,...}
	}
	
	public int nextNegBinomial(int r, double p) {
		/*
		 *  r = number of failures
		 *  p = probability of success in each trial
		 */
		int x = 0, n = 0;
		while (n < r)
			if (nextBernoulli(p) == 0)
				n++;
			else
				x++;
		return x;
	}
	
	public int nextPoisson(double lambda) {
		/*
		 *  lambda = rate parameter
		 */
		double u, p = 1;
		int i = 0;
		do {
			i++;
			u = nextDouble();
			p = p*u;
		} while (p > Math.exp(-lambda));
		return i-1;
	}
	
	public int nextUniform(int a, int b) {
		/*
		 *  a = min value
		 *  b = max value
		 */
		return a+nextInt(b-a+1);
	}
	
	
	// CONTINUOUS DISTRIBUTIONS
	
	public double nextErlang(int k, double lambda) {
		/*
		 *  k = shape parameter
		 *  lambda = rate parameter
		 */
		double sum = 0;
		for (int i=0; i<k; i++)
			sum += nextExponential(1/lambda);
		return sum;
	}
	
	public double nextExponential(double lambda) {
		/*
		 *  lambda = rate parameter
		 */
		return -Math.log(nextDouble())/lambda;
	}
	
	public double nextLogNormal(double mu, double sigma) {
		/*
		 *  mu = mean of log(X)
		 *  sigma = standard deviation of log(X)
		 */
		return Math.exp(nextNormal(mu, sigma));
	}
	
	public double nextNormal(double mu, double sigma) {
		/*
		 *  mu = mean
		 *  sigma = standard deviation
		 */
		return mu+nextGaussian()*sigma;
	}
	
	public double nextNormalPos(double mu, double sigma) {
		/*
		 *  mu = mean
		 *  sigma = standard deviation
		 *  NOTE:  this function only returns positive values
		 */
		double v;
		do {
			v = mu+nextGaussian()*sigma;
		} while (v < 0);
		return v;
	}
	
	public double nextTriangular(double a, double b, double c) {
		/*
		 *  a = min value
		 *  b = max value
		 *  c = mode/peak value (a<=c<=b)
		 */
		double u = nextDouble();
		double frac = (c-a)/(b-a);
		double v;
		if (u < frac)
			v = Math.sqrt(u*frac);
		else
			v = 1-Math.sqrt((1-u)*(1-frac));
		return a+(b-a)*v;
	}
	
	public double nextUniform(double a, double b) {
		/*
		 *  a = min value
		 *  b = max value
		 */
		return a+(b-a)*nextDouble();
	}
	
	public double nextWeibull(double lambda, double k) {
		/*
		 *  lambda = scale parameter
		 *  k = shape parameter
		 */
		return lambda*Math.pow(-Math.log(nextDouble()),1/k);
	}
	
}