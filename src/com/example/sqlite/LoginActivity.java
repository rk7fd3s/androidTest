package com.example.sqlite;

import com.example.sqlite.db.UserInfo;
import com.example.sqlite.db.UserInfoDao;

import com.example.sqlite.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});

//		UserInfoDao dao  = new UserInfoDao(LoginActivity.this);
//		dao.init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
//		super.onCreateOptionsMenu(menu);
//		getMenuInflater().inflate(R.menu.login, menu);
//		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);

			// 非同期処理でログイン認証
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, UserInfo> {
        // 処理中ダイアログ
        private ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
                // バックグラウンドの処理前にUIスレッドでダイアログ表示
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage(getResources().getText(
                                R.string.login_progress_signing_in));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);	// モーダル
                progressDialog.show();
        }

		@Override
		protected UserInfo doInBackground(Void... params) {
			// 意味もなく２秒間スリープしてみてます
			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return null;
			}

			// user_infoテーブルからマッチするものがあるか検索
			UserInfoDao dao  = new UserInfoDao(LoginActivity.this);
			UserInfo userInfo = dao.load(mEmail, mPassword);

			return userInfo;
		}

		@Override
		protected void onPostExecute(final UserInfo userInfo) {
			// 処理中ダイアログをクローズ
            progressDialog.dismiss();

			mAuthTask = null;

			// nullじゃなければログイン成功
			if (userInfo != null) {
				Intent mainIntent = new Intent();
				mainIntent.setClassName("com.example.sqlite", "com.example.sqlite.MainActivity");
				mainIntent.putExtra(UserInfo.TABLE_NAME, userInfo);
				startActivity(mainIntent);
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
            progressDialog.dismiss();
		}
	}
}