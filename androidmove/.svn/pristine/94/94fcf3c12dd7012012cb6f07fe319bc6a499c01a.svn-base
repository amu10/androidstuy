package com.itotem.view.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class SyncHorizontalScrollView extends HorizontalScrollView{


    private static final String TAG = SyncScrollView.class.getSimpleName();
    View mView;
    public SyncHorizontalScrollView(Context context) {
        super(context);
    }
    
    public SyncHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mView!=null){
            mView.scrollTo(l, t);
        }
    }

    public void setScrollView(View view){
        mView = view;
    }

}
