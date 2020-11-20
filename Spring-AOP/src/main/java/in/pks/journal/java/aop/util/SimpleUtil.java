package in.pks.journal.java.aop.util;

import in.pks.journal.java.aop.dto.SimpleDTO;
import in.pks.journal.java.aop.annotation.Constraint;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SimpleUtil {

    public LocalDate dateAfterDays(@Constraint("NotNull") SimpleDTO dto, @Constraint("NotNull") Integer numberOfDays){
        return dto.getDate().plusDays(numberOfDays);
    }

    public void printDate(@Constraint("NotNull") SimpleDTO dto){
        System.out.println(dto.getDate());
    }

}
