package com.example.tiltballfunf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
	
/**
 * @author catarinamoura
 *
 */
public class BallView extends View {
	
	public float positionX;
	public float positionY;
	public final int radius;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public BallView(Context context, float positionX, float positionY,
			int radius) {
		super(context);
		this.positionX = positionX;
		this.positionY = positionY;
		this.radius = radius;
		// set the color 
		paint.setColor(Color.GREEN);
	} 
	
	// Method will called automatically by invalidate()
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawCircle(positionX, positionY, radius, paint);
	}
	
	
}
