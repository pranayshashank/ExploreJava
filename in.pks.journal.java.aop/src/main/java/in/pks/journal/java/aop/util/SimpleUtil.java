package in.pks.journal.java.aop.util;

import in.pks.journal.java.aop.dto.SimpleDTO;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SimpleUtil {

    public LocalDate dateAfterDays(@NonNull SimpleDTO dto, int numberOfDays){
        return dto.getDate().plusDays(numberOfDays);
    }

}
