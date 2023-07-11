package com.safetynet.alerts.unit.util;

import com.safetynet.alerts.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DateUtilsTest {

    private final DateUtils dateUtils = DateUtils.getInstance();

    @Test
    public void testIsMajor() {
        assertTrue(dateUtils.isMajor(dateUtils.getDate("01/01/2000")));
        assertFalse(dateUtils.isMajor(new Date()));
        assertTrue(dateUtils.isMajor(dateUtils.getDate("01/01/2000").getTime()));
        assertFalse(dateUtils.isMajor(new Date().getTime()));
    }

    @Test
    public void testGetAge() {
        assertEquals(23, dateUtils.getAge(dateUtils.getDate("01/01/2000"))); // Tested on june 2023
        assertEquals(0, dateUtils.getAge(new Date()));
    }

    @Test
    public void testGetDate() {
        assertEquals(dateUtils.getDate("01/01/2000"), dateUtils.getDate(946681200000L));
        assertThrows(RuntimeException.class, () -> dateUtils.getDate("zzz"));
    }

    @Test
    public void testGetDateWithFormat() {
        assertEquals(dateUtils.getFormattedDate(dateUtils.getDate("01/01/2000")), dateUtils.getFormattedDate(dateUtils.getDate("01/01/2000"), "dd/MM/yyyy"));
    }
}
