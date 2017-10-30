package com.github.fanfever.repayment;


import com.github.fanfever.repayment.bean.BidBean;
import com.github.fanfever.repayment.bean.ProductBean;
import com.github.fanfever.repayment.bean.ProductBeanList;
import com.github.fanfever.repayment.bean.RepaymentBean;
import com.github.fanfever.repayment.utils.CalculatorUtils;
import com.github.fanfever.repayment.utils.MapperUtils;
import com.github.fanfever.repayment.utils.RepaymentType;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public enum Business {

	INSTANCE;

	public List<RepaymentBean> createRepayment(BidBean bidBean) {
		BigDecimal online_principal = BigDecimal.ZERO;
		BigDecimal offline_principal = BigDecimal.ZERO;
		List<RepaymentBean> list = new ArrayList<RepaymentBean>();
		for (int thePeriod = 1; thePeriod <= bidBean.getPeriod(); thePeriod++) {
			RepaymentBean repaymentBean = new RepaymentBean();
			repaymentBean.setPeriod(thePeriod);
			if (bidBean.getOnline_repayment_type() == RepaymentType.REPAYMENT_TYPE_ACPI) {
				if (thePeriod < bidBean.getPeriod()) {
					repaymentBean.setOnline_principal(CalculatorUtils.getACPIMonthPaymentPrincipalByThePeriod(bidBean.getAmount(), bidBean.getOnline_year_irr(), bidBean.getPeriod(), thePeriod));
					online_principal = online_principal.add(repaymentBean.getOnline_principal());
				} else {
					repaymentBean.setOnline_principal(bidBean.getAmount().subtract(online_principal));
				}
				repaymentBean.setOnline_interest(CalculatorUtils.getACPIMonthPaymentInterestByThePeriod(bidBean.getAmount(), bidBean.getOnline_year_irr(), bidBean.getPeriod(), thePeriod));
				if (thePeriod < bidBean.getPeriod()) {
					repaymentBean.setOnline_amount(CalculatorUtils.getACPIMonthPaymentAmount(bidBean.getAmount(), bidBean.getOnline_year_irr(), bidBean.getPeriod()));
				} else {
					repaymentBean.setOnline_amount(repaymentBean.getOnline_principal().add(repaymentBean.getOnline_interest()));
				}
			}
			if (bidBean.getOnline_repayment_type() == RepaymentType.REPAYMENT_TYPE_ATFI) {
				repaymentBean.setOnline_principal(CalculatorUtils.getATFIMonthPaymentPrincipalByThePeriod(bidBean.getAmount(), bidBean.getOnline_year_irr(), bidBean.getPeriod(), thePeriod));
				repaymentBean.setOnline_interest(CalculatorUtils.getATFIMonthPaymentInterestByThePeriod(bidBean.getAmount(), bidBean.getOnline_year_irr(), bidBean.getPeriod(), thePeriod));
				repaymentBean.setOnline_amount(CalculatorUtils.getATFIMonthPaymentAmount(bidBean.getAmount(), bidBean.getOnline_year_irr(), bidBean.getPeriod(), thePeriod));
			}
			if (bidBean.getOffline_repayment_type() != RepaymentType.REPAYMENT_TYPE_CUSTOM) {
				if (bidBean.getOffline_repayment_type() == RepaymentType.REPAYMENT_TYPE_ACAI) {
					if (thePeriod < bidBean.getPeriod()) {
						repaymentBean.setOffline_principal(CalculatorUtils.getACAIMonthPaymentPrincipal(bidBean.getAmount(), bidBean.getOnline_year_irr(), bidBean.getPeriod()));
						offline_principal = offline_principal.add(repaymentBean.getOffline_principal());
					} else {
						repaymentBean.setOffline_principal(bidBean.getAmount().subtract(offline_principal));
					}
					repaymentBean.setOffline_interest(CalculatorUtils.getACAIMonthPaymentInterest(bidBean.getAmount(), bidBean.getOffline_year_irr(), bidBean.getPeriod()));
					if (thePeriod < bidBean.getPeriod()) {
						repaymentBean.setOffline_amount(CalculatorUtils.getACAIMonthPaymentAmount(bidBean.getAmount(), bidBean.getOffline_year_irr(), bidBean.getPeriod()));
					} else {
						repaymentBean.setOffline_amount(repaymentBean.getOffline_principal().add(repaymentBean.getOffline_interest()));
					}
				}
				if (bidBean.getOffline_repayment_type() == RepaymentType.REPAYMENT_TYPE_ATFI) {
					repaymentBean.setOffline_principal(CalculatorUtils.getATFIMonthPaymentPrincipalByThePeriod(bidBean.getAmount(), bidBean.getOffline_year_irr(), bidBean.getPeriod(), thePeriod));
					repaymentBean.setOffline_interest(CalculatorUtils.getATFIMonthPaymentInterestByThePeriod(bidBean.getAmount(), bidBean.getOffline_year_irr(), bidBean.getPeriod(), thePeriod));
					repaymentBean.setOffline_amount(CalculatorUtils.getATFIMonthPaymentAmount(bidBean.getAmount(), bidBean.getOffline_year_irr(), bidBean.getPeriod(), thePeriod));
				}
				repaymentBean.setDiff_principal(repaymentBean.getOffline_principal().subtract(repaymentBean.getOnline_principal()));
				repaymentBean.setDiff_interest(repaymentBean.getOffline_interest().subtract(repaymentBean.getOnline_interest()));
				repaymentBean.setDiff_amount(repaymentBean.getOffline_amount().subtract(repaymentBean.getOnline_amount()));
			}
			list.add(repaymentBean);
		}
		return list;
	}

	public ProductBean getProductBean(ProductBean productBean) {
		List<ProductBean> productBeanList = queryProductBeanList();
		for (ProductBean tempProductBean : productBeanList) {
			if (tempProductBean.getProduct_name().contains(productBean.getProduct_name()) && tempProductBean.getPeriod().intValue() == productBean.getPeriod().intValue()) {
				return tempProductBean;
			}
		}
		return null;
	}

	@SuppressWarnings("resource")
	private List<ProductBean> queryProductBeanList() {
		String result;
		try {
			File directory = new File("");
			InputStreamReader isr = new InputStreamReader(new FileInputStream(directory.getCanonicalPath() + "\\product.xml"), "UTF-8");
			BufferedReader in = new BufferedReader(isr);
			result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			return MapperUtils.converyToJavaBean(result, ProductBeanList.class).getProductBeanList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
