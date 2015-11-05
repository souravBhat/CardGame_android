package com.sourav.cardgame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MainActivity extends Activity {

    ImageView image11, image12, image13, image21, image22, image23, image31, image32, image33;
    TextView leveltext;
    ArrayList<ImageView> ImageViews = new ArrayList<ImageView>();
    Integer queenOfHeartsid;
    private static boolean gameOver;
    private static boolean clicked;
    int level, count;
    Integer id;
    int backimageid;

    int viewWidth = 200;
    int viewHeight = 200;



    boolean dealt=false;
    final Object lock=new Object();


    Thread gt;
    ArrayList<Deck> Levels;

    private static LruCache<String , Bitmap> cached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image11 = (ImageView) findViewById(R.id.image11);
        image12 = (ImageView) findViewById(R.id.image12);
        image13 = (ImageView) findViewById(R.id.image13);
        image21 = (ImageView) findViewById(R.id.image21);
        image22 = (ImageView) findViewById(R.id.image22);
        image23 = (ImageView) findViewById(R.id.image23);
        image31 = (ImageView) findViewById(R.id.image31);
        image32 = (ImageView) findViewById(R.id.image32);
        image33 = (ImageView) findViewById(R.id.image33);
        leveltext=(TextView)findViewById(R.id.leveltext);


       /* image11.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                image11.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                viewWidth = image11.getMeasuredWidth();
                viewHeight = image11.getMeasuredHeight();
                viewHeight++;
            }
        });*/

        ImageViews.add(image11);
        ImageViews.add(image12);
        ImageViews.add(image13);
        ImageViews.add(image21);
        ImageViews.add(image22);
        ImageViews.add(image23);
        ImageViews.add(image31);
        ImageViews.add(image32);
        ImageViews.add(image33);

        Levels = new ArrayList<Deck>();
        queenOfHeartsid = R.drawable.queen_of_hearts2;
        backimageid = R.drawable.backimage;


        long maxmemory=Runtime.getRuntime().maxMemory();
        cached=new LruCache<String, Bitmap>((int)maxmemory/8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };




    }

    @Override
    protected void onStart() {
        super.onStart();
        createLevels();
        level = 0;
        gameOver = false;
        clicked = false;
        count = 0;
        gt = new Thread(new gameThread(this));

    }

    Object playlock=new Object();
    boolean canPlay=true;
    @Override
    protected void onResume() {
        super.onResume();
       synchronized (playlock)
       {
           if(canPlay)
               gt.start();
           else
               try {
                   playlock.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
       }


    }

    public void createLevels() {
        ArrayList<Cards> c1 = new ArrayList<Cards>();
        c1.add(new Cards(Cards.AC));
        c1.add(new Cards(Cards.QH));
        c1.add(new Cards(Cards.AD));
        c1.add(new Cards(Cards.AS));
        c1.add(new Cards(Cards.JH));
        c1.add(new Cards(Cards.KH));
        c1.add(new Cards(Cards.JC));
        c1.add(new Cards(Cards.JS));
        c1.add(new Cards(Cards.KC));
        Deck deck = new Deck(c1);
        Levels.add(deck);
        ArrayList<Cards> c2 = new ArrayList<Cards>();
        c2.add(new Cards(Cards.AD));
        c2.add(new Cards(Cards.AH));
        c2.add(new Cards(Cards.AS));
        c2.add(new Cards(Cards.KD));
        c2.add(new Cards(Cards.QC));
        c2.add(new Cards(Cards.QH));
        c2.add(new Cards(Cards.KH));
        c2.add(new Cards(Cards.JC));
        c2.add(new Cards(Cards.JD));
        Levels.add(new Deck(c2));

        ArrayList<Cards> c3 = new ArrayList<Cards>();
        c3.add(new Cards(Cards.AD));
        c3.add(new Cards(Cards.AH));
        c3.add(new Cards(Cards.AS));
        c3.add(new Cards(Cards.KD));
        c3.add(new Cards(Cards.JH));
        c3.add(new Cards(Cards.QH));
        c3.add(new Cards(Cards.JC));
        c3.add(new Cards(Cards.JS));
        c3.add(new Cards(Cards.KC));

        Levels.add(new Deck(c3));
        Deck d4;
        for(int i=0;i<5;i++)
        {
            d4=new Deck();
            d4.createDeck();
            Levels.add(d4);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    protected void onStop() {
        super.onStop();

    }

    public  void dealDeck(int onLevel) {
        Deck deck = Levels.get(onLevel);
        deck.shuffleDeck();
        //ImageLoader imageLoader=new ImageLoader(ImageViews);
        for (int i = 0; i < ImageViews.size(); i++) {
            id = getResources().getIdentifier(deck.get(i).getCardname(), "drawable", getPackageName());
            ImageViews.get(i).setImageBitmap(decodeBitmapFromResource(getResources(), id, viewWidth, viewHeight));

            //imageLoader.execute(id);
            ImageViews.get(i).setTag(id);
            id = null;
        }
        Log.i("game", "card dealing done");
        dealt=true;

    }

    public  void showBackAll() {
        for (int i = 0; i < ImageViews.size(); i++) {
            ImageViews.get(i).setImageBitmap(decodeBitmapFromResource(getResources(), backimageid, viewWidth, viewHeight));
        }
    }

    public void showBackSelected(ImageView view)
    {
        view.setImageBitmap(decodeBitmapFromResource(getResources(), backimageid, viewWidth, viewHeight));
    }

    public  void showFront(ImageView view)
    {
        int id=(Integer)view.getTag();
        view.setImageBitmap(decodeBitmapFromResource(getResources(), id,viewWidth, viewHeight ));

    }

    public static Bitmap decodeBitmapFromResource(Resources rs, int rsId, int reqWidth, int reqHeight)
    {
        Bitmap image=cached.get(String.valueOf(rsId));
        if(image!=null)
        {
            return image;
        }
        else
        {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeResource(rs,rsId,options);
            options.inSampleSize=calculateInsampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds=false;
            image=BitmapFactory.decodeResource(rs,rsId,options);
            cached.put(String.valueOf(rsId),image );

            return image;
        }



    }

    public static int calculateInsampleSize(BitmapFactory.Options options, int reqwidth, int reqheight)
    {
         int width=options.outWidth;
         int height=options.outHeight;
        int inSampleSize=1;

        if(width>reqwidth || height>reqheight)
        {
            int halfHeight=height/2;
            int halfWidth=width/2;
            while((halfHeight/inSampleSize) >reqheight && (halfWidth/inSampleSize)>reqwidth )
            {
                inSampleSize*=2;
            }
        }
        return inSampleSize;

    }



    public  void checkImage(View view) {
        Log.i("game", "card clicked");
        synchronized (lock)
        {
            if(!clicked && dealt)
            {
                ImageView i=(ImageView)view;
                showFront(i);
                if(i.getTag().equals(queenOfHeartsid))
                {
                    level++;
                    count=0;

                }
                else
                {
                    Toast.makeText(this, "Game over",
                            Toast.LENGTH_SHORT).show();
                    gameOver=true;
                }
                clicked=true;
                dealt=false;
                Log.i("game", "card clicked changed to "+ clicked);
            }
        }




    }

    public void onPauseButton(View view) {
        onPause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        canPlay=false;
    }

    public class gameThread implements Runnable
    {
        Context c;
        int difficultySec;
        public gameThread(Context c)
        {
            this.c=c;
            difficultySec=600;
        }
        @Override
        public void run() {
            try {
                Log.i("game", "worker thread started");
                while (!gameOver && level < Levels.size()) {
                    Log.i("game", "deck dealing on ui started");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (lock)
                            {
                                dealDeck(level);
                                dealt=true;
                                leveltext.setText(level + 1 + "");
                                lock.notifyAll();
                            }

                            Log.i("game", "card dealing notified");
                        }
                    });

                    Log.i("game", "checking dealt condition");



                  synchronized (lock)
                  {

                      if(!dealt)
                      {
                          Log.i("game", "worker thread to wait");
                          lock.wait();
                      }
                      Log.i("game", "deck has been dealt..return to worker thread "+ level);

                      if(level<=3 && level>1)
                          difficultySec=500;
                      if (level<=6 && level>3)
                          difficultySec=400;
                      //if(level>6)
                      // difficultySec=350;

                      Thread.sleep(difficultySec);
                  }
                    Log.i("game", "after pause..startin ui thread to show back");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showBackAll();
                        }
                    });
                    //canClick=true;
                    Log.i("game", "checking game logic"+ difficultySec);
                    while (!clicked) {
                        if (count > 3) {
                            gameOver = true;
                            break;
                        }
                        count++;
                        Thread.sleep(1000);
                        Log.i("game", "count "+ count);

                    }
                    clicked = false;
                    Log.i("game", "in worker thread after click in level "+ level);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(gameOver)
                        Toast.makeText(getApplicationContext(), "Game over from ui",
                                Toast.LENGTH_SHORT).show();
                    finish();
                }
            });




        }


    }


}
