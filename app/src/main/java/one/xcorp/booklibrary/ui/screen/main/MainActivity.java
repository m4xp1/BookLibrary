package one.xcorp.booklibrary.ui.screen.main;

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

import com.annimon.stream.Stream;

import butterknife.BindView;
import butterknife.OnClick;
import one.xcorp.booklibrary.R;
import one.xcorp.booklibrary.core.data.IDataProvider;
import one.xcorp.booklibrary.core.data.book.Book;
import one.xcorp.booklibrary.core.data.book.BookProviderFactory;
import one.xcorp.booklibrary.ui.screen.Activity;
import one.xcorp.booklibrary.ui.screen.book.BookEditActivity;
import one.xcorp.booklibrary.ui.widget.RecyclerTouchHelper;

public class MainActivity extends Activity {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private IDataProvider<Book> provider;
    private BookAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureRecycler();

        provider = BookProviderFactory.getDefault();
        adapter.setItems(Stream.of(provider.list())
                .sortBy(Book::getAuthor)
                .toList());
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
        startActivity(BookEditActivity.launch(this, book));
    }

    @OnClick(R.id.fab)
    public void onAddClick(View view) {
        startActivity(BookEditActivity.launch(this));
    }

    private final RecyclerTouchHelper callbackTouchHelper =
            new RecyclerTouchHelper(0, ItemTouchHelper.LEFT) {
                @Override protected View obtainForeground(RecyclerView.ViewHolder viewHolder) {
                    return ((BookAdapter.ViewHolder) viewHolder).foreground;
                }

                @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int deletedIndex = viewHolder.getAdapterPosition();
                    Book deletedItem = adapter.removeItem(deletedIndex);
                    provider.delete(deletedItem);

                    Snackbar snackbar = Snackbar
                            .make(coordinator, R.string.book_was_deleted, Snackbar.LENGTH_LONG);
                    snackbar.setAction(android.R.string.cancel,
                            view -> {
                                adapter.restoreItem(deletedItem, deletedIndex);
                                provider.add(deletedItem);
                            });
                    snackbar.show();
                }
            };
}