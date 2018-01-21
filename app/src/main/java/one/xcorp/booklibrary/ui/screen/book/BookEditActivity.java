package one.xcorp.booklibrary.ui.screen.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import one.xcorp.booklibrary.R;
import one.xcorp.booklibrary.core.data.IDataProvider;
import one.xcorp.booklibrary.core.data.book.Book;
import one.xcorp.booklibrary.core.data.book.BookProviderFactory;
import one.xcorp.booklibrary.ui.dialog.InputDialog;
import one.xcorp.booklibrary.ui.screen.Activity;

public class BookEditActivity extends Activity {

    public static final String EXTRA_BOOK = "book";

    public static Intent launch(@NonNull Context context) {
        return new Intent(context, BookEditActivity.class);
    }

    public static Intent launch(@NonNull Context context, @NonNull Book book) {
        Intent intent = new Intent(context, BookEditActivity.class);
        intent.putExtra(EXTRA_BOOK, book);

        return intent;
    }

    @BindView(R.id.illustration)
    ImageView illustration;
    @BindView(R.id.author)
    TextInputLayout author;
    @BindView(R.id.name)
    TextInputLayout name;
    @BindView(R.id.year)
    TextInputLayout year;
    @BindView(R.id.pages)
    TextInputLayout pages;
    @BindView(R.id.excerpt)
    TextInputLayout excerpt;

    private String illustrationValue;
    private String authorValue;
    private String nameValue;
    private Integer yearValue;
    private Integer pagesValue;
    private String excerptValue;

    private IDataProvider<Book> provider;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        setTitle(R.string.title_adding);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAppBarScrolled(false);

        provider = BookProviderFactory.getDefault();

        book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);
        if (book != null) {
            setTitle(R.string.title_editing);
            setInitValues();
        }

        bindDialogListeners();
    }

    private void bindDialogListeners() {
        InputDialog inputDialog = (InputDialog)
                getSupportFragmentManager().findFragmentByTag(InputDialog.class.getName());
        if (inputDialog != null) {
            inputDialog.setPositiveListener(this::loadImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_book_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_done:
                onDoneClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    private void setInitValues() {
        if (book.getIllustration() != null) {
            loadImage(book.getIllustration());
        }
        author.getEditText().setText(book.getAuthor());
        name.getEditText().setText(book.getName());
        year.getEditText().setText(String.valueOf(book.getYear()));
        pages.getEditText().setText(String.valueOf(book.getPages()));
        excerpt.getEditText().setText(book.getExcerpt());
    }

    private void loadImage(@NonNull String url) {
        Glide.with(illustration.getContext())
                .load(url)
                .into(illustration);
        illustration.setContentDescription(url);
    }

    @OnClick(R.id.btn_illustration)
    public void onChangeIllustration() {
        InputDialog dialog = InputDialog.newInstance(
                getString(R.string.dialog_choose_illustration_title),
                getString(R.string.dialog_choose_illustration_message),
                illustration.getContentDescription().toString());
        dialog.setPositiveListener(this::loadImage);
        dialog.show(getSupportFragmentManager(), InputDialog.class.getName());
    }

    @OnTextChanged({R.id.authorEdt, R.id.nameEdt, R.id.yearEdt, R.id.pagesEdt})
    public void onTextChanged() {
        //noinspection ConstantConditions
        hideErrorTextInput(author.getEditText().isFocused() ? author :
                name.getEditText().isFocused() ? name :
                        year.getEditText().isFocused() ? year : pages);
    }

    @SuppressWarnings("ConstantConditions")
    private void onDoneClick() {
        if (!validate()) {
            return;
        }

        if (book == null) {
            book = new Book(authorValue, nameValue, yearValue, pagesValue)
                    .setIllustration(illustrationValue)
                    .setExcerpt(excerptValue);

            provider.add(book);
        } else {
            book.setIllustration(illustrationValue);
            book.setAuthor(authorValue);
            book.setName(nameValue);
            book.setYear(yearValue);
            book.setPages(pagesValue);
            book.setExcerpt(excerptValue);

            provider.update(book);
        }

        finish();
    }

    @SuppressWarnings("ConstantConditions")
    private boolean validate() {
        boolean isValid = true;

        illustrationValue = validString(
                illustration.getContentDescription().toString(), null);

        authorValue = author.getEditText().getText().toString();
        if (authorValue.isEmpty()) {
            showErrorTextInput(author, R.string.empty_value);
            isValid = false;
        }
        nameValue = name.getEditText().getText().toString();
        if (nameValue.isEmpty()) {
            showErrorTextInput(name, R.string.empty_value);
            isValid = false;
        }
        yearValue = castToInt(year.getEditText().getText().toString());
        if (yearValue == null) {
            showErrorTextInput(year, R.string.empty_value);
            isValid = false;
        }
        pagesValue = castToInt(pages.getEditText().getText().toString());
        if (pagesValue == null) {
            showErrorTextInput(pages, R.string.empty_value);
            isValid = false;
        }

        excerptValue = validString(excerpt.getEditText().getText().toString(), null);

        return isValid;
    }

    private void showErrorTextInput(TextInputLayout textInput, int resId) {
        textInput.setErrorEnabled(true);
        textInput.setError(getString(resId));
    }

    private void hideErrorTextInput(TextInputLayout textInput) {
        textInput.setErrorEnabled(false);
        textInput.setError("");
    }

    @SuppressWarnings("SameParameterValue")
    private String validString(String value, String defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : value;
    }

    private Integer castToInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
