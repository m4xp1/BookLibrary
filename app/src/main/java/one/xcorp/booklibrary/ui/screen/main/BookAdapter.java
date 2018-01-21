package one.xcorp.booklibrary.ui.screen.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import one.xcorp.booklibrary.R;
import one.xcorp.booklibrary.core.data.book.Book;

@SuppressWarnings("WeakerAccess")
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private final String yearPattern;
    private final String pagesPattern;

    private final List<Book> items;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> items) {
        this.yearPattern = context.getString(R.string.short_year_pattern);
        this.pagesPattern = context.getString(R.string.short_pages_pattern);

        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_book, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book item = items.get(position);

        holder.author.setText(item.getAuthor());
        holder.year.setText(String.format(yearPattern, item.getYear()));
        holder.name.setText(item.getName());
        holder.pages.setText(String.format(pagesPattern, item.getPages()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public @Nullable Book removeItem(int position) {
        Book item = items.remove(position);
        notifyItemRemoved(position);

        return item;
    }

    public void restoreItem(Book item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.foreground)
        View foreground;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.year)
        TextView year;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.pages)
        TextView pages;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}