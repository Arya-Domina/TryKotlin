package com.example.programmer.trykotlin.ui;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.programmer.trykotlin.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchableAdapterJ extends Adapter implements Filterable {

    public SearchableAdapterJ(@NotNull Context context, @NotNull ArrayList<User> list) {
        super(context, list);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
//                if (constraint != null && constraint.length() > 0) {
//                    autocomplete(constraint.toString());
//                    filterResults.values = autocompleteItems;
//                    filterResults.count = autocompleteItems.size();
//                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
//                else {
//                    notifyDataSetInvalidated();
//                }
            }
        };
        return filter;
    }
}
