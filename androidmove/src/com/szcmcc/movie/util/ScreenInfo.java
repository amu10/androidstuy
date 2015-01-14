package com.szcmcc.movie.util;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.szcmcc.movie.view.GalleryFlow1;

public class ScreenInfo {
	public enum ScreenType {
		LOW, MID, HIGH
	}

	public enum ViewType {
		PORTRAIT, LANDSCAPE
	}

	private static ScreenType mScreenType = ScreenType.HIGH;
	private static ViewType mViewType = ViewType.PORTRAIT;
	private static int mScreenWidth;
	private static int mScreenHeight;
	private static int webviewScaleNum=75;
	private static int listviewitem=62;
	private static int gridheight=58;
	
	private static int buttonsmaxhegit=61;
	private static int buttonsmaxwidth=120;
	private static int buttonsmaxfont=13;
	
	private static int buttonsminhegit=60;
	private static int buttonsminwidth=120;
	private static int buttonsminfont=13;
	public static int getListviewitem() {
		return listviewitem;
	}

	private static LinearLayout.LayoutParams mainGalleryLayoutParams=null;
	private static LinearLayout.LayoutParams mainitemGalleryLayoutParams=null;
	private static LinearLayout.LayoutParams car_type__galleryRParams=null; 
	private static GalleryFlow1.LayoutParams galleryImageLayoutParams=null; 
	public static LinearLayout.LayoutParams getMainitemGalleryLayoutParams() {
		return mainitemGalleryLayoutParams;
	}


	public static void setMainitemGalleryLayoutParams(
			LinearLayout.LayoutParams mainitemGalleryLayoutParams) {
		ScreenInfo.mainitemGalleryLayoutParams = mainitemGalleryLayoutParams;
	}


	public static LinearLayout.LayoutParams getMainGalleryLayoutParams() {
		return mainGalleryLayoutParams;
	}


	public static void setListviewitem(int listviewitem) {
		ScreenInfo.listviewitem = listviewitem;
	}

	public static Activity mActivity;

	// private static int mSelectDialogItemsId;

	public static void setScreenType(ViewType viewType) {
		mViewType = viewType;
	}

	public static void setScreenInfo(Activity activity) {
		if(buttonsmaxhegit==61)
		{
		mActivity = activity;
		mScreenWidth = activity.getWindowManager().getDefaultDisplay()
				.getWidth();
		mScreenHeight = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		initLayoutParams(activity);
		}
	}

	public static int getWebviewScaleNum() {
		return webviewScaleNum;
	}

	public static void setWebviewScaleNum(int webviewScaleNum) {
		ScreenInfo.webviewScaleNum = webviewScaleNum;
	}

	public static ScreenType getScreenType() {
		return mScreenType;
	}

	public static ViewType getViewType() {
		return mViewType;
	}

	public static int getScreenWidth() {
		return mActivity.getWindowManager().getDefaultDisplay().getWidth();
	}

	public static int getScreenHeight() {
		return mActivity.getWindowManager().getDefaultDisplay().getHeight();
	}

	public static int getBouncedGalleyDrawableBound() {
		return webviewScaleNum;
	}

	public static LinearLayout.LayoutParams getCar_type__galleryRParams() {
		return car_type__galleryRParams;
	}


	public static GalleryFlow1.LayoutParams getGalleryImageLayoutParams() {
		return galleryImageLayoutParams;
	}


	private static void initLayoutParams(Context context) {
		
			initParameters(context);
	
	}

	public static int getButtonsmaxhegit() {
		return buttonsmaxhegit;
	}


	public static void setButtonsmaxhegit(int buttonsmaxhegit) {
		ScreenInfo.buttonsmaxhegit = buttonsmaxhegit;
	}


	public static int getButtonsmaxwidth() {
		return buttonsmaxwidth;
	}


	public static void setButtonsmaxwidth(int buttonsmaxwidth) {
		ScreenInfo.buttonsmaxwidth = buttonsmaxwidth;
	}


	public static int getButtonsmaxfont() {
		return buttonsmaxfont;
	}


	public static void setButtonsmaxfont(int buttonsmaxfont) {
		ScreenInfo.buttonsmaxfont = buttonsmaxfont;
	}


	public static int getButtonsminhegit() {
		return buttonsminhegit;
	}


	public static void setButtonsminhegit(int buttonsminhegit) {
		ScreenInfo.buttonsminhegit = buttonsminhegit;
	}


	public static int getButtonsminwidth() {
		return buttonsminwidth;
	}


	public static void setButtonsminwidth(int buttonsminwidth) {
		ScreenInfo.buttonsminwidth = buttonsminwidth;
	}


