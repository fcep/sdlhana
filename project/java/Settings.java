// This string is autogenerated by ChangeAppSettings.sh, do not change spaces amount
package net.sourceforge.clonekeenplus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.util.Log;
import java.io.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.StatFs;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.lang.String;

class Settings
{
	static String SettingsFileName = "libsdl-settings.cfg";

	static boolean settingsLoaded = false;
	static boolean settingsChanged = false;

	static void Save(final MainActivity p)
	{
		try {
			ObjectOutputStream out = new ObjectOutputStream(p.openFileOutput( SettingsFileName, p.MODE_WORLD_READABLE ));
			out.writeBoolean(Globals.DownloadToSdcard);
			out.writeBoolean(Globals.PhoneHasArrowKeys);
			out.writeBoolean(Globals.PhoneHasTrackball);
			out.writeBoolean(Globals.UseAccelerometerAsArrowKeys);
			out.writeBoolean(Globals.UseTouchscreenKeyboard);
			out.writeInt(Globals.TouchscreenKeyboardSize);
			out.writeInt(Globals.AccelerometerSensitivity);
			out.writeInt(Globals.AccelerometerCenterPos);
			out.writeInt(Globals.TrackballDampening);
			out.writeInt(Globals.AudioBufferConfig);
			out.writeInt(Globals.OptionalDataDownload.length);
			for(int i = 0; i < Globals.OptionalDataDownload.length; i++)
				out.writeBoolean(Globals.OptionalDataDownload[i]);
			out.writeInt(Globals.TouchscreenKeyboardTheme);
			out.writeInt(Globals.RightClickMethod);
			out.writeBoolean(Globals.ShowScreenUnderFinger);
			out.writeInt(Globals.LeftClickMethod);
			out.writeBoolean(Globals.MoveMouseWithJoystick);
			out.writeBoolean(Globals.ClickMouseWithDpad);
			out.writeInt(Globals.ClickScreenPressure);
			out.writeInt(Globals.ClickScreenTouchspotSize);
			out.writeBoolean(Globals.KeepAspectRatio);
			out.writeInt(Globals.MoveMouseWithJoystickSpeed);
			out.writeInt(Globals.MoveMouseWithJoystickAccel);
			out.writeInt(SDL_Keys.JAVA_KEYCODE_LAST);
			for( int i = 0; i < SDL_Keys.JAVA_KEYCODE_LAST; i++ )
			{
				out.writeInt(Globals.RemapHwKeycode[i]);
			}
			out.writeInt(Globals.RemapScreenKbKeycode.length);
			for( int i = 0; i < Globals.RemapScreenKbKeycode.length; i++ )
			{
				out.writeInt(Globals.RemapScreenKbKeycode[i]);
			}
			out.writeInt(Globals.ScreenKbControlsShown.length);
			for( int i = 0; i < Globals.ScreenKbControlsShown.length; i++ )
			{
				out.writeBoolean(Globals.ScreenKbControlsShown[i]);
			}

			out.close();
			settingsLoaded = true;
			
		} catch( FileNotFoundException e ) {
		} catch( SecurityException e ) {
		} catch ( IOException e ) {};
	}

