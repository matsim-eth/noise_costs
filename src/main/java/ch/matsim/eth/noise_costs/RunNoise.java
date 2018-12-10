package ch.matsim.eth.noise_costs;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsManagerImpl;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class RunNoise {
    private Scenario scenario;
    private String config;
    private String events;

    public static void main(String[] args) {
        String config = "/home/ctchervenkov/Documents/scenarios/siouxfalls-2014/config_default.xml";
        String events = "/home/ctchervenkov/Documents/scenarios/siouxfalls-2014/10.events.xml.gz";
        new RunNoise(config, events).run();
    }

    public RunNoise(String config, String events) {
        this.config = config;
        this.events = events;
    }

    public void run() {
        Scenario scenario = ScenarioUtils.loadScenario(ConfigUtils.loadConfig(config));
        double[] box = NetworkUtils.getBoundingBox(scenario.getNetwork().getNodes().values());

        EventsManager eventsManager = new EventsManagerImpl();
        NoiseListener noiseListener = new NoiseListener(scenario, box[0], box[2], box[1], box[3], 100.0, 100.0, 500.0);
        eventsManager.addHandler(noiseListener);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(events);

        noiseListener.getNoiseGrid();

        System.exit(1);

    }
}
