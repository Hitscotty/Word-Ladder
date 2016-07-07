package com.google.engedu.wordladder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.engedu.worldladder.R;

public class SolverActivity extends AppCompatActivity {

    private String [] path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_solver);
        path = getIntent().getStringArrayExtra(WordSelectionActivity.PATH);

        TextView start = (TextView) findViewById(R.id.startTextView);
        TextView end   = (TextView) findViewById(R.id.endTextView);

        start.setText(path[0]);
        end.setText(path[path.length-1]);
    }

    public void onSolve(View view){
        int [] ids = {R.id.editText5, R.id.editText4, R.id.editText3, R.id.editText2, R.id.editText1};
        for(int i = 0 ; i < ids.length; i++){
            EditText v = (EditText) findViewById(ids[i]);
            v.setText(path[i + 1]);
        }
    }
}
