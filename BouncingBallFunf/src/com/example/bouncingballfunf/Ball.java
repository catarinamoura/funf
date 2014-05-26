package com.example.bouncingballfunf;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball {

	private Point position; // Position of the ball
	private int color; // color of the ball
	private int radius; // radius of the ball
	private int dx; // Change in x position of the ball
	private int dy; // Change y position of the ball
	private Paint paint; // Android object holding the color for drawing on the canvas

	public Ball(int x, int y,int color, int radius) {

		this.position = new Point(x,y); // set x and y position
		this.color = color;
		this.radius = radius;
		this.dx = 0;
		this.dy = 0;
		paint = new Paint();
		paint.setColor(this.color);
	}

	public Point getPosition(){
		return this.position;
	}

	public int getPositionX() {
		return this.position.x;
	}

	public int getPositionY() {
		return this.position.y;
	}

	public void setPosition(int x,int y) {;
	this.position.x = x;
	this.position.y=y;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}


	public void setDx(int speed) {
		this.dx = speed;
	}

	public void setDy(int speed) {
		this.dy = speed;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public void bouncing(Canvas canvas){

		position.x = position.x + dx ;
		position.y = position.y+ dy;

		// Stop Conditions
		if(position.x > canvas.getWidth() || position.x < 0){
			dx = dx*-1;
		}

		if(position.y > canvas.getHeight() || position.y < 0){
			dy =dy*-1;
		}
	}

}
