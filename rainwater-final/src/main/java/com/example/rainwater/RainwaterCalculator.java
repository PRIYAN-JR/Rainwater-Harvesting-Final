package com.example.rainwater;

public class RainwaterCalculator {

    public static double calculateHarvestedWater(
            double roofArea,
            double rainfall,
            double runoffCoefficient) {

        return roofArea * rainfall * runoffCoefficient;
    }

    public static double calculateAnnualRequirement(
            int familyMembers,
            double dailyRequirement) {

        return familyMembers * dailyRequirement * 365;
    }

    public static double calculateMonthlyRequirement(
            int familyMembers,
            double dailyRequirement) {

        return familyMembers * dailyRequirement * 30;
    }

    public static double calculateMonthlyAverage(double annualHarvestedWater) {

        return annualHarvestedWater / 12;
    }
}
