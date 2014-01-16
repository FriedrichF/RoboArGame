package com.fhws.RoboARGame;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.qualcomm.QCARUnityPlayer.DebugLog;
import com.qualcomm.QCARUnityPlayer.QCARPlayerNativeActivity;

public class MainActivity extends QCARPlayerNativeActivity {

	private CheckBox lights;
	private static CheckBox resetSliders;
	private SpeedSeekBar seekBarLeft;
	private SpeedSeekBar seekBarRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final long delay = 5000;//ms
	       
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                ViewGroup rootView = (ViewGroup)MainActivity.this.findViewById(android.R.id.content);
                 
                // find the first leaf view (i.e. a view without children)
                // the leaf view represents the topmost view in the view stack
                View topMostView = getLeafView(rootView);
                 
                // let's add a sibling to the leaf view
                ViewGroup leafParent = (ViewGroup)topMostView.getParent();
                //Button sampleButton = new Button(MainActivity.this);
                //sampleButton.setText("Press Me");
                
                View myView = getLayoutInflater().inflate(R.layout.speed_control, null);

				initController(myView);

				//Toast.makeText(getApplicationContext(), mac, Toast.LENGTH_LONG).show();
                
                leafParent.addView(myView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                 
            }
        };
         
        handler.postDelayed(runnable, delay);
    }   
     
    private View getLeafView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)view;
            for (int i = 0; i < vg.getChildCount(); ++i) {
                View chview = vg.getChildAt(i);
                View result = getLeafView(chview);
                if (result != null) 
                    return result;
            }
            return null;
        }
        else {
            DebugLog.LOGE("Found leaf view");
            return view;
        }
    }
    
    private void initController(View view){
		resetSliders = (CheckBox) view.findViewById(R.id.cbSliders);
		
		
		//Initialisieren von Checkbox für LEDs und dem Listener
		lights = (CheckBox) view.findViewById(R.id.cbLightSwitch);

		
		seekBarLeft=(SpeedSeekBar) view.findViewById(R.id.seekBarLeft);
		//seekBarLeft.setPosition(MotorPosition.LEFT);


		//Initialisieren der rechten Seekbar + Festlegen der Position
		seekBarRight=(SpeedSeekBar) view.findViewById(R.id.seekBarRight);
		//seekBarRight.setPosition(MotorPosition.RIGHT);
		
		//Linke und rechte Seekbar wieder in den Ausgangszustand setzen
		seekBarLeft.setProgress(seekBarLeft.getMax()/2);
		seekBarRight.setProgress(seekBarRight.getMax()/2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
