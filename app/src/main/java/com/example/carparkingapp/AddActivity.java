package com.example.carparkingapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText etTiming = findViewById(R.id.et_timing);
        etTiming.setOnClickListener(v->{
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int mintue = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this,(view,hourofday,mintue1)->{
                String selectedTime = String.format("%02d:%02d", hourofday, mintue1);
                etTiming.setText(selectedTime);
            },
              hour,
              mintue,
              false
            );
            timePickerDialog.show();

        });


        setContentView(R.layout.activity_add);
        EditText etName = findViewById(R.id.et_Name);
        EditText etPrice = findViewById(R.id.et_Price);
        EditText etCity = findViewById(R.id.et_city);


        Button save = findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String price = etPrice.getText().toString();
                String city = etCity.getText().toString();
                String timing = etTiming.getText().toString();
                Manage manage = new Manage();
                manage.name = name;
                manage.city = city;
                manage.timing = timing;
               manage.price = Integer.parseInt(price);
                //save data
                FirebaseDatabase.getInstance()
                        .getReference("Manage")
//                        .child(FirebaseAuth.getInstance().getUid())
                        .push()
                        .setValue(manage);
                Toast.makeText(AddActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }


        });
    }
}