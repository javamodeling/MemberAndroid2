package com.devpino.memberandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MemberAdd2Activity extends AppCompatActivity {

    private AutoCompleteTextView editTextEmail;

    private AutoCompleteTextView editTextName;

    private EditText editTextPassword;

    private EditText editTextMobileNo1;

    private EditText editTextMobileNo2;

    private EditText editTextMobileNo3;

    private AutoCompleteTextView editTextHomepage;

    private RadioButton radioButtonMale;

    private RadioButton radioButtonFemale;

    private AutoCompleteTextView editTextAddress;

    private Spinner spinnerJob;

    private EditText editTextComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add2_layout);

        this.editTextEmail = (AutoCompleteTextView)findViewById(R.id.editTextEmail);
        this.editTextName = (AutoCompleteTextView)findViewById(R.id.editTextName);
        this.editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        this.editTextMobileNo1 = (EditText)findViewById(R.id.editTextMobileNo1);
        this.editTextMobileNo2 = (EditText)findViewById(R.id.editTextMobileNo2);
        this.editTextMobileNo3 = (EditText)findViewById(R.id.editTextMobileNo3);
        this.editTextHomepage = (AutoCompleteTextView)findViewById(R.id.editTextHomepage);
        this.editTextAddress = (AutoCompleteTextView)findViewById(R.id.editTextAddress);
        this.editTextComments = (EditText)findViewById(R.id.editTextComments);

        this.radioButtonMale = (RadioButton)findViewById(R.id.radioButtonMale);
        this.radioButtonFemale = (RadioButton)findViewById(R.id.radioButtonFemale);

        this.spinnerJob = (Spinner)findViewById(R.id.spinnerJob);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSave) {

            saveMember();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveMember() {


        editTextEmail.setError(null);
        String email = editTextEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {

            editTextEmail.setError(getString(R.string.message_email_required));
            editTextEmail.requestFocus();

            return;
        }
        else if (!isEmailValid(email)) {

            editTextEmail.setError(getString(R.string.message_email_invalid));
            editTextEmail.requestFocus();

            return;
        }

        String name = editTextName.getText().toString();

        if (TextUtils.isEmpty(name)) {

            editTextName.setError(getString(R.string.message_name_required));
            editTextName.requestFocus();

            return;
        }

        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(password)) {

            editTextPassword.setError(getString(R.string.message_name_required));
            editTextPassword.requestFocus();

            return;
        }

        String mobileNo1 = editTextMobileNo1.getText().toString();
        String mobileNo2 = editTextMobileNo2.getText().toString();
        String mobileNo3 = editTextMobileNo3.getText().toString();
        String address = editTextAddress.getText().toString();
        String homepage = editTextHomepage.getText().toString();
        String comments = editTextComments.getText().toString();

        boolean isMale = radioButtonMale.isChecked();

        String job = spinnerJob.getSelectedItem().toString();

        Member member = new Member();
        member.setEmail(email);
        member.setMemberName(name);
        member.setPassword(password);
        member.setMobileNo(mobileNo1 + "-" + mobileNo2 + "-" + mobileNo3);
        member.setAddress(address);
        member.setHomepage(homepage);
        member.setComments(comments);

        if (isMale) {
            member.setGender("M");
        }
        else {
            member.setGender("F");
        }

        member.setJob(job);

        MemberDbAdapter memberDbAdapter = MemberDbAdapter.getInstance();

        memberDbAdapter.open();

        memberDbAdapter.addMember(member);

        memberDbAdapter.close();

        finish();

    }

    private boolean isEmailValid(String email) {

        return email.contains("@");
    }
}
