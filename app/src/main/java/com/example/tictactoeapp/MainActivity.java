package com.example.tictactoeapp;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.tictactoeapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore, playerTwoScore,playerStatus;
    private Button [] buttons=new Button[9];
    private Button resetGame;
    private int playerOneScoreCount, playerTwoScoreCount,rountCount;
    boolean activePlayer;

    ActivityMainBinding binding;
    int [] gameState={2,2,2,2,2,2,2,2,2};
    int [][] winningPositions ={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        binding.playerOne.setText(getPlayerOneName);
        binding.playerTwo.setText(getPlayerTwoName);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore=(TextView) findViewById(R.id.playerTwoScore);
        playerStatus=(TextView)findViewById(R.id.playerStatus);
        resetGame=(Button) findViewById(R.id.resetGame);
        for(int i=0;i< buttons.length;i++){
            String buttonID="btn_"+i;
            int resourceID=getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i]=(Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rountCount=0;
        playerOneScoreCount=0;
        playerTwoScoreCount=0;
        activePlayer=true;


    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonID= view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer=Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));
        if(activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFFFFFFF"));
            gameState[gameStatePointer]=0;
        }else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#8692f7"));
            gameState[gameStatePointer]=1;
        }
        rountCount++;
        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,binding.playerOne.getText() + " is a Winner!",Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,binding.playerTwo.getText() + " is a Winner!",Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount==9){
                playAgain();
            Toast.makeText(this,"No winner!",Toast.LENGTH_SHORT).show();
        }else{
            activePlayer=!activePlayer;
        }
        if(playerOneScoreCount>playerTwoScoreCount){
            playerStatus.setText("");
        }else if(playerOneScoreCount<playerTwoScoreCount){
            playerStatus.setText("");
        }else{
            playerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner(){
        boolean winnerResult=false;
        for(int[] winningPosion:winningPositions){
            if(gameState[winningPosion[0]]==gameState[winningPosion[1]]&&gameState[winningPosion[1]]==gameState[winningPosion[2]]&&gameState[winningPosion[0]]!=2){
                winnerResult=true;
            }
        }
        return  winnerResult;
    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void  playAgain(){
        rountCount=0;
        activePlayer=true;
        for(int i=0;i<buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}