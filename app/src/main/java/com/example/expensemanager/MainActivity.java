package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.expensemanager.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    FirebaseDatabase database;

    private String radioButton;
    private String tAccount;
    private String lastAccountBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();

        tAccount = getIntent().getStringExtra("account");

        readData(lastBalance -> {
            lastAccountBalance=lastBalance;
            if (lastAccountBalance==null) {
                lastAccountBalance=String.valueOf(0);
                Log.d("TAG", "SelectAccountActivity - onDataChange: "+tAccount+" last Transaction amount is : "+lastAccountBalance);
                binding.accountLastBalance.setText(lastAccountBalance);
                binding.accountLastBalance.setFocusable(false);
                binding.accountLastBalance.setFocusableInTouchMode(false);
                binding.accountLastBalance.setClickable(false);
            } else {
                Log.d("TAG", "SelectAccountActivity - onDataChange: " + tAccount + " last Transaction amount is : " + lastAccountBalance);
                binding.accountLastBalance.setText(lastAccountBalance);
                binding.accountLastBalance.setFocusable(false);
                binding.accountLastBalance.setFocusableInTouchMode(false);
                binding.accountLastBalance.setClickable(false);
            }
        });

        binding.transactionAccount.setText(tAccount);
        binding.transactionAccount.setFocusable(false);
        binding.transactionAccount.setFocusableInTouchMode(false);
        binding.transactionAccount.setClickable(false);



        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton)group.findViewById(checkedId);
                Toast.makeText(MainActivity.this, button.getText(), Toast.LENGTH_SHORT).show();
                radioButton=button.getText().toString();
            }
        });

        binding.buttonAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tName, tAmount, tAccount, tReason;

                String balance = binding.accountLastBalance.getText().toString();
                Log.d("TAG", "onCreate: Account Last Balance in Variable is - "+balance);

                tName = Objects.requireNonNull(binding.transactionName.getText()).toString();
                tAmount = Objects.requireNonNull(binding.transactionAmount.getText()).toString();
                tAccount = Objects.requireNonNull(binding.transactionAccount.getText()).toString();
                tReason = Objects.requireNonNull(binding.transactionReason.getText()).toString();

                if (!tName.isEmpty() && !tAmount.isEmpty() && !tAccount.isEmpty() && !tReason.isEmpty()) {

                    Transactions transactions = new Transactions(
                            tName,
                            tAmount,
                            tAccount,
                            radioButton,
                            tReason,
                            balance,
                            new Date().getTime()
                    );

                    database.getReference().child(tAccount).push()
                            .setValue(transactions)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Transaction Added into Account!!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Transaction Failed. Try Again..", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });

    }

    private void readData(FirebaseCallback firebaseCallback) {

        final String[] accountLastBalance = new String[1];

        database.getReference().child(tAccount).orderByKey().limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            accountLastBalance[0] =Objects.requireNonNull(dataSnapshot.child("tAmount").getValue()).toString();
                        }
//                        Log.d("TAG", "SelectAccountActivity - onDataChange: "+selectedAccount+" last Transaction amount is : "+accountLastBalance);
                        firebaseCallback.onCallback(accountLastBalance[0]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private interface FirebaseCallback {
        void onCallback(String lastBalance);
    }

}