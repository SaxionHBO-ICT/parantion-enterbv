package nl.enterbv.easion.Model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.enterbv.easion.R;

/**
 * Created by joepv on 15.jun.2016.
 */

public class TaskListAdapter extends ArrayAdapter<Enquete> {

    public TaskListAdapter(Context context, List<Enquete> objects) {
        super(context, -1, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task_list, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.tv_item_title);
        title.setText(getItem(position).getLabel());

        TextView organization = (TextView) convertView.findViewById(R.id.tv_item_organization);
        organization.setText(getItem(position).getSender());

        TextView date = (TextView) convertView.findViewById(R.id.tv_item_date);
        date.setText(getItem(position).getCreationDate());

        View progress = convertView.findViewById(R.id.item_progress);

        switch (getItem(position).getProgress()) {
            case 0:
                progress.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 1:
                progress.setBackgroundColor(Color.parseColor("#606060"));

                break;
            case 2:
                break;
            default:
                break;


        }

        return convertView;
    }
}
