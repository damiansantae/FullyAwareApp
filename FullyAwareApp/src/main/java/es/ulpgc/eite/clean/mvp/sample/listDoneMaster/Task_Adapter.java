package es.ulpgc.eite.clean.mvp.sample.listDoneMaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.ulpgc.eite.clean.mvp.sample.R;
import es.ulpgc.eite.clean.mvp.sample.app.TaskDone;

/**
 * Created by Damian on 26/02/2017.
 */

public class Task_Adapter extends ArrayAdapter<TaskDone> {
    public Task_Adapter(Context context, int resource) {
        super(context, resource);
    }

    public Task_Adapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public Task_Adapter(Context context, int resource, TaskDone[] objects) {
        super(context, resource, objects);
    }

    public Task_Adapter(Context context, int resource, int textViewResourceId, TaskDone[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public Task_Adapter(Context context, int resource, List<TaskDone> objects) {
        super(context, resource, objects);
    }

    public Task_Adapter(Context context, int resource, int textViewResourceId, List<TaskDone> objects) {
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
        TaskDone TaskDone = getItem(position);

        // Setup.
        tag.setImageResource(TaskDone.getTagId());
        title.setText(TaskDone.getTitle());
        description.setText(TaskDone.getDescription());
        date.setText(TaskDone.getDate());

        return convertView;
    }

}
