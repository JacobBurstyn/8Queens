package com.example.a8queens;

import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.Visualizer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class visualizer extends AppCompatActivity {
    int solnum=91;
    Button[][] sq = new Button[8][8];
    int[] board = new int[8];
    qvisualizer mQvisualizer = new qvisualizer(8);
    int[][] solutions = {
            {0, 4, 7, 5, 2, 6, 1, 3},
            {0, 5, 7, 2, 6, 3, 1, 4},
            {0, 6, 3, 5, 7, 1, 4, 2},
            {0, 6, 4, 7, 1, 3, 5, 2},
            {1, 3, 5, 7, 2, 0, 6, 4},
            {1, 4, 6, 0, 2, 7, 5, 3},
            {1, 4, 6, 3, 0, 7, 5, 2},
            {1, 5, 0, 6, 3, 7, 2, 4},
            {1, 5, 7, 2, 0, 3, 6, 4},
            {1, 6, 2, 5, 7, 4, 0, 3},
            {1, 6, 4, 7, 0, 3, 5, 2},
            {1, 7, 5, 0, 2, 4, 6, 3},
            {2, 0, 6, 4, 7, 1, 3, 5},
            {2, 4, 1, 7, 0, 6, 3, 5},
            {2, 4, 1, 7, 5, 3, 6, 0},
            {2, 4, 6, 0, 3, 1, 7, 5},
            {2, 4, 7, 3, 0, 6, 1, 5},
            {2, 5, 1, 4, 7, 0, 6, 3},
            {2, 5, 1, 6, 0, 3, 7, 4},
            {2, 5, 1, 6, 4, 0, 7, 3},
            {2, 5, 3, 0, 7, 4, 6, 1},
            {2, 5, 3, 1, 7, 4, 6, 0},
            {2, 5, 7, 0, 3, 6, 4, 1},
            {2, 5, 7, 0, 4, 6, 1, 3},
            {2, 5, 7, 1, 3, 0, 6, 4},
            {2, 6, 1, 7, 4, 0, 3, 5},
            {2, 6, 1, 7, 5, 3, 0, 4},
            {2, 7, 3, 6, 0, 5, 1, 4},
            {3, 0, 4, 7, 1, 6, 2, 5},
            {3, 0, 4, 7, 5, 2, 6, 1},
            {3, 1, 4, 7, 5, 0, 2, 6},
            {3, 1, 6, 2, 5, 7, 0, 4},
            {3, 1, 6, 2, 5, 7, 4, 0},
            {3, 1, 6, 4, 0, 7, 5, 2},
            {3, 1, 7, 4, 6, 0, 2, 5},
            {3, 1, 7, 5, 0, 2, 4, 6},
            {3, 5, 0, 4, 1, 7, 2, 6},
            {3, 5, 7, 1, 6, 0, 2, 4},
            {3, 5, 7, 2, 0, 6, 4, 1},
            {3, 6, 0, 7, 4, 1, 5, 2},
            {3, 6, 2, 7, 1, 4, 0, 5},
            {3, 6, 4, 1, 5, 0, 2, 7},
            {3, 6, 4, 2, 0, 5, 7, 1},
            {3, 7, 0, 2, 5, 1, 6, 4},
            {3, 7, 0, 4, 6, 1, 5, 2},
            {3, 7, 4, 2, 0, 6, 1, 5},
            {4, 0, 3, 5, 7, 1, 6, 2},
            {4, 0, 7, 3, 1, 6, 2, 5},
            {4, 0, 7, 5, 2, 6, 1, 3},
            {4, 1, 3, 5, 7, 2, 0, 6},
            {4, 1, 3, 6, 2, 7, 5, 0},
            {4, 1, 5, 0, 6, 3, 7, 2},
            {4, 1, 7, 0, 3, 6, 2, 5},
            {4, 2, 0, 5, 7, 1, 3, 6},
            {4, 2, 0, 6, 1, 7, 5, 3},
            {4, 2, 7, 3, 6, 0, 5, 1},
            {4, 6, 0, 2, 7, 5, 3, 1},
            {4, 6, 0, 3, 1, 7, 5, 2},
            {4, 6, 1, 3, 7, 0, 2, 5},
            {4, 6, 1, 5, 2, 0, 3, 7},
            {4, 6, 1, 5, 2, 0, 7, 3},
            {4, 6, 3, 0, 2, 7, 5, 1},
            {4, 7, 3, 0, 2, 5, 1, 6},
            {4, 7, 3, 0, 6, 1, 5, 2},
            {5, 0, 4, 1, 7, 2, 6, 3},
            {5, 1, 6, 0, 2, 4, 7, 3},
            {5, 1, 6, 0, 3, 7, 4, 2},
            {5, 2, 0, 6, 4, 7, 1, 3},
            {5, 2, 0, 7, 3, 1, 6, 4},
            {5, 2, 0, 7, 4, 1, 3, 6},
            {5, 2, 4, 6, 0, 3, 1, 7},
            {5, 2, 4, 7, 0, 3, 1, 6},
            {5, 2, 6, 1, 3, 7, 0, 4},
            {5, 2, 6, 1, 7, 4, 0, 3},
            {5, 2, 6, 3, 0, 7, 1, 4},
            {5, 3, 0, 4, 7, 1, 6, 2},
            {5, 3, 1, 7, 4, 6, 0, 2},
            {5, 3, 6, 0, 2, 4, 1, 7},
            {5, 3, 6, 0, 7, 1, 4, 2},
            {5, 7, 1, 3, 0, 6, 4, 2},
            {6, 0, 2, 7, 5, 3, 1, 4},
            {6, 1, 3, 0, 7, 4, 2, 5},
            {6, 1, 5, 2, 0, 3, 7, 4},
            {6, 2, 0, 5, 7, 4, 1, 3},
            {6, 2, 7, 1, 4, 0, 5, 3},
            {6, 3, 1, 4, 7, 0, 2, 5},
            {6, 3, 1, 7, 5, 0, 2, 4},
            {6, 4, 2, 0, 5, 7, 1, 3},
            {7, 1, 3, 0, 6, 4, 2, 5},
            {7, 1, 4, 2, 0, 6, 3, 5},
            {7, 2, 0, 5, 1, 4, 6, 3},
            {7, 3, 0, 2, 5, 1, 6, 4} };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpScreen();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "I love 8 Queens App");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }
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
                    bs.setBackgroundColor(Color.BLUE);
                    continue;
                }
                Button bs = sq[i][j];
                bs.setBackgroundColor(Color.WHITE);
                skip = true;
            }
        }
    }

    public void place(View view) {
    }



    public void solve(View view) {
        int row = 0;
        int col = 0;
        findViewById(R.id.b00).setBackgroundResource(R.drawable.redqueensb);
        solnum = (solnum == 91) ?  0 : solnum+1;
        draw(solnum);

    }

    private void draw(int solNum) {

        setUpScreen();
        for (int i = 0; i < 8; i++) {
            int row = solutions[solNum][i];
            sq[row][i].setBackgroundResource(R.drawable.redqueensb);
        }
        int i = solNum+1;
        String s = "Solution # " + i;
        TextView t = findViewById(R.id.displaymessage);
        t.setText(s);

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


    public void back(View view) {
        findViewById(R.id.b00).setBackgroundResource(R.drawable.redqueensb);
        solnum = (solnum == 0) ?  91 : solnum-1;
        draw(solnum);
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
