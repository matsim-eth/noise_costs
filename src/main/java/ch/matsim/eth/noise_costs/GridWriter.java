package ch.matsim.eth.noise_costs;

import java.io.*;

public class GridWriter {
    private double[][] grid;

    public GridWriter(double[][] grid) {
        this.grid = grid;
    }

    public void write(String outputPath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)));
    }

}
