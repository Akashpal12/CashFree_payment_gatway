package com.example.cashfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.CFTheme;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.exception.CFInvalidArgumentException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.ui.api.CFDropCheckoutPayment;
import com.cashfree.pg.ui.api.CFPaymentComponent;

public class MainActivity extends AppCompatActivity implements CFCheckoutResponseCallback {

    Button btnPay,Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPay=findViewById(R.id.btnPay);
        Next=findViewById(R.id.Next);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PayActivity.class));
            }
        });


//        try {
//            CFPaymentGatewayService gatewayService  = CFPaymentGatewayService.getInstance();
//            gatewayService.setCheckoutCallback(MainActivity.this);
//        } catch (CFException e) {
//            e.printStackTrace();
//        }
        


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                try {
                    CFSession cfSession = new CFSession.CFSessionBuilder()
                            .setEnvironment(CFSession.Environment.SANDBOX)
                            .setPaymentSessionID("uLb46A9oowr3FcADsvev")
                            .setOrderId("order_941322LUoK0CdEdDolufwkq1hBo4sgxm")
                            .build();
                    CFPaymentComponent cfPaymentComponent = new CFPaymentComponent.CFPaymentComponentBuilder()
                            // Shows only Card and UPI modes
                            .add(CFPaymentComponent.CFPaymentModes.CARD)
                            .add(CFPaymentComponent.CFPaymentModes.UPI)
                            .add(CFPaymentComponent.CFPaymentModes.WALLET)
                            .add(CFPaymentComponent.CFPaymentModes.NB)
                            .add(CFPaymentComponent.CFPaymentModes.EMI)
                            .add(CFPaymentComponent.CFPaymentModes.PAY_LATER)
                            .add(CFPaymentComponent.CFPaymentModes.PAYPAL)
                            .build();
                    // Replace with your application's theme colors
                    CFTheme cfTheme = new CFTheme.CFThemeBuilder()
                            .setNavigationBarBackgroundColor("#fc2678")
                            .setNavigationBarTextColor("#ffffff")
                            .setButtonBackgroundColor("#fc2678")
                            .setButtonTextColor("#ffffff")
                            .setPrimaryTextColor("#000000")
                            .setSecondaryTextColor("#000000")
                            .build();
                    CFDropCheckoutPayment cfDropCheckoutPayment = new CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder()
                            .setSession(cfSession)
                            .setCFUIPaymentModes(cfPaymentComponent)
                            .setCFNativeCheckoutUITheme(cfTheme)
                            .build();
                    CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
                    gatewayService.setCheckoutCallback(MainActivity.this);
                    gatewayService.doPayment(MainActivity.this, cfDropCheckoutPayment);
                } catch (CFException exception) {
                    exception.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onPaymentVerify(String orderID) {
        Log.d("onPaymentVerify", "verifyPayment triggered");
        // Start verifying your payment
        Toast.makeText(this, "Success"+orderID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        Log.d("onPaymentFailure " + orderID, cfErrorResponse.getMessage());
        Toast.makeText(this, "Fail"+cfErrorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

}