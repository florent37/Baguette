package com.github.florent37.baguette;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by florentchampigny on 19/04/15.
 */
public class Baguette {

    private static final String TAG = Baguette.class.getSimpleName();

    public interface BaguetteListener {
        public void onActionClicked();
    }

    /**
     * @hide
     */
    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     */
    public static final int LENGTH_SHORT = 0;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     */
    public static final int LENGTH_LONG = 1;

    private static final int LONG_DELAY = 3500; // 3.5 seconds
    private static final int SHORT_DELAY = 2000; // 2 seconds

    Context mContext;
    CharSequence mText;
    int mDuration;
    View mView;
    View mVActionView;
    ImageView mVActionImageView;

    private Baguette(Context context, CharSequence text, int duration) {
        this.mContext = context;
        this.mText = text;
        this.mDuration = duration;

        View v = LayoutInflater.from(mContext).inflate(R.layout.baguette_layout, null, false);
        TextView tv = (TextView) v.findViewById(R.id.baguette_message);
        tv.setText(text);

        View clickableLayout = v.findViewById(R.id.baguette_layout);

        mView = v;
        clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click");
                doHide();
            }
        });

        mVActionView = v.findViewById(R.id.baguette_action_layout);
        mVActionImageView = (ImageView) v.findViewById(R.id.baguette_action_icon);
    }

    public static Baguette makeText(Context context, CharSequence text, @Duration int duration) {
        Baguette baguette = new Baguette(context, text, duration);
        return baguette;
    }

    public static Baguette makeText(Context context, int resId, @Duration int duration) {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    private static Baguette currentBaguette = null;
    private static Baguette lastBaguette = null;
    private Baguette nextBaguette = null;

    public void show() {
        if (currentBaguette == null) {
            currentBaguette = this;
            lastBaguette = this;
            doShow();
        } else {
            lastBaguette.nextBaguette = this;
            lastBaguette = this;
        }
    }

    public void cancel() {

    }

    public Baguette enableUndo(final BaguetteListener baguetteListener) {
        mVActionView.setVisibility(View.VISIBLE);
        mVActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doHide();
                if (baguetteListener != null) {
                    baguetteListener.onActionClicked();
                }
            }
        });
        return this;
    }

    public Baguette setAction(final Drawable drawable, final BaguetteListener baguetteListener) {
        mVActionImageView.setImageDrawable(drawable);
        mVActionView.setVisibility(View.VISIBLE);
        mVActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doHide();
                if (baguetteListener != null) {
                    baguetteListener.onActionClicked();
                }
            }
        });
        return this;
    }

    private int comuteDuration() {
        if (mDuration == LENGTH_SHORT)
            return SHORT_DELAY;
        else return LONG_DELAY;
    }

    final static Handler mHandler = new Handler(Looper.getMainLooper());

    private void handleShow() {
        if (mView != null && mContext != null && mContext instanceof Activity) {
            ((ViewGroup) ((Activity) mContext).getWindow().getDecorView()).addView(mView);
            mView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                @Override
                public void onDraw() {
                    mView.setAlpha(0);
                    mView.setTranslationY(mView.getHeight());
                    mView.animate().alpha(1).translationY(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mHandler.postDelayed(mHide, comuteDuration());
                        }
                    }).setDuration(500).start();
                    mView.getViewTreeObserver().removeOnDrawListener(this);
                }
            });
        }
    }

    private void handleHide() {
        mView.animate().alpha(0).translationY(mView.getHeight()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mView != null && mView.getParent() != null) {
                    ((ViewGroup) mView.getParent()).removeView(mView);
                    currentBaguette = nextBaguette;

                    if (currentBaguette != null) {
                        currentBaguette.doShow();
                    } else {
                        lastBaguette = null;
                    }
                }
            }
        }).setDuration(500).start();
    }

    private void doShow() {
        Log.d(TAG, "doShow");
        mHandler.post(mShow);
    }

    private void doHide() {
        mHandler.post(mHide);
    }

    final Runnable mShow = new Runnable() {
        @Override
        public void run() {
            handleShow();
        }
    };

    final Runnable mHide = new Runnable() {
        @Override
        public void run() {
            handleHide();
        }
    };

}
