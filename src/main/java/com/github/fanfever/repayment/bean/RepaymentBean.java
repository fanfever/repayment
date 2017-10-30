package com.github.fanfever.repayment.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepaymentBean {

	private int period;

	private BigDecimal online_principal;
	private BigDecimal online_interest;
	private BigDecimal online_amount;

	private BigDecimal offline_principal;
	private BigDecimal offline_interest;
	private BigDecimal offline_amount;

	private BigDecimal diff_principal;
	private BigDecimal diff_interest;
	private BigDecimal diff_amount;

}
