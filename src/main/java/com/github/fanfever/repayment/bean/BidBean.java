package com.github.fanfever.repayment.bean;

import com.github.fanfever.repayment.utils.RepaymentType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BidBean {

	private int period;
	private BigDecimal amount;
	private BigDecimal online_year_irr;
	private BigDecimal offline_year_irr;
	private RepaymentType online_repayment_type;
	private RepaymentType offline_repayment_type;

}
