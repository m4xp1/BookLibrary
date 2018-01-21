package one.xcorp.booklibrary.ui.screen.book;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import one.xcorp.booklibrary.R;
import one.xcorp.booklibrary.ui.screen.Activity;

public class BookDetailsActivity extends Activity {

    @BindView(R.id.illustration)
    ImageView illustration;
    @BindView(R.id.btn_illustration)
    FloatingActionButton illustrationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAppBarScrolled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_edit:
                onEditClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onEditClick() {

    }

    @OnClick(R.id.btn_illustration)
    public void onChangeIllustration() {

    }
}
