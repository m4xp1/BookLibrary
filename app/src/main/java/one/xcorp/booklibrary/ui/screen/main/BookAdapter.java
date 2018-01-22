package one.xcorp.booklibrary.ui.screen.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.function.BiConsumer;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import one.xcorp.booklibrary.R;
import one.xcorp.booklibrary.core.data.book.Book;

@SuppressWarnings("WeakerAccess")
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private final String yearPattern;
    private final String pagesPattern;

    private final List<Book> items = new ArrayList<>();

    private BiConsumer<Book, Integer> clickListener;

    public BookAdapter(@NonNull Context context) {
        this.yearPattern = context.getString(R.string.short_year_pattern);
        this.pagesPattern = context.getString(R.string.short_pages_pattern);
    }

    public void setItems(Collection<? extends Book> books) {
        items.clear();
        items.addAll(books);

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_book, parent, false));

        viewHolder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                int position = viewHolder.getAdapterPosition();
                clickListener.accept(items.get(position), position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book item = items.get(position);

        if (item.getIllustration() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getIllustration())
                    .into(holder.illustration);
        } else {
            Glide.with(holder.itemView.getContext())
                    .clear(holder.illustration);
            holder.illustration.setImageDrawable(null);
        }

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

    public void setOnClickListener(BiConsumer<Book, Integer> listener) {
        clickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.illustration)
        ImageView illustration;
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