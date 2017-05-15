package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class CalendarFragment extends Fragment implements OnDateSelectedListener{
    private MaterialCalendarView calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = (MaterialCalendarView) view.findViewById(R.id.calendar_view);
        calendar.setOnDateChangedListener(this);

        //calendar.

        getActivity().setTitle(getString(R.string.my_calendar));

        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        CalendarDayDetailsFragmentDialog day =new CalendarDayDetailsFragmentDialog();
        Bundle args = new Bundle();
        String d = date.toString().substring(12,date.toString().length()-1);
        String[] tmp = d.split("-");

        tmp[1]=String.valueOf(new Integer(tmp[1]).intValue()+1).length()==1?"0"+String.valueOf(new Integer(tmp[1]).intValue()+1):String.valueOf(new Integer(tmp[1]).intValue()+1);
        tmp[2]=tmp[2].length()==1?"0"+tmp[2]:tmp[2];

        args.putString("date", String.format("%s-%s-%s",tmp));
        day.setArguments(args);
        day.show(getFragmentManager(), "fragmentManager");
    }


}
