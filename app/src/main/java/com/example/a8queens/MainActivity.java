package com.example.a8queens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {






    Button[][] sq = new Button[8][8];


    EightQueens mEightQueens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewcontroller();

        mEightQueens  = new EightQueens();

        setContentView(R.layout.activity_main);
        setUpScreen();
        setupFAB();
    }

    @Override protected void onSaveInstanceState (Bundle outState)
    {
        super.onSaveInstanceState (outState);
        outState.putBoolean("continuousCheck", continuousCheck);
        TextView tv = findViewById(R.id.displaymessage);
        String s = tv.getText().toString();
        outState.putString("message", s);
        int i = 0;
        for (boolean[] b: mEightQueens.board) {
            outState.putBooleanArray("row"+i, b);
            i++;
        }

    }

    @Override protected void onRestoreInstanceState (Bundle savedInstanceState)
    {
        super.onRestoreInstanceState (savedInstanceState);
        continuousCheck = savedInstanceState.getBoolean("continuousCheck");
        for (int i = 0; i < 8; i++) {
            mEightQueens.board[i] = (savedInstanceState.getBooleanArray ("row"+i));
        }
        TextView tv = findViewById(R.id.displaymessage);
        tv.setText(savedInstanceState.getString("message"));
        redrawBoard();
    }


    @Override public boolean onPrepareOptionsMenu (Menu menu)
    {
        //menu.findItem (R.id.action_toggle_auto_save).setChecked (mPrefUseAutoSave);
       // menu.findItem (R.id.action_turn_show_error_messages).setChecked (mPrefShowErrors);
        return super.onPrepareOptionsMenu (menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.continuous_check);
        menuItem.setChecked(continuousCheck);
        return true;
    }
    public void Clear(MenuItem item) {
        View v = findViewById(R.id.grid_view);  // just to trick java to let me call the clearboard method
        clearBoard(v);
    }

    public void showAbout (MenuItem item)
    {
        Utils.showInfoDialog (MainActivity.this, R.string.about_dialog_title,
                R.string.about_dialog_banner);
    }
    
    Boolean continuousCheck=false;
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        if(item.isChecked()){
            item.setChecked(false);
            continuousCheck =false;
        }else{
            item.setChecked(true);
            continuousCheck= true;
        }
        return true;
    }




    private void setupFAB ()
    {
        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                press(view);
            }
        });
    }

    private void setUpScreen() {
        viewcontroller();
        Boolean skip = false;
        for (int i = 0; i < 8 ; i++) {
            skip= !skip;
            for (int j = 0; j < 8; j++) {
                System.out.println(i+"i +  j"+ j);
                if(skip){
                    skip=  false;
                    Button bs = sq[i][j];
                    bs.setBackgroundResource(R.drawable.queen);
                    continue;
                }
                Button bs = sq[i][j];
                bs.setBackgroundResource(R.drawable.queeneli);
                skip = true;
            }
        }
    }

    public void launchVisualizer(View view) {
        startActivity(new Intent(MainActivity.this, visualizer.class));
    }

    public void press(View view) {

        if(mEightQueens.isWinner()){
            String s = "You solved the puzzle";
            Toast.makeText(MainActivity.this, s,Toast.LENGTH_SHORT).show();
            playWinMusic();
            TextView t = findViewById(R.id.displaymessage);
            t.setText(s);
        }else{
            String s;
            boolean hasred = showError();
            if(hasred){
                vibrate();
                s = "You have a queen on an invalid square";
            }else{
                String q = peicesonboard==1? "" : "s";
                s =peicesonboard+" queen"+q+" placed";
            }
            TextView t = findViewById(R.id.displaymessage);
            t.setText(s);
            if(peicesonboard<=8 && !hasred){
                t.setTextColor(Color.GREEN);
            }
            else{
                t.setTextColor(Color.RED);
            }


        }

    }

    private void playWinMusic() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.winsound);
        mp.start();
    }


    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    int peicesonboard = 0;
    private boolean showError() {
        boolean hasRed =false;
        peicesonboard =0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(mEightQueens.board[i][j]){
                    peicesonboard++;
                }

                if(mEightQueens.board[i][j]){
                    for (int k = 0; k < mEightQueens.upDiagonal.length; k++) {
                        if(mEightQueens.upDiagonal[k]>1){
                            if( (i-j) + 7 == k){
                                sq[i][j].setBackgroundResource(R.drawable.reddqueensb);
                                hasRed=true;
                            }
                        }
                        if(mEightQueens.downDiagonal[k]>1){
                            if( i+j == k){
                                sq[i][j].setBackgroundResource(R.drawable.reddqueensb);
                                hasRed=true;
                            }
                        }
                    }
                }

               if(mEightQueens.problemSquares[i][j]){
                   if(mEightQueens.board[i][j]){
                       sq[i][j].setBackgroundResource(R.drawable.reddqueensb);
                       hasRed=true;
                   }

               }
            }
        }
        return hasRed;
    }

    public void clearBoard(View view) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mEightQueens.board[i][j] = false;
            }
        }
        TextView t = findViewById(R.id.displaymessage);
        t.setText("");
        redrawBoard();
    }

    public void place(View view) {
        Button b = (Button) view;
        placeHelper(b);
        redrawBoard();

        if(continuousCheck){
            press(view);
        }else {
            updateTextView();
        }
    }

    private void updateTextView() {
        TextView tv = findViewById(R.id.displaymessage);
        String q = peices==1? "" : "s";
        tv.setText(peices + " queen"+q+" placed");
        if(peices>8){
            tv.setTextColor(Color.RED);
        }
        else{
            tv.setTextColor(Color.GREEN);
        }

    }

    int peices =0;
    private void redrawBoard() {

        peices = 0;
        Boolean skip = false;

        for (int i = 0; i < 8; i++) {
            skip = !skip;
            for (int j = 0; j < 8; j++) {
                if (skip) {
                    skip = false;
                    Button bs = sq[i][j];
                    bs.setBackgroundColor(Color.WHITE);
                    if (mEightQueens.board[i][j]) {
                        peices++;
                        bs.setBackgroundResource(R.drawable.queensb);
                    }
                    continue;
                }
                Button bs = sq[i][j];
                bs.setBackgroundResource(R.color.BlackBoard);
                if (mEightQueens.board[i][j]) {
                    peices++;
                    bs.setBackgroundResource(R.drawable.queensblack);
                }
                skip = true;
            }

        }
    }

    // I looked there seems to be some sort of way to automate this process through passing in the grid with the button
    //  But I was unable to get that to work :(  so I did this instead.
    private void placeHelper(Button b) {
        switch (b.getId()) {
            case R.id.b00:
                mEightQueens.board[0][0] = !mEightQueens.board[0][0];
                break;
            case R.id.b01:
                mEightQueens.board[0][1] = !mEightQueens.board[0][1];
                break;
            case R.id.b02:
                mEightQueens.board[0][2] = !mEightQueens.board[0][2];
                break;
            case R.id.b03:
                mEightQueens.board[0][3] = !mEightQueens.board[0][3];
                break;
            case R.id.b04:
                mEightQueens.board[0][4] = !mEightQueens.board[0][4];
                break;
            case R.id.b05:
                mEightQueens.board[0][5] = !mEightQueens.board[0][5];
                break;
            case R.id.b06:
                mEightQueens.board[0][6] = !mEightQueens.board[0][6];
                break;
            case R.id.b07:
                mEightQueens.board[0][7] = !mEightQueens.board[0][7];
                break;

                ///////////
            case R.id.b10:
                mEightQueens.board[1][0] = !mEightQueens.board[1][0];
                break;
            case R.id.b11:
                mEightQueens.board[1][1] = !mEightQueens.board[1][1];
                break;
            case R.id.b12:
                mEightQueens.board[1][2] = !mEightQueens.board[1][2];
                break;
            case R.id.b13:
                mEightQueens.board[1][3] = !mEightQueens.board[1][3];
                break;
            case R.id.b14:
                mEightQueens.board[1][4] = !mEightQueens.board[1][4];
                break;
            case R.id.b15:
                mEightQueens.board[1][5] = !mEightQueens.board[1][5];
                break;
            case R.id.b16:
                mEightQueens.board[1][6] = !mEightQueens.board[1][6];
                break;
            case R.id.b17:
                mEightQueens.board[1][7] = !mEightQueens.board[1][7];
                break;

                ////


            case R.id.b20:
                mEightQueens.board[2][0] = !mEightQueens.board[2][0];
                break;
            case R.id.b21:
                mEightQueens.board[2][1] = !mEightQueens.board[2][1];
                break;
            case R.id.b22:
                mEightQueens.board[2][2] = !mEightQueens.board[2][2];
                break;
            case R.id.b23:
                mEightQueens.board[2][3] = !mEightQueens.board[2][3];
                break;
            case R.id.b24:
                mEightQueens.board[2][4] = !mEightQueens.board[2][4];
                break;
            case R.id.b25:
                mEightQueens.board[2][5] = !mEightQueens.board[2][5];
                break;
            case R.id.b26:
                mEightQueens.board[2][6] = !mEightQueens.board[2][6];
                break;
            case R.id.b27:
                mEightQueens.board[2][7] = !mEightQueens.board[2][7];
                break;
                //
            case R.id.b30:
                mEightQueens.board[3][0] = !mEightQueens.board[3][0];
                break;
            case R.id.b31:
                mEightQueens.board[3][1] = !mEightQueens.board[3][1];
                break;
            case R.id.b32:
                mEightQueens.board[3][2] = !mEightQueens.board[3][2];
                break;
            case R.id.b33:
                mEightQueens.board[3][3] = !mEightQueens.board[3][3];
                break;
            case R.id.b34:
                mEightQueens.board[3][4] = !mEightQueens.board[3][4];
                break;
            case R.id.b35:
                mEightQueens.board[3][5] = !mEightQueens.board[3][5];
                break;
            case R.id.b36:
                mEightQueens.board[3][6] = !mEightQueens.board[3][6];
                break;
            case R.id.b37:
                mEightQueens.board[3][7] = !mEightQueens.board[3][7];
                break;
                /////


            case R.id.b40:
                mEightQueens.board[4][0] = !mEightQueens.board[4][0];
                break;
            case R.id.b41:
                mEightQueens.board[4][1] = !mEightQueens.board[4][1];
                break;
            case R.id.b42:
                mEightQueens.board[4][2] = !mEightQueens.board[4][2];
                break;
            case R.id.b43:
                mEightQueens.board[4][3] = !mEightQueens.board[4][3];
                break;
            case R.id.b44:
                mEightQueens.board[4][4] = !mEightQueens.board[4][4];
                break;
            case R.id.b45:
                mEightQueens.board[4][5] = !mEightQueens.board[4][5];
                break;
            case R.id.b46:
                mEightQueens.board[4][6] = !mEightQueens.board[4][6];
                break;
            case R.id.b47:
                mEightQueens.board[4][7] = !mEightQueens.board[4][7];
                break;

            ///////////
            case R.id.b50:
                mEightQueens.board[5][0] = !mEightQueens.board[5][0];
                break;
            case R.id.b51:
                mEightQueens.board[5][1] = !mEightQueens.board[5][1];
                break;
            case R.id.b52:
                mEightQueens.board[5][2] = !mEightQueens.board[5][2];
                break;
            case R.id.b53:
                mEightQueens.board[5][3] = !mEightQueens.board[5][3];
                break;
            case R.id.b54:
                mEightQueens.board[5][4] = !mEightQueens.board[5][4];
                break;
            case R.id.b55:
                mEightQueens.board[5][5] = !mEightQueens.board[5][5];
                break;
            case R.id.b56:
                mEightQueens.board[5][6] = !mEightQueens.board[5][6];
                break;
            case R.id.b57:
                mEightQueens.board[5][7] = !mEightQueens.board[5][7];
                break;

            ////


            case R.id.b60:
                mEightQueens.board[6][0] = !mEightQueens.board[6][0];
                break;
            case R.id.b61:
                mEightQueens.board[6][1] = !mEightQueens.board[6][1];
                break;
            case R.id.b62:
                mEightQueens.board[6][2] = !mEightQueens.board[6][2];
                break;
            case R.id.b63:
                mEightQueens.board[6][3] = !mEightQueens.board[6][3];
                break;
            case R.id.b64:
                mEightQueens.board[6][4] = !mEightQueens.board[6][4];
                break;
            case R.id.b65:
                mEightQueens.board[6][5] = !mEightQueens.board[6][5];
                break;
            case R.id.b66:
                mEightQueens.board[6][6] = !mEightQueens.board[6][6];
                break;
            case R.id.b67:
                mEightQueens.board[6][7] = !mEightQueens.board[6][7];
                break;
            //
            case R.id.b70:
                mEightQueens.board[7][0] = !mEightQueens.board[7][0];
                break;
            case R.id.b71:
                mEightQueens.board[7][1] = !mEightQueens.board[7][1];
                break;
            case R.id.b72:
                mEightQueens.board[7][2] = !mEightQueens.board[7][2];
                break;
            case R.id.b73:
                mEightQueens.board[7][3] = !mEightQueens.board[7][3];
                break;
            case R.id.b74:
                mEightQueens.board[7][4] = !mEightQueens.board[7][4];
                break;
            case R.id.b75:
                mEightQueens.board[7][5] = !mEightQueens.board[7][5];
                break;
            case R.id.b76:
                mEightQueens.board[7][6] = !mEightQueens.board[7][6];
                break;
            case R.id.b77:
                mEightQueens.board[7][7] = !mEightQueens.board[7][7];
                break;
            default:
                // code block
        }
    }

    // same here there probably is a way to locate the buttons by their positions in the grid layout
    public void viewcontroller() {

        sq[0][0] = findViewById(R.id.b00);
        sq[0][1] = findViewById(R.id.b01);
        sq[0][2] = findViewById(R.id.b02);
        sq[0][3] = findViewById(R.id.b03);
        sq[0][4] = findViewById(R.id.b04);
        sq[0][5] = findViewById(R.id.b05);
        sq[0][6] = findViewById(R.id.b06);
        sq[0][7] = findViewById(R.id.b07);

        sq[1][0] = findViewById(R.id.b10);
        sq[1][1] = findViewById(R.id.b11);
        sq[1][2] = findViewById(R.id.b12);
        sq[1][3] = findViewById(R.id.b13);
        sq[1][4] = findViewById(R.id.b14);
        sq[1][5] = findViewById(R.id.b15);
        sq[1][6] = findViewById(R.id.b16);
        sq[1][7] = findViewById(R.id.b17);

        sq[2][0] = findViewById(R.id.b20);
        sq[2][1] = findViewById(R.id.b21);
        sq[2][2] = findViewById(R.id.b22);
        sq[2][3] = findViewById(R.id.b23);
        sq[2][4] = findViewById(R.id.b24);
        sq[2][5] = findViewById(R.id.b25);
        sq[2][6] = findViewById(R.id.b26);
        sq[2][7] = findViewById(R.id.b27);

        sq[3][0] = findViewById(R.id.b30);
        sq[3][1] = findViewById(R.id.b31);
        sq[3][2] = findViewById(R.id.b32);
        sq[3][3] = findViewById(R.id.b33);
        sq[3][4] = findViewById(R.id.b34);
        sq[3][5] = findViewById(R.id.b35);
        sq[3][6] = findViewById(R.id.b36);
        sq[3][7] = findViewById(R.id.b37);

        sq[4][0] = findViewById(R.id.b40);
        sq[4][1] = findViewById(R.id.b41);
        sq[4][2] = findViewById(R.id.b42);
        sq[4][3] = findViewById(R.id.b43);
        sq[4][4] = findViewById(R.id.b44);
        sq[4][5] = findViewById(R.id.b45);
        sq[4][6] = findViewById(R.id.b46);
        sq[4][7] = findViewById(R.id.b47);

        sq[5][0] = findViewById(R.id.b50);
        sq[5][1] = findViewById(R.id.b51);
        sq[5][2] = findViewById(R.id.b52);
        sq[5][3] = findViewById(R.id.b53);
        sq[5][4] = findViewById(R.id.b54);
        sq[5][5] = findViewById(R.id.b55);
        sq[5][6] = findViewById(R.id.b56);
        sq[5][7] = findViewById(R.id.b57);

        sq[6][0] = findViewById(R.id.b60);
        sq[6][1] = findViewById(R.id.b61);
        sq[6][2] = findViewById(R.id.b62);
        sq[6][3] = findViewById(R.id.b63);
        sq[6][4] = findViewById(R.id.b64);
        sq[6][5] = findViewById(R.id.b65);
        sq[6][6] = findViewById(R.id.b66);
        sq[6][7] = findViewById(R.id.b67);

        sq[7][0] = findViewById(R.id.b70);
        sq[7][1] = findViewById(R.id.b71);
        sq[7][2] = findViewById(R.id.b72);
        sq[7][3] = findViewById(R.id.b73);
        sq[7][4] = findViewById(R.id.b74);
        sq[7][5] = findViewById(R.id.b75);
        sq[7][6] = findViewById(R.id.b76);
        sq[7][7] = findViewById(R.id.b77);

    }



}
