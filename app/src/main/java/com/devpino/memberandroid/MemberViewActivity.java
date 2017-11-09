package com.devpino.memberandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MemberViewActivity extends AppCompatActivity {

    private long no;

    private EditText editTextEmail;

    private EditText editTextName;

    private EditText editTextPassword;

    private EditText editTextMobileNo1;

    private EditText editTextMobileNo2;

    private EditText editTextMobileNo3;

    private EditText editTextHomepage;

    private RadioButton radioButtonMale;

    private RadioButton radioButtonFemale;

    private EditText editTextAddress;

    private Spinner spinnerJob;

    private EditText editTextComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);

        this.editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        this.editTextName = (EditText)findViewById(R.id.editTextName);
        this.editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        this.editTextMobileNo1 = (EditText)findViewById(R.id.editTextMobileNo1);
        this.editTextMobileNo2 = (EditText)findViewById(R.id.editTextMobileNo2);
        this.editTextMobileNo3 = (EditText)findViewById(R.id.editTextMobileNo3);
        this.editTextHomepage = (EditText)findViewById(R.id.editTextHomepage);
        this.editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        this.editTextComments = (EditText)findViewById(R.id.editTextComments);

        this.radioButtonMale = (RadioButton)findViewById(R.id.radioButtonMale);
        this.radioButtonFemale = (RadioButton)findViewById(R.id.radioButtonFemale);

        this.spinnerJob = (Spinner)findViewById(R.id.spinnerJob);

        no = getIntent().getLongExtra("no", 0);

        loadData();
    }

    private void loadData() {

        MemberDbAdapter memberDbAdapter = MemberDbAdapter.getInstance();
        memberDbAdapter.open();

        Member member = memberDbAdapter.obtainMember(no);

        memberDbAdapter.close();

        this.editTextEmail.setText(member.getEmail());
        this.editTextName.setText(member.getMemberName());
        this.editTextPassword.setText(member.getPassword());

        String mobileNo = member.getMobileNo();
        int index = mobileNo.indexOf("-");
        String mobileNo1 = mobileNo.substring(0, index);

        int index2 = mobileNo.lastIndexOf("-");
        String mobileNo3 = mobileNo.substring(index2 + 1);

        String mobileNo2 = mobileNo.substring(index + 1, index2);

        this.editTextMobileNo1.setText(mobileNo1);
        this.editTextMobileNo2.setText(mobileNo2);
        this.editTextMobileNo3.setText(mobileNo3);

        this.editTextHomepage.setText(member.getHomepage());
        this.editTextAddress.setText(member.getAddress());
        this.editTextComments.setText(member.getComments());

        if (member.getGender().equals("M")) {
            radioButtonMale.setChecked(true);
        }
        else {
            radioButtonFemale.setChecked(true);
        }

        String[] jobs = getResources().getStringArray(R.array.jobs);

        int i = 0;

        for (; i < jobs.length; i++) {

            if (member.getJob().equals(jobs[i])) {
                break;
            }
        }

        spinnerJob.setSelection(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.view_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemEdit) {

            Intent intent = new Intent(this, MemberEditActivity.class);

            intent.putExtra("no", no);
            startActivity(intent);

        }
        else if (item.getItemId() == R.id.itemDelete) {

            AlertDialog.Builder builder	= new AlertDialog.Builder(this);
            AlertDialog alert = null;

            builder.setMessage(getString(R.string.message_delete))
                    .setTitle(getString(R.string.delete))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            deleteMember();
                        }})
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }});

            alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteMember() {

        MemberDbAdapter memberDbAdapter = MemberDbAdapter.getInstance();
        memberDbAdapter.open();

        boolean result = memberDbAdapter.removeMember(no);

        memberDbAdapter.close();

        if (result) {

            finish();
        }
    }
}
