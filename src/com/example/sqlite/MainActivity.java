package com.example.sqlite;

import java.util.List;

import com.example.sqlite.db.UserDetail;
import com.example.sqlite.db.UserDetailDao;
import com.example.sqlite.db.UserInfo;

import com.example.sqlite.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    // 現在ログイン中のUserInfoオブジェクト
    private UserInfo userInfo = null;

    private TextView txvUserInfoName = null;
    private TextView txvUserInfoEmpNo = null;
    private TextView txvUserInfoMailaddress = null;
    private TextView txvUserInfoGender = null;
    private TextView txvUserInfoBirthday = null;
    private TextView txvUserInfoTel = null;
    
    // 一覧表示用ListView
    private ListView listView = null;
    
    private ArrayAdapter<UserDetail> arrayAdapter = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        // Intentから対象のUserInfoオブジェクトを取得
        userInfo = (UserInfo)getIntent().getSerializableExtra( UserInfo.TABLE_NAME);

        txvUserInfoName = (TextView)findViewById( R.id.userInfoName);
        txvUserInfoEmpNo = (TextView)findViewById( R.id.userInfoEmpNo);
        txvUserInfoMailaddress = (TextView)findViewById( R.id.userInfoMailaddress);
        txvUserInfoGender = (TextView)findViewById( R.id.userInfoGender);
        txvUserInfoBirthday = (TextView)findViewById( R.id.userInfoBirthday);
        txvUserInfoTel = (TextView)findViewById( R.id.userInfoTel);
        
        listView = (ListView) findViewById(R.id.list);
        
        arrayAdapter = new ArrayAdapter<UserDetail>(this,
                android.R.layout.simple_list_item_1);
        
        // アダプタを設定
        listView.setAdapter(arrayAdapter);
        
        // 表示内容の更新
        updateView();
	}

    /**
     * 画面の表示内容を更新する
     */
    private void updateView(){
    	if (userInfo != null) {
        	UserDetailDao dao  = new UserDetailDao(MainActivity.this);
        	UserDetail userDetail = dao.load(userInfo.getRowid());
        	
        	txvUserInfoName.setText(userDetail.getName());
        	txvUserInfoEmpNo.setText(userDetail.getEmpNo());
        	txvUserInfoMailaddress.setText(userInfo.getMailaddress());
        	txvUserInfoGender.setText(("m".equals(userDetail.getGender())) ? "男性" : "女性");
        	txvUserInfoBirthday.setText(userDetail.getBirthday());
        	txvUserInfoTel.setText(userDetail.getTel());
        	
        	List<UserDetail> list = dao.list();
        	
        	// 表示データのクリア
            arrayAdapter.clear();

            // 表示データの設定
            for (UserDetail part : list) {
                arrayAdapter.add(part);
            }
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
	}

}