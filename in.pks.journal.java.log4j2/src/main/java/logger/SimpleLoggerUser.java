package logger;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.io.IOException;

public class SimpleLoggerUser {

    static {
        try {
            initializeLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        // test now.
        BusinessModuleLog.print();
    }

    private static void initializeLogger() throws IOException {
        // create a configuration Builder first
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        // Create a Pattern Layout now
        LayoutComponentBuilder standard = builder.newLayout("PatternLayout")
                //.addAttribute("pattern", "%d [%t] %-5level: %msg%n%C");
                //.addAttribute("pattern", "[%p]\t[%d] [Thread: %t] [%c :: %M] - %m%n");
                .addAttribute("pattern", "[%p]\t[%d] [Thread: %t] [%c] - %m%n");

        // next create an Appender builder and add it to ConfigurationBuilder
        configureConsoleAppender(builder, standard);

        // now configure a File appender
        configureFileAppender(builder, standard);

        // Configure a Rolling File appender
        configureRollingFileAppender(builder, standard);

        // Configure Root logger
        configureRootLogger(builder);

        // Add additional logger
        addAdditionalLogger(builder);

        // Write XML Configuration
        builder.writeXmlConfiguration(System.out);

        // Finally, initialize the configurator
        Configurator.initialize(builder.build());
    }

    private static void configureConsoleAppender(ConfigurationBuilder<BuiltConfiguration> builder, LayoutComponentBuilder standard){
        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        console.add(standard);
        builder.add(console);
    }

    private static void configureFileAppender(ConfigurationBuilder<BuiltConfiguration> builder, LayoutComponentBuilder standard){
        AppenderComponentBuilder file = builder.newAppender("log", "File");
        file.addAttribute("fileName", "/Users/pranay/logs/logging.log");
        file.add(standard);
        builder.add(file);  // Add layout
    }

    private static void configureRollingFileAppender(ConfigurationBuilder<BuiltConfiguration> builder, LayoutComponentBuilder standard){
        AppenderComponentBuilder rollingFile = builder.newAppender("rolling", "RollingFile");
        rollingFile.addAttribute("fileName", "/Users/pranay/logs/rolling.log");
        rollingFile.addAttribute("filePattern", "rolling-%d{MM-dd-yy}.log.gz");
        rollingFile.add(standard);  // Add layout

        ComponentBuilder triggeringPolicies = builder.newComponent("Policies")
                .addComponent(builder.newComponent("CronTriggeringPolicy")
                        .addAttribute("schedule", "0 0 0 * * ?"))
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
                        .addAttribute("size", "100M"));
        rollingFile.addComponent(triggeringPolicies);

        builder.add(rollingFile);
    }

    private static void configureRootLogger(ConfigurationBuilder<BuiltConfiguration> builder){
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.DEBUG);
        rootLogger.add(builder.newAppenderRef("stdout"));
        rootLogger.add(builder.newAppenderRef("log"));

        builder.add(rootLogger);
    }

    private static void addAdditionalLogger(ConfigurationBuilder<BuiltConfiguration> builder){
        LoggerComponentBuilder logger = builder.newLogger("com", Level.DEBUG);
        logger.add(builder.newAppenderRef("rolling"));
        logger.addAttribute("additivity", false);

        builder.add(logger);
    }

}
