package ar.com.telecom.iot.tuyacustommethodsdkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.tuya.iotapp.activator.bean.DeviceRegistrationResultBean;
import com.tuya.iotapp.activator.bean.ErrorDeviceBean;
import com.tuya.iotapp.activator.bean.RegistrationTokenBean;
import com.tuya.iotapp.activator.bean.SuccessDeviceBean;
import com.tuya.iotapp.activator.builder.ActivatorBuilder;
import com.tuya.iotapp.activator.config.IEZActivator;
import com.tuya.iotapp.activator.config.TYActivatorManager;
import com.tuya.iotapp.common.utils.IoTCommonUtil;
import com.tuya.iotapp.network.response.ResultListener;

public class DevicePairing extends AppCompatActivity {

    Button btnEZMode;
    TextView lblResultPairing;
    Button btnBLEMode;

    String lastToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_pairing);

        lblResultPairing = findViewById(R.id.lblResultPairing);
        btnEZMode = findViewById(R.id.btnEZMode);
        btnEZMode.setOnClickListener(v -> {
            TYActivatorManager.getActivator().getRegistrationToken("1568286631379114",
                    "EZ",
                    IoTCommonUtil.getTimeZoneId(),
                    "tecotuyadevice1671",
                    new ResultListener<RegistrationTokenBean>() {
                        @Override
                        public void onFailure(String errorCode, String errorMsg) {
                            lblResultPairing.setText("Error: " + errorMsg);

                        }

                        @Override
                        public void onSuccess(RegistrationTokenBean registrationTokenBean) {

                            ActivatorBuilder builder =
                                    new ActivatorBuilder(
                                            DevicePairing.this,
                                            "AMARILLOAMARILLOLOSPLATANOS",
                                            "cocacola",
                                            registrationTokenBean.getRegion(),
                                            registrationTokenBean.getToken(),
                                            registrationTokenBean.getSecret());
                            ActivateDeviceEZ(builder);
                        }
                    }
            );
        } );

        btnBLEMode = findViewById(R.id.btnBLEMode);
        btnBLEMode.setOnClickListener(v -> {
            TYActivatorManager.getActivator().discoverSubDevices("", 100, new ResultListener<Boolean>() {
                @Override
                public void onFailure(String errorCode, String errorMsg) {
                      lblResultPairing.setText("Error: " + errorMsg);
                }

                @Override
                public void onSuccess(Boolean result) {
                    lblResultPairing.setText("Success: " + result);
                }
            });
        });
    }

    //Function to generate string deviceUUID with this format tecotuyadevice1671
    private String generateDeviceUUID() {
        String deviceUUID = "tecotuyadevice";
        deviceUUID += (int) (Math.random() * 10000);
        return deviceUUID;
    }

    void ActivateDeviceEZ(ActivatorBuilder builder){
        IEZActivator mEZActivator = TYActivatorManager.newEZActivator(builder);
        //Start configuration
        mEZActivator.start();
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(3000);
                TYActivatorManager.getActivator().getRegistrationResultToken(builder.getToken(), new ResultListener<DeviceRegistrationResultBean>() {
                    @Override
                    public void onFailure(String errorCode, String errorMsg) {
                        lblResultPairing.setText("Error: " + errorMsg);
                    }

                    @Override
                    public void onSuccess(DeviceRegistrationResultBean deviceRegistrationResultBean) {

                        String result = "Exitosos: \n";
                        for (SuccessDeviceBean device : deviceRegistrationResultBean.getSuccessDevices()) {
                            result += "ID: " + device.getId() + "\n";
                            result += "Nombre: " + device.getName() + "\n";
                            result += "Product ID: " + device.getProductId() + "\n";
                            result += "Longitud: " + device.getLon() + "\n";
                            result += "Latitud: " + device.getLat() + "\n";
                            result += "IP: " + device.getIp() + "\n";
                            result += "En línea: " + device.getOnline() + "\n";
                            result += "UUID: " + device.getUuid() + "\n";
                        }
                        result += "\n---\nFallidos: \n";
                        for (ErrorDeviceBean device : deviceRegistrationResultBean.getErrorDevices())
                        {
                            result += "id: " + device.getId() + "\n";
                            result += "name: " + device.getName() + "\n";
                            result += "Error: " + device.getErrorCode() + "\n";
                            result += "Mensaje: " + device.getErrorMsg() + "\n";
                        }

                        lblResultPairing.setText(result);
                    }


                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        mEZActivator.stop();
    };

}