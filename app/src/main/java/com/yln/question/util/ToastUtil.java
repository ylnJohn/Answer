/**
 * ToastUtil.java	  V1.0   2013-4-16 上午10:50:00
 *
 * Copyright Talkweb Information System Co. ,Ltd. All rights reserved.
 *
 * Modification history(By    Time    Reason):
 * 
 * Description:
 */

package com.yln.question.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	public static void showToast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context,int str){
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
}
