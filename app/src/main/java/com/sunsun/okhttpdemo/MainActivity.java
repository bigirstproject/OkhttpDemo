package com.sunsun.okhttpdemo;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.sunsun.okhttpdemo.http.SimpleHttp;
import com.sunsun.okhttpdemo.util.UriProvider;

import java.io.IOException;

import debug.KGLog;

public class MainActivity extends BaseWorkerFragmentActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 100;
    private static final int RSPONE_CODE = 101;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                sendEmptyBackgroundMessage(REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void handleBackgroundMessage(Message msg) {
        switch (msg.what) {
            case REQUEST_CODE:
                try {
                    String respone = SimpleHttp.requestGetOne(UriProvider.DISCOVERY_URL);
                    Message requsetMsg = new Message();
                    requsetMsg.what = RSPONE_CODE;
                    requsetMsg.obj = respone;
                    sendUiMessage(requsetMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case RSPONE_CODE:
                if (msg != null && msg.obj instanceof String) {
                    KGLog.d(msg.obj.toString());
                    showToast(msg.obj.toString());
                }
                break;
            default:
                break;
        }
    }

}
