/**
 * this class provides some instruction on how to use the app and allows users to input their max carbs
 */
package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {


    /**
     * creates the Welcome page
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }


    /**
     * opens the restaurant choice page and passes on the max carbs
     *
     * @param v
     */
    public void performRestaurantChoice(View v) {
        Intent intent = new Intent(this, RestaurantChoice.class);

        EditText editText = findViewById(R.id.inputMaxCarbs);
        String maxCarbs;
        if (!editText.getText().toString().isEmpty()) {
            maxCarbs = editText.getText().toString();
        }
        else {
            Toast.makeText(getApplicationContext(), "Enter max carbs please", Toast.LENGTH_SHORT).show();
            return;
        }
        Double maxCarbsDouble = Double.parseDouble(maxCarbs);
        //pass on max carbs
        intent.putExtra("maxCarbs", maxCarbsDouble);

        startActivity(intent);
    }

}