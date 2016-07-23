package vn.net.mobitv.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.net.mobitv.R;
import vn.net.mobitv.objects.Answer;

/**
 * @author quydm
 */
public class AnswerAdapter extends ArrayAdapter<Answer> {

	private LayoutInflater inflater;

	public AnswerAdapter(Context context, List<Answer> objects) {
		super(context, 0, objects);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.answer, null);

		Answer answer = getItem(position);
		TextView txt = (TextView) convertView.findViewById(R.id.answer_text);
		txt.setText(answer.text);

		return convertView;
	}

}
