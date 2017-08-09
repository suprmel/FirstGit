import java.awt.*;
import java.util.Random;

public class Project3 {
  //class constants
  public static int ballX;
  public static int ballY;
  public static int BallVelocityX;
  public static int BallVelocityY;
  public static final int PANEL_WIDTH= 500;
  public static final int PANEL_HEIGHT= 400;
  public static final int BALL_SIZE= 10;
  public static Color BALL_COLOR= Color.RED;
  public static final Color BACKGROUND_COLOR=Color.WHITE;
  public static final int KEY_SPACE = ' ';
  public static final int KEY_NEW_GAME = 'N';
  public static final int KEY_SCORE = 'S';
  public static int PADDLE_LENGTH= 50;
  public static int paddleX=460;
  public static int paddleY=175;
  public static Color PADDLE_COLOR= Color.BLACK;
  public static int UP_ARROW=38;
  public static int DOWN_ARROW= 40;
  public static int PADDLE_MOVE_AMOUNT= 5;
  public static int COMPUTERPADX= 40;
  public static int COMPUTERPADY= 175;
  public static int COMPUTERPADDLE_LENGTH= 50;
  public static int MAX_COMPUTER_PADDLE_MOVE=2;
  public static Color COLOR_SCORE=Color.GREEN;
  public static int COMPUTER_SCOREX=90;
  public static int USER_SCOREX=390;
  public static int SCORES_Y=90;
  public static int pc=0;
  public static int user=0;
  public static Font normalFont;
  public static Font scoreFont;
  public static int SCORES_FONT_SIZE= 30;
  public static int MAX_SCORES=5;
  public static boolean gameOver=false;
  public static int countgames=0;
  public static int countpcscore=0;
  public static int countuserscore=0;
  public static int[] scoresofPC= new int[10];
  public static int[] scoresofuser=new int[10];
  public static int boxWidth=200;
  public static int boxHeight=0;
  public static int k=0;
  public static boolean scoresDisplayed=false;
  public static int MAX_BALL_VELOCITY_X=6;
  public static int MIN_BALL_VELOCITY_X=3;
  public static int MAX_BALL_VELOCITY_Y=5;
  public static int MIN_BALL_VELOCITY_Y=-5;
  public static Random rand = new Random();
  
  //main method
  public static void main (String[] args){
    BallVelocityX= 0;
    BallVelocityY=0;
    DrawingPanel panel=new DrawingPanel (PANEL_WIDTH,PANEL_HEIGHT);
    Graphics g= panel.getGraphics();
    normalFont = g.getFont();
    ballX= 250;
    ballY= 200;
    drawBall(g,BALL_COLOR);
    drawPaddle(g,PADDLE_COLOR);
    drawComputerPaddle(g,PADDLE_COLOR);
    normalFont = g.getFont();
    scoreFont = new Font(normalFont.getName(),normalFont.getStyle(),SCORES_FONT_SIZE);
    startGame(panel,g);
  }
  
  //startGame method
  public static void startGame(DrawingPanel panel, Graphics g){
    while(true){
      g.setColor(Color.BLACK);
      g.setFont(normalFont);
      g.drawString( "Project 3 written by Melissa Gonzalez Montes", 10, 15);
      handleKeys(panel,g);
      if(!gameOver && !scoresDisplayed) {
        moveBall(g);
        detectHit();
        pcdetectHit();
        moveComputerPaddle(g);
        drawScores(g,COLOR_SCORE);
      }
      panel.sleep(50);
    }
  }
  
  //drawBall method
  public static void drawBall(Graphics g, Color c){
    g.setColor(BALL_COLOR);
    g.fillOval(ballX-(BALL_SIZE/2),ballY-(BALL_SIZE/2),BALL_SIZE,BALL_SIZE);
  }
  
  //moveBall method IS INSIDE startGame LOOP
  public static void moveBall(Graphics g){
    g.setColor(BACKGROUND_COLOR);
    g.fillOval(ballX-(BALL_SIZE/2),ballY-(BALL_SIZE/2),BALL_SIZE,BALL_SIZE);
    ballX=ballX+BallVelocityX;
    ballY= ballY+BallVelocityY;
    g.setColor(BALL_COLOR);
    g.fillOval(ballX-(BALL_SIZE/2),ballY-(BALL_SIZE/2),BALL_SIZE,BALL_SIZE);
    
    if (ballX>PANEL_WIDTH-BALL_SIZE){
      resetBall(g);
      drawScores(g,BACKGROUND_COLOR);
      addToComputerScore(g);
      drawScores(g,COLOR_SCORE);
    }else if (ballX<0){
      g.setColor(BACKGROUND_COLOR);
      g.fillOval(ballX-(BALL_SIZE/2),ballY-(BALL_SIZE/2),BALL_SIZE,BALL_SIZE);
      ballX=245;
      ballY=195;
      BallVelocityX=-BallVelocityX;
      BallVelocityY=+BallVelocityY;
      drawScores(g,BACKGROUND_COLOR);
      addToUserScore(g);
      drawScores(g,COLOR_SCORE);
    }
    drawPaddle(g,PADDLE_COLOR);
  }
  
