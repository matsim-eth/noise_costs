package ch.matsim.eth.noise_costs.handler;

public class NoiseCostCalculator {
    private double rentCostPerBracket = 103.62;
    private double rentCostImissionsThreshold = 54.0;
    private double hypertensionCostPerBracket = 18.74;
    private double hypertensionCostImissionsThreshold = 56.0;
    private double ischemicHeartDiseaseCostPerBracket = 21.45;
    private double ischemicHeartDiseaseCostImissionsThreshold = 64.0;

    public NoiseCostCalculator(double rentCostPerBracket, double rentCostImissionsThreshold, double hypertensionCostPerBracket, double hypertensionCostImissionsThreshold, double ischemicHeartDiseaseCostPerBracket, double ischemicHeartDiseaseCostImissionsThreshold) {
        this.rentCostPerBracket = rentCostPerBracket;
        this.rentCostImissionsThreshold = rentCostImissionsThreshold;
        this.hypertensionCostPerBracket = hypertensionCostPerBracket;
        this.hypertensionCostImissionsThreshold = hypertensionCostImissionsThreshold;
        this.ischemicHeartDiseaseCostPerBracket = ischemicHeartDiseaseCostPerBracket;
        this.ischemicHeartDiseaseCostImissionsThreshold = ischemicHeartDiseaseCostImissionsThreshold;
    }

    public double calculateRentCosts(double noiseImmission, double affectedFacilityUnits, double timeInterval, double timeBinSize) {
        double rentCostBracket = 0.0;
        if (noiseImmission > rentCostImissionsThreshold) {
            rentCostBracket = (Math.round(noiseImmission) - rentCostImissionsThreshold);
        }
        double rentCostsPerFacility = rentCostBracket * rentCostPerBracket;

        return (rentCostsPerFacility) * affectedFacilityUnits;
    }

    public double calculateHealthCosts(double noiseImmission, double affectedAgentUnits, double timeInterval, double timeBinSize) {
        double hypertensionCostBracket = 0.0;
        if (noiseImmission > hypertensionCostImissionsThreshold) {
            hypertensionCostBracket = (Math.round(noiseImmission) - hypertensionCostImissionsThreshold);
        }
        double hypertensionCostsPerAgent = hypertensionCostBracket * hypertensionCostPerBracket;
        double hypertensionCosts = (hypertensionCostsPerAgent) * affectedAgentUnits;

        double ischemicHeartDiseaseCostBracket = 0.0;
        if (noiseImmission > ischemicHeartDiseaseCostImissionsThreshold) {
            ischemicHeartDiseaseCostBracket = (Math.round(noiseImmission) - ischemicHeartDiseaseCostImissionsThreshold);
        }
        double ischemicHeartDiseaseCostsPerAgent = ischemicHeartDiseaseCostBracket * ischemicHeartDiseaseCostPerBracket;
        double ischemicHeartDiseaseCosts = (ischemicHeartDiseaseCostsPerAgent) * affectedAgentUnits;

        return hypertensionCosts + ischemicHeartDiseaseCosts;
    }

    public double calculateDamageCosts(double noiseImmission, double affectedAgentUnits, double affectedFacilityUnits, double timeInterval, double timeBinSize) {
        return calculateRentCosts(noiseImmission, affectedFacilityUnits, timeInterval, timeBinSize) + calculateHealthCosts(noiseImmission, affectedAgentUnits, timeInterval, timeBinSize);
    }

    //	public static double calculateDamageCosts(double noiseImmission, double affectedAgentUnits, double timeInterval, double annualCostRate, double timeBinSize) {
//
//		String daytimeType = "NIGHT";
//
//		if (timeInterval > 6 * 3600 && timeInterval <= 18 * 3600) {
//			daytimeType = "DAY";
//		} else if (timeInterval > 18 * 3600 && timeInterval <= 22 * 3600) {
//			daytimeType = "EVENING";
//		}
//
//		double lautheitsgewicht = 0;
//
//		if (daytimeType == "DAY"){
//			if (noiseImmission < 50){
//			} else {
//				lautheitsgewicht = Math.pow(2.0 , 0.1 * (noiseImmission - 50));
//			}
//		} else if (daytimeType == "EVENING"){
//			if (noiseImmission < 45){
//			} else {
//				lautheitsgewicht = Math.pow(2.0 , 0.1 * (noiseImmission - 45));
//			}
//		} else if (daytimeType == "NIGHT"){
//			if (noiseImmission < 40){
//			} else {
//				lautheitsgewicht = Math.pow(2.0 , 0.1 * (noiseImmission - 40));
//			}
//
//		} else {
//			throw new RuntimeException("Neither day, evening nor night. Aborting...");
//		}
//
//		double laermEinwohnerGleichwert = lautheitsgewicht * affectedAgentUnits;
//		double damageCosts = ( annualCostRate * laermEinwohnerGleichwert / 365. ) * ( timeBinSize / (24.0 * 3600) );
//
//		return damageCosts;
//	}
}
