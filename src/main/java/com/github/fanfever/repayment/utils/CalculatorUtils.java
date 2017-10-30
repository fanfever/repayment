package com.github.fanfever.repayment.utils;

import java.math.BigDecimal;

public class CalculatorUtils {

	private static final Integer DIVIDE_SCALE = 10;

	private static BigDecimal getMonthIrr(BigDecimal yearIrr){
		return yearIrr.divide(new BigDecimal(12), DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getACPIMonthPaymentAmount(BigDecimal amount, BigDecimal yearIrr, int period) {
		BigDecimal monthIrr = getMonthIrr(yearIrr);
		BigDecimal monthInterest = amount.multiply(monthIrr);
		BigDecimal pow = new BigDecimal(Math.pow(monthIrr.add(BigDecimal.ONE).doubleValue(), period));
		BigDecimal a = monthInterest.multiply(pow);
		BigDecimal b = pow.subtract(BigDecimal.ONE);
		return a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getACPIMonthPaymentPrincipalByThePeriod(BigDecimal amount, BigDecimal yearIrr, int period, int thePeriod) {
		BigDecimal monthIrr = getMonthIrr(yearIrr);
		BigDecimal monthInterest = amount.multiply(monthIrr);
		BigDecimal pow = new BigDecimal(Math.pow(monthIrr.add(BigDecimal.ONE).doubleValue(), thePeriod - 1));
		BigDecimal a = monthInterest.multiply(pow);
		BigDecimal b = new BigDecimal(Math.pow(monthIrr.add(BigDecimal.ONE).doubleValue(), period)).subtract(BigDecimal.ONE);
		return a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getACPIMonthPaymentInterestByThePeriod(BigDecimal amount, BigDecimal yearIrr, int period, int thePeriod) {
		return getACPIMonthPaymentAmount(amount, yearIrr, period).subtract(getACPIMonthPaymentPrincipalByThePeriod(amount, yearIrr, period, thePeriod));
	}

	public static BigDecimal getACAIMonthPaymentAmount(BigDecimal amount, BigDecimal yearIrr, int period) {
		BigDecimal monthIrr = getMonthIrr(yearIrr);
		BigDecimal monthCapital  = amount.divide(new BigDecimal(period), DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
		BigDecimal monthInterest = amount.multiply(monthIrr);
		return monthCapital.add(monthInterest);
	}

	public static BigDecimal getACAIMonthPaymentPrincipal(BigDecimal amount, BigDecimal yearIrr, int period) {
		return amount.divide(new BigDecimal(period), 2, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getACAIMonthPaymentInterest(BigDecimal amount, BigDecimal yearIrr, int period) {
		BigDecimal monthIrr = getMonthIrr(yearIrr);
		return amount.multiply(monthIrr);
	}

	public static BigDecimal getATFIMonthPaymentAmount(BigDecimal amount, BigDecimal yearIrr, int period, int thePeriod) {
		BigDecimal monthIrr = getMonthIrr(yearIrr);
		BigDecimal monthInterest = amount.multiply(monthIrr);
		return period == thePeriod ? amount.add(monthInterest) : monthInterest;
	}

	public static BigDecimal getATFIMonthPaymentPrincipalByThePeriod(BigDecimal amount, BigDecimal yearIrr, int period, int thePeriod) {
		return period == thePeriod ? amount : BigDecimal.ZERO;
	}

	public static BigDecimal getATFIMonthPaymentInterestByThePeriod(BigDecimal amount, BigDecimal yearIrr, int period, int thePeriod) {
		BigDecimal monthIrr = getMonthIrr(yearIrr);
		return amount.multiply(monthIrr);
	}

	public static BigDecimal getSpecialProductEachInterest(BigDecimal amount, BigDecimal yearIrr) {
		BigDecimal monthIrr = getMonthIrr(yearIrr);
		return amount.multiply(monthIrr);
	}

}
