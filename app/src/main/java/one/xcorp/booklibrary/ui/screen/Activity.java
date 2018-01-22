package one.xcorp.booklibrary.ui.screen;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.ButterKnife;

@SuppressWarnings({"unused", "SameParameterValue"})
public abstract class Activity extends AppCompatActivity {

    private static final int PERCENTAGE_TO_SHOW_TITLE = 79;

    private @Nullable AppBarLayout appBarLayout;
    private @Nullable CollapsingToolbarLayout collapsingToolbarLayout;
    private @Nullable Toolbar toolbar;

    private int maxScrollSize;
    private boolean isShowTitle;
    private CharSequence title;

    private Toast toast;
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        appBarLayout = findViewById(getResources()
                .getIdentifier("app_bar", "id", getPackageName()));
        collapsingToolbarLayout = findViewById(getResources()
                .getIdentifier("toolbar_layout", "id", getPackageName()));
        toolbar = findViewById(getResources()
                .getIdentifier("toolbar", "id", getPackageName()));

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void setTitle(int resId) {
        super.setTitle(resId);
        this.title = getString(resId);
        if (toolbar != null) {
            toolbar.setTitle(resId);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.title = title;
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    public @Nullable AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    public @Nullable CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return collapsingToolbarLayout;
    }

    public @Nullable Toolbar getToolbar() {
        return toolbar;
    }

    protected void setAppBarScrolled(boolean scrolled) {
        if (appBarLayout == null) {
            return;
        }

        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (behavior == null) {
            params.setBehavior(behavior = new AppBarLayout.Behavior());
        }

        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return scrolled;
            }
        });
    }

    protected void showExpandedTitle() {
        if (appBarLayout == null || collapsingToolbarLayout == null || toolbar == null) {
            return;
        }

        collapsingToolbarLayout.setTitle(title);
        appBarLayout.removeOnOffsetChangedListener(onOffsetChangedListener);
        isShowTitle = false;
    }

    protected void hideExpandedTitle() {
        if (appBarLayout == null || collapsingToolbarLayout == null || toolbar == null) {
            return;
        }

        title = collapsingToolbarLayout.getTitle();

        toolbar.setTitle("");
        collapsingToolbarLayout.setTitle("");

        appBarLayout.addOnOffsetChangedListener(
                onOffsetChangedListener = (appBarLayout, verticalOffset) -> {
                    if (maxScrollSize == 0) {
                        maxScrollSize = appBarLayout.getTotalScrollRange();
                    }

                    int percentage = (Math.abs(verticalOffset)) * 100 / maxScrollSize;

                    if (percentage > PERCENTAGE_TO_SHOW_TITLE && !isShowTitle) {
                        collapsingToolbarLayout.setTitle(title);
                        isShowTitle = true;
                    } else if (percentage < PERCENTAGE_TO_SHOW_TITLE && isShowTitle) {
                        collapsingToolbarLayout.setTitle("");
                        isShowTitle = false;
                    }
                });
    }

    public void showToast(int resId) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(this, resId, Toast.LENGTH_LONG);
        toast.show();
    }
}