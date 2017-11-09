package com.devpino.memberandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MemberDialog {

	public static final int YES_BUTTON = 1;
	public static final int NO_BUTTON = 2;
	
	private static int RESULT = -1;
	
	public static int showAlertDialog(String title, String message, Context context) {

		AlertDialog.Builder builder	= new AlertDialog.Builder(context);
		
		builder.setMessage(message)
			   .setTitle(title)
			   .setCancelable(false)
			   .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	
				   public void onClick(DialogInterface dialog, int id) {
					   dialog.cancel();
					   RESULT = YES_BUTTON;
					}});
		
		AlertDialog alert = builder.create();
		alert.show();
		
		return RESULT;
	}

	public static int showYesNoDialog(String title, String message, Context context) {

		AlertDialog.Builder builder	= new AlertDialog.Builder(context);
		AlertDialog alert = null;
		
		builder.setMessage(message)
			   .setTitle(title)
			   .setCancelable(false)
			   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

				   public void onClick(DialogInterface dialog, int id) {
						//
					   dialog.cancel();
					   RESULT = YES_BUTTON;
					}})
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						RESULT = NO_BUTTON;
					}});
		
		alert = builder.create();
		alert.show();
		
		return RESULT;
	}

}
