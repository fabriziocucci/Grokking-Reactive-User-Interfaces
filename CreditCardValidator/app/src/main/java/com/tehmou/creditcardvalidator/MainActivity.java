package com.tehmou.creditcardvalidator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tehmou.creditcardvalidator.utils.CardType;
import com.tehmou.creditcardvalidator.utils.FocusWatcherObservable;
import com.tehmou.creditcardvalidator.utils.TextWatcherObservable;
import com.tehmou.creditcardvalidator.utils.ValidationUtils;

import java.util.Arrays;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener((view) -> {
            Log.d(TAG, "Submit");
            findViewById(R.id.container).requestFocus();
        });

        final EditText creditCardNumberView = (EditText) findViewById(R.id.credit_card_number);
        final EditText creditCardCvcView = (EditText) findViewById(R.id.credit_card_cvc);
        final TextView creditCardTypeView = (TextView) findViewById(R.id.credit_card_type);
        final TextView errorText = (TextView) findViewById(R.id.error_text);

        

        // Do all side-effects
        ValidationUtils.setupTextView(creditCardNumberView, showErrorForCreditCardNumber);

        // Show error in case CVC code is not valid
        ValidationUtils.setupTextView(creditCardCvcView, showErrorForCvc);

        // Show card type (no error)
        ValidationUtils.setupCardType(creditCardTypeView, cardType);

        // Enable submit button if number and CVC are valid
        ValidationUtils.setupSubmitButton(submitButton, isValidNumber, isValidCvc, isKnownCardType);

        // Show a list of errors in the UI
        ValidationUtils.setupErrorDisplay(errorText, isKnownCardType, isValidCheckSum, isValidCvc);
    }
}