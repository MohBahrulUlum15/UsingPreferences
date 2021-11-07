package com.rememberdev.usingpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mViewUser = findViewById(R.id.edt_email_sign_in);
        mViewPassword = findViewById(R.id.edt_password_sign_in_sign_in);

        mViewPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                razia();
                return true;
            }
            return false;
        });

        findViewById(R.id.btn_sign_in).setOnClickListener((v)-> {
            razia();
        });

        findViewById(R.id.btn_sign_up_sign_in).setOnClickListener((v)-> {
            startActivity(new Intent(getBaseContext(), RegisterActivity.class));
        });
    }

    /*
    ke Main Activity jika data Status Login dari Data Preferences bernilai true
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
    }

    /*
    Men-check inputan Username dan Password dan Memberikan akses ke MainActivity
     */
    private void razia() {
        /* Mereset semua Error dan fokus menjadi default*/
        mViewUser.setError(null);
        mViewPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        /* Mengambil text dari form User dan form Password dengan variabel baru bertipe String */
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        /*
        Jika form User kosong atau TIDAK memenuhi kriteria di Method cekUser() maka, set Error
        di form User dengan men Set variabel fokus dan error di Viewnya juga cancel menjadi true
         */
        if (TextUtils.isEmpty(user)) {
            mViewUser.setError("This field is required");
            fokus = mViewUser;
            cancel = true;
        } else if (!cekUser(user)) {
            mViewUser.setError("This username is not found!");
            fokus = mViewUser;
            cancel = true;
        }

        /*
        Sama syarat percabangannya dengan User seperti di atas. Bedanya ini untuk form Password
         */
        if (TextUtils.isEmpty(password)) {
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        } else if (!cekPassword(password)) {
            mViewPassword.setError("This password is incorect!");
            fokus = mViewPassword;
            cancel = true;
        }

        /*
        Jika cancel true, variabel fokus mendapatkan fokus
         */
        if (cancel) {
            fokus.requestFocus();
        } else {
            masuk();
        }
    }

    /*
    Menuju ke MainActivity dan Set User dan Set Status sedang login, di Preferences
     */
    private void masuk() {
        Preferences.setLoggedInUser(getBaseContext(),
                Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedInStatus(getBaseContext(), true);
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

    /*
    True jika parameter password sama dengan data password yang terdaftar dari Preferences
     */
    private boolean cekPassword(String password) {
        return password.equals(Preferences.getRegisteredPassword(getBaseContext()));
    }

    /*
    True jika parameter user sama dengan data user yang terdaftar dari Preferences
     */
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}