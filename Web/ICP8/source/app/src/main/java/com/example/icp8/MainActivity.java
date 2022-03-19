package com.example.icp8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usn;
    EditText psw;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usn = findViewById(R.id.username);
        psw = findViewById(R.id.password);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Username and Password both are missing
                if(usn.getText().toString().isEmpty() && psw.getText().toString().isEmpty()) {

                    Toast.makeText(MainActivity.this, "Please provide valid Login credentials", Toast.LENGTH_SHORT).show();

                } else {
                    if ((usn.getText().toString().equals("admin")) && (psw.getText().toString().equals("admin"))) {
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Successfully Signed in", Toast.LENGTH_SHORT).show();

                    } else { //Invalid Credentials
                        if (!usn.getText().toString().equals("admin")) {
                            if (!psw.getText().toString().equals("admin")) {
                                Toast.makeText(MainActivity.this, "Username and password is incorrect", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Username is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Password is  incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}