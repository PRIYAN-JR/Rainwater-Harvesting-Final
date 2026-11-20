Rainwater Harvesting Potential Calculator for Households

Project Overview

The Rainwater Harvesting Potential Calculator for Households is a Java-based web application developed using Spring Boot. It helps households estimate the amount of rainwater that can be collected from their rooftops and compares the available rainwater with their daily and annual water requirements.

The application calculates rainwater harvesting potential based on rooftop area, rainfall, and roof type. It also estimates household water demand based on the number of family members and their daily water consumption.

Objectives

• Calculate monthly and annual rainwater harvesting potential.
• Estimate household water requirements.
• Consider different rooftop materials and their runoff coefficients.
• Identify water sufficiency, surplus, or deficit.
• Reduce manual calculations and improve accuracy.
• Promote rainwater harvesting and water conservation.

Technologies Used

• Programming Language: Java
• Framework: Spring Boot
• JDK: Java 17 or above
• Build Tool: Maven
• API: REST API
• Data Format: JSON
• IDE: Visual Studio Code

Key Features

1. Rainwater Calculation: Estimates the quantity of rainwater that can be collected from rooftops.
2. Roof Type Selection: Supports concrete, metal, tile, and cement sheet roofs.
3. Monthly and Annual Estimation: Calculates rainwater availability for different periods.
4. Household Water Demand: Estimates water requirements based on family size and daily consumption.
5. Surplus and Deficit Analysis: Compares available rainwater with household water demand.
6. Input Validation: Handles invalid inputs and provides appropriate error responses.

Working Principle

1. The user enters rooftop area and rainfall details.
2. The user selects the roof type.
3. The system determines the corresponding runoff coefficient.
4. The application calculates the estimated rainwater collection.
5. Household water demand is calculated using family size and daily consumption.
6. The system compares rainwater availability with household demand.
7. The results indicate whether the available rainwater is sufficient, in surplus, or in deficit.

Calculation

Rainwater Harvesting Potential:

Rainwater (Litres) = Rooftop Area (m²) × Rainfall (mm) × Runoff Coefficient

Household Water Demand:

Daily Demand (Litres) = Number of Family Members × Daily Water Requirement per Person

Annual demand is calculated using the daily requirement over the year.

Project Structure

The project is organised using Java source files and the Spring Boot application structure.

• "src/main/java" – Java source code
• "src/main/resources" – Application resources and configuration
• "pom.xml" – Maven dependencies and project configuration

Applications

• Household rainwater harvesting estimation
• Domestic water management
• Water conservation planning
• Sustainable water resource management
• Rainwater collection awareness

Future Enhancements

• Live rainfall data integration.
• Graphical representation of monthly water availability.
• Rainwater storage tank capacity estimation.
• Historical rainfall analysis.

Conclusion

This project provides an automated and user-friendly solution for estimating household rainwater harvesting potential. It reduces manual calculations, helps users understand their water availability, and encourages sustainable water conservation practices.

---

Project Domain: Java Development | Spring Boot | Web Application | Water Resource Management

Project Type: Academic Java Project
