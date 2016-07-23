package vn.net.mobitv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import vn.net.mobitv.objects.Answer;
import vn.net.mobitv.objects.Question;
import vn.net.mobitv.util.AnswerAdapter;
import vn.net.mobitv.util.DataHolder;

/**
 * @author quydm
 */
public class QuestionActivity extends Activity {

	private static final int MAX_QUESTION = 3;

	private TextView txtQuestionNumber;
	private TextView txtQuestionText;

	private ListView listAnswers;

	private int questionNumber = 0;
	private int numCorrectAnswers = 0;

	private Answer selectedAnswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);

		txtQuestionNumber = (TextView) findViewById(R.id.question_number);
		txtQuestionText = (TextView) findViewById(R.id.question_text);
		listAnswers = (ListView) findViewById(R.id.list_answers);

		Collections.shuffle(DataHolder.questions);
		DataHolder.answeredQuestion = new ArrayList<>();

		showQuestion();
	}

	public void nextQuestion(View v) {
		if (selectedAnswer != null && selectedAnswer.correct)
			numCorrectAnswers++;

		questionNumber++;
		if (questionNumber == MAX_QUESTION) {
			Intent intent = new Intent(this, ResultActivity.class);

			if (numCorrectAnswers == questionNumber)
				intent.putExtra("win", true);
			else
				intent.putExtra("win", false);

			startActivity(intent);
			finish();
			return;
		}

		showQuestion();
	}

	private void showQuestion() {
		selectedAnswer = null;

		Question question = DataHolder.questions.get(questionNumber);
		DataHolder.answeredQuestion.add(question);

		txtQuestionNumber.setText(getString(R.string.lbl_question_number, (questionNumber + 1)));
		txtQuestionText.setText(question.text);

		Collections.shuffle(question.answers);
		AnswerAdapter adapter = new AnswerAdapter(this, question.answers);
		listAnswers.setAdapter(adapter);
		listAnswers.setOnItemClickListener(new AnswerListener(question) {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				selectedAnswer = getQuestion().answers.get(position);
			}
		});
	}

	private abstract class AnswerListener implements AdapterView.OnItemClickListener {

		private Question question;

		public AnswerListener(Question question) {
			this.question = question;
		}

		public Question getQuestion() {
			return question;
		}

	}

}
