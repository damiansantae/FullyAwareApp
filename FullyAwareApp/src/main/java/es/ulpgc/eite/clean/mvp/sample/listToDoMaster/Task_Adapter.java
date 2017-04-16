package es.ulpgc.eite.clean.mvp.sample.listToDoMaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.TaskToDo;


public class Task_Adapter extends ArrayAdapter<TaskToDo> {
    public Task_Adapter(Context context, int resource) {
        super(context, resource);
    }

    public Task_Adapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public Task_Adapter(Context context, int resource, TaskToDo[] objects) {
        super(context, resource, objects);
    }

    public Task_Adapter(Context context, int resource, int textViewResourceId, TaskToDo[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public Task_Adapter(Context context, int resource, List<TaskToDo> objects) {
        super(context, resource, objects);
    }

    public Task_Adapter(Context context, int resource, int textViewResourceId, List<TaskToDo> objects) {
        super(context, resource, textViewResourceId, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.item_list,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView tag = (ImageView) convertView.findViewById(R.id.tag);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        // Lead actual.
        TaskToDo taskToDo = getItem(position);

        // Setup.
        tag.setImageResource(taskToDo.getTagId());
        title.setText(taskToDo.getTitle());
        description.setText(taskToDo.getDescription());
        date.setText(taskToDo.getDate());

        return convertView;
    }

}
