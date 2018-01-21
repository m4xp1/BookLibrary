package one.xcorp.booklibrary.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

@SuppressWarnings("unused")
public class FABScrollBehavior extends FloatingActionButton.Behavior {

    private boolean isHide;

    public FABScrollBehavior() { /* do nothing */ }

    public FABScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target,
                                       int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child,
                               @NonNull View target,
                               int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed,
                               int type) {
        super.onNestedScroll(coordinatorLayout, child, target,
                dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        if (!isHide && dyConsumed > 0) {
            isHide = true;
            int bottomMargin = ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin;
            child.animate()
                    .translationY(child.getHeight() + bottomMargin)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
        } else if (isHide && dyConsumed < 0) {
            isHide = false;
            child.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }
}