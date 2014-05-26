package com.example.tiltballfunf;

import java.util.Timer;
import java.util.TimerTask;

import com.example.tiltballfunf.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.SensorEventListener;
import android.graphics.PointF;
import android.widget.FrameLayout;

public class MainActivity extends Activity{

	BallView ball =null;
	Handler handler=new Handler();
	Timer timer=null;
	TimerTask timerTask=null;
	int screenWidth;
	int screenHeight;
	PointF ballPosition;
	PointF ballSpeed;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState){
		// Set the window to run in full screen and keep the screen on
		getWindow().setFlags(0xFFFFFFFF,LayoutParams.FLAG_FULLSCREEN|LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//create the pointer to the main screen
		final FrameLayout mainView= (FrameLayout) findViewById(R.id.main_view);

		// get the screen dimensions

		//		Display display = getWindowManager().getDefaultDisplay();
		//		this.screenWidth = display.getWidth();
		//		this.screenHeight = display.getHeight();

		DisplayMetrics display = this.getResources().getDisplayMetrics();
		this.screenWidth = display.widthPixels;
		this.screenHeight = display.heightPixels;



		// Initialize and set the position of the ball and speed
		ballPosition = new PointF();	
		ballPosition.x= screenWidth/2;
		ballPosition.y= screenHeight/2;

		ballSpeed = new PointF();
		ballSpeed.x=0;
		ballSpeed.y=0;

		// create the ball with radius 50

		ball = new BallView(this, ballPosition.x, ballPosition.y, 50);
		// Add the ball to the main screen
		mainView.addView(ball);
		// Call the onDraw BallView
		ball.invalidate();

		// listener Accelerometer sensor
		((SensorManager)getSystemService(Context.SENSOR_SERVICE)).registerListener(
				new SensorEventListener() {    

					public void onSensorChanged(SensorEvent event) {  
						//set ball speed based on phone tilt (ignore Z axis)
						ballSpeed.x = -event.values[0];
						ballSpeed.y = event.values[1];
						//timer event will redraw ball
					}
					//ignore this event
					public void onAccuracyChanged(Sensor sensor, int accuracy) {} 
				},
				((SensorManager)getSystemService(Context.SENSOR_SERVICE))
				.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);

		//listener for touch event  		
		mainView.setOnTouchListener(new android.view.View.OnTouchListener() {
			public boolean onTouch(android.view.View v, android.view.MotionEvent e) {
				//set ball position based on screen touch
				ballPosition.x = e.getX();
				ballPosition.y = e.getY();
				//timer event will redraw ball
				return true;
			}});
	}
	// listener for menu button
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Exit"); //only one menu item
		return super.onCreateOptionsMenu(menu);
	}

	   //listener for menu item clicked
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle item selection    
    	if (item.getTitle() == "Exit") //user clicked Exit
    		finish(); //will call onPause
   		return super.onOptionsItemSelected(item);    
    }

    //app moved to background, stop background threads
    public void onPause() 
    {
    	timer.cancel(); //kill or release timer
    	timer = null;
    	timerTask = null;
    	super.onPause();
    }
    
    public void onResume() 
    {
        //create timer to move ball to new position
        timer = new Timer(); 
        timerTask = new TimerTask() {
			public void run() {
				// Debug on logcat
				android.util.Log.d(
				    "TiltBall","Timer position - " + ballPosition.x + ":" + ballPosition.y);
			    //move the ball based on the current speed
				ballPosition.x += ballSpeed.x;
				ballPosition.y += ballSpeed.y;
				
				//if ball goes off screen, reposition to opposite side of screen
				if (ballPosition.x > screenWidth) ballPosition.x=0;
				if (ballPosition.y > screenHeight) ballPosition.y=0;
				if (ballPosition.x < 0) ballPosition.x=screenWidth;
				if (ballPosition.y < 0) ballPosition.y=screenHeight;
				//update the ball instance
				ball.positionX = ballPosition.x;
				ball.positionY = ballPosition.y;
				//redraw ball
				handler.post(new Runnable() {
				    public void run() {	
					   ball.invalidate();
				  }});
			}};
			
			// start the timer
        timer.schedule(timerTask,10,10); 
        super.onResume();
    }
	public void onDestroy() 
    {
    	super.onDestroy();
    	// remove the app from memory 
    	android.os.Process.killProcess(android.os.Process.myPid()); 
    }

    /* onConfiguraionChanged handler is called when user tilts phone 
     * enough to trigger landscape view however the app should stay portrait
     * so ignore the event
     */
    public void onConfigurationChanged(Configuration newConfig)
	{
       super.onConfigurationChanged(newConfig);
	}
}









//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.os.Build;
//
//public class MainActivity extends ActionBarActivity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//	}
//
//}
