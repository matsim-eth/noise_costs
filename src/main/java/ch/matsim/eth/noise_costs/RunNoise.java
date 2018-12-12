package ch.matsim.eth.noise_costs;

import ch.matsim.eth.noise_costs.data.NoiseContext;
import ch.matsim.eth.noise_costs.handler.LinkSpeedCalculation;
import ch.matsim.eth.noise_costs.handler.NoiseTimeTracker;
import ch.matsim.eth.noise_costs.handler.PersonActivityTracker;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.OutputDirectoryLogging;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.events.algorithms.EventWriterXML;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.File;
import java.io.IOException;

public class RunNoise {
    private static final Logger log = Logger.getLogger(RunNoise.class);

    private Scenario scenario;
    private String config;
    private String events;
    private String outputFilePath;

    private NoiseContext noiseContext = null;
    private NoiseTimeTracker timeTracker = null;

    public static void main(String[] args) {
        String config = "/home/ctchervenkov/Documents/scenarios/siouxfalls-2014/config_default.xml";
        String events = "/home/ctchervenkov/Documents/scenarios/siouxfalls-2014/10.events.xml.gz";
        String outputFilePath = "/home/ctchervenkov/Documents/projects/road_pricing/noise_output/";
        new RunNoise(config, events, outputFilePath).run();
    }

    public RunNoise(String config, String events, String outputFilePath) {
        this.config = config;
        this.events = events;
        this.outputFilePath = outputFilePath;
    }

    public void run() {

        Scenario scenario = ScenarioUtils.loadScenario(ConfigUtils.loadConfig(this.config));
        NoiseConfigGroup noiseParameters = ConfigUtils.addOrGetModule(scenario.getConfig(), NoiseConfigGroup.class) ;

        File file = new File(outputFilePath);
        file.mkdirs();

        OutputDirectoryLogging.catchLogEntries();
        try {
            OutputDirectoryLogging.initLoggingWithOutputDirectory(outputFilePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        noiseContext = new NoiseContext(scenario);
        NoiseWriter.writeReceiverPoints(noiseContext, outputFilePath + "/receiverPoints/", false);

        EventsManager eventsManager = EventsUtils.createEventsManager();

        timeTracker = new NoiseTimeTracker();
        timeTracker.setNoiseContext(noiseContext);
        timeTracker.setEvents(eventsManager);
        timeTracker.setOutputFilePath(outputFilePath);

        eventsManager.addHandler(timeTracker);

        if (noiseContext.getNoiseParams().isUseActualSpeedLevel()) {
            LinkSpeedCalculation linkSpeedCalculator = new LinkSpeedCalculation();
            linkSpeedCalculator.setNoiseContext(noiseContext);
            eventsManager.addHandler(linkSpeedCalculator);
        }

        EventWriterXML eventWriter = null;
        if (noiseContext.getNoiseParams().isThrowNoiseEventsAffected() || noiseContext.getNoiseParams().isThrowNoiseEventsCaused()) {
            eventWriter = new EventWriterXML(outputFilePath + "events_NoiseImmission_Offline.xml.gz");
            eventsManager.addHandler(eventWriter);
        }

        if (noiseContext.getNoiseParams().isComputePopulationUnits()) {
            PersonActivityTracker actTracker = new PersonActivityTracker(noiseContext);
            eventsManager.addHandler(actTracker);
        }

        log.info("Reading events file...");
        MatsimEventsReader reader = new MatsimEventsReader(eventsManager);
        reader.readFile(this.events);
        log.info("Reading events file... Done.");

        timeTracker.computeFinalTimeIntervals();

        if (noiseContext.getNoiseParams().isThrowNoiseEventsAffected() || noiseContext.getNoiseParams().isThrowNoiseEventsCaused()) {
            eventWriter.closeFile();
        }
        log.info("Noise calculation completed.");

    }
}