	public static int getButtonsminfont() {
		return buttonsminfont;
	}


	public static void setButtonsminfont(int buttonsminfont) {
		ScreenInfo.buttonsminfont = buttonsminfont;
	}


	private static void initParameters(Context context) {
		if (mViewType == ViewType.PORTRAIT) {
			switch (mScreenWidth) {
			case 240:// ldpi
				mgridheight=10;
				buttonsmaxhegit=70;
				buttonsmaxwidth=100;
				buttonsmaxfont=15;
				buttonsminhegit=40;
				buttonsminwidth=80;
				buttonsminfont=10;
				mScreenType = ScreenType.LOW;
				webviewScaleNum = 35;
				listviewitem=30;
				car_type__galleryRParams=new LinearLayout.LayoutParams(240,194);
				mainitemGalleryLayoutParams=new LinearLayout.LayoutParams(240, 150);
				//主面gallery 
				gridheight=58;
				mainGalleryLayoutParams=new LinearLayout.LayoutParams(240, 150);
				 galleryImageLayoutParams=new GalleryFlow1.LayoutParams(120, 80); 
				break;
			case 320:// mdpi
				mgridheight=15;
				buttonsmaxhegit=70;
				buttonsmaxwidth=110;
				buttonsmaxfont=15;
				buttonsminhegit=60;
				buttonsminwidth=100;
				buttonsminfont=12;
				mScreenType = ScreenType.MID;
				webviewScaleNum = 38;
				listviewitem=43;
				car_type__galleryRParams=new LinearLayout.LayoutParams(320,169);
				mainitemGalleryLayoutParams=new LinearLayout.LayoutParams(320, 200);
				 galleryImageLayoutParams=new GalleryFlow1.LayoutParams(200, 140); 
				//主面gallery 
				mainGalleryLayoutParams=new LinearLayout.LayoutParams(320, 200);
				gridheight=80;
				break;
			case 480:// hdpi
				mgridheight=25;
				buttonsmaxhegit=100;
				buttonsmaxwidth=180;
				buttonsmaxfont=20;
				buttonsminhegit=60;
				buttonsminwidth=120;
				buttonsminfont=15;
				mScreenType = ScreenType.HIGH;
				webviewScaleNum = 50;
				listviewitem=62;
				car_type__galleryRParams=new LinearLayout.LayoutParams(480,278);
				mainGalleryLayoutParams=new LinearLayout.LayoutParams(480, 300);
				
				 galleryImageLayoutParams=new GalleryFlow1.LayoutParams(375, 278); 
				mainitemGalleryLayoutParams=new LinearLayout.LayoutParams(480, 300);
				gridheight=122;
				break;
			case 640:// hdpi ��������
				mgridheight=30;
				buttonsmaxhegit=140;
				buttonsmaxwidth=220;
				buttonsmaxfont=24;
				buttonsminhegit=80;
				buttonsminwidth=140;
				buttonsminfont=18;
				mScreenType = ScreenType.HIGH;
				webviewScaleNum = 75;
				listviewitem=62;
				car_type__galleryRParams=new LinearLayout.LayoutParams(640,320);
				mainGalleryLayoutParams=new LinearLayout.LayoutParams(640, 400);
				 galleryImageLayoutParams=new GalleryFlow1.LayoutParams(200, 140); 
				mainitemGalleryLayoutParams=new LinearLayout.LayoutParams(640, 400);
				gridheight=122;
				break;
			default:
				mgridheight=30;
				mScreenType = ScreenType.HIGH;
				listviewitem=62;
				int width=mScreenWidth;
				int height=(mScreenWidth/80)*50;
				gridheight=122;
				car_type__galleryRParams=new LinearLayout.LayoutParams(width,278);
				 galleryImageLayoutParams=new GalleryFlow1.LayoutParams(200, 140); 
				mainGalleryLayoutParams=new LinearLayout.LayoutParams(width, height);
				mainitemGalleryLayoutParams=new LinearLayout.LayoutParams(width, height);
				break;
			}
		}
	}


	public static int getGridheight() {
		return gridheight;
	}

	//获取到座位Grid的宽度
	private static int mgridheight=25;
	public static int getSeatGridWidth(int column) {
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;
		int heightPixels = dm.heightPixels;
		mgridheight = (widthPixels - (column * 1 + 1))/column;
//		System.out.println("dongdianzhouScreenInfo1" + widthPixels +"   "+ heightPixels );
//		System.out.println("dongdianzhouScreenInfo2" + widthPixels +"   "+ heightPixels +"  "+ mgridheight);
		return mgridheight;
	}
}