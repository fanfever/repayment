package com.github.fanfever.repayment.bean;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "row")
public class ProductBean {

	private String product_name;

	private Integer period;

	private Double year_irr;

}
