package ch.matsim.eth.noise_costs;

public class Grid {
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final double dx;
    private final double dy;
    private final int nx;
    private final int ny;
    private double[][] grid;

    public Grid(double xMin, double xMax, double yMin, double yMax, double dx, double dy) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.dx = dx;
        this.dy = dy;
        this.nx = (int) Math.ceil((xMax - xMin) / dx);
        this.ny = (int) Math.ceil((yMax - yMin) / dy);
        this.grid = new double[nx][ny];
    }

    public void addValue(double x, double y, double value) {
        if (this.xMin <= x && this.xMax >= x && this.yMin <= y && this.yMax >= y) {
            int[] index = getIndex(x, y);
            grid[index[0]][index[1]] += value;
        }
    }

    public double getValue(double x, double y, double value) {
        int[] index = getIndex(x, y);
        return grid[index[0]][index[1]];
    }

    public int[] getIndex(double x, double y) {
        int ix = (int) Math.floor((x - xMin) / dx);
        int iy = (int) Math.floor((y - yMin) / dy);
        return new int[]{ix, iy};
    }
}
