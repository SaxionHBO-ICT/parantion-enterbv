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


        //Progress of task: 0 = Nieuw, 1 = Bezig, 2 = Klaar
        switch (getItem(position).getProgress()) {
            case 0:
                progress.setBackgroundResource(R.drawable.nieuw);
                break;
            case 1:
                progress.setBackgroundResource(R.drawable.bezig);
                break;
            case 2:
                progress.setBackgroundResource(R.drawable.klaar);
                break;
            default:
                break;


        }

        return convertView;
    }
}
