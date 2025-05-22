import java.awt.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

public class World{
  public static void main(String[] args) {
//    try {
//      BufferedReader br = new BufferedReader(new FileReader("ScoreCount.txt"));
//      int s = Integer.parseInt(br.readLine());
//      winCount = s;
//    } catch (Exception e) {
//      System.out.println("faisdlfjl");
//    }
    run();
  }
  public static int winCount = 0;

  public static void run() {
    Display display = new Display(1000, 625);
    try{
      Thread.sleep(500);
    }
    catch(Exception e){

    }
    try {
      Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));

      clip.open(AudioSystem.getAudioInputStream(new File("assets/stereoMadness.wav")));

      clip.setFramePosition(0);
      clip.start();
    }
    catch(Exception e){

    }
    display.run();
  }

  private final int groundShift = 120;
  private final int groundWidth = 200;
  private ArrayList<Sprite> sprites;
  private ArrayList<SpikeSprite> spikeSprites;
  private ArrayList<Sprite> groundSprites;
  private ArrayList<Sprite> imageSprites;
  private int totGroundSprites;
  private int width;
  private int height;
  private Color background;
  private Color floor;
  private int currColor;
  private int frameCount;
  private int frameShift;
  private Long millis;
  private int oldWins = winCount;

  public World(int w, int h){
    width = w;
    height = h;
    sprites = new ArrayList<Sprite>();
    spikeSprites = new ArrayList<>();
    groundSprites = new ArrayList<>();
    imageSprites = new ArrayList<>();
    double dir;
    background = new Color(50, 50, 255);
    floor = new Color(0,0,100);
    currColor = 3;
    frameShift = 0;




  sprites.add(new PlayerSprite(0 , height-50-groundShift, 50,50,"assets/player/p2.png"));
//
//first jumps
  spikeSprites.add(new SpikeSprite(900, height-50-groundShift, 50, 50, "assets/spike1.png"));
  spikeSprites.add(new SpikeSprite(1615, height-30-groundShift, 45, 30, "assets/spike2.png"));
  spikeSprites.add(new SpikeSprite(1655, height-50-groundShift, 50, 50, "assets/spike1.png"));
  //spikes + platform jumps
  spikeSprites.add(new SpikeSprite(2325, height-50-groundShift, 50, 50, "assets/spike1.png"));
  spikeSprites.add(new SpikeSprite(2375, height-50-groundShift, 50, 50, "assets/spike1.png"));
  sprites.add(new Sprite( 2425, height-50-groundShift, 50, 50, "assets/block1.png"));
  sprites.add(new Sprite( 2650, height-50-groundShift, 50, 50, "assets/blockTopEdgeTransp.png"));
  sprites.add(new Sprite( 2650, height-50-groundShift-50+2, 50, 50, "assets/block1.png"));
  sprites.add(new Sprite( 2875, height-50-groundShift, 50, 50, "assets/blockPillarTransp.png"));
  sprites.add(new Sprite( 2875, height-50-groundShift-50, 50, 50, "assets/blockTopEdgeTransp.png"));
  sprites.add(new Sprite( 2875 , height-50-groundShift-100+2 , 50, 50, "assets/block1.png"));

  //2 block jump
  spikeSprites.add(new SpikeSprite(3700, height-50-groundShift, 50, 50, "assets/spike1.png"));
  spikeSprites.add(new SpikeSprite(3750, height-50-groundShift, 50, 50, "assets/spike1.png"));

  //long platform 1
  sprites.add(new Sprite(4000, height-50-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
  for(int i = 4050; i <= 4250; i+= 50)
    sprites.add(new Sprite(i, height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
  sprites.add(new Sprite(4300 , height-50-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));

  //long platform 2+spike
  sprites.add(new Sprite(4500 , height-50-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
  for(int i = 4550; i <= 4900; i += 50)
    sprites.add(new Sprite(i , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
  spikeSprites.add(new SpikeSprite(4700, height-100-groundShift, 50, 50, "assets/spike1.png"));
  sprites.add(new Sprite(4950 , height-50-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));

  //2 block high platform + spike
    sprites.add(new Sprite(5150 , height-50-groundShift, 50, 50, "assets/blockLeftTransp.png"));
    imageSprites.add(new Sprite(5200 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
    for(int i = 5250; i <= 5600; i += 50)
      imageSprites.add(imageSprites.size()-1,new Sprite(i , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
    sprites.add(new Sprite(5650 , height-50-groundShift, 50, 50, "assets/blockRightTransp.png"));
    sprites.add(new Sprite(5150 , height-100-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
    for(int i = 5200; i <= 5600; i += 50)
      sprites.add(new Sprite(i , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
    sprites.add(new Sprite(5650 , height-100-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));
    spikeSprites.add(new SpikeSprite(5450, height-150-groundShift, 50, 50, "assets/spike1.png"));

    //slab jumps
    int k = 150;
    for(int i = 5875; i <=6800; i += 225) {
        sprites.add(new Sprite(i, height - groundShift - k, 50, 21, "assets/slab.png"));
        k += 50;
    }

    //huge platform
    sprites.add(new Sprite(7025 , height-350-groundShift, 50, 50, "assets/blockLeftEdgeTransp.png"));
    for(int i = 7075; i <= 8075; i+= 50)
      sprites.add(new Sprite(i , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
    sprites.add(new Sprite(8075 , height-350-groundShift, 50, 50, "assets/blockRightCornerTransp.png"));
    for(int j = 50; j<=  300; j += 50){
      sprites.add(new Sprite(7075 , height-j-groundShift, 50, 50, "assets/blockLeftTransp.png"));
      for(int i = 7125; i<= 8075; i+= 50){
        imageSprites.add(new Sprite(i , height-j-groundShift, 50, 50, "assets/blockTransp.png"));
      }
    }

    //jumps onto slabs over spikes
    for(int i = 7275; i <= 7425; i += 50)
      spikeSprites.add(new SpikeSprite(i, height-400-groundShift, 50, 50, "assets/spike1.png"));
    for(int i : new int[]{7325,7375}) sprites.add(new Sprite(i, height-440-groundShift , 50, 21, "assets/slab.png"));
    for(int i = 7700; i <= 7850; i += 50)
      spikeSprites.add(new SpikeSprite(i, height-400-groundShift, 50, 50, "assets/spike1.png"));
    for(int i : new int[]{7750,7800}) sprites.add(new Sprite(i, height-440-groundShift , 50, 21, "assets/slab.png"));

    //1 block drop on platform
    for(int i = 8125; i <= 8625; i+= 50)
      sprites.add(new Sprite(i , height-300-groundShift, 50, 50, "assets/blockTopTransp.png"));
    for(int j = 50; j<=  250; j += 50) {
      for (int i = 8125; i <= 8625; i += 50) {
        imageSprites.add(new Sprite(i, height - j - groundShift, 50, 50, "assets/blockTransp.png"));
      }
    }

    //underneath slabs+spikes
    for(int i = 8275; i <= 8425; i+= 50){
      sprites.add(new Sprite(i, height-381-groundShift, 50, 21, "assets/slab.png"));
      spikeSprites.add(new SpikeSprite(i, height-431-groundShift, 50, 50, "assets/spike1.png"));
    }

    //1 block ascension on platform+spike
    sprites.add(new Sprite(8675 , height-350-groundShift, 50, 50+1, "assets/blockLeftCornerTransp.png"));
    for(int i = 8725; i <= 8825; i+= 50)
      sprites.add(new Sprite(i , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
    sprites.add(new Sprite(8875 , height-350-groundShift, 50, 50+1, "assets/blockRightCornerTransp.png"));
    for(int j = 50; j<=  300; j += 50){
      for(int i = 8675; i<= 8875; i+= 50){
        if(j == 300 && i == 8875)imageSprites.add(new Sprite(i , height-j-groundShift, 50, 50, "assets/blockRightTransp.png"));
          else imageSprites.add(new Sprite(i , height-j-groundShift, 50, 50, "assets/blockTransp.png"));
      }
    }
    spikeSprites.add(new SpikeSprite(8875, height-400-groundShift, 50, 50, "assets/spike1.png"));

    //2 block drop+spike+ledge
    for(int i = 8925; i <= 9225; i+= 50)
      sprites.add(new Sprite(i , height-250-groundShift, 50, 50, "assets/blockTopTransp.png"));
    spikeSprites.add(new SpikeSprite(8925, height-300-groundShift, 50, 50, "assets/spike1.png"));
    for(int j = 50; j<=  200; j += 50) {
      imageSprites.add(new Sprite(9225, height - j - groundShift, 50, 50, "assets/blockRightTransp.png"));
      for (int i = 8925; i <= 9175; i += 50) {
        imageSprites.add(new Sprite(i, height - j - groundShift, 50, 50, "assets/blockTransp.png"));
      }
    }
    for(int i = 9275; i <= 9425; i += 50){
      sprites.add(new Sprite(i , height-250-groundShift, 50, 50, "assets/blockLedgeTransp.png"));
    }
    sprites.add(new Sprite(9475 , height-250-groundShift, 50, 50, "assets/blockRightEdgeTransp.png"));

    //descending slab platforms + spikes
    for(int i = 9625; i <= 9775; i+=50){
      sprites.add(new Sprite(i, height-250-groundShift, 50, 21, "assets/slab.png"));
    }
    spikeSprites.add(new SpikeSprite(9775, height-300-groundShift, 50, 50, "assets/spike1.png"));
    for(int i = 9850; i <= 10150; i+=50){
      sprites.add(new Sprite(i, height-175-groundShift, 50, 21, "assets/slab.png"));
    }
    spikeSprites.add(new SpikeSprite(10150, height-225-groundShift, 50, 50, "assets/spike1.png"));
    for(int i = 10225; i <= 10425; i+=50){
      sprites.add(new Sprite(i, height-100-groundShift, 50, 21, "assets/slab.png"));
    }

    //slab jumps + spikes
    int m = 150;
    for(int i = 10625; i <= 11750; i += 225) {
      sprites.add(new Sprite(i, height - groundShift - m, 50, 21, "assets/slab.png"));
      m += 50;
    }
    spikeSprites.add(new SpikeSprite(11750, height-m-groundShift, 50, 50, "assets/spike1.png"));


    //ledge+tunnel
    sprites.add(new Sprite(11625, height - groundShift - 225, 50, 50, "assets/blockLeftEdgeTransp.png"));
    for(int i = 11675; i <= 11925; i+= 50){
      sprites.add(new Sprite(i, height - groundShift - 225, 50, 50, "assets/blockLedgeTransp.png"));
    }
    sprites.add(new Sprite(11975 , height-400-groundShift, 50, 50, "assets/blockLeftCornerFlippedTransp.png"));
    for(int i = 12025; i <= 12175; i+= 50) {
      imageSprites.add(new Sprite(i , height-400-groundShift, 50, 50, "assets/blockBottomTransp.png"));
    }
    sprites.add(new Sprite(12225 , height-400-groundShift, 50, 50, "assets/blockRightCornerFlippedTransp.png"));
    for(int j = 450; j <= 800; j+=50){
      imageSprites.add(new Sprite(11975 , height-j-groundShift, 50, 50, "assets/blockLeftTransp.png"));
        imageSprites.add(new Sprite(12225 , height-j-groundShift, 50, 50, "assets/blockRightTransp.png"));
        for(int i = 12025; i <= 12175; i+= 50){
        imageSprites.add(new Sprite(i , height-j-groundShift, 50, 50, "assets/blockTransp.png"));
      }
    }
    for(int i = 11975; i <= 20000; i+= 50){
      sprites.add(new Sprite(i, height - groundShift - 225, 50, 50, "assets/blockTopTransp.png"));
    }
    for(int j = 25; j <= 175; j+= 50){
      sprites.add(new Sprite(11975 , height-j-groundShift, 50, 50, "assets/blockLeftTransp.png"));
      for(int i = 12025; i <= 20000; i+= 50){
        imageSprites.add(new Sprite(i , height-j-groundShift, 50, 50, "assets/blockTransp.png"));
      }
    }

//    sprites.add(new Sprite(7025 , height-350-groundShift, 50, 50, "assets/blockLeftEdgeTransp.png"));
//    for(int i = 7075; i <= 8075; i+= 50)
//      sprites.add(new Sprite(i , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(8075 , height-350-groundShift, 50, 50, "assets/blockRightCornerTransp.png"));
//    for(int j = 50; j<=  300; j += 50){
//      sprites.add(new Sprite(7075 , height-j-groundShift, 50, 50, "assets/blockLeftTransp.png"));
//      for(int i = 7125; i<= 8075; i+= 50){
//        imageSprites.add(new Sprite(i , height-j-groundShift, 50, 50, "assets/blockTransp.png"));
//      }
//    }


    totGroundSprites = 6;
    for(int i = 0; i < totGroundSprites; i++){
      groundSprites.add(new Sprite(groundWidth*i -50, height-groundShift, groundWidth, groundShift, "assets/grounds/ground01.png"));
    }
  }
  
  public void stepAll() {
    sprites.get(0).step(this);
    for (int i = 1; i < sprites.size(); i++) {
      Sprite s = sprites.get(i);
      if(s.getLeft()+s.getWidth() < sprites.get(0).getLeft()-300){
        //System.out.println("removing " + sprites.get(i).getClass().getName());
        sprites.remove(i);
        i--;
      }
    }
    for(int i = 0; i < spikeSprites.size(); i++){
      Sprite s = spikeSprites.get(i);
      if(s.getLeft()+s.getWidth() < sprites.get(0).getLeft()-300){
        //System.out.println("removing " + sprites.get(i).getClass().getName());
        spikeSprites.remove(i);
        i--;
      }
    }
    for(int i = 0; i < imageSprites.size(); i++){
      Sprite s = imageSprites.get(i);
      if(s.getLeft()+s.getWidth() < sprites.get(0).getLeft()-300){
        imageSprites.remove(i);
        i--;
      }
    }
    if(sprites.size() == 1 && imageSprites.size() == 0){

      System.exit(0);
    }
    if(groundSprites.get(0).getLeft()+groundSprites.get(0).getWidth() < sprites.get(0).getLeft()-100) {
      groundSprites.remove(0);
      groundSprites.add(new Sprite(groundWidth*totGroundSprites-50,height-groundShift, groundWidth,120, "assets/grounds/ground01.png"));
      totGroundSprites++;
    }
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public int getNumSprites()
  {
    return sprites.size();
  }
  public Sprite getSprite(int index)
  {
    return sprites.get(index);
  }
  public Sprite removeSprite(int index){
    Sprite sprite = sprites.remove(index);
    return sprite;
  }

  public int getNumSpikeSprites(){return spikeSprites.size();}
  public Sprite getSpikeSprite(int index){return spikeSprites.get(index);}
  public Sprite removeSprikeSprite(int index){return spikeSprites.remove(index);}

  public void mouseClicked(int x, int y)
  {
    //System.out.println("mouseClicked:  " + x + ", " + y);
  }
  
  public void keyPressed(int key)
  {
    if(key == 32){
      PlayerSprite sprite = (PlayerSprite)sprites.get(0);
      sprite.jump();
      //endTimer();
    }
  }

  public void startTimer(){
    millis = System.currentTimeMillis();
  }
  public void endTimer(){
    System.out.println(System.currentTimeMillis()-millis);
  }
  public Long getEndTimer(){return System.currentTimeMillis()-millis;}
  
  public void keyReleased(int key) {
    //System.out.println("keyReleased:  " + key);
  }
  
  public String getTitle()
  {
    return "BlockDashV2";
  }

  public int getFrameShift(){return frameShift;}

  public void setFrameShift(int n){
    frameShift = n;
  }
  public void incFrameShift(int n){frameShift += n;}

  public void paintComponent(Graphics g) {

    g.setColor(background);
    g.fillRect(0, 0, width, height);
    g.drawImage(Display.getImage("assets/backgrounds/bg01-hd.png"),0, 0, null);
    g.setColor(floor);
    g.fillRect(0, height-groundShift+frameShift, width, height+frameShift);
    g.setColor(new Color(255,255,255));
    g.setFont(new Font(Font.SANS_SERIF,0, 50));
    g.drawString("wins: " + winCount,50,50);
    
      for (int i = 0; i < sprites.size(); i++) {
      Sprite sprite = sprites.get(i);
//      LineSegment[] hbox = sprite.getHitBox();
//
//      for(int j = 0; j < hbox.length; j++){
//        g.drawLine((int)(hbox[j].getX1()- sprites.get(0).getLeft() + 300), (int)(hbox[j].getY1()+frameShift) , (int)(hbox[j].getX2()- sprites.get(0).getLeft() + 300) ,(int)(hbox[j].getY2()+frameShift));
//      }
//        if(sprites.get(0).getLeft()+sprites.get(0).getWidth() < 300){
//          g.drawImage(Display.getImage(sprite.getImage()),
//                  (int) (sprite.getLeft()), (int) (sprite.getTop() + frameShift),
//                  sprite.getWidth(), sprite.getHeight(), null);
//        }
//        else {
          g.drawImage(Display.getImage(sprite.getImage()),
                  (int) (sprite.getLeft() - sprites.get(0).getLeft() + 300), (int) (sprite.getTop() + frameShift),
                  sprite.getWidth(), sprite.getHeight(), null);
        //}
    }
    for (int i = 0; i < spikeSprites.size(); i++) {
      Sprite sprite = spikeSprites.get(i);
//      LineSegment[] hbox = spikeSprite.getHitBox();
//
//      for(int j = 0; j < hbox.length; j++){
//        g.drawLine((int)(hbox[j].getX1()- sprites.get(0).getLeft() + 300), (int)(hbox[j].getY1()+frameShift) , (int)(hbox[j].getX2()- sprites.get(0).getLeft() + 300) ,(int)(hbox[j].getY2()+frameShift));
//      }
//      if(sprites.get(0).getLeft()+sprites.get(0).getWidth() < 300){
//        g.drawImage(Display.getImage(sprite.getImage()),
//                (int) (sprite.getLeft()), (int) (sprite.getTop() + frameShift),
//                sprite.getWidth(), sprite.getHeight(), null);
//      }
//      else {
        g.drawImage(Display.getImage(sprite.getImage()),
                (int) (sprite.getLeft() - sprites.get(0).getLeft() + 300), (int) (sprite.getTop() + frameShift),
                sprite.getWidth(), sprite.getHeight(), null);
      //}
    }
    for(int i = 0; i < groundSprites.size(); i++){
      Sprite sprite = groundSprites.get(i);
//      if(sprites.get(0).getLeft()+sprites.get(0).getWidth() < 300){
//        g.drawImage(Display.getImage(sprite.getImage()),
//                (int)(sprite.getLeft()), (int)sprite.getTop()+frameShift,
//                sprite.getWidth(), sprite.getHeight(), null);
//      }
//      else{
        g.drawImage(Display.getImage(sprite.getImage()),
                (int)(sprite.getLeft() - sprites.get(0).getLeft() +100), (int)sprite.getTop()+frameShift,
                sprite.getWidth(), sprite.getHeight(), null);
      //}
    }
    for(int i = 0; i < imageSprites.size(); i++){
      Sprite sprite = imageSprites.get(i);
//      if(sprites.get(0).getLeft()+sprites.get(0).getWidth() < 300){
//        g.drawImage(Display.getImage(sprite.getImage()),
//                (int)(sprite.getLeft()), (int)sprite.getTop()+frameShift,
//                sprite.getWidth(), sprite.getHeight(), null);
//      }
//      else{
      g.drawImage(Display.getImage(sprite.getImage()),
              (int)(sprite.getLeft() - sprites.get(0).getLeft() +300), (int)sprite.getTop()+frameShift,
              sprite.getWidth(), sprite.getHeight(), null);
    }
    if(sprites.get(0).getLeft() > 12000){
      currColor = 4;
    }
    if(sprites.get(0).getLeft() > 15000 && sprites.get(0).getLeft() < 15050){
//      try {
//        File in = new File("ScoreCount.txt");
//        in.delete();
//        in.createNewFile();
//        FileWriter f = new FileWriter(in);
//        f.append(Integer.toString(winCount+ 1));
//      }
//      catch(Exception e){}
    }
    if(frameCount % 20 == 0) {
      if (currColor == 1) {
        background = new Color(background.getRed()-3, background.getGreen()+3, background.getBlue());
        floor = new Color(floor.getRed()-1, floor.getGreen()+1, floor.getBlue());
        if(background.getGreen() >= 255-3) currColor = 2;
      }
      else if (currColor == 2) {
        background = new Color(background.getRed(), background.getGreen()-3, background.getBlue()+3);
        floor = new Color(floor.getRed(), floor.getGreen()-1, floor.getBlue()+1);
        if(background.getBlue() >= 255-3) currColor = 3;
      }
      else if(currColor == 3){
        background = new Color(background.getRed()+3, background.getGreen(), background.getBlue() - 3);
        floor = new Color(floor.getRed()+1, floor.getGreen(), floor.getBlue()-1);
        if(background.getRed() >= 255-3) currColor = 1;
      }
      else{
        int backRed = background.getRed();
        int backGreen = background.getGreen();
        int backBlue = background.getBlue();
        if(backRed > 1) backRed-=2;
        if(backGreen > 3) backGreen-=4;
        if(backBlue > 1) backBlue-=2;
        int fRed = floor.getRed();
        int fGreen = floor.getGreen();
        int fBlue = floor.getBlue();
        if(fBlue > 1) fBlue-=2;
        if(fGreen > 3) fGreen-=4;
        if(fBlue >1) fBlue-=2;
        background = new Color(backRed, backGreen, backBlue);
        floor = new Color(fRed, fGreen, fBlue);

      }
    }
    frameCount++;
  }


//    //normal sprites
//    sprites.add(new Sprite(Math.random() * (width - 50),
//                           Math.random() * (height - 50), 50, 50, "square.png"));
//    sprites.add(new Sprite(Math.random() * (width - 50),
//                           Math.random() * (height - 50), 50, 50, "square.png"));
//    //mobile sprites
//    dir = Math.random() * 2 * Math.PI;
//    sprites.add(new MobileSprite(Math.random() * (width - 50),
//                                 Math.random() * (height - 50), 50, 50, "circle.png",
//                                 Math.cos(dir), Math.sin(dir)));
//    dir = Math.random() * 2 * Math.PI;
//    sprites.add(new MobileSprite(Math.random() * (width - 50),
//                                 Math.random() * (height - 50), 50, 50, "circle.png",
//                                 Math.cos(dir), Math.sin(dir)));
//    //heavy sprites
//    dir = Math.random() * 2 * Math.PI;
//    sprites.add(new HeavySprite(Math.random() * (width - 50),
//                                 Math.random() * (height - 50), 50, 50, "triangle.png",
//                                 Math.cos(dir), Math.sin(dir)));
//    dir = Math.random() * 2 * Math.PI;
//    sprites.add(new HeavySprite(Math.random() * (width - 50),
//                                 Math.random() * (height - 50), 50, 50, "triangle.png",
//                                 Math.cos(dir), Math.sin(dir)));




  //spikeSprites.add(new SpikeSprite(500, height-50-groundShift, 50, 50, "assets/spike1.png"));
//
//    spikeSprites.add(new SpikeSprite(1150, height-30-groundShift, 45, 30, "assets/spike2.png"));
//    spikeSprites.add(new SpikeSprite(1195, height-50-groundShift, 50, 50, "assets/spike1.png"));
//
//
//    spikeSprites.add(new SpikeSprite(1925, height-50-groundShift, 50, 50, "assets/spike1.png"));
//    spikeSprites.add(new SpikeSprite(1975, height-50-groundShift, 50, 50, "assets/spike1.png"));
//    sprites.add(new Sprite( 2025, height-50-groundShift, 50, 50, "assets/block1.png"));
//    sprites.add(new Sprite( 2225, height-50-groundShift, 50, 50, "assets/blockTopEdgeTransp.png"));
//    sprites.add(new Sprite( 2225, height-50-groundShift-50+2, 50, 50, "assets/block1.png"));
//    sprites.add(new Sprite( 2425, height-50-groundShift, 50, 50, "assets/blockPillarTransp.png"));
//    sprites.add(new Sprite( 2425, height-50-groundShift-50, 50, 50, "assets/blockTopEdgeTransp.png"));
//    sprites.add(new Sprite( 2425, height-50-groundShift-100+2 , 50, 50, "assets/block1.png"));
//        spikeSprites.add(new SpikeSprite(1850, height-50-groundShift, 50, 50, "assets/spike1.png"));
//    spikeSprites.add(new SpikeSprite(1900, height-50-groundShift, 50, 50, "assets/spike1.png"));
//    sprites.add(new Sprite( 2025, height-50-groundShift, 50, 50, "assets/block1.png"));
//    sprites.add(new Sprite( 2225, height-50-groundShift, 50, 50, "assets/blockTopEdgeTransp.png"));
//    sprites.add(new Sprite( 2225, height-50-groundShift-50+2, 50, 50, "assets/block1.png"));
//    sprites.add(new Sprite( 2425, height-50-groundShift, 50, 50, "assets/blockPillarTransp.png"));
//    sprites.add(new Sprite( 2425, height-50-groundShift-50, 50, 50, "assets/blockTopEdgeTransp.png"));
//    sprites.add(new Sprite( 2425, height-50-groundShift-100+2 , 50, 50, "assets/block1.png"));
//
//    spikeSprites.add(new SpikeSprite(3300, height-50-groundShift, 50, 50, "assets/spike1.png"));
//    spikeSprites.add(new SpikeSprite(3350, height-50-groundShift, 50, 50, "assets/spike1.png"));
//
//    sprites.add(new Sprite(3700, height-50-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
//    sprites.add(new Sprite(3750, height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(3800, height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(3850, height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(3900, height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(3950 ,  height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4000 , height-50-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));
//
//
//    sprites.add(new Sprite(4200 , height-50-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
//    sprites.add(new Sprite(4250 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4300 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4350 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4400 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    spikeSprites.add(new SpikeSprite(4400, height-100-groundShift, 50, 50, "assets/spike1.png"));
//    sprites.add(new Sprite(4450 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4500 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4550 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4600 , height-50-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));
//
//    sprites.add(new Sprite(4800 , height-50-groundShift, 50, 50, "assets/blockLeftTransp.png"));
//    sprites.add(new Sprite(4850 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(4900 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(4950 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(5000 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(5050 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(5100 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(5150 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(5200 , height-50-groundShift, 50, 50, "assets/blockTransp.png"));
//    sprites.add(new Sprite(5250 , height-50-groundShift, 50, 50, "assets/blockRightTransp.png"));
//    sprites.add(new Sprite(4800 , height-100-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
//    sprites.add(new Sprite(4850 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4900 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(4950 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(5000 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(5050 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(5100 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(5150 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(5200 , height-100-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(5250 , height-100-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));
//    spikeSprites.add(new SpikeSprite(5075, height-150-groundShift, 50, 50, "assets/spike1.png"));
//
//    sprites.add(new Sprite(5450 , height-150-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(5675 , height-200-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(5900 , height-250-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(6125 , height-300-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(6350 , height-350-groundShift, 50, 21, "assets/slab.png"));
//
//    sprites.add(new Sprite(6475 , height-350-groundShift, 50, 50, "assets/blockLeftEdgeTransp.png"));
//    sprites.add(new Sprite(6525 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6575 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6625 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6675 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6725 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6775 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6825 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6875 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6925 , height-350-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(6975 , height-350-groundShift, 50, 50 , "assets/blockTopTransp.png"));
}


//    sprites.add(new Sprite(250 , height-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(475 , height-50-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(700 , height-100-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(925 , height-150-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(1150 , height-200-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(1375 , height-250-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(1600 , height-300-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(1825 , height-350-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(2050 , height-400-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(2275 , height-450-groundShift, 50, 21, "assets/slab.png"));
//    sprites.add(new Sprite(2500 , height-500-groundShift, 50, 21, "assets/slab.png"));
//


//    sprites.add(new Sprite(1600 , height-50-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
//    sprites.add(new Sprite(1650 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(1700 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(1750 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(1800 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(1850 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(1900 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(1950 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(2000 , height-50-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));
//
//        sprites.add(new Sprite(2200 , height-50-groundShift-1, 50, 50+1, "assets/blockLeftCornerTransp.png"));
//    sprites.add(new Sprite(2250 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(2300 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(2350 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(2400 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new SpikeSprite(2400, height-100-groundShift, 50, 50, "assets/spike1.png"));
//    sprites.add(new Sprite(2450 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(2500 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(2550 , height-50-groundShift, 50, 50, "assets/blockTopTransp.png"));
//    sprites.add(new Sprite(2600 , height-50-groundShift-1, 50, 50+1, "assets/blockRightCornerTransp.png"));