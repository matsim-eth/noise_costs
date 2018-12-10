package ch.matsim.eth.noise_costs;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.network.Link;

import java.util.Map;

public class NoiseListener implements LinkEnterEventHandler {
    private Scenario scenario;
    private Map<Id<Link>, Double> linkId2Emission;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private double dx;
    private double dy;
    private int nx;
    private int ny;
    private double exposureRadius;
    private double[][] noiseGrid;

    public NoiseListener(Scenario scenario, double xMin, double xMax, double yMin, double yMax, double dx, double dy, double exposureRadius) {
        this.scenario = scenario;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.dx = dx;
        this.dy = dy;
        this.exposureRadius = exposureRadius;
        this.nx = (int) Math.ceil((xMax - xMin) / dx);
        this.ny = (int) Math.ceil((yMax - yMin) / dy);
        this.noiseGrid = new double[nx][ny];
    }

    @Override
    public void handleEvent(LinkEnterEvent event) {
        Id<Link> linkId = event.getLinkId();
        Coord linkCoord = scenario.getNetwork().getLinks().get(linkId).getCoord();

        int ix_c = (int) Math.floor((linkCoord.getX() - xMin) / dx);
        int iy_c = (int) Math.floor((linkCoord.getY() - yMin) / dy);

        int dix = (int) Math.round(this.exposureRadius / this.dx);
        int diy = (int) Math.round(this.exposureRadius / this.dy);

        int ix_min = Math.max(ix_c - dix, 0);
        int iy_min = Math.max(iy_c - diy, 0);
        int ix_max = Math.min(ix_c + dix, this.nx);
        int iy_max = Math.min(iy_c + diy, this.ny);

        for (int ix = ix_min; ix < ix_max; ix++) {
            for (int iy = iy_min; iy < iy_max; iy++) {
                this.noiseGrid[ix][iy] += 1 / ( Math.sqrt( Math.pow( (ix_c - ix) , 2.0) + Math.pow( (iy_c - iy) , 2.0)) + 1.0);
            }
        }
    }

    @Override
    public void reset(int iteration) {
    }

    public double[][] getNoiseGrid() {
        return noiseGrid;
    }
}
