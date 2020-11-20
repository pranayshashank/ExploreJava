package core.app;

import core.service.DisplayService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DisplayApp {

    public static void main(String[] args) {
        System.out.println(">> Starting spring container.");
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext("core.config")) {
            System.out.println(">> Instantiating DisplayService");
            DisplayService service = context.getBean(DisplayService.class);

            System.out.println(">> Executing sampleDisplay");
            service.sampleDisplay();

            DisplayApp app = new DisplayApp();
        }
        System.out.println(">> Terminating DisplayService now.");
    }


}
