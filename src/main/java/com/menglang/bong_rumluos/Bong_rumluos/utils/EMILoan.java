package com.menglang.bong_rumluos.Bong_rumluos.utils;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.EMIResponse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EMILoan {

    public static List<EMIResponse> calculateEMILoan(double principal, double annualInterestRate, LocalDate start_date, LocalDate end_date) {
        List<EMIResponse> schedule = new ArrayList<>();

        // Convert annual interest rate to monthly rate
        double monthlyInterestRate = annualInterestRate / 12 / 100;

        //calculateMonths
        long tenureInMonths=calculateTotalMonths(start_date,end_date);

        // Calculate EMI using the formula
        double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureInMonths)) /
                (Math.pow(1 + monthlyInterestRate, tenureInMonths) - 1);

        // Initialize variables
        double outstandingBalance = principal;

        for (int months = 1; months <=tenureInMonths ; months++) {
            double interestPayment = outstandingBalance * monthlyInterestRate;
            double principalPayment = emi - interestPayment;
            outstandingBalance -= principalPayment;

            // Ensure the final outstanding balance is zero (handle rounding errors)
            outstandingBalance = Math.max(outstandingBalance, 0);

            // Add the result to the schedule
            schedule.add(new EMIResponse(months, emi, principalPayment, interestPayment, outstandingBalance));
        }
        return schedule;
    }

    public static Long calculateTotalMonths(LocalDate start_date,LocalDate end_date){
        return ChronoUnit.MONTHS.between(start_date, end_date);
    }



}
