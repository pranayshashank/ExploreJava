package in.pks.journal.java.aop.util;

import in.pks.journal.java.aop.config.SimpleConfiguration;
import in.pks.journal.java.aop.dto.SimpleDTO;
import in.pks.journal.java.aop.exception.ConstraintValidationException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class SimpleUtilTest {

    SimpleUtil util = null;
    SimpleDTO dto = null;

    @Before
    public void initBean(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SimpleConfiguration.class);
        util = (SimpleUtil) context.getBean(SimpleUtil.class);

        dto = new SimpleDTO();
        dto.setDate(LocalDate.now());
        dto.setPlace("BLR, India");
    }

    @Test
    public void testPrintDateWithNotNullParam(){
        util.printDate(dto);
    }

    @Test (expected = ConstraintValidationException.class)
    public void testPrintDateWithNullParam(){
        util.printDate(null);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testDateAfterDaysWithDtoParamNull(){
        util.dateAfterDays(null, 2);
    }


    @Test(expected = ConstraintValidationException.class)
    public void testDateAfterDaysWithNumOfDayParamNull(){
        util.dateAfterDays(dto, null);
    }

    @Test
    public void testDateAfterDaysWithNotNullParams(){
        util.dateAfterDays(dto, 3);
    }

}