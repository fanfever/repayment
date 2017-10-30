package com.github.fanfever.repayment.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AboutDialog extends AnchorPane {
	private static Stage newAlertDialog;

	private AboutDialog() {
		FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/AboutDialog.fxml"));
		fXMLLoader.setRoot(AboutDialog.this);
		fXMLLoader.setController(AboutDialog.this);
		try {
			fXMLLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static void showAlertDialog() {
		if (newAlertDialog == null) {
			newAlertDialog = new Stage(StageStyle.DECORATED);
			newAlertDialog.setResizable(false);
			AboutDialog wiAlertDialog = new AboutDialog();
			newAlertDialog.setTitle("关于");
			newAlertDialog.setScene(new Scene(wiAlertDialog, 600, 400));
			newAlertDialog.show();
		} else {
			newAlertDialog.show();
		}
	}

	public static void showAboutDialog() {
		showAlertDialog();
	}
}
