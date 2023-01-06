package ar.com.telecom.iot.tuyacustommethodsdkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.tuya.iotapp.device.api.TYDeviceManager;
import com.tuya.iotapp.device.bean.DeviceStatusBean;
import com.tuya.iotapp.network.response.ResultListener;

import java.util.List;

public class StatusDevices extends AppCompatActivity {

    Button btnDevicePairingWithPermission;
    Button btnDevicePairingNoPermission;

    TextView lblResponses;
    String responses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_devices);

        btnDevicePairingWithPermission = findViewById(R.id.btnDevicePairingWithPermission);
        btnDevicePairingNoPermission = findViewById(R.id.btnDevicePairingNoPermission);
        lblResponses = findViewById(R.id.lblResponses);

        btnDevicePairingWithPermission.setOnClickListener(v -> {

            TYDeviceManager.getDeviceBusiness().queryDeviceStatus("vdevo167301602463248", new ResultListener<List<DeviceStatusBean>>() {
                @Override
                public void onFailure(String errorCode, String errorMsg) {
                    responses = "Error: " + errorMsg;
                    lblResponses.setText(responses);
                }

                @Override
                public void onSuccess(List<DeviceStatusBean> deviceStatusBeans) {
                    responses = "Success: \n";
                    for (DeviceStatusBean deviceStatusBean : deviceStatusBeans) {
                        responses += deviceStatusBean.getCode() + " - " + deviceStatusBean.getValue() + "\n";
                    }
                    lblResponses.setText(responses);
                }
            });

        });

        //btnDevicePairingNoPermission  vdevo167301989699576
        btnDevicePairingNoPermission.setOnClickListener(v -> {

            TYDeviceManager.getDeviceBusiness().queryDeviceStatus("vdevo167301989699576", new ResultListener<List<DeviceStatusBean>>() {
                @Override
                public void onFailure(String errorCode, String errorMsg) {
                    responses = "Error: " + errorMsg;
                    lblResponses.setText(responses);
                }

                @Override
                public void onSuccess(List<DeviceStatusBean> deviceStatusBeans) {
                    responses = "Success: \n";
                    for (DeviceStatusBean deviceStatusBean : deviceStatusBeans) {
                        responses += deviceStatusBean.getCode() + " - " + deviceStatusBean.getValue() + "\n";
                    }
                    lblResponses.setText(responses);
                }
            });

        });

    }
}