	static void Load( final MainActivity p )
	{
		if(settingsLoaded) // Prevent starting twice
		{
			return;
		}
		System.out.println("libSDL: Settings.Load(): enter");
		nativeInitKeymap();
		for( int i = 0; i < SDL_Keys.JAVA_KEYCODE_LAST; i++ )
		{
			int sdlKey = nativeGetKeymapKey(i);
			int idx = 0;
			for(int ii = 0; ii < SDL_Keys.values.length; ii++)
				if(SDL_Keys.values[ii] == sdlKey)
					idx = ii;
			Globals.RemapHwKeycode[i] = idx;
		}
		for( int i = 0; i < Globals.RemapScreenKbKeycode.length; i++ )
		{
			int sdlKey = nativeGetKeymapKeyScreenKb(i);
			int idx = 0;
			for(int ii = 0; ii < SDL_Keys.values.length; ii++)
				if(SDL_Keys.values[ii] == sdlKey)
					idx = ii;
			Globals.RemapScreenKbKeycode[i] = idx;
		}
		Globals.ScreenKbControlsShown[0] = Globals.AppNeedsArrowKeys;
		Globals.ScreenKbControlsShown[1] = Globals.AppNeedsTextInput;
		for( int i = 2; i < 7; i++ )
			Globals.ScreenKbControlsShown[i] = ( i - 2 < Globals.AppTouchscreenKeyboardKeysAmount );
		for( int i = 8; i < 12; i++ )
			Globals.ScreenKbControlsShown[i] = true;

		try {
			ObjectInputStream settingsFile = new ObjectInputStream(new FileInputStream( p.getFilesDir().getAbsolutePath() + "/" + SettingsFileName ));
			Globals.DownloadToSdcard = settingsFile.readBoolean();
			Globals.PhoneHasArrowKeys = settingsFile.readBoolean();
			Globals.PhoneHasTrackball = settingsFile.readBoolean();
			Globals.UseAccelerometerAsArrowKeys = settingsFile.readBoolean();
			Globals.UseTouchscreenKeyboard = settingsFile.readBoolean();
			Globals.TouchscreenKeyboardSize = settingsFile.readInt();
			Globals.AccelerometerSensitivity = settingsFile.readInt();
			Globals.AccelerometerCenterPos = settingsFile.readInt();
			Globals.TrackballDampening = settingsFile.readInt();
			Globals.AudioBufferConfig = settingsFile.readInt();
			Globals.OptionalDataDownload = new boolean[settingsFile.readInt()];
			for(int i = 0; i < Globals.OptionalDataDownload.length; i++)
				Globals.OptionalDataDownload[i] = settingsFile.readBoolean();
			Globals.TouchscreenKeyboardTheme = settingsFile.readInt();
			Globals.RightClickMethod = settingsFile.readInt();
			Globals.ShowScreenUnderFinger = settingsFile.readBoolean();
			Globals.LeftClickMethod = settingsFile.readInt();
			Globals.MoveMouseWithJoystick = settingsFile.readBoolean();
			Globals.ClickMouseWithDpad = settingsFile.readBoolean();
			Globals.ClickScreenPressure = settingsFile.readInt();
			Globals.ClickScreenTouchspotSize = settingsFile.readInt();
			Globals.KeepAspectRatio = settingsFile.readBoolean();
			Globals.MoveMouseWithJoystickSpeed = settingsFile.readInt();
			Globals.MoveMouseWithJoystickAccel = settingsFile.readInt();
			int readKeys = settingsFile.readInt();
			for( int i = 0; i < readKeys; i++ )
			{
				Globals.RemapHwKeycode[i] = settingsFile.readInt();
			}
			if( settingsFile.readInt() != Globals.RemapScreenKbKeycode.length )
				throw new IOException();
			for( int i = 0; i < Globals.RemapScreenKbKeycode.length; i++ )
			{
				Globals.RemapScreenKbKeycode[i] = settingsFile.readInt();
			}
			if( settingsFile.readInt() != Globals.ScreenKbControlsShown.length )
				throw new IOException();
			for( int i = 0; i < Globals.ScreenKbControlsShown.length; i++ )
			{
				Globals.ScreenKbControlsShown[i] = settingsFile.readBoolean();
			}
			
			settingsLoaded = true;

			System.out.println("libSDL: Settings.Load(): loaded settings successfully");
			
			return;
			
		} catch( FileNotFoundException e ) {
		} catch( SecurityException e ) {
		} catch ( IOException e ) {};
		
		// This code fails for both of my phones!
		/*
		Configuration c = new Configuration();
		c.setToDefaults();
		
		if( c.navigation == Configuration.NAVIGATION_TRACKBALL || 
			c.navigation == Configuration.NAVIGATION_DPAD ||
			c.navigation == Configuration.NAVIGATION_WHEEL )
		{
			Globals.AppNeedsArrowKeys = false;
		}
		
		System.out.println( "libSDL: Phone keypad type: " + 
				(
				c.navigation == Configuration.NAVIGATION_TRACKBALL ? "Trackball" :
				c.navigation == Configuration.NAVIGATION_DPAD ? "Dpad" :
				c.navigation == Configuration.NAVIGATION_WHEEL ? "Wheel" :
				c.navigation == Configuration.NAVIGATION_NONAV ? "None" :
				"Unknown" ) );
		*/

		System.out.println("libSDL: Settings.Load(): loading settings failed, running config dialog");
		p.setUpStatusLabel();
		showConfig(p);
	}

	// ===============================================================================================
	
	public static void showConfig(final MainActivity p) {
		settingsChanged = true;

		if( Globals.OptionalDataDownload == null )
			Globals.OptionalDataDownload = new boolean[Globals.DataDownloadUrl.split("\\^").length];
		Globals.OptionalDataDownload[0] = true;

		showConfigMainMenu(p);
	}

