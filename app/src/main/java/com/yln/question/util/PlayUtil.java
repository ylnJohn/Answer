/**
 * PlayUtil.java	  V1.0   2013-6-15 下午8:21:27
 *
 * Copyright Talkweb Information System Co. ,Ltd. All rights reserved.
 *
 * Modification history(By    Time    Reason):
 * 
 * Description:
 */

package com.yln.question.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.yln.question.R;

import java.io.IOException;

/**
 * descrition：
 * 
 * @author yaolinnan
 * 
 *         <p>
 *         modify history:
 *         </p>
 */
public class PlayUtil {

	public static void playWin(MediaPlayer player, Context context) {
		if(player!=null)
			player.reset();
		else
			player=new MediaPlayer();
		AssetFileDescriptor afd=context.getResources().openRawResourceFd(R.raw.win);
//		if (!player.isPlaying()) {
		try {
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			player.prepare();
			player.setLooping(false);
			player.setVolume(1f,1f);
			player.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		}

	}

	public static void playLoss(MediaPlayer player, Context context) {
		if(player!=null)
			player.reset();
		else
			player=new MediaPlayer();
		AssetFileDescriptor afd=context.getResources().openRawResourceFd(R.raw.loss);
//		player = MediaPlayer.create(context, R.raw.loss);
//		if (!player.isPlaying()) {
		try {
				player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
						afd.getLength());
				player.prepare();
				player.setLooping(false);
				player.setVolume(1f,1f);
				player.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//		}

	}

}
