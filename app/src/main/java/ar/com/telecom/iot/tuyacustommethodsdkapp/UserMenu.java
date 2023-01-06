package ar.com.telecom.iot.tuyacustommethodsdkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tuya.iotapp.asset.api.TYAssetManager;
import com.tuya.iotapp.asset.bean.AssetBean;
import com.tuya.iotapp.asset.bean.AssetDeviceBean;
import com.tuya.iotapp.asset.bean.AssetDeviceListBean;
import com.tuya.iotapp.asset.bean.AssetsBean;
import com.tuya.iotapp.device.bean.DeviceBean;
import com.tuya.iotapp.network.response.BizResponse;
import com.tuya.iotapp.network.response.ResultListener;

import java.util.List;

public class UserMenu extends AppCompatActivity {


    Button btnQueryAssets;
    TextView lblResponses;
    TextView lblSelectedAsset;
    String FirstAssetId;
    Button btnClearAssetID;

    Button btnQueryDevicesByAssets;

    Button btnDevicePairing;

    Button btnControlDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        btnQueryAssets = findViewById(R.id.btnQueryAssets);
        lblResponses = findViewById(R.id.lblResponses);
        lblSelectedAsset = findViewById(R.id.lblSelectedAsset);
        btnClearAssetID = findViewById(R.id.btnClearAssetID);
        btnQueryDevicesByAssets = findViewById(R.id.btnQueryDevicesByAssets);
        btnControlDevice = findViewById(R.id.btnControlDevice);
        btnDevicePairing = findViewById(R.id.btnDevicePairing);

        btnClearAssetID.setOnClickListener(v -> {
            lblSelectedAsset.setText("Selected Asset: None");
            FirstAssetId = null;
        });

        //btnQueryAssets onclick
        btnQueryAssets.setOnClickListener(v -> {
            TYAssetManager.getAssetBusiness().queryAssets(FirstAssetId != null ? FirstAssetId : "", 0, 10, new ResultListener<AssetsBean>() {
                @Override
                public void onFailure(String errorCode, String errorMsg) {
                    Toast.makeText(UserMenu.this, "Error: " + errorMsg, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSuccess(AssetsBean assetsBean) {
                    String response = "Project Name: " + assetsBean.getProjectName()
                            + "\nAssets Count: " + assetsBean.getAssets().size();
                    if (assetsBean.getAssets().size() > 0) {
                        FirstAssetId = assetsBean.getAssets().get(0).getAssetId();
                        lblSelectedAsset.setText("Selected Asset: " + FirstAssetId);
                        for (AssetBean asset : assetsBean.getAssets()) {
                            response += "\n------------------" +
                                    "\n--assetID: " + asset.getAssetId() +
                                    "\n--assetName: " + asset.getAssetName() +
                                    "\n--parentAssetId " + asset.getParentAssetId() +
                                    "\n--permision: " + asset.getPermission();

                        }

                    }

                    lblResponses.setText(response);
                }
            });
        });

        btnQueryDevicesByAssets.setOnClickListener(v -> {
            if (FirstAssetId == null) {
                Toast.makeText(UserMenu.this, "Please select an asset first", Toast.LENGTH_SHORT).show();
                return;
            }
            TYAssetManager.getAssetBusiness().queryDevicesByAssetId(FirstAssetId, "", 10, new ResultListener<AssetDeviceListBean>() {
                @Override
                public void onFailure(String errorCode, String errorMsg) {

                }

                @Override
                public void onSuccess(AssetDeviceListBean assetDeviceListBean) {
                    String response = "Devices Count: " + assetDeviceListBean.component1().size();
                    if (assetDeviceListBean.component1().size() > 0) {
                        for (AssetDeviceBean device : assetDeviceListBean.component1()) {
                            response += "\n------------------" +
                                    "\n--deviceID: " + device.getDeviceId() +
                                    "\n--assetId: " + device.getAssetId() +
                                    "\n--assetName " + device.getAssetName();

                        }

                    }

                    lblResponses.setText(response);
                }
            });
        });

        btnControlDevice.setOnClickListener(v -> {
            //start StatusDevices activity
            startActivity(new android.content.Intent(UserMenu.this, StatusDevices.class));
        });

        btnDevicePairing.setOnClickListener(v -> {
            //start DevicePairing activity
            startActivity(new android.content.Intent(UserMenu.this, DevicePairing.class));
        });
    }
}