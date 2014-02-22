package com.example.sqlite.db;

import java.io.Serializable;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class UserInfo implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "user_info";

	// カラム名
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MAILADDRESS = "mailaddress";
	public static final String COLUMN_PASSWORD = "password";

	// プロパティ
	private Long rowid = null;
	private String mailaddress = null;
	private String password = null;

	public Long getRowid() {
		return rowid;
	}

	public void setRowid(Long rowid) {
		this.rowid = rowid;
	}


	public String getMailaddress() {
		return mailaddress;
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}