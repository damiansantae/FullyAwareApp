package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.Task;

public class TaskRecyclerViewAdapter
        extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> items;

    public TaskRecyclerViewAdapter() {
        items = new ArrayList<>();
    }

    public void setItemList(List<Task> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.tag.setImageResource(items.get(position).getTagId());
        holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
        holder.date.setText(items.get(position).getDate());
     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onItemClicked(holder.item);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        public final  ImageView tag;
        public final  TextView title;
        public final  TextView description;
        public final    TextView date;

        public Task item;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
             tag = (ImageView) view.findViewById(R.id.tag);
            title = (TextView) view.findViewById(R.id.title);
             description = (TextView) view.findViewById(R.id.description);
           date = (TextView) view.findViewById(R.id.date);

        }

       /* @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }*/
    }
}