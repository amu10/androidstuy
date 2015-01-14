package com.itotem.view.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
/**自己重写一个类,继承SCROLLVIEW,重写ONSCROLLCHANGED方法,把你要控制的VIEW SET进来,然后,在ONSCROLLCHANGED方法中,设置该VIEW的SCROLLTO,ONSCROLLCHANGED方法中有四个参数,第二个是纵坐标"t",设置到scrollTo(0,t)中,这样,就可以实现了.
当然别忘了在LAYOUT的XML文件中,对应的SCROLLVIEW,换成自己重写的这个类.
*/
public class SyncScrollView extends ScrollView{

    private static final String TAG = SyncScrollView.class.getSimpleName();
    View mView;
    public SyncScrollView(Context context) {
        super(context);
    }
    
    public SyncScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SyncScrollView(Context context, AttributeSet attrs) {
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
