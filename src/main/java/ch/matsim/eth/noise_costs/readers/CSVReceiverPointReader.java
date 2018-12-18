package ch.matsim.eth.noise_costs.readers;

import ch.matsim.eth.noise_costs.data.ReceiverPoint;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.core.utils.misc.Counter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReceiverPointReader {
    public Map<Id<ReceiverPoint>, Coord> read(String path) throws IOException {
        Map<Id<ReceiverPoint>, Coord> rp2Coord = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

        List<String> header = null;
        String line = null;

        Counter linecounter = new Counter("line #");

        while ((line = reader.readLine()) != null) {
            List<String> row = Arrays.asList(line.split(";"));

            if (row.size() > 3) {
                throw new RuntimeException("More than three columns. Aborting...");
            }

            if (header == null) {
                header = row;
            } else {
                Id<ReceiverPoint> rpId = Id.create(row.get(header.indexOf("receiverPointId")), ReceiverPoint.class);
                double x = Double.parseDouble(row.get(header.indexOf("xCoord")));
                double y = Double.parseDouble(row.get(header.indexOf("yCoord")));
                rp2Coord.putIfAbsent(rpId, new Coord(x,y));
                linecounter.incCounter();
            }
        }

        reader.close();
        linecounter.incCounter();
        linecounter.printCounter();

        return rp2Coord;
    }
}
