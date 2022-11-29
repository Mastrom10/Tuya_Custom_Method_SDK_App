package ar.com.telecom.iot.tuyacustommethodsdkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tuya.iotapp.componet.TuyaIoTSDK;
import com.tuya.iotapp.network.api.RegionHostConst;
import com.tuya.iotapp.network.response.BizResponse;
import com.tuya.iotapp.network.response.ResultListener;
import com.tuya.iotapp.user.api.TYUserManager;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        TuyaIoTSDK.builder().init(this.getApplication(),
                        "fwxxhwmkqsacr57ex5gx","5897ea58ab674278a62584c98fde9f10")
                .hostConfig(RegionHostConst.REGION_HOST_US)
                .debug(true)
                .build();

        btnLogin.setOnClickListener(v -> {

            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            TYUserManager.getUserBusiness().login(username, password,
                    new ResultListener<BizResponse>() {
                        @Override
                        public void onFailure(String s, String s1) {
                            //failure
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            //Log
                            System.out.println("Login failed: " + s + " - " + s1);
                        }

                        @Override
                        public void onSuccess(BizResponse bizResponse) {
                            //Toast message and Log
                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            System.out.println("Login Success");




                        }
                    });


        });




    }
}