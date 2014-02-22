package com.example.sqlite.db;

import java.io.Serializable;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class UserDetail implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "user_detail";

	// カラム名
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMPNO = "emp_no";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_TEL = "tel";

	// プロパティ
	private Long rowid = null;
	private String name = null;
	private String empNo = null;
	private String gender = null;
	private String birthday = null;
	private String tel = null;

	public Long getRowid() {
		return rowid;
	}

	public void setRowid(Long rowid) {
		this.rowid = rowid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String emp_no) {
		this.empNo = emp_no;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * ListView表示の際に利用するのでユーザ名+会社名を返す
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( getEmpNo());
		if( getName() != null){
			builder.append(":");
			builder.append(getName());
		}
		return builder.toString();
	}
}