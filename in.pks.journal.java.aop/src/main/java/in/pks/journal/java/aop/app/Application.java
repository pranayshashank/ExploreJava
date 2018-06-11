package in.pks.journal.java.aop.app;

import in.pks.journal.java.aop.config.SimpleConfiguration;
import in.pks.journal.java.aop.dto.SimpleDTO;
import in.pks.journal.java.aop.util.SimpleUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SimpleConfiguration.class)){

            SimpleUtil util = (SimpleUtil) context.getBean(SimpleUtil.class);
            SimpleDTO dto = new SimpleDTO();
            dto.setDate(LocalDate.now());
            dto.setPlace("BLR, India");
            LocalDate localDate = util.dateAfterDays(dto, null);
            System.out.println(localDate);

            //util.printDate(dto);
            //util.printDate(null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
