package example.rafao1991.filterablelistexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<String> mainList, filteredMainList;
    private ItemFilter mFilter = new ItemFilter();

    public MainAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.mainList = list;
        this.filteredMainList = list;
    }

    @Override
    public int getCount() {
        return filteredMainList.size();
    }

    @Override
    public String getItem(int position) {
        return filteredMainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        TextView text;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_main, viewGroup, false);
            text = (TextView) view.findViewById(R.id.text_main);
            view.setTag(new ViewHolder(text));
        } else {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            text = viewHolder.text;
        }

        String item = getItem(position);
        text.setText(item);

        return view;
    }

    private static class ViewHolder {
        public final TextView text;

        public ViewHolder(TextView text) {
            this.text = text;
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterString = charSequence.toString().toLowerCase();
            FilterResults results = new FilterResults();

            final ArrayList<String> list = mainList;
            final ArrayList<String> filteredList = new ArrayList<>(list.size());

            String filterableString ;

            for (int i = 0; i < list.size(); i++) {
                filterableString = list.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    filteredList.add(filterableString);
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredMainList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
