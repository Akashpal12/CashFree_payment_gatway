package com.example.cashfree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.exception.CFInvalidArgumentException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutPayment;
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutTheme;

public class PayActivity extends AppCompatActivity implements CFCheckoutResponseCallback {

    Button btnWebPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        btnWebPay=findViewById(R.id.btnWebPay);

//        try {
//            // If you are using a fragment then you need to add this line inside onCreate() of your Fragment
//            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
//        } catch (CFException e) {
//            e.printStackTrace();
//        }

        btnWebPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CFSession cfSession = new CFSession.CFSessionBuilder()
                            .setEnvironment(CFSession.Environment.PRODUCTION)
                            .setPaymentSessionID("cA6fvFnX5uSashDgySpZ")
                            .setOrderId("order_941322LUhiHyMoYetcCnPajFnPhedItg")
                            .build();
                    // Replace with your application's theme colors
                    CFWebCheckoutTheme cfTheme = new CFWebCheckoutTheme.CFWebCheckoutThemeBuilder()
                            .setNavigationBarBackgroundColor("#fc2678")
                            .setNavigationBarTextColor("#ffffff")
                            .build();
                    CFWebCheckoutPayment cfWebCheckoutPayment = new CFWebCheckoutPayment.CFWebCheckoutPaymentBuilder()
                            .setSession(cfSession)
                            .setCFWebCheckoutUITheme(cfTheme)
                            .build();
                    CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();

                    gatewayService.doPayment(PayActivity.this, cfWebCheckoutPayment);
                } catch (CFException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPaymentVerify(String orderID) {

        Log.d("onPaymentVerify", "verifyPayment triggered");
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {

        Log.d("onPaymentFailure " + orderID, cfErrorResponse.getMessage());
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
    }
}