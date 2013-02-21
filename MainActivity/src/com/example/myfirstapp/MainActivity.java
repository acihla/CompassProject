 package com.example.myfirstapp;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.property.Parent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Build;
import android.util.Log;
import android.view.View.OnTouchListener;
import android.view.View;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews.RemoteView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public int previousXIndex;
	public int previousYIndex;
	public String prevButton;
	public int prevXTouch;
	public int prevYTouch;
	public int prevTouchTime; 
	public String currentSequence = "";
	public Map<String, String[]> keygonSequences; 
	public String textOutput;
	public String lastWord;
	public TextView[] currentPossibilties;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textOutput = "";
        
     // ** Declare your Bitmap somewhere **      
        final Bitmap nb = BitmapFactory.decodeResource(getResources(), R.drawable.nb);
        final Bitmap sb = BitmapFactory.decodeResource(getResources(), R.drawable.sb);
        final Bitmap eb = BitmapFactory.decodeResource(getResources(), R.drawable.eb);
        final Bitmap wb = BitmapFactory.decodeResource(getResources(), R.drawable.wb);
        final Bitmap ng = BitmapFactory.decodeResource(getResources(), R.drawable.ng);
        final Bitmap neg = BitmapFactory.decodeResource(getResources(), R.drawable.neg);
        final Bitmap eg = BitmapFactory.decodeResource(getResources(), R.drawable.eg);
        final Bitmap seg = BitmapFactory.decodeResource(getResources(), R.drawable.seg);
        final Bitmap sg = BitmapFactory.decodeResource(getResources(), R.drawable.sg);
        final Bitmap swg = BitmapFactory.decodeResource(getResources(), R.drawable.swg);
        final Bitmap wg = BitmapFactory.decodeResource(getResources(), R.drawable.wg);
        final Bitmap nwg = BitmapFactory.decodeResource(getResources(), R.drawable.nwg);
        final Bitmap center = BitmapFactory.decodeResource(getResources(), R.drawable.center);
        
        

        
   //     findViewById(R.id.imageView1).setMinimumHeight(2*((int)findViewById(R.id.imageView1).getHeight()));
     //   findViewById(R.id.imageView1).setMinimumWidth(2*((int)findViewById(R.id.imageView1).getWidth()));
        
        InputStream myInputStream = getResources().openRawResource(R.raw.keygonsequenceoutput);
        try {
			HSSFWorkbook workbook = new HSSFWorkbook(myInputStream);
			HSSFSheet worksheet = workbook.getSheet("keygonSequenceOutput");
			//Log.d("Our sequence is", currentSequence.toString());
			keygonSequences = new HashMap<String, String[]>();
			for(int i = 0; i < worksheet.getLastRowNum(); i++) {
				HSSFRow currentRow = worksheet.getRow(i);
			//	Log.d("ROW NAME",  currentRow.getCell(0).getStringCellValue());
 				//Log.d("ROW SEQ", "" + currentRow.getCell(1).getStringCellValue());
				
				String currentWordSeq = "" + currentRow.getCell(1).getStringCellValue();
				currentWordSeq = currentWordSeq.substring(1);
				String currentWord = currentRow.getCell(0).getStringCellValue();
				String currentWordFreq = "" + currentRow.getCell(2).getStringCellValue();
				currentWordFreq = currentWordFreq.substring(1);
				if(keygonSequences.containsKey(currentWordSeq)) {
					String[] currentArray = keygonSequences.get(currentWordSeq);
					
					for(int j = 0; j < currentArray.length ; j++) {
						
						if((currentArray[j] == null)) {
							currentArray[j] = currentWord;
							j = currentArray.length;
							break;
						}
						if(currentArray[j].equals(currentWord)) {
							j = currentArray.length;
							break;
						}
						
					}
					/*int j = 0;
					while(!(currentArray[j] == null) && j < 4) {
						if(currentArray[j].equals(currentWord)) {
							
						}
						j++;
					}
					
					currentArray[j] = currentWord; 
					/* if (currentWordSeq.equals("01")){
						Log.d("sequencer", currentWord);
					}
					
					for(int j = 0; j < currentArray.length ; j++) {
						if((currentArray[j] == null)) {
							currentArray[j] = currentWord;
							j = currentArray.length;
						}
					} */
				
					keygonSequences.put(currentWordSeq,currentArray);
				}
				else {
					String[] newCurrentArray = new String[5];
					newCurrentArray[0] = currentWord;
					keygonSequences.put(currentWordSeq, newCurrentArray);
				}
				
				
			}
			}
		  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    	final String fileToRead = "//assets/keygonSequencerOutput.xls";
        final ImageView compass = (ImageView) findViewById(R.id.imageView1); 
        final TextView textOutputView = (TextView) findViewById(R.id.textView2);
        final Button enterButton = (Button) findViewById(R.id.button1);
        final TextView word1 = (TextView) findViewById(R.id.textView3);
        final TextView word2 = (TextView) findViewById(R.id.textView4);
        final TextView word3 = (TextView) findViewById(R.id.textView5);
        final TextView word4 = (TextView) findViewById(R.id.textView6);
        final TextView word5 = (TextView) findViewById(R.id.textView7);
        final TextView word6 = (TextView) findViewById(R.id.textView8);
        final TextView word7 = (TextView) findViewById(R.id.textView9);

        word1.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		textOutputView.append(word1.getText() + " ");
        		textOutput += " " + word1.getText(); 
        		lastWord = (String) word1.getText();
        		currentSequence = "";
        		word1.setText("");
        		word2.setText("");
        		word3.setText("");
        		word4.setText("");
        		word5.setText("");
        		word6.setText("");
        		word7.setText("");
        	}
        });

        word2.setOnClickListener(new View.OnClickListener() {
        			
        	@Override
        	public void onClick(View v) {
        		textOutputView.append(word2.getText() + " ");
        		textOutput += " " + word2.getText(); 
        		lastWord = (String) word2.getText();
        		currentSequence = "";
        		word1.setText("");
        		word2.setText("");
        		word3.setText("");
        		word4.setText("");
        		word5.setText("");
        		word6.setText("");
        		word7.setText("");
        	}
        });

        word3.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		textOutputView.append(word3.getText() + " ");
        		textOutput += " " + word3.getText(); 
        		lastWord = (String)word3.getText();
        		currentSequence = "";
        		word1.setText("");
        		word2.setText("");
        		word3.setText("");
        		word4.setText("");
        		word5.setText("");
        		word6.setText("");
        		word7.setText("");
        	}
        });
        
        word4.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		textOutputView.append(word4.getText() + " ");
        		textOutput += " " + word4.getText(); 
        		lastWord = (String)word4.getText();
        		currentSequence = "";
        		word1.setText("");
        		word2.setText("");
        		word3.setText("");
        		word4.setText("");
        		word5.setText("");
        		word6.setText("");
        		word7.setText("");
        	}
        });
        
        word5.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		textOutputView.append(word5.getText() + " ");
        		textOutput += " " + word5.getText(); 
        		lastWord = (String)word5.getText();
        		currentSequence = "";
        		word1.setText("");
        		word2.setText("");
        		word3.setText("");
        		word4.setText("");
        		word5.setText("");
        		word6.setText("");
        		word7.setText("");
        	}
        });
        
        
        word6.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		textOutputView.append(word6.getText() + " ");
        		textOutput += " " + word6.getText(); 
        		lastWord = (String)word6.getText();
        		currentSequence = "";
        		word1.setText("");
        		word2.setText("");
        		word3.setText("");
        		word4.setText("");
        		word5.setText("");
        		word6.setText("");
        		word7.setText("");
        	}
        });
        
        word7.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		textOutputView.append(word7.getText() + " ");
        		textOutput += " " + word7.getText(); 
        		lastWord = (String)word7.getText();
        		currentSequence = "";
        		word1.setText("");
        		word2.setText("");
        		word3.setText("");
        		word4.setText("");
        		word5.setText("");
        		word6.setText("");
        		word7.setText("");
        	}
        });
        
        enterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					 //new FileInputStream(fileToRead);
					/*BufferedReader myReader = new BufferedReader(new InputStreamReader(myInputStream)); 
					String line = "";
					try{line = myReader.readLine();}
					catch(Exception ex) {}
					Log.d("testOUT", line);*/
				Log.d("CURRENT SEQUENCE IS", "" + currentSequence);
				
				if ((keygonSequences.get(currentSequence) != null)){
					String currentWord = keygonSequences.get(currentSequence)[0];
					if(keygonSequences.get(currentSequence)[1] != null)
						Log.d("OTHER POSSIBLE WORDS ARE1", keygonSequences.get(currentSequence)[1]);
					if(keygonSequences.get(currentSequence)[2] != null)
						Log.d("OTHER POSSIBLE WORDS ARE2", keygonSequences.get(currentSequence)[2]);
					if(keygonSequences.get(currentSequence)[3] != null)
						Log.d("OTHER POSSIBLE WORDS ARE3", keygonSequences.get(currentSequence)[3]);
					if(keygonSequences.get(currentSequence)[4] != null)
						Log.d("OTHER POSSIBLE WORDS ARE4", keygonSequences.get(currentSequence)[4]);
					Log.d("Our word is", "" + currentWord);
					textOutput += " " + currentWord;
					lastWord = (String) currentWord;
					textOutputView.setText(textOutput); 
				}
					word1.setText("");
	        		word2.setText("");
	        		word3.setText("");
	        		word4.setText("");
	        		word5.setText("");
	        		word6.setText("");
	        		word7.setText("");
					currentSequence = "";
			}
        });
				
        
        compass.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
                int eventPadTouch = event.getAction();
                int iX = (int) event.getX(); //might have to add 1
                int iY = (int) event.getY(); //might have to add 1
                
                
                boolean isXYPositive = iX>=0 && iY>=0;
                
                switch (eventPadTouch) {

                    case MotionEvent.ACTION_DOWN:
                        if (isXYPositive && iX<nb.getWidth() && iY<nb.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (nb.getPixel(iX,iY)!=0) {
                                // NORTH BUMPER
                            	prevButton = "northBump";
                            	Log.d("NB", "was picked");
                            	
                            }               
                        }
                        if (isXYPositive && iX<sb.getWidth() && iY<sb.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (sb.getPixel(iX,iY)!=0) {
                                // SOUTH BUMPER
                            	prevButton = "southBump";
                            	Log.d("SB", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<eb.getWidth() && iY<eb.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (eb.getPixel(iX,iY)!=0) {
                                // EAST BUMPER
                            	prevButton = "eastBump";
                            	Log.d("EB", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<wb.getWidth() && iY<wb.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (wb.getPixel(iX,iY)!=0) {
                                // WEST BUMPER
                            	prevButton = "westBump";
                            	Log.d("WB", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<ng.getWidth() && iY<ng.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (ng.getPixel(iX,iY)!=0) {
                                // NORTH GROUPING
                            	prevButton = "text";
                            	currentSequence += "0";
                            	Log.d("NG", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<neg.getWidth() && iY<neg.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (neg.getPixel(iX,iY)!=0) {
                                // NORTH EAST GROUPING
                            	prevButton = "text";
                            	currentSequence += "1";
                            	Log.d("NEG", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<eg.getWidth() && iY<eg.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (eg.getPixel(iX,iY)!=0) {
                                // EAST GROUPING
                            	prevButton = "text";
                            	currentSequence += "2";
                            	Log.d("EG", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<seg.getWidth() && iY<seg.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (seg.getPixel(iX,iY)!=0) {
                                // SOUTH EAST GROUPING
                            	prevButton = "text";
                            	currentSequence += "3";
                            	Log.d("SEG", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<sg.getWidth() && iY<sg.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (sg.getPixel(iX,iY)!=0) {
                                // SOUTH GROUPING
                            	prevButton = "text";
                            	currentSequence += "4";
                            	Log.d("SG", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<swg.getWidth() && iY<swg.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (swg.getPixel(iX,iY)!=0) {
                                // SOUTH WEST GROUPING
                            	prevButton = "text";
                            	currentSequence += "5";
                            	Log.d("SWG", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<wg.getWidth() && iY<wg.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (wg.getPixel(iX,iY)!=0) {
                                // WEST GROUPING
                            	prevButton = "text";
                            	currentSequence += "6";
                            	Log.d("WG", "was picked");
                            }               
                        }
                        if (isXYPositive && iX<nwg.getWidth() && iY<nwg.getHeight()) { // ** Makes sure that X and Y are not less than 0, and no more than the height and width of the image.                
                            if (nwg.getPixel(iX,iY)!=0) {
                                // NORTH WEST GROUPING
                            	prevButton = "text";
                            	currentSequence += "7";
                            	Log.d("NWG", "was picked");
                            }            
                        }
                        if (isXYPositive && iX<center.getWidth() && iY<center.getHeight()) {
                        	if(center.getPixel(iX,iY) != 0) {
                        		prevButton = "center";
                        		prevXTouch = iX;
                        		prevYTouch = iY;
                        		Log.d("CENTER", "was picked");
                        	}
                        }
                        if (prevButton.equals("text") && keygonSequences.containsKey(currentSequence)) {
                        	//TextView possibilities = (TextView) findViewById(R.id.textView2);
                        	//possibilities.setText("");
                        	for (int k = 0; k < keygonSequences.get(currentSequence).length; k++) {
                        		TextView tv;
                        		if (keygonSequences.get(currentSequence)[k] != null) {
                        			if(k == 0){
                        				tv = (TextView)findViewById(R.id.textView3);
                        				tv.setText(keygonSequences.get(currentSequence)[k].toString());
                        			}
                        			if(k == 1){
                        				tv = (TextView)findViewById(R.id.textView4);
                        				tv.setText(keygonSequences.get(currentSequence)[k].toString());
                        			}
                        			if(k == 2){
                        				tv = (TextView)findViewById(R.id.textView5);
                        				tv.setText(keygonSequences.get(currentSequence)[k].toString());
                        			}
                        			if(k == 3){
                        				tv = (TextView)findViewById(R.id.textView6);
                        				tv.setText(keygonSequences.get(currentSequence)[k].toString());
                        			}
                        			if(k == 4){
                        				tv = (TextView)findViewById(R.id.textView7);
                        				tv.setText(keygonSequences.get(currentSequence)[k].toString());
                        			}
                        			if(k == 5){
                        				tv = (TextView)findViewById(R.id.textView8);
                        				tv.setText(keygonSequences.get(currentSequence)[k].toString());
                        			}
                        			if(k == 6){
                        				tv = (TextView)findViewById(R.id.textView9);
                        				tv.setText(keygonSequences.get(currentSequence)[k].toString());
                        			}
                        			
                        		}
                        	}
                        	
                        }
                        return true;     
                        
                    case MotionEvent.ACTION_UP:
                    	prevTouchTime = (int) event.getEventTime() - (int) event.getDownTime(); 
                    	if(isXYPositive && prevButton.equals("northBump") && iX<sb.getWidth() && iY<sb.getHeight()) {
                    		if (sb.getPixel(iX, iY) !=0){
                    			//Swipe north to south LOWERCASE
                    			if(prevTouchTime > 2000) {
                    				Log.d("SWIPING", "LOWERCASE LONG");
                    			}
                    			else {
                    				Log.d("SWIPING", "LOWERCASE ACTION");
                    			}
                    			prevButton = "lowCase";
                    		}
                    	}
                    	else if(isXYPositive && prevButton.equals("southBump") && iX<nb.getWidth() && iY<nb.getHeight()) {
                    		if (nb.getPixel(iX, iY) !=0){
                    			//Swipe south to north UPPERCASE
                    			if(prevTouchTime > 2000) {
                    				Log.d("SWIPING", "CAPSLOCK");
                    			}
                    			else {
                    				Log.d("SWIPING", "UPPERCASE ACTION");
                    			}
                    			prevButton = "upCase";
                    		}
                    	}
                    	else if(isXYPositive && prevButton.equals("eastBump") && iX<wb.getWidth() && iY<wb.getHeight()) {
                    		if (wb.getPixel(iX, iY) !=0){
                    			//Swipe east to west DELETE TO LEFT
                    			if(prevTouchTime > 2000) {
                    				Log.d("SWIPING", "DELETE ENTIRE WORD TO LEFT");
                    			}
                    			else {
                    				Log.d("SWIPING", "DELETE TO LEFT");
                    				textOutput = textOutput.substring(0, textOutput.length() - lastWord.length());
                    				Log.d("new textoutput", textOutput);
                    				textOutputView.setText(textOutput);
                    			}
                    			prevButton = "lDel";
                    		}
                    	}
                    	if(isXYPositive && prevButton.equals("westBump") && iX<eb.getWidth() && iY<eb.getHeight()) {
                    		if (eb.getPixel(iX, iY) !=0){
                    			//Swipe west to east DELETE TO RIGHT
                    			if(prevTouchTime > 2000) {
                    				Log.d("SWIPING", "DELETE ENTIRE WORD TO RIGHT");
                    			}
                    			else {
                    				Log.d("SWIPING", "DELETE TO RIGHT");
                    			}
                    			prevButton = "rDel";
                    		}
                    	}
                    	return true;
                    	
                    case MotionEvent.ACTION_MOVE:
                    	if (isXYPositive && prevButton.equals("center")) {
                    		RelativeLayout.LayoutParams layoutCoords = (RelativeLayout.LayoutParams) compass.getLayoutParams();
                    		int leftMarg = layoutCoords.leftMargin + iX - prevXTouch;
                    		int topMarg = layoutCoords.topMargin + iY - prevYTouch;
                    		layoutCoords.leftMargin = leftMarg;
                    		layoutCoords.topMargin = topMarg;
                    		compass.setLayoutParams(layoutCoords);
                    		break;
                    	}
                        return true;
                }           
                return false;
			}
        }); 
        
        compass.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if (prevButton == "lowCase"){
					Log.d("SWIPING", "LOWERCASE ACTION");
					prevButton = "";
					return true;
				}
				if (prevButton == "upCase") {
					Log.d("SWIPING", "CAPSLOCK");
					prevButton = "";
					return true; 
				}
				if (prevButton == "lDel") {
					Log.d("SWIPING", "DELETE ENTIRE WORD TO LEFT");
					prevButton = "";
					return true; 
				}
				if (prevButton == "rDel") {
					Log.d("SWIPING", "DELETE ENTIRE WORD TO RIGHT");
					prevButton = "";
					return true; 
				}
				return false;
			}
		});
    }
    
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
}

 


//getLayoutInflater().inflate(R.layout.activity_main, null); 
/*if(k == 0) {
	TextView tv = (TextView) findViewById(R.id.textView3);
	tv.setText(keygonSequences.get(currentSequence)[k]); 
}
if(k == 1) {
	TextView tv = (TextView) findViewById(R.id.textView4); 
	tv.setText(keygonSequences.get(currentSequence)[k]);
}
if(k == 2) {
	TextView tv = (TextView) findViewById(R.id.textView5); 
	tv.setText(keygonSequences.get(currentSequence)[k]);
}
/*if(k == 3) {
	TextView tv = (TextView) findViewById(R.id.textView6);
	tv.setText(keygonSequences.get(currentSequence)[k]);
}
if(k == 4) {
	TextView tv = (TextView) findViewById(R.id.textView7);
	tv.setText(keygonSequences.get(currentSequence)[k]);
}*/

//possibilities.append(keygonSequences.get(currentSequence)[k] + "  ");
      /*  myDetector = new GestureDetector(this, new GestureListener); 
        private class GestureListener extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // Set up the Scroller for a fling
                float scrollTheta = vectorToScalarScroll(
                        velocityX,
                        velocityY,
                        e2.getX() - mPieBounds.centerX(),
                        e2.getY() - mPieBounds.centerY());
                mScroller.fling(
                        0,
                        (int) getPieRotation(),
                        0,
                        (int) scrollTheta / FLING_VELOCITY_DOWNSCALE,
                        0,
                        0,
                        Integer.MIN_VALUE,
                        Integer.MAX_VALUE);

                // Start the animator and tell it to animate for the expected duration of the fling.
                if (Build.VERSION.SDK_INT >= 11) {
                    mScrollAnimator.setDuration(mScroller.getDuration());
                    mScrollAnimator.start();
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // The user is interacting with the pie, so we want to turn on acceleration
                // so that the interaction is smooth.
                mPieView.accelerate();
                if (isAnimationRunning()) {
                    stopScrolling();
                }
                return true;
            }
        } */
    


