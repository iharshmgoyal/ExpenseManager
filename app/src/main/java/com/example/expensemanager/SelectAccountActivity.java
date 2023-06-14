package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.expensemanager.databinding.ActivitySelectAccountBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class SelectAccountActivity extends AppCompatActivity {

    ActivitySelectAccountBinding binding;

    FirebaseDatabase database;

    String selectedAccount;

    String accountLastBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySelectAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();

        ArrayAdapter<CharSequence> accountAdapter = ArrayAdapter.createFromResource(this, R.array.accountArray, android.R.layout.simple_spinner_item);
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.accountSelectSpinner.setAdapter(accountAdapter);

        binding.accountSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.matches("Select Account")) {

                } else {
                    Log.d("TAG", "onItemSelected: Selected University : "+selectedItem);
                    selectedAccount = selectedItem;
                    Toast.makeText(adapterView.getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.accountChoiceMadeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectAccountActivity.this, MainActivity.class);
                intent.putExtra("account", selectedAccount);
                intent.putExtra("accountBalance",accountLastBalance);
                Log.d("TAG", "onClick: Last Check Account is : "+selectedAccount+" balance = "+accountLastBalance);
                startActivity(intent);
            }
        });
    }
}