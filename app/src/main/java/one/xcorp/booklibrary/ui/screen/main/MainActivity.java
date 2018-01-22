package one.xcorp.booklibrary.ui.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import one.xcorp.booklibrary.R;
import one.xcorp.booklibrary.core.data.IDataProvider;
import one.xcorp.booklibrary.core.data.book.Book;
import one.xcorp.booklibrary.core.data.book.BookProviderFactory;
import one.xcorp.booklibrary.ui.screen.Activity;
import one.xcorp.booklibrary.ui.screen.book.BookEditActivity;
import one.xcorp.booklibrary.ui.widget.RecyclerTouchHelper;

import static one.xcorp.booklibrary.core.Utils.sort;
import static one.xcorp.booklibrary.ui.screen.book.BookEditActivity.EXTRA_BOOK;
import static one.xcorp.booklibrary.ui.screen.book.BookEditActivity.RESULT_ADD;
import static one.xcorp.booklibrary.ui.screen.book.BookEditActivity.RESULT_UPDATE;

public class MainActivity extends Activity {

    private static final int REQUEST_EDIT_ACTIVITY = 100;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private IDataProvider<Book> provider;
    private BookAdapter adapter;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureRecycler();

        provider = BookProviderFactory.getDefault();
        disposables.add(provider.list()
                .subscribeOn(Schedulers.io())
                .map(books -> sort(books, Book::getAuthor))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::setItems, throwable -> showToast(R.string.loading_failed)));
    }

    private void configureRecycler() {
        LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        ItemTouchHelper touchHelper = new ItemTouchHelper(callbackTouchHelper);
        touchHelper.attachToRecyclerView(recycler);

        adapter = new BookAdapter(this);
        adapter.setOnClickListener(this::onItemClick);
        recycler.setAdapter(adapter);

        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    @SuppressWarnings("unused")
    private void onItemClick(Book book, int position) {
        startActivityForResult(BookEditActivity.launch(this, book), REQUEST_EDIT_ACTIVITY);
    }

    @OnClick(R.id.fab)
    public void onAddClick(View view) {
        startActivityForResult(BookEditActivity.launch(this), REQUEST_EDIT_ACTIVITY);
    }

    public void onDelete(Book book, int position) {
        disposables.add(provider.delete(book)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> adapter.removeItem(book), throwable -> {
                    showToast(R.string.request_failed);
                    adapter.insertItem(book, position);
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_ACTIVITY && data != null) {
            Book book = (Book) data.getSerializableExtra(EXTRA_BOOK);
            if (resultCode == RESULT_ADD) {
                adapter.getItems().add(book);
                sort(adapter.getItems(), Book::getAuthor);
                adapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_UPDATE) {
                adapter.replaceItem(book);
            }
        }
    }

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

    private final RecyclerTouchHelper callbackTouchHelper =
            new RecyclerTouchHelper(0, ItemTouchHelper.LEFT) {
                @Override protected View obtainForeground(RecyclerView.ViewHolder viewHolder) {
                    return ((BookAdapter.ViewHolder) viewHolder).foreground;
                }

                @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    Book book = adapter.removeItem(position);

                    Snackbar snackbar = Snackbar
                            .make(coordinator, R.string.book_was_deleted, Snackbar.LENGTH_LONG);
                    snackbar.setAction(android.R.string.cancel,
                            view -> adapter.insertItem(book, position));
                    snackbar.addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            if (event != DISMISS_EVENT_ACTION) {
                                onDelete(book, position);
                            }
                        }
                    });
                    snackbar.show();
                }
            };
}