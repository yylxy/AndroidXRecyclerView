package com.joyoung.xrecylerview.databinding_adapter;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;

@SuppressWarnings("WeakerAccess")
public class DataBindingHolder<HB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private HB mBinding;

    public DataBindingHolder(HB binding) {
        super(binding.getRoot());
        mBinding = binding;
        setItemBackground();
    }

    public HB getBinding() {
        return mBinding;
    }
    
    
    public void setItemBackground() {
        int pressed = Color.GRAY;
        Drawable content = itemView.getBackground();
        if (content == null) {
            content = new ColorDrawable(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList colorList = new ColorStateList(new int[][]{{}}, new int[]{pressed});
            RippleDrawable ripple = new RippleDrawable(colorList, content.getAlpha() == 0 ? null : content, content.getAlpha() == 0 ? new ColorDrawable(Color.WHITE) : null);
            setBackgroundCompat(itemView, ripple);
        } else {
            StateListDrawable bg = new StateListDrawable();
            // View.PRESSED_ENABLED_STATE_SET
            bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, new ColorDrawable(pressed));
            // View.EMPTY_STATE_SET
            bg.addState(new int[]{}, content);
        }
    }
    
    @SuppressLint("ObsoleteSdkInt")
    private void setBackgroundCompat(View view, Drawable drawable) {
        int pL = view.getPaddingLeft();
        int pT = view.getPaddingTop();
        int pR = view.getPaddingRight();
        int pB = view.getPaddingBottom();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setPadding(pL, pT, pR, pB);
    }
}
