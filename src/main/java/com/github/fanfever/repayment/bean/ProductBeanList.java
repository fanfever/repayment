package com.github.fanfever.repayment.bean;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "data")
public class ProductBeanList {

	private List<ProductBean> productBeanList;

	@XmlElement(name = "row")
	public List<ProductBean> getProductBeanList() {
		return productBeanList;
	}

}
