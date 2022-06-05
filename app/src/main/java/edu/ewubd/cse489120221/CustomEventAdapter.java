package edu.ewubd.cse489120221;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class CustomEventAdapter extends ArrayAdapter<Event> {

    private final Context context;
    private final ArrayList<Event> value;


    public CustomEventAdapter(@NonNull Context context, @NonNull ArrayList<Event> objects) {
        super(context, -1, objects);
        this.context = context;
        this.value = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_event_row, parent, false);

        TextView eventName = rowView.findViewById(R.id.event_name);
        TextView eventDateTime = rowView.findViewById(R.id.date_time);
        TextView eventPlaceName = rowView.findViewById(R.id.place);

        eventName.setText(value.get(position).name);
        eventDateTime.setText(value.get(position).datetime);
        eventPlaceName.setText(value.get(position).place);

        return rowView;
    }
}