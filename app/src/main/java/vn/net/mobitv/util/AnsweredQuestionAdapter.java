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
import vn.net.mobitv.objects.Question;

/**
 * @author quydm
 */
public class AnsweredQuestionAdapter extends ArrayAdapter<Question> {

	private LayoutInflater inflater;

	public AnsweredQuestionAdapter(Context context, List<Question> objects) {
		super(context, 0, objects);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.answered_question, null);

		Question question = getItem(position);
		TextView txtQuestNumber = (TextView) convertView.findViewById(R.id.answered_question_number);
		txtQuestNumber.setText(getContext().getString(R.string.answered_question_number, (position + 1), question.text));

		TextView txtCorrectAnswer = (TextView) convertView.findViewById(R.id.correct_answer);
		for (Answer a : question.answers) {
			if (a.correct) {
				txtCorrectAnswer.setText(getContext().getString(R.string.answered_answers, a.text));
				break;
			}
		}

		return convertView;
	}

}
