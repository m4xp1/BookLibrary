package one.xcorp.booklibrary.ui.screen;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

@SuppressWarnings({"unused", "SameParameterValue"})
public abstract class Activity extends AppCompatActivity {

    private @Nullable Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        toolbar = findViewById(getResources()
                .getIdentifier("toolbar", "id", getPackageName()));

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void setTitle(int resId) {
        super.setTitle(resId);
        if (toolbar != null) {
            toolbar.setTitle(resId);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    public @Nullable Toolbar getToolbar() {
        return toolbar;
    }

    protected void setAppBarScrolled(boolean scrolled) {
        AppBarLayout appBar = findViewById(getResources()
                .getIdentifier("app_bar", "id", getPackageName()));

        if (appBar == null) {
            return;
        }

        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
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
}