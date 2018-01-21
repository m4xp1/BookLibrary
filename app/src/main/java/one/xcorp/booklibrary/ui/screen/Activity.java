package one.xcorp.booklibrary.ui.screen;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

@SuppressWarnings("unused")
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

    public @Nullable Toolbar getToolbar() {
        return toolbar;
    }
}