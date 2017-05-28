package es.ulpgc.eite.clean.mvp.sample;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import es.ulpgc.eite.clean.mvp.sample.app.Subject;
import es.ulpgc.eite.clean.mvp.sample.app.Task;
import es.ulpgc.eite.clean.mvp.sample.listDoneMaster.ListDoneViewMaster;
import es.ulpgc.eite.clean.mvp.sample.listToDoMaster.ListToDoViewMaster;


public class TaskRecyclerViewAdapter
        extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {


    private List<Task> items;
    private ListToDoViewMaster listToDoViewMaster;
    private ListDoneViewMaster listDoneViewMaster;


    public TaskRecyclerViewAdapter(ListToDoViewMaster listToDoViewMaster) {
      this.listToDoViewMaster =listToDoViewMaster;
        items = new ArrayList<>();
    }
    public TaskRecyclerViewAdapter(ListDoneViewMaster listDoneViewMaster) {
        this.listDoneViewMaster = listDoneViewMaster;
        items = new ArrayList<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list, parent, false);
        return new ViewHolder(view);
    }

    public void setItemList(List<Task> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    public List<Task> getItems() {
        return this.items;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Task task = items.get(position);
        holder.bindView(task);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        private ImageView tag;
        private TextView abrv;
        private Subject subject;
        private TextView title;
        private TextView description;
        private TextView date;
        private Drawable drawable;

        public Task item;

        public ViewHolder(View view) {
            super(view);

            itemView = view;

        }


        public void bindView(final Task task) {
            subject = task.getSubject();
            Integer color = subject.getColor();
            tag = (ImageView) itemView.findViewById(R.id.color_subject);
            abrv = (TextView) itemView.findViewById(R.id.tag_subjectc);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);

            String abrev = null;
            if(listToDoViewMaster!=null){
                drawable = listToDoViewMaster.getDrawable(R.drawable.circle);
                drawable.setColorFilter(listToDoViewMaster.getColor(color), PorterDuff.Mode.SRC_OVER);
                itemView.setSelected(listToDoViewMaster.getPresenter().isSelected(getAdapterPosition()));
                 abrev = listToDoViewMaster.getPresenter().getCases(task);

                if(listToDoViewMaster.getPresenter().isTaskForgotten(task.getDate())){
                    date.setTextColor(Color.RED);
                }

            }else if(listDoneViewMaster !=null){
               drawable = listDoneViewMaster.getDrawable(R.drawable.circle);
                drawable.setColorFilter(listDoneViewMaster.getColor(color), PorterDuff.Mode.SRC_OVER);
                itemView.setSelected(listDoneViewMaster.getPresenter().isSelected(getAdapterPosition()));
                abrev = listDoneViewMaster.getPresenter().getCases(task);
            }

            title.setText(task.getTitle());
            description.setText(task.getDescription());
            date.setText(task.getDate());

            tag.setImageDrawable(drawable);
            abrv.setText(abrev);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listToDoViewMaster!=null){
                       listToDoViewMaster.getPresenter().onListClick(itemView, getAdapterPosition(), task);
                    }else if(listDoneViewMaster !=null){
                        listDoneViewMaster.getPresenter().onListClick(itemView, getAdapterPosition(), task);
                    }


                    notifyDataSetChanged();

                }
            });
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(listToDoViewMaster!=null){
                        listToDoViewMaster.getPresenter().onLongListClick(v, getAdapterPosition());

                    }else if(listDoneViewMaster !=null){
                   listDoneViewMaster.getPresenter().onLongListClick(v,getAdapterPosition());
                    }

                    return true;
                }
            });

        }
    }


}
