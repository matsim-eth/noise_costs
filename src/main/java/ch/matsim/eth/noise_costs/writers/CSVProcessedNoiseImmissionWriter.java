package ch.matsim.eth.noise_costs.writers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

public class CSVProcessedNoiseImmissionWriter {
    final private Collection<DailyCountItem> items;

    public CSVDailyCountsWriter(Collection<DailyCountItem> items) {
        this.items = items;
    }

    public void write(String outputPath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath)));

        writer.write(formatHeader() + "\n");
        writer.flush();

        for (DailyCountItem item : items) {
            writer.write(formatItem(item) + "\n");
            writer.flush();
        }

        writer.flush();
        writer.close();
    }

    private String formatHeader() {
        return String.join(";", new String[] {
                "Receiver Point Id", "x", "y"
        });
    }

    private String formatItem(DailyCountItem item) {
        return String.join(";", new String[] {
                item.countStationId,
                item.link.toString(),
                String.valueOf(item.location.getX()),
                String.valueOf(item.location.getY()),
                String.valueOf(item.reference),
                String.valueOf(item.simulation)
        });
    }
}
