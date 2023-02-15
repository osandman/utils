package net.osandman.util.parsing;

import org.junit.jupiter.api.Test;

import static net.osandman.util.parsing.AverageGrade.*;
import static org.junit.jupiter.api.Assertions.*;

class AverageGradeTest {

    @Test
    void getAverageGradeTest() {
        assertEquals(4.50d, getAverageSumFromSingleDigits("a45d!5p4", 2));
        assertEquals(4.00d, getAverageSumFromSingleDigits("3-4-5", 2));
        assertEquals(4.33d, getAverageSumFromSingleDigits(" 4 4 5 ", 2));
        assertEquals(3.75d, getAverageSumFromSingleDigits("(4+4+4+3)", 2));
    }
}