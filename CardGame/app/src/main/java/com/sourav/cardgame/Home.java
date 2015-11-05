package com.sourav.cardgame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Home extends ActionBarActivity {

    ImageView i;
    RelativeLayout r;
    int reqh, reqw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        i=(ImageView)findViewById(R.id.logo);
        r=(RelativeLayout)findViewById(R.id.homeRelative);
        ViewTreeObserver o=r.getViewTreeObserver();
        if(o.isAlive())
        {
            o.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    reqw=i.getMeasuredWidth();
                    reqh=i.getMeasuredHeight();
                    i.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }




    }


    public static Bitmap decodeSampledBitmap (Resources rs, int rsid, int width, int height)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(rs,rsid,options);
        options.inSampleSize=calcultaInsamplesize(options, width, height);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(rs, rsid, options);

    }

    public static  int calcultaInsamplesize(BitmapFactory.Options options, int rewidth, int reheight)
    {

        int insamplesize=1;
        int width=options.outWidth;
        int height=options.outHeight;

        if (width>rewidth || height>reheight)
        {
            int halfheight=height/2;
            int halfwidth=width/2;
            while(halfwidth/insamplesize > width && halfheight/insamplesize>height)
            {
                insamplesize*=2;
            }
        }
        return insamplesize;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(i!=null)
        {
            ViewTreeObserver o=i.getViewTreeObserver();

        }
        i.setImageBitmap(decodeSampledBitmap(getResources(), R.drawable.logo, reqw, reqh));
      //  Toast.makeText(this, " req w "+reqw +" and reqh "+ reqh,
       //         Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        i.setImageDrawable(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startNew(View view) {
        startActivity(new Intent(this,MainActivity.class ));
    }
}