  //resetBall method INSIDE handleKeys
  public static void resetBall(Graphics g){
    g.setColor(BACKGROUND_COLOR);
    g.fillOval(ballX-(BALL_SIZE/2),ballY-(BALL_SIZE/2),BALL_SIZE,BALL_SIZE);
    ballX=245;
    ballY=195;
    g.setColor(BALL_COLOR);
    g.fillOval(ballX-(BALL_SIZE/2),ballY-(BALL_SIZE/2),BALL_SIZE,BALL_SIZE);
    BallVelocityX= rand.nextInt(MAX_BALL_VELOCITY_X - MIN_BALL_VELOCITY_X + 1) + MIN_BALL_VELOCITY_X;
    BallVelocityY= rand.nextInt(MAX_BALL_VELOCITY_Y - MIN_BALL_VELOCITY_Y + 1) + MIN_BALL_VELOCITY_Y;
  }
  
  //handleKeys method INSIDE startGame
  public static void handleKeys(DrawingPanel panel, Graphics g) {
    int keyCode = panel.getKeyCode();
    if (gameOver==true && keyCode==KEY_NEW_GAME ){
      newGame(g);
    }
    if (keyCode==KEY_SCORE){
      if (!scoresDisplayed){
        drawScore(g);
        scoresDisplayed=true;
      }
      else{
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(PANEL_WIDTH/2 - boxWidth/2, PANEL_HEIGHT/2 - boxHeight/2 , boxWidth+1, boxHeight+1);
        scoresDisplayed=false;
      }
    }
    if (keyCode == KEY_SPACE)
      resetBall(g);
    else if (keyCode == UP_ARROW)
      movePaddle(g,-PADDLE_MOVE_AMOUNT);
    else if (keyCode == DOWN_ARROW)
      movePaddle(g,PADDLE_MOVE_AMOUNT);
  }
  //drawScore inside HandleKeys
  public static void drawScore(Graphics g){
       boxHeight = 15 * (k+1);
       g.setFont(normalFont);
       g.setColor(Color.BLACK);
       g.drawRect(PANEL_WIDTH/2 - boxWidth/2, PANEL_HEIGHT/2 - boxHeight/2 , boxWidth, boxHeight);
       g.drawString("Computer (" + countpcscore + ")", PANEL_WIDTH/2 - boxWidth/2 + 10, PANEL_HEIGHT/2 - boxHeight/2 + 13);
       g.drawString("User (" + countuserscore + ")", PANEL_WIDTH/2 + 18, PANEL_HEIGHT/2 - boxHeight/2 + 13);
       
       for(int i=0; i<k; i++) {
         g.drawString(Integer.toString(scoresofPC[i]), PANEL_WIDTH/2 - boxWidth/4, PANEL_HEIGHT/2 - boxHeight/2 + 13 + 15*(i+1));
         g.drawString(Integer.toString(scoresofuser[i]), PANEL_WIDTH/2 + boxWidth/4, PANEL_HEIGHT/2 - boxHeight/2 + 13 + 15*(i+1));
       }
  }
   //newGame inside HandleKeys
  public static void newGame(Graphics g){
    g.setFont(scoreFont);
      g.setColor(BACKGROUND_COLOR);
      g.drawString("You have lost", 150, 100);
      g.setColor(BACKGROUND_COLOR);
      g.drawString( "You won!", 190, 100);
      drawScores(g,BACKGROUND_COLOR);
      System.out.println("size of scoresPC: " + scoresofPC.length + "countGames: " + countgames);
      scoresofPC[countgames]=pc;
      scoresofuser[countgames]=user;
      pc= 0;
      user=0;
      drawScores(g,COLOR_SCORE);
      gameOver=false;
      resetBall(g);
  }
  
  //drawPaddle method 
  public static void drawPaddle(Graphics g, Color c){
    g.setColor(c);
    g.drawLine(paddleX, paddleY, paddleX, paddleY+PADDLE_LENGTH);
  }
  
  //movePaddle method INSIDE handleKeys which uses keyCode
  public static void movePaddle(Graphics g, int deltaY){
    
    g.setColor(BACKGROUND_COLOR);
    g.drawLine(paddleX,paddleY,paddleX,paddleY+PADDLE_LENGTH);
    g.setColor(PADDLE_COLOR);
    paddleY = paddleY+deltaY;
    g.drawLine(paddleX, paddleY, paddleX, paddleY+PADDLE_LENGTH);
    
    if (paddleY+PADDLE_LENGTH>PANEL_HEIGHT){
      paddleY=350;
    }
    if (paddleY<0){
      paddleY=0;
    }
  }
  
