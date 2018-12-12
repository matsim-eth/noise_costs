/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2014 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

/**
 * 
 */
package ch.matsim.eth.noise_costs.data;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;

import java.util.*;

/**
 * 
 * Extends the basic information of a receiver point towards data required for the computation of noise.
 * 
 * @author ikaddoura
 *
 */
public class NoiseReceiverPoint extends ReceiverPoint {
	
	public NoiseReceiverPoint(Id<ReceiverPoint> id, Coord coord) {
		super(id, coord);
	}

	private Map<Id<Person>, List<PersonActivityInfo>> personId2actInfos = null;//new HashMap<>(0);
	
	// initialization
	private Map<Id<Link>, Double> linkId2distanceCorrection = null;//new HashMap<>(0);
	private Map<Id<Link>, Double> linkId2angleCorrection = null;//new HashMap<>(0);
			
	// time-specific information
	private double finalImmission = 0.;
	private double affectedAgentUnits = 0.;
	private double affectedHomeFacilityUnits = 0.;

    private double rentDamageCosts;
    private double rentCostsPerAffectedHomeFacilityUnit;

    private double healthDamageCosts;
    private double healthCostsPerAffectedAgentUnit;

    private double totalDamageCosts;


	public Map<Id<Person>, List<PersonActivityInfo>> getPersonId2actInfos() {
		if(personId2actInfos == null) {
			personId2actInfos = new HashMap<>(4);
		}
		return Collections.unmodifiableMap(personId2actInfos);
	}

	public void addPersonActInfo(Id<Person> person, PersonActivityInfo info) {
		if(personId2actInfos == null) {
			personId2actInfos = new HashMap<>(4);
		}
		List<PersonActivityInfo> infos = this.personId2actInfos.get(person);
		if(infos == null) {
			infos = new ArrayList<>();
			this.personId2actInfos.put(person, infos);
		}
		infos.add(info);
	}
	
	public Map<Id<Link>, Double> getLinkId2distanceCorrection() {
		if(linkId2distanceCorrection == null) {
			linkId2distanceCorrection = new HashMap<>();
		}
		return Collections.unmodifiableMap(linkId2distanceCorrection);
	}

	public void setLinkId2distanceCorrection(Id<Link> linkId, Double distanceCorrection) {
		if(linkId2distanceCorrection == null) {
			linkId2distanceCorrection = new HashMap<>();
		}
		this.linkId2distanceCorrection.put(linkId, distanceCorrection);
	}

	public Map<Id<Link>, Double> getLinkId2angleCorrection() {
		if(linkId2angleCorrection== null) {
			linkId2angleCorrection = new HashMap<>();
		}
		return Collections.unmodifiableMap(linkId2angleCorrection);
	}

	public void setLinkId2angleCorrection(Id<Link> linkId, Double angleCorrection) {
		if(linkId2angleCorrection== null) {
			linkId2angleCorrection = new HashMap<>();
		}
		this.linkId2angleCorrection.put(linkId, angleCorrection);
	}

	public double getFinalImmission() {
		return finalImmission;
	}

	public void setFinalImmission(double finalImmission) {
		this.finalImmission = finalImmission;
	}

    public double getHealthDamageCosts() {
        return healthDamageCosts;
    }

    public void setHealthDamageCosts(double healthDamageCosts) {
        this.healthDamageCosts = healthDamageCosts;
    }

    public double getHealthCostsPerAffectedAgentUnit() {
		return healthCostsPerAffectedAgentUnit;
	}

	public void setHealthCostsPerAffectedAgentUnit(
			double healthCostsPerAffectedAgentUnit) {
		this.healthCostsPerAffectedAgentUnit = healthCostsPerAffectedAgentUnit;
	}

	public double getAffectedAgentUnits() {
		return affectedAgentUnits;
	}

	public void setAffectedAgentUnits(double affectedAgentsUnits) {
		this.affectedAgentUnits = affectedAgentsUnits;
	}

    public double getRentDamageCosts() {
        return rentDamageCosts;
    }

    public void setRentDamageCosts(double rentDamageCosts) {
        this.rentDamageCosts = rentDamageCosts;
    }

    public double getRentCostsPerAffectedHomeFacilityUnit() {
        return rentCostsPerAffectedHomeFacilityUnit;
    }

    public void setRentCostsPerAffectedHomeFacilityUnit(double rentCostsPerAffectedHomeFacilityUnit) {
        this.rentCostsPerAffectedHomeFacilityUnit = rentCostsPerAffectedHomeFacilityUnit;
    }

    public double getAffectedHomeFacilityUnits() {
        return affectedHomeFacilityUnits;
    }

    public void setAffectedHomeFacilityUnits(double affectedHomeFacilityUnits) {
        this.affectedHomeFacilityUnits = affectedHomeFacilityUnits;
    }

    public double getTotalDamageCosts() {
        return totalDamageCosts;
    }

    public void setTotalDamageCosts(double totalDamageCosts) {
        this.totalDamageCosts = totalDamageCosts;
    }

	@Override
	public String toString() {
		return "NoiseReceiverPoint [personId2actInfos=" + personId2actInfos
				+ ", linkId2distanceCorrection=" + linkId2distanceCorrection
				+ ", linkId2angleCorrection=" + linkId2angleCorrection
//				+ ", linkId2IsolatedImmission=" + linkId2IsolatedImmission
//				+ ", linkId2IsolatedImmissionPlusOneCar=" + linkId2IsolatedImmissionPlusOneCar
//				+ ", linkId2IsolatedImmissionPlusOneHGV=" + linkId2IsolatedImmissionPlusOneHGV 
				+ ", finalImmission=" + finalImmission 
				+ ", affectedAgentUnits=" + affectedAgentUnits
                + ", affectedHomeFacilityUnits=" + affectedHomeFacilityUnits
                + ", rentDamageCosts=" + rentDamageCosts
                + ", rentCostsPerAffectedHomeFacilityUnit=" + rentCostsPerAffectedHomeFacilityUnit
                + ", healthDamageCosts=" + healthDamageCosts
                + ", healthCostsPerAffectedAgentUnit=" + healthCostsPerAffectedAgentUnit
				+ ", totalDamageCosts=" + totalDamageCosts
                + "]";
	}

	public void reset() {
		resetTimeInterval();
		this.personId2actInfos = null;
	}
	
	public void resetTimeInterval() {
//		linkId2IsolatedImmission.clear();
		this.setFinalImmission(0.);
		this.setAffectedAgentUnits(0.);
		this.setHealthDamageCosts(0.);
		this.setRentDamageCosts(0.);
		this.setHealthCostsPerAffectedAgentUnit(0.);
		this.setRentCostsPerAffectedHomeFacilityUnit(0.);
	}
}
