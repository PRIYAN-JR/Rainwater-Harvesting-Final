package com.example.rainwater;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    @PostMapping("/calculate")
    public CalculationResult calculate(@RequestBody CalculationRequest r) {
        if (r == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Request body is required.");
        }

        if (r.roofArea() <= 0 || r.rainfallMm() < 0
                || r.familyMembers() <= 0 || r.dailyNeedPerPerson() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Enter valid positive roof area and family size. "
                    + "Rainfall and water need cannot be negative.");
        }

        RoofType roofType = RoofType.fromInput(r.roofType());
        if (roofType == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid roof type.");
        }

        boolean monthly = "monthly".equalsIgnoreCase(r.period() == null ? "" : r.period().trim());

        double coefficient = roofType.getRunoffCoefficient();

        double harvested = RainwaterCalculator.calculateHarvestedWater(
                r.roofArea(), r.rainfallMm(), coefficient);

        double demand;
        double average;
        if (monthly) {
            demand = RainwaterCalculator.calculateMonthlyRequirement(
                    r.familyMembers(), r.dailyNeedPerPerson());
            average = harvested / 30;   // daily average for the month
        } else {
            demand = RainwaterCalculator.calculateAnnualRequirement(
                    r.familyMembers(), r.dailyNeedPerPerson());
            average = RainwaterCalculator.calculateMonthlyAverage(harvested);
        }

        double difference = harvested - demand;

        return new CalculationResult(
                monthly ? "monthly" : "annual",
                r.month() == null ? "" : r.month(),
                coefficient,
                Math.round(harvested),
                Math.round(average),
                Math.round(demand),
                Math.round(Math.max(0, difference)),
                Math.round(Math.max(0, -difference)),
                difference >= 0);
    }

    public record CalculationRequest(double roofArea, double rainfallMm, String roofType,
                                     int familyMembers, double dailyNeedPerPerson,
                                     String period, String month) {}

    public record CalculationResult(String period, String month, double runoffCoefficient,
                                    long collectedLitres, long monthlyAverageLitres,
                                    long householdDemandLitres, long surplusLitres,
                                    long deficitLitres, boolean demandMet) {}
}
