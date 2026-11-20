package com.example.rainwater;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RainwaterCalculatorTest {

    @Test
    void acceptsCommonRoofTypeNames() {
        assertEquals(RoofType.CONCRETE, RoofType.fromInput("concrete"));
        assertEquals(RoofType.METAL, RoofType.fromInput("metal sheet"));
        assertEquals(RoofType.METAL, RoofType.fromInput("metal-sheet"));
        assertEquals(RoofType.CEMENT_SHEET, RoofType.fromInput("cement sheet"));
        assertEquals(RoofType.CEMENT_SHEET, RoofType.fromInput("cement-sheet"));
    }

    @Test
    void calculatesValuesForRequestedRoofType() {
        CalculatorController controller = new CalculatorController();

        CalculatorController.CalculationResult result = controller.calculate(
                new CalculatorController.CalculationRequest(
                        100,
                        700,
                        "metal sheet",
                        4,
                        23,
                        "annual",
                        "January"
                )
        );

        assertEquals("annual", result.period());
        assertEquals(0.9, result.runoffCoefficient());
        assertEquals(63000, result.collectedLitres());
        assertEquals(5250, result.monthlyAverageLitres());
        assertEquals(33580, result.householdDemandLitres());
        assertEquals(29420, result.surplusLitres());
    }

    @Test
    void rejectsMissingOrUnknownRoofTypes() {
        CalculatorController controller = new CalculatorController();

        assertThrows(ResponseStatusException.class, () -> controller.calculate(
                new CalculatorController.CalculationRequest(
                        100,
                        700,
                        null,
                        4,
                        23,
                        "annual",
                        "January"
                )
        ));

        assertThrows(ResponseStatusException.class, () -> controller.calculate(
                new CalculatorController.CalculationRequest(
                        100,
                        700,
                        "unknown",
                        4,
                        23,
                        "annual",
                        "January"
                )
        ));
    }
}
