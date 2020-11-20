package core.service;

import core.beans.LCDDisplay;
import core.beans.LEDDisplay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DisplayService {
    @Inject
    private ApplicationContext context;

    @Value("${app.name}")
    private String appName;

    @Value("${env.name}-${app.version}")
    private String appVersion;

    public void sampleDisplay() {

        System.err.println(String.format("Application Name is: %s and version: %s",
                this.appName,
                this.appVersion));

        LEDDisplay ledDisplay = context.getBean("LEDDisplayBean", LEDDisplay.class);
        System.err.println(ledDisplay.display("Displaying message on LED: " + ledDisplay.getId()));
        System.err.println(ledDisplay.display("Resolution of LED Display: " + ledDisplay.getResolution()));

        LCDDisplay displayUnit = context.getBean("LCDDisplay", LCDDisplay.class);
        System.err.println(displayUnit.display("Displaying message on LCD: " + displayUnit.getId()));
    }
}
