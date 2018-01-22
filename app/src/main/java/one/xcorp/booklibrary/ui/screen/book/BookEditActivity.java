package one.xcorp.booklibrary.ui.screen.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import one.xcorp.booklibrary.R;
import one.xcorp.booklibrary.core.data.IDataProvider;
import one.xcorp.booklibrary.core.data.book.Book;
import one.xcorp.booklibrary.core.data.book.BookProviderFactory;
import one.xcorp.booklibrary.ui.dialog.InputDialog;
import one.xcorp.booklibrary.ui.screen.Activity;

import static one.xcorp.booklibrary.core.Utils.castToInt;
import static one.xcorp.booklibrary.core.Utils.validString;

public class BookEditActivity extends Activity {

    public static final String EXTRA_BOOK = "book";

    public static final int RESULT_ADD = 10;
    public static final int RESULT_UPDATE = 11;

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

    private IDataProvider<Book> provider;
    private Book book;

    private Values values = new Values();
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAppBarScrolled(false);
        bindDialogListeners();

        provider = BookProviderFactory.getDefault();

        book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);
        if (book != null) {
            setTitle(book.getAuthor());
            setBookValues();
        }
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
    private void setBookValues() {
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
                .listener(imageRequestListener)
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
            book = new Book(values.author, values.name, values.year, values.pages)
                    .setIllustration(values.illustration)
                    .setExcerpt(values.excerpt);

            complete(provider.add(book), book, RESULT_ADD);
        } else {
            book.setIllustration(values.illustration);
            book.setAuthor(values.author);
            book.setName(values.name);
            book.setYear(values.year);
            book.setPages(values.pages);
            book.setExcerpt(values.excerpt);

            complete(provider.update(book), book, RESULT_UPDATE);
        }
    }

    private void complete(Completable completable, Book book, int resultCode) {
        disposables.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_BOOK, book);
                    setResult(resultCode, data);
                    finish();
                }, throwable -> showToast(R.string.request_failed)));
    }

    @SuppressWarnings("ConstantConditions")
    private boolean validate() {
        boolean isValid = true;

        values.illustration = validString(
                illustration.getContentDescription().toString(), null);

        values.author = author.getEditText().getText().toString();
        if (values.author.isEmpty()) {
            showErrorTextInput(author, R.string.empty_value);
            isValid = false;
        }
        values.name = name.getEditText().getText().toString();
        if (values.name.isEmpty()) {
            showErrorTextInput(name, R.string.empty_value);
            isValid = false;
        }
        values.year = castToInt(year.getEditText().getText().toString());
        if (values.year == null) {
            showErrorTextInput(year, R.string.empty_value);
            isValid = false;
        }
        values.pages = castToInt(pages.getEditText().getText().toString());
        if (values.pages == null) {
            showErrorTextInput(pages, R.string.empty_value);
            isValid = false;
        }

        values.excerpt = validString(excerpt.getEditText().getText().toString(), null);

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

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

    private final RequestListener<Drawable> imageRequestListener = new RequestListener<Drawable>() {
        @Override public boolean onLoadFailed(@Nullable GlideException e,
                                              Object model,
                                              Target<Drawable> target,
                                              boolean isFirstResource) {
            showExpandedTitle();
            return false;
        }

        @Override public boolean onResourceReady(Drawable resource,
                                                 Object model,
                                                 Target<Drawable> target,
                                                 DataSource dataSource,
                                                 boolean isFirstResource) {
            hideExpandedTitle();
            return false;
        }
    };

    private static class Values {
        String illustration;
        String author;
        String name;
        Integer year;
        Integer pages;
        String excerpt;
    }
}