	static int MainMenuLastSelected = 0;
	static void showConfigMainMenu(final MainActivity p)
	{
		ArrayList<CharSequence> items = new ArrayList<CharSequence> ();

		items.add(p.getResources().getString(R.string.storage_question));

		if( Globals.DataDownloadUrl.split("\\^").length > 1 )
			items.add(p.getResources().getString(R.string.optional_downloads));

		items.add(p.getResources().getString(R.string.controls_additional));

		if( Globals.AppNeedsArrowKeys || Globals.MoveMouseWithJoystick )
			items.add(p.getResources().getString(R.string.controls_question));

		if( ! ( ! Globals.UseAccelerometerAsArrowKeys || Globals.AppHandlesJoystickSensitivity ) )
			items.add(p.getResources().getString(R.string.accel_question));

		if( Globals.UseTouchscreenKeyboard )
			items.add(p.getResources().getString(R.string.controls_screenkb));

		if( Globals.AppUsesMouse )
			items.add(p.getResources().getString(R.string.leftclick_question));
		
		if( Globals.MoveMouseWithJoystick )
			items.add(p.getResources().getString(R.string.pointandclick_joystickmouse));

		items.add(p.getResources().getString(R.string.audiobuf_question));

		if( Globals.RightClickMethod == Globals.RIGHT_CLICK_WITH_PRESSURE || Globals.LeftClickMethod == Globals.LEFT_CLICK_WITH_PRESSURE )
			items.add(p.getResources().getString(R.string.measurepressure));
		
		items.add(p.getResources().getString(R.string.remap_hwkeys));

		if( Globals.UseTouchscreenKeyboard )
    		items.add(p.getResources().getString(R.string.remap_screenkb));

		items.add(p.getResources().getString(R.string.ok));

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.device_config));
		builder.setSingleChoiceItems(items.toArray(new CharSequence[0]), MainMenuLastSelected, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				MainMenuLastSelected = item;
				dialog.dismiss();
				int selected = 0;

				if( item == selected )
					showDownloadConfig(p);
				selected++;

				if( Globals.DataDownloadUrl.split("\\^").length <= 1 )
					item += 1;
				else
					if( item == selected )
						showOptionalDownloadConfig(p);
				selected++;

				if( item == 2 )
					showAdditionalInputConfig(p);
				selected++;

				if( ! Globals.AppNeedsArrowKeys && ! Globals.MoveMouseWithJoystick )
					item += 1;
				else
					if( item == selected )
						showKeyboardConfig(p);
				selected++;

				if( ! Globals.UseAccelerometerAsArrowKeys || Globals.AppHandlesJoystickSensitivity )
					item += 1;
				else
					if( item == selected )
						showAccelerometerConfig(p);
				selected++;

				if( ! Globals.UseTouchscreenKeyboard )
					item += 1;
				else
					if( item == selected )
						showScreenKeyboardConfig(p);
				selected++;

				if( ! Globals.AppUsesMouse )
					item += 1;
				else
					if( item == selected )
						showLeftClickConfig(p);
				selected++;
				
				if( ! Globals.MoveMouseWithJoystick )
					item += 1;
				else
					if( item == selected )
						showJoystickMouseConfig(p);
				selected++;

				if( item == selected )
					showAudioConfig(p);
				selected++;

				if( ! ( Globals.RightClickMethod == Globals.RIGHT_CLICK_WITH_PRESSURE ||
						Globals.LeftClickMethod == Globals.LEFT_CLICK_WITH_PRESSURE ) )
					item += 1;
				else
					if( item == selected )
						showTouchPressureMeasurementTool(p);
				selected++;

				if( item == selected )
					showRemapHwKeysConfig(p);
				selected++;

				if( ! Globals.UseTouchscreenKeyboard )
					item += 1;
				else
					if( item == selected )
						showRemapScreenKbConfig(p);
				selected++;
				
				if( item == selected )
				{
					Save(p);
					p.startDownloader();
				}
				selected++;
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}


	static void showDownloadConfig(final MainActivity p) {

		long freeSdcard = 0;
		long freePhone = 0;
		try {
			StatFs sdcard = new StatFs(Environment.getExternalStorageDirectory().getPath());
			StatFs phone = new StatFs(Environment.getDataDirectory().getPath());
			freeSdcard = (long)sdcard.getAvailableBlocks() * sdcard.getBlockSize() / 1024 / 1024;
			freePhone = (long)phone.getAvailableBlocks() * phone.getBlockSize() / 1024 / 1024;
		}catch(Exception e) {}

		final CharSequence[] items = { p.getResources().getString(R.string.storage_phone, freePhone),
										p.getResources().getString(R.string.storage_sd, freeSdcard) };
		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		String [] downloadFiles = Globals.DataDownloadUrl.split("\\^");
		builder.setTitle(downloadFiles[0].split("[|]")[0]);
		builder.setSingleChoiceItems(items, Globals.DownloadToSdcard ? 1 : 0, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.DownloadToSdcard = (item == 1);

				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	};

	static void showOptionalDownloadConfig(final MainActivity p) {

		String [] downloadFiles = Globals.DataDownloadUrl.split("\\^");
		if(downloadFiles.length <= 1)
		{
			Globals.OptionalDataDownload = new boolean[1];
			Globals.OptionalDataDownload[0] = true;
			showConfigMainMenu(p);
			return;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.optional_downloads));

		CharSequence[] items = new CharSequence[ downloadFiles.length - 1 ];
		for(int i = 1; i < downloadFiles.length; i++ )
			items[i-1] = new String(downloadFiles[i].split("[|]")[0]);

		if( Globals.OptionalDataDownload == null || Globals.OptionalDataDownload.length != items.length + 1 )
			Globals.OptionalDataDownload = new boolean[downloadFiles.length];
		Globals.OptionalDataDownload[0] = true;
		boolean defaults[] = new boolean[downloadFiles.length-1];
		for(int i=1; i<downloadFiles.length; i++)
			defaults[i-1] = Globals.OptionalDataDownload[i];

		builder.setMultiChoiceItems(items, defaults, new DialogInterface.OnMultiChoiceClickListener() 
		{
			public void onClick(DialogInterface dialog, int item, boolean isChecked) 
			{
				Globals.OptionalDataDownload[item+1] = isChecked;
			}
		});
		builder.setPositiveButton(p.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});

		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	};
	
	static void showAdditionalInputConfig(final MainActivity p)
	{
		CharSequence[] items = {
			p.getResources().getString(R.string.controls_screenkb),
			p.getResources().getString(R.string.controls_accelnav),
			p.getResources().getString(R.string.pointandclick_keepaspectratio),
			p.getResources().getString(R.string.pointandclick_showcreenunderfinger),
			p.getResources().getString(R.string.pointandclick_joystickmouse),
			p.getResources().getString(R.string.click_with_dpadcenter) };

		boolean defaults[] = { 
			Globals.UseTouchscreenKeyboard,
			Globals.UseAccelerometerAsArrowKeys,
			Globals.KeepAspectRatio,
			Globals.ShowScreenUnderFinger,
			Globals.MoveMouseWithJoystick,
			Globals.ClickMouseWithDpad
		};

		if( ! Globals.AppUsesMouse )
		{
			CharSequence[] items2 = {
				p.getResources().getString(R.string.controls_screenkb),
				p.getResources().getString(R.string.controls_accelnav),
				p.getResources().getString(R.string.pointandclick_keepaspectratio),
				p.getResources().getString(R.string.pointandclick_showcreenunderfinger) };

			boolean defaults2[] = { 
				Globals.UseTouchscreenKeyboard,
				Globals.UseAccelerometerAsArrowKeys,
				Globals.KeepAspectRatio,
				Globals.ShowScreenUnderFinger };

				items = items2;
				defaults = defaults2;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.controls_additional));
		builder.setMultiChoiceItems(items, defaults, new DialogInterface.OnMultiChoiceClickListener() 
		{
			public void onClick(DialogInterface dialog, int item, boolean isChecked) 
			{
				if( item == 0 )
					Globals.UseTouchscreenKeyboard = isChecked;
				if( item == 1 )
					Globals.UseAccelerometerAsArrowKeys = isChecked;
				if( item == 2 )
					Globals.KeepAspectRatio = isChecked;
				if( item == 3 )
					Globals.ShowScreenUnderFinger = isChecked;
				if( item == 4 )
					Globals.MoveMouseWithJoystick = isChecked;
				if( item == 5 )
					Globals.ClickMouseWithDpad = isChecked;
			}
		});
		builder.setPositiveButton(p.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});

		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showAccelerometerConfig(final MainActivity p)
	{
		if( ! Globals.UseAccelerometerAsArrowKeys || Globals.AppHandlesJoystickSensitivity )
		{
			Globals.AccelerometerSensitivity = 2; // Slow, full range
			showAccelerometerCenterConfig(p);
			return;
		}
		
		final CharSequence[] items = { p.getResources().getString(R.string.accel_fast),
										p.getResources().getString(R.string.accel_medium),
										p.getResources().getString(R.string.accel_slow) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(R.string.accel_question);
		builder.setSingleChoiceItems(items, Globals.AccelerometerSensitivity, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.AccelerometerSensitivity = item;

				dialog.dismiss();
				showAccelerometerCenterConfig(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showAccelerometerCenterConfig(final MainActivity p)
	{
		if( ! Globals.UseAccelerometerAsArrowKeys || Globals.AppHandlesJoystickSensitivity )
		{
			Globals.AccelerometerCenterPos = 2; // Fixed horizontal center position
			showConfigMainMenu(p);
			return;
		}
		
		final CharSequence[] items = { p.getResources().getString(R.string.accel_floating),
										p.getResources().getString(R.string.accel_fixed_start),
										p.getResources().getString(R.string.accel_fixed_horiz) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(R.string.accel_question_center);
		builder.setSingleChoiceItems(items, Globals.AccelerometerCenterPos, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.AccelerometerCenterPos = item;

				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}


	static void showScreenKeyboardConfig(final MainActivity p)
	{
		if( ! Globals.UseTouchscreenKeyboard )
		{
			Globals.TouchscreenKeyboardSize = 0;
			showScreenKeyboardThemeConfig(p);
			return;
		}
		
		final CharSequence[] items = {	p.getResources().getString(R.string.controls_screenkb_large),
										p.getResources().getString(R.string.controls_screenkb_medium),
										p.getResources().getString(R.string.controls_screenkb_small),
										p.getResources().getString(R.string.controls_screenkb_tiny) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.controls_screenkb_size));
		builder.setSingleChoiceItems(items, Globals.TouchscreenKeyboardSize, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.TouchscreenKeyboardSize = item;

				dialog.dismiss();
				showScreenKeyboardThemeConfig(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showScreenKeyboardThemeConfig(final MainActivity p)
	{
		if( ! Globals.UseTouchscreenKeyboard )
		{
			Globals.TouchscreenKeyboardTheme = 0;
			showConfigMainMenu(p);
			return;
		}
		
		final CharSequence[] items = {
			p.getResources().getString(R.string.controls_screenkb_by, "Ugly Arrows", "pelya"),
			p.getResources().getString(R.string.controls_screenkb_by, "Ultimate Droid", "Sean Stieber")
			};

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.controls_screenkb_theme));
		builder.setSingleChoiceItems(items, Globals.TouchscreenKeyboardTheme, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.TouchscreenKeyboardTheme = item;

				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showAudioConfig(final MainActivity p)
	{
		final CharSequence[] items = {	p.getResources().getString(R.string.audiobuf_verysmall),
										p.getResources().getString(R.string.audiobuf_small),
										p.getResources().getString(R.string.audiobuf_medium),
										p.getResources().getString(R.string.audiobuf_large) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(R.string.audiobuf_question);
		builder.setSingleChoiceItems(items, Globals.AudioBufferConfig, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.AudioBufferConfig = item;
				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showLeftClickConfig(final MainActivity p)
	{
		if( ! Globals.AppUsesMouse )
		{
			Globals.LeftClickMethod = Globals.LEFT_CLICK_NORMAL;
			showRightClickConfig(p);
			return;
		}
		final CharSequence[] items = {	p.getResources().getString(R.string.leftclick_normal),
										p.getResources().getString(R.string.leftclick_near_cursor),
										p.getResources().getString(R.string.leftclick_multitouch),
										p.getResources().getString(R.string.leftclick_pressure),
										p.getResources().getString(R.string.leftclick_dpadcenter) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(R.string.leftclick_question);
		builder.setSingleChoiceItems(items, Globals.LeftClickMethod, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.LeftClickMethod = item;
				dialog.dismiss();
				showRightClickConfig(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showRightClickConfig(final MainActivity p)
	{
		if( ! Globals.AppNeedsTwoButtonMouse )
		{
			Globals.RightClickMethod = Globals.RIGHT_CLICK_NONE;
			showConfigMainMenu(p);
			return;
		}
		final CharSequence[] items = {	p.getResources().getString(R.string.rightclick_none),
										p.getResources().getString(R.string.rightclick_multitouch),
										p.getResources().getString(R.string.rightclick_pressure),
										p.getResources().getString(R.string.rightclick_menu) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(R.string.rightclick_question);
		builder.setSingleChoiceItems(items, Globals.RightClickMethod, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.RightClickMethod = item;
				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showKeyboardConfig(final MainActivity p)
	{
		if( ! Globals.AppNeedsArrowKeys && ! Globals.MoveMouseWithJoystick )
		{
			Globals.PhoneHasArrowKeys = false;
			Globals.PhoneHasTrackball = false;
			showTrackballConfig(p);
			return;
		}
		
		final CharSequence[] items = { p.getResources().getString(R.string.controls_arrows),
										p.getResources().getString(R.string.controls_trackball),
										p.getResources().getString(R.string.controls_touch) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.controls_question));
		builder.setSingleChoiceItems(items, Globals.PhoneHasArrowKeys ? 0 : ( Globals.PhoneHasTrackball ? 1 : 2 ), new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.PhoneHasArrowKeys = (item == 0);
				Globals.PhoneHasTrackball = (item == 1);

				dialog.dismiss();
				showTrackballConfig(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showTrackballConfig(final MainActivity p)
	{
		if( ! Globals.PhoneHasTrackball )
		{
			Globals.TrackballDampening = 0;
			showConfigMainMenu(p);
			return;
		}
		
		final CharSequence[] items = { p.getResources().getString(R.string.trackball_no_dampening),
										p.getResources().getString(R.string.trackball_fast),
										p.getResources().getString(R.string.trackball_medium),
										p.getResources().getString(R.string.trackball_slow) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.trackball_question));
		builder.setSingleChoiceItems(items, Globals.TrackballDampening, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.TrackballDampening = item;

				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showJoystickMouseConfig(final MainActivity p)
	{
		if( ! Globals.MoveMouseWithJoystick )
		{
			Globals.MoveMouseWithJoystickSpeed = 0;
			showJoystickMouseAccelConfig(p);
			return;
		}
		
		final CharSequence[] items = {	p.getResources().getString(R.string.accel_slow),
										p.getResources().getString(R.string.accel_medium),
										p.getResources().getString(R.string.accel_fast) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(R.string.pointandclick_joystickmousespeed);
		builder.setSingleChoiceItems(items, Globals.MoveMouseWithJoystickSpeed, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.MoveMouseWithJoystickSpeed = item;

				dialog.dismiss();
				showJoystickMouseAccelConfig(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showJoystickMouseAccelConfig(final MainActivity p)
	{
		if( ! Globals.MoveMouseWithJoystick )
		{
			Globals.MoveMouseWithJoystickAccel = 0;
			showConfigMainMenu(p);
			return;
		}
		
		final CharSequence[] items = {	p.getResources().getString(R.string.none),
										p.getResources().getString(R.string.accel_slow),
										p.getResources().getString(R.string.accel_medium),
										p.getResources().getString(R.string.accel_fast) };

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(R.string.pointandclick_joystickmousespeed);
		builder.setSingleChoiceItems(items, Globals.MoveMouseWithJoystickAccel, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				Globals.MoveMouseWithJoystickAccel = item;

				dialog.dismiss();
				showConfigMainMenu(p);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	public interface TouchEventsListener
	{
		public void onTouchEvent(final MotionEvent ev);
	}

	public interface KeyEventsListener
	{
		public void onKeyEvent(final int keyCode);
	}

	static void showTouchPressureMeasurementTool(final MainActivity p)
	{
		if( Globals.RightClickMethod == Globals.RIGHT_CLICK_WITH_PRESSURE || Globals.LeftClickMethod == Globals.LEFT_CLICK_WITH_PRESSURE )
		{
			p.setText(p.getResources().getString(R.string.measurepressure_touchplease));
			p.touchMeasurementTool = new TouchMeasurementTool(p);
		}
		else
		{
			showConfigMainMenu(p);
		}
	}

	public static class TouchMeasurementTool implements TouchEventsListener
	{
		MainActivity p;
		ArrayList<Integer> force = new ArrayList<Integer>();
		ArrayList<Integer> radius = new ArrayList<Integer>();
		static final int maxEventAmount = 100;
		
		public TouchMeasurementTool(MainActivity _p) 
		{
			p = _p;
		}

		public void onTouchEvent(final MotionEvent ev)
		{
			force.add(new Integer((int)(ev.getPressure() * 1000.0)));
			radius.add(new Integer((int)(ev.getSize() * 1000.0)));
			p.setText(p.getResources().getString(R.string.measurepressure_response, force.get(force.size()-1), radius.get(radius.size()-1)));
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) { }
			
			if( force.size() >= maxEventAmount )
			{
				p.touchMeasurementTool = null;
				Globals.ClickScreenPressure = getAverageForce();
				Globals.ClickScreenTouchspotSize = getAverageRadius();
				System.out.println("SDL: measured average force " + Globals.ClickScreenPressure + " radius " + Globals.ClickScreenTouchspotSize);
				showConfigMainMenu(p);
			}
		}

		int getAverageForce()
		{
			int avg = 0;
			for(Integer f: force)
			{
				avg += f;
			}
			return avg / force.size();
		}
		int getAverageRadius()
		{
			int avg = 0;
			for(Integer r: radius)
			{
				avg += r;
			}
			return avg / radius.size();
		}
	}
	
	static void showRemapHwKeysConfig(final MainActivity p)
	{
		p.setText(p.getResources().getString(R.string.remap_hwkeys_press));
		p.keyRemapTool = new KeyRemapTool(p);
	}

	public static class KeyRemapTool implements KeyEventsListener
	{
		MainActivity p;
		public KeyRemapTool(MainActivity _p)
		{
			p = _p;
		}
		
		public void onKeyEvent(final int keyCode)
		{
			p.keyRemapTool = null;
			int keyIndex = keyCode;
			if( keyIndex < 0 )
				keyIndex = 0;
			if( keyIndex > SDL_Keys.JAVA_KEYCODE_LAST )
				keyIndex = 0;

			final int KeyIndexFinal = keyIndex;
			AlertDialog.Builder builder = new AlertDialog.Builder(p);
			builder.setTitle(R.string.remap_hwkeys_select);
			builder.setSingleChoiceItems(SDL_Keys.names, Globals.RemapHwKeycode[keyIndex], new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int item)
				{
					Globals.RemapHwKeycode[KeyIndexFinal] = item;

					dialog.dismiss();
					showConfigMainMenu(p);
				}
			});
			AlertDialog alert = builder.create();
			alert.setOwnerActivity(p);
			alert.show();
		}
	}

	static void showRemapScreenKbConfig(final MainActivity p)
	{
		CharSequence[] items = {
			p.getResources().getString(R.string.remap_screenkb_joystick),
			p.getResources().getString(R.string.remap_screenkb_button_text),
			p.getResources().getString(R.string.remap_screenkb_button) + " 1",
			p.getResources().getString(R.string.remap_screenkb_button) + " 2",
			p.getResources().getString(R.string.remap_screenkb_button) + " 3",
			p.getResources().getString(R.string.remap_screenkb_button) + " 4",
			p.getResources().getString(R.string.remap_screenkb_button) + " 5",
			p.getResources().getString(R.string.remap_screenkb_button) + " 6",
			// Not implemented yet!
			/*
			p.getResources().getString(R.string.remap_screenkb_button_zoomin),
			p.getResources().getString(R.string.remap_screenkb_button_zoomout),
			p.getResources().getString(R.string.remap_screenkb_button_rotateleft),
			p.getResources().getString(R.string.remap_screenkb_button_rotateright),
			*/
		};

		boolean defaults[] = { 
			Globals.ScreenKbControlsShown[0],
			Globals.ScreenKbControlsShown[1],
			Globals.ScreenKbControlsShown[2],
			Globals.ScreenKbControlsShown[3],
			Globals.ScreenKbControlsShown[4],
			Globals.ScreenKbControlsShown[5],
			Globals.ScreenKbControlsShown[6],
			Globals.ScreenKbControlsShown[7],
			// Not implemented yet!
			/*
			Globals.ScreenKbControlsShown[8],
			Globals.ScreenKbControlsShown[9],
			Globals.ScreenKbControlsShown[10],
			Globals.ScreenKbControlsShown[11],
			*/
		};
		
		
		if( ! Globals.UseTouchscreenKeyboard )
		{
			for( int i = 0; i < 8; i++ )
				Globals.ScreenKbControlsShown[i] = false;
			
			CharSequence[] items2 = {
				p.getResources().getString(R.string.remap_screenkb_button_zoomin),
				p.getResources().getString(R.string.remap_screenkb_button_zoomout),
				p.getResources().getString(R.string.remap_screenkb_button_rotateleft),
				p.getResources().getString(R.string.remap_screenkb_button_rotateright),
			};

			boolean defaults2[] = { 
				Globals.ScreenKbControlsShown[8],
				Globals.ScreenKbControlsShown[9],
				Globals.ScreenKbControlsShown[10],
				Globals.ScreenKbControlsShown[11],
			};

			items = items2;
			defaults = defaults2;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(p.getResources().getString(R.string.remap_screenkb));
		builder.setMultiChoiceItems(items, defaults, new DialogInterface.OnMultiChoiceClickListener() 
		{
			public void onClick(DialogInterface dialog, int item, boolean isChecked) 
			{
				if( ! Globals.UseTouchscreenKeyboard )
					item += 8;
				Globals.ScreenKbControlsShown[item] = isChecked;
			}
		});
		builder.setPositiveButton(p.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int item) 
			{
				dialog.dismiss();
				showRemapScreenKbConfig2(p, 0);
			}
		});

		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	static void showRemapScreenKbConfig2(final MainActivity p, final int currentButton)
	{
		CharSequence[] items = {
			p.getResources().getString(R.string.remap_screenkb_button) + " 1",
			p.getResources().getString(R.string.remap_screenkb_button) + " 2",
			p.getResources().getString(R.string.remap_screenkb_button) + " 3",
			p.getResources().getString(R.string.remap_screenkb_button) + " 4",
			p.getResources().getString(R.string.remap_screenkb_button) + " 5",
			p.getResources().getString(R.string.remap_screenkb_button) + " 6",
			// Not implemented yet!
			/*
			p.getResources().getString(R.string.remap_screenkb_button_zoomin),
			p.getResources().getString(R.string.remap_screenkb_button_zoomout),
			p.getResources().getString(R.string.remap_screenkb_button_rotateleft),
			p.getResources().getString(R.string.remap_screenkb_button_rotateright),
			*/
		};
		
		if( currentButton >= items.length ) // Globals.RemapScreenKbKeycode.length )
		{
			showConfigMainMenu(p);
			return;
		}
		if( ! Globals.ScreenKbControlsShown[currentButton + 2] )
		{
			showRemapScreenKbConfig2(p, currentButton + 1);
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(p);
		builder.setTitle(items[currentButton]);
		builder.setSingleChoiceItems(SDL_Keys.names, Globals.RemapScreenKbKeycode[currentButton], new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int item)
			{
				Globals.RemapScreenKbKeycode[currentButton] = item;

				dialog.dismiss();
				showRemapScreenKbConfig2(p, currentButton + 1);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOwnerActivity(p);
		alert.show();
	}

	// ===============================================================================================

	static void Apply(Activity p)
	{
		nativeIsSdcardUsed( Globals.DownloadToSdcard ? 1 : 0 );
		
		if( Globals.PhoneHasTrackball )
			nativeSetTrackballUsed();
		if( Globals.AppUsesMouse )
			nativeSetMouseUsed( Globals.RightClickMethod,
								Globals.ShowScreenUnderFinger ? 1 : 0,
								Globals.LeftClickMethod,
								Globals.MoveMouseWithJoystick ? 1 : 0,
								Globals.ClickMouseWithDpad ? 1 : 0,
								Globals.ClickScreenPressure,
								Globals.ClickScreenTouchspotSize,
								Globals.MoveMouseWithJoystickSpeed,
								Globals.MoveMouseWithJoystickAccel );
		if( Globals.AppUsesJoystick && (Globals.UseAccelerometerAsArrowKeys || Globals.UseTouchscreenKeyboard) )
			nativeSetJoystickUsed();
		if( Globals.AppUsesMultitouch )
			nativeSetMultitouchUsed();
		nativeSetAccelerometerSettings(Globals.AccelerometerSensitivity, Globals.AccelerometerCenterPos);
		nativeSetTrackballDampening(Globals.TrackballDampening);
		if( Globals.UseTouchscreenKeyboard )
		{
			nativeSetTouchscreenKeyboardUsed();
			nativeSetupScreenKeyboard(	Globals.TouchscreenKeyboardSize,
										Globals.TouchscreenKeyboardTheme,
										8, // Globals.AppTouchscreenKeyboardKeysAmount, - set later by nativeSetScreenKbKeyUsed()
										Globals.AppTouchscreenKeyboardKeysAmountAutoFire,
										1, //Globals.AppNeedsArrowKeys ? 1 : 0,
										1 ); //Globals.AppNeedsTextInput ? 1 : 0 );
		}
		SetupTouchscreenKeyboardGraphics(p);
		for( int i = 0; i < SDL_Keys.JAVA_KEYCODE_LAST; i++ )
		{
			nativeSetKeymapKey(i, SDL_Keys.values[Globals.RemapHwKeycode[i]]);
		}

		for( int i = 0; i < Globals.ScreenKbControlsShown.length; i++ )
		{
			nativeSetScreenKbKeyUsed(i, Globals.ScreenKbControlsShown[i] ? 1 : 0);
		}
		for( int i = 0; i < Globals.RemapScreenKbKeycode.length; i++ )
		{
			nativeSetKeymapKeyScreenKb(i, SDL_Keys.values[Globals.RemapScreenKbKeycode[i]]);
		}

		String lang = new String(Locale.getDefault().getLanguage());
		if( Locale.getDefault().getCountry().length() > 0 )
			lang = lang + "_" + Locale.getDefault().getCountry();
		System.out.println( "libSDL: setting envvar LANGUAGE to '" + lang + "'");
		nativeSetEnv( "LANG", lang );
		nativeSetEnv( "LANGUAGE", lang );
		// TODO: get current user name and set envvar USER, the API is not availalbe on Android 1.6 so I don't bother with this
	}

	static byte [] loadRaw(Activity p,int res)
	{
		byte [] buf = new byte[65536 * 2];
		byte [] a = new byte[0];
		try{
			InputStream is = new GZIPInputStream(p.getResources().openRawResource(res));
			int readed = 0;
			while( (readed = is.read(buf)) >= 0 )
			{
				byte [] b = new byte [a.length + readed];
				System.arraycopy(a, 0, b, 0, a.length);
				System.arraycopy(buf, 0, b, a.length, readed);
				a = b;
			}
		} catch(Exception e) {};
		return a;
	}
	
	static void SetupTouchscreenKeyboardGraphics(Activity p)
	{
		if( Globals.UseTouchscreenKeyboard )
		{
			if( Globals.TouchscreenKeyboardTheme == 1 )
			{
				nativeSetupScreenKeyboardButtons(loadRaw(p, R.raw.ultimatedroid));
			}
		}
	}
	
	private static native void nativeIsSdcardUsed(int flag);
	private static native void nativeSetTrackballUsed();
	private static native void nativeSetTrackballDampening(int value);
	private static native void nativeSetAccelerometerSettings(int sensitivity, int centerPos);
	private static native void nativeSetMouseUsed(int RightClickMethod, int ShowScreenUnderFinger, int LeftClickMethod, 
													int MoveMouseWithJoystick, int ClickMouseWithDpad, int MaxForce, int MaxRadius,
													int MoveMouseWithJoystickSpeed, int MoveMouseWithJoystickAccel);
	private static native void nativeSetJoystickUsed();
	private static native void nativeSetMultitouchUsed();
	private static native void nativeSetTouchscreenKeyboardUsed();
	private static native void nativeSetupScreenKeyboard(int size, int theme, int nbuttons, int nbuttonsAutoFire, int showArrows, int showTextInput);
	private static native void nativeSetupScreenKeyboardButtons(byte[] img);
	private static native void nativeInitKeymap();
	private static native int nativeGetKeymapKey(int key);
	private static native void nativeSetKeymapKey(int javakey, int key);
	private static native int nativeGetKeymapKeyScreenKb(int keynum);
	private static native void nativeSetKeymapKeyScreenKb(int keynum, int key);
	private static native void nativeSetScreenKbKeyUsed(int keynum, int used);
	public static native void nativeSetEnv(final String name, final String value);
}

