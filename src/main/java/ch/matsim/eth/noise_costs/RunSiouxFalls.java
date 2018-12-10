package ch.matsim.eth.noise_costs;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

public class RunSiouxFalls {
    public static void main(String[] args) {
        String configPath = args[0];
        RunSiouxFalls.run(ConfigUtils.loadConfig(configPath));
    }

    static void run(Config config) {
        config.controler().setLastIteration(10);
        config.controler().setWriteEventsInterval(2);



        Scenario scenario = ScenarioUtils.loadScenario(config);


        Controler controler = new Controler(scenario);
        controler.run();
    }
}
