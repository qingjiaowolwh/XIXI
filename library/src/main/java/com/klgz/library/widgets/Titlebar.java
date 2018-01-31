package com.klgz.library.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klgz.library.R;

public class Titlebar extends RelativeLayout{
	
    public Titlebar(Context context) {
		super(context);
		init();
	}
	
	public Titlebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private TextView mTxtTitle;
    private ImageView mImgLeft;
	private CheckBox mTxtRight;
	private void init() {
    	View rootView = inflate(getContext(), R.layout.layout_titlebar, this);
    	mTxtTitle = (TextView) rootView.findViewById(R.id.title_middle);
        mImgLeft = (ImageView) rootView.findViewById(R.id.title_left);
        mTxtRight= (CheckBox) rootView.findViewById(R.id.title_right);
    }
	
    public void setTitleText(int textRes){
    	mTxtTitle.setText(getContext().getString(textRes));
    }
    
    public void setTitleText(String text){
    	mTxtTitle.setText(text);
    }
    
    public void setOnBackListener(OnClickListener listener){
    	mImgLeft.setOnClickListener(listener);
    }
    
    public void setBackVisibility(int visible){
    	mImgLeft.setVisibility(visible);
    }

    public void setRightText(int textRes){
        mTxtRight.setText(getContext().getString(textRes));
    }

    public CheckBox setRightText(String text){
        mTxtRight.setText(text);
       return mTxtRight;
    }

    public void setRightOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
        mTxtRight.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}
