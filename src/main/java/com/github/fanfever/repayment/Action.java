package com.github.fanfever.repayment;

import com.github.fanfever.repayment.bean.BidBean;
import com.github.fanfever.repayment.bean.ProductBean;
import com.github.fanfever.repayment.bean.RepaymentBean;
import com.github.fanfever.repayment.utils.RepaymentType;
import com.github.fanfever.repayment.view.AboutDialog;
import com.github.fanfever.repayment.view.AlertDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class Action {

	@FXML
	private VBox vbox;

	@FXML
	private TextField amount, period, online_year_irr, offline_year_irr;

	@FXML
	private ChoiceBox<String> online_repayment_type, offline_repayment_type;

	@FXML
	private TableView<RepaymentBean> tableView;

	// ObservableList<String> cb1List = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		// online_repayment_type.setItems(cb1List);
		period.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				onlineYearIrrChange(null);
			}
		});

		online_repayment_type.getSelectionModel().select(0);
		offline_repayment_type.getSelectionModel().select(0);
		online_repayment_type.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int repaymentType = 0;
				if (newValue.intValue() == 0) {
					repaymentType = 1;
				}
				onlineYearIrrChange(repaymentType);
			}
		});
	}

	private void onlineYearIrrChange(Integer repaymentType) {
		try {
			ProductBean tempProductBean = new ProductBean();
			tempProductBean.setProduct_name(null == repaymentType ? (RepaymentType.getByContent(online_repayment_type.getValue()) == RepaymentType.REPAYMENT_TYPE_ACPI ? "GQ群富通" : "GQ冠惠通") : (repaymentType != 1 ? "GQ群富通" : "GQ冠惠通"));
			
			tempProductBean.setPeriod(Integer.parseInt(period.getText()));
			ProductBean productBean = Business.INSTANCE.getProductBean(tempProductBean);
			online_year_irr.setText(String.valueOf(productBean.getYear_irr()));
		} catch (RuntimeException e) {
		}
	}

	@FXML
	private void onAboutAction(ActionEvent event) {
		AboutDialog.showAlertDialog();
	}

	@FXML
	private void onMenuExit(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	private void onlineRepaymentTypeUpdateText() {
		try {
			ProductBean tempProductBean = new ProductBean();
			tempProductBean.setProduct_name(RepaymentType.getByContent(online_repayment_type.getValue()) == RepaymentType.REPAYMENT_TYPE_ACPI ? "GQ群富通" : "GQ冠惠通");
			tempProductBean.setPeriod(Integer.parseInt(period.getText()));
			ProductBean productBean = Business.INSTANCE.getProductBean(tempProductBean);
			online_year_irr.setText(String.valueOf(productBean.getYear_irr()));
		} catch (RuntimeException e) {
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void submit(ActionEvent event) {
		BidBean bidBean = new BidBean();
		try {
			bidBean.setAmount(new BigDecimal(amount.getText()));
			bidBean.setPeriod(Integer.parseInt(period.getText()));
			bidBean.setOnline_year_irr(new BigDecimal(online_year_irr.getText()));
			bidBean.setOffline_year_irr(new BigDecimal(offline_year_irr.getText()));
			bidBean.setOnline_repayment_type(RepaymentType.getByContent(online_repayment_type.getValue()));
			bidBean.setOffline_repayment_type(RepaymentType.getByContent(offline_repayment_type.getValue()));

			Field[] fieldArray = RepaymentBean.class.getDeclaredFields();

			for (int i = 0; i < fieldArray.length; i++) {
				TableColumn tableColumn = ((TableColumn) tableView.getColumns().get(i));
				tableColumn.setCellValueFactory(new PropertyValueFactory(fieldArray[i].getName()));
			}

			ObservableList<RepaymentBean> list = FXCollections.observableArrayList();

			list.addAll(Business.INSTANCE.createRepayment(bidBean));
			tableView.setItems(list);

		} catch (RuntimeException e) {
			AlertDialog.showAlertDialog("输入参数有误, 请查证! ");
		}

	}
}
