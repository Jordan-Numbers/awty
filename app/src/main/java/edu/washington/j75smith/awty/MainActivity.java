package edu.washington.j75smith.awty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });
    }

    private void startStop(){
        Button theButton = (Button) findViewById(R.id.btn_send);
        if(theButton.getText() == "Start"){
            //startSending

            theButton.setText(R.string.btn_stop);
        } else {
            //stop sending
            
            theButton.setText(R.string.btn_start);
        }
    }
}
