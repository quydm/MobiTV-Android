package vn.net.mobitv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import vn.net.mobitv.util.AnsweredQuestionAdapter;
import vn.net.mobitv.util.DataHolder;

/**
 * @author quydm
 */
public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		boolean win = getIntent().getBooleanExtra("win", false);
		TextView lblResult = (TextView) findViewById(R.id.lbl_result);
		if (win)
			lblResult.setText(R.string.lbl_message2);
		else
			lblResult.setText(R.string.lbl_message3);

		ListView listQuestion = (ListView) findViewById(R.id.list_question);
		AnsweredQuestionAdapter adapter = new AnsweredQuestionAdapter(this, DataHolder.answeredQuestion);
		listQuestion.setAdapter(adapter);
	}

	public void replay(View v) {
		startActivity(new Intent(this, QuestionActivity.class));
		finish();
	}

	public void quit(View v) {
		finish();
	}

}
