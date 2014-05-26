package com.example.bouncingballfunf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class AnimationView extends View {
	
	private final int FRAME_RATE = 15;
	private Paint paint; // Paint the object 
	private Handler handler; // Handler for animation timing
	Ball myBall;
	
	public AnimationView(Context context, AttributeSet attrs){
		super(context,attrs);
		this.handler = new Handler();
		paint = new Paint();
		paint.setColor(Color.GREEN);
		myBall = new Ball(100,100, paint.getColor(),50);
	
		//Set speed of the ball
		myBall.setDx(10);
		myBall.setDy(10);
	}
	
	protected void onDraw(Canvas c){
		// Certifying that the ball is in the edges of the cnavas 
		myBall.bouncing(c);
		// Draw the ball on the canvas
		c.drawCircle(myBall.getPositionX(), myBall.getPositionY(), myBall.getRadius(), myBall.getPaint());
		handler.postDelayed(runnable, FRAME_RATE);
	}
	
	private Runnable runnable =new Runnable(){
		public void run(){
			invalidate(); // Clean the screen call onDraw
		}
	};
	
}
