package com.github.fanfever.repayment.utils;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

public enum RepaymentType {

	/**
	 * 等额本息
	 */
	REPAYMENT_TYPE_ACPI(1, "等额本息"),
	/**
	 * 先息后本
	 */
	REPAYMENT_TYPE_ATFI(2, "先息后本"),

	/**
	 * 等额等息
	 */
	REPAYMENT_TYPE_ACAI(3, "等额等息"),
	/**
	 * 自定义
	 */
	REPAYMENT_TYPE_CUSTOM(4, "自定义");

	@Getter
	private final int value;

	@Getter
	private final String content;

	RepaymentType(@NonNull final int value, @NonNull final String content) {
		this.value = value;
		this.content = content;
	}

	public static RepaymentType getByContent(final String content){
		return Lists.newArrayList(RepaymentType.values()).stream().filter(i -> i.getContent().equals(content)).findFirst().orElseThrow(() -> new RuntimeException(""));
	}

}
