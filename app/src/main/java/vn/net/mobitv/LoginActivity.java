package vn.net.mobitv;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.net.mobitv.objects.Question;
import vn.net.mobitv.util.DataHolder;

/**
 * @author quydm
 */
public class LoginActivity extends Activity {

	private EditText txtCusName;
	private EditText txtPhone;
	private ProgressDialog loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		txtCusName = (EditText) findViewById(R.id.txt_customer_name);
		txtPhone = (EditText) findViewById(R.id.txt_phone);
		txtPhone.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
				if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
					showQuestion(null);
					return true;
				}
				return false;
			}
		});

		loading = new ProgressDialog(this);
		loading.setTitle("");
		loading.setMessage(getString(R.string.loading));
		loading.setCancelable(false);
	}

	public void showQuestion(View v) {
		if (TextUtils.isEmpty(txtCusName.getText().toString())) {
			showToast(R.string.empty_customer_name);
			return;
		}

		if (TextUtils.isEmpty(txtPhone.getText().toString())) {
			showToast(R.string.empty_phone);
			return;
		}

		loading.show();

		parseJSON();
		login();
	}

	private void showToast(int stringId) {
		Toast toast = Toast.makeText(this, stringId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
		toast.show();
	}

	private void parseJSON() {
		try {
			InputStream is = getAssets().open("questions.txt");
			Question[] questions = new Gson().fromJson(new InputStreamReader(is), Question[].class);
			DataHolder.questions = new ArrayList<>(Arrays.asList(questions));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void login() {
		FormBody.Builder builder = new FormBody.Builder();
		builder.add("phone", txtPhone.getText().toString());
		builder.add("name", txtCusName.getText().toString());

		Request request = new Request.Builder().url("http://quydm.name.vn").post(builder.build()).build();
		new OkHttpClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onFailure(Call call, IOException e) {
				loginSuccess();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				loginSuccess();
			}
		});
	}

	private void loginSuccess() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				loading.dismiss();
			}
		});

		startActivity(new Intent(LoginActivity.this, QuestionActivity.class));
		finish();
	}

}