  //method detectHit is INSIDE startGame
  public static void detectHit(){
    if( (BallVelocityX>0) && (ballY+(BALL_SIZE/2)>paddleY) && (ballY-(BALL_SIZE/2)<paddleY+PADDLE_LENGTH) && (ballX+(BALL_SIZE/2)>=paddleX) && (ballX-(BALL_SIZE/2)<paddleX)){
      BallVelocityX= -BallVelocityX;
      BallVelocityY= BallVelocityY+(ballY-(paddleY + PADDLE_LENGTH/2))/8;
    }
    if (ballY>395 || ballY<5) {
      BallVelocityY= -BallVelocityY;
    }
  }
  //drawComputerPaddle method 
  public static void drawComputerPaddle(Graphics g, Color c){
    g.setColor(c);
    g.drawLine(COMPUTERPADX, paddleY, COMPUTERPADX, paddleY+COMPUTERPADDLE_LENGTH);
  }
  //moveComputerPaddle method INSIDE startGame which uses keyCode
  public static void moveComputerPaddle(Graphics g){
    if (COMPUTERPADY+(COMPUTERPADDLE_LENGTH/2)<ballY){
      g.setColor(BACKGROUND_COLOR);
      g.drawLine(COMPUTERPADX,COMPUTERPADY,COMPUTERPADX,COMPUTERPADY+COMPUTERPADDLE_LENGTH);
      COMPUTERPADY=COMPUTERPADY+MAX_COMPUTER_PADDLE_MOVE;
      g.setColor(PADDLE_COLOR);
      g.drawLine(COMPUTERPADX, COMPUTERPADY, COMPUTERPADX, COMPUTERPADY+COMPUTERPADDLE_LENGTH);
    }
    if (COMPUTERPADY+(COMPUTERPADDLE_LENGTH/2)>ballY){
      g.setColor(BACKGROUND_COLOR);
      g.drawLine(COMPUTERPADX,COMPUTERPADY,COMPUTERPADX,COMPUTERPADY+COMPUTERPADDLE_LENGTH);
      COMPUTERPADY=COMPUTERPADY-MAX_COMPUTER_PADDLE_MOVE;
      g.setColor(PADDLE_COLOR);
      g.drawLine(COMPUTERPADX, COMPUTERPADY, COMPUTERPADX, COMPUTERPADY+COMPUTERPADDLE_LENGTH);
    }
    if (COMPUTERPADY+PADDLE_LENGTH>PANEL_HEIGHT){
      COMPUTERPADY=350;
    }
    if (COMPUTERPADY<0){
      COMPUTERPADY=0;
    }
  }
  //method pcdetectHit is INSIDE startGame
  public static void pcdetectHit(){
    if((BallVelocityX <0) && (ballY-(BALL_SIZE/2)>COMPUTERPADY) && (ballY+(BALL_SIZE/2)<(COMPUTERPADY+COMPUTERPADDLE_LENGTH)) && (ballX-(BALL_SIZE/2)<COMPUTERPADX) && (ballX+(BALL_SIZE/2)>COMPUTERPADX)){
      BallVelocityX= -BallVelocityX;
    }
  }
  public static void drawScores(Graphics g, Color c){
    g.setFont(scoreFont);
    String st = "" + pc;
    String u=""+user;
    g.setColor(c);
     g.drawString( st, COMPUTER_SCOREX, SCORES_Y);
     g.drawString( u, USER_SCOREX, SCORES_Y);
}
  public static void addToComputerScore(Graphics g){
    pc++; 
    if (pc==5 || user==5){
      scoresofPC[k] = pc;
      scoresofuser[k] = user;
      k++;
      if(k == 10){
        k = 0;
      }
      else{
        resetBall(g);
        System.out.println("incrementing countgames ");
        countgames++;
        ballX= 245;
        ballY= 195;
        BallVelocityX= 0;
        BallVelocityY=0;
        gameOver=true;
      }
    }
    if (pc==5){
      g.setFont(scoreFont);
      g.setColor(Color.RED);
      g.drawString("You have lost", 150, 100);
      countpcscore++;
    }
  }
  public static void addToUserScore(Graphics g){
    user++;
    if (pc==5 || user==5){
      scoresofPC[k] = pc;
      scoresofuser[k] = user;
      k++;
      if(k == 10){
        k = 0;
      }else{
        resetBall(g);
        System.out.println("incrementing countgames ");
        countgames++;
        ballX= 245;
        ballY= 195;
        BallVelocityX= 0;
        BallVelocityY=0;
        gameOver=true;
      }
    }
    if (user==5){
      g.setColor(Color.BLUE);
      g.setFont(scoreFont);
      g.drawString( "You won!", 190, 100);
      countuserscore++;
    }
  }
}
  
