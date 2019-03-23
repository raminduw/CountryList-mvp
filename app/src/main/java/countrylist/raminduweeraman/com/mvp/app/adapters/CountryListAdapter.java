package countrylist.raminduweeraman.com.mvp.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import countrylist.raminduweeraman.com.mvp.R;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryListItemViewModel;


public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {
    private ItemClickListener clickListener;
    private List<CountryListItemViewModel> countryListItemViewModels;

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView countryCode,countryName,countryRegion;

        public CountryViewHolder(View view) {
            super(view);
            countryCode = (TextView) view.findViewById(R.id.countryCode);
            countryName = (TextView) view.findViewById(R.id.countryName);
            countryRegion = (TextView) view.findViewById(R.id.countryRegion);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(countryListItemViewModels.get(getAdapterPosition()));
            }
        }
    }


    public CountryListAdapter(List<CountryListItemViewModel> countryListItemViewModels, ItemClickListener clickListener) {
        this.countryListItemViewModels = countryListItemViewModels;
        this.clickListener = clickListener;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_list_item, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        CountryListItemViewModel sectionListItemViewModel = countryListItemViewModels.get(position);
        holder.countryName.setText(sectionListItemViewModel.getName());
        holder.countryCode.setText(sectionListItemViewModel.getAlpha2Code());
        holder.countryRegion.setText(sectionListItemViewModel.getRegion());
    }

    @Override
    public int getItemCount() {
        return countryListItemViewModels.size();
    }

    public interface ItemClickListener {
        void onClick(CountryListItemViewModel countryListItemViewModel);
    }
}
