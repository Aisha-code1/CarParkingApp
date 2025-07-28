package com.example.carparkingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {
    EditText edtFeedback;
    Button btnSubmit;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        edtFeedback = findViewById(R.id.edt_feedback);
        btnSubmit = findViewById(R.id.btn_submit);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btnSubmit.setOnClickListener(v -> {
            String text = edtFeedback.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(this, "Please enter feedback", Toast.LENGTH_SHORT).show();
                return;
            }

            Feedback feedback = new Feedback(userId, text);

            FirebaseDatabase.getInstance().getReference("Feedbacks")
                    .push()
                    .setValue(feedback)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());

            edtFeedback.setText(""); // Clear input
        });
    }
}
