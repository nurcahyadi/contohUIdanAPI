package com.example.android.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class cobaPaypal extends AppCompatActivity {
    TextView aa;
    PayPalConfiguration aconfig;
    String paypalID= "ATXZQzNLibFBmQXhctSKRxx_bn61xjOxuhLQDdMPrZRH_tFPNnFneMihDuTsy7mo0lUyXMWNPUAFbt4l" ;
    int paypalreqcode = 999;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba_paypal);

        aa = findViewById(R.id.response);

        aconfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(paypalID);
        Intent service = new Intent(this, PayPalService.class);
        service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, aconfig);
        startService(service);

    }


    public void pay(View view){

        PayPalPayment payment = new PayPalPayment(new BigDecimal(10), "USD","test paypal",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, aconfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent,paypalreqcode);

    }

    public void pay1(View view){

        PayPalPayment payment = new PayPalPayment(new BigDecimal(20), "USD","test paypal",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, aconfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent,paypalreqcode);

    }

    @Override
    protected void onActivityResult(int requestcode,int resultcode, Intent data){

        super.onActivityResult(requestcode,resultcode,data);

        if (requestcode == paypalreqcode){
            if (resultcode == Activity.RESULT_OK){
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm!=null) {

                    String state = confirm.getProofOfPayment().getState();

                    if (state.equals("approved")) {

                        aa.setText("payment approved");

                    } else {

                        aa.setText("error");

                    }
                }
                else {

                    aa.setText("confirmation is null");
                }
            }
        }

    }



}
