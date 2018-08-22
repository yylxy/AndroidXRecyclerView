package com.joyoung.xrecylerview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * 说明：ViewHolder，view的一些设置
 * 杨阳：360621904 2018/5/30
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class WrapperViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = new SparseArray<>();
    private Context mContext;
    private int mLayoutId;
    private View mConvertView;

    public WrapperViewHolder(View itemView, int layoutId) {
        super(itemView);
        mLayoutId = layoutId;
        mConvertView = itemView;
        mContext = itemView.getContext();
    }


    public int getLayoutId() {
        return mLayoutId;
    }

    /**
     * 方法功能：设置view的背景点击效果
     */
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

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  说明：view 设置方法     2018/5/31
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public WrapperViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public WrapperViewHolder setText(int viewId, Number text) {
        TextView tv = getView(viewId);
        tv.setText(String.valueOf(text));
        return this;
    }

    public WrapperViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public WrapperViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }


    public WrapperViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public WrapperViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public WrapperViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public WrapperViewHolder setBackgroundDrawable(int viewId, Drawable drawable) {
        View view = getView(viewId);
        setBackgroundCompat(view, drawable);
        return this;
    }

    public WrapperViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public WrapperViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint({"NewApi", "ObsoleteSdkInt"})
    public WrapperViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public WrapperViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public WrapperViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public WrapperViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public WrapperViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public WrapperViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public WrapperViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public WrapperViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public WrapperViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public WrapperViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public WrapperViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public WrapperViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public void setViewSize(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.height = size;
        params.width = size;
        view.setLayoutParams(params);
        view.setMinimumWidth(size);
        view.setMinimumHeight(size);
    }

    public WrapperViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public WrapperViewHolder setOnTouchListener(int viewId,
                                                View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public WrapperViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
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
