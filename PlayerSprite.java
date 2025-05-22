public class PlayerSprite extends MobileSprite {

    private final int groundShift= 120;
    private final double jump = -8 ;
    private boolean onGround;
    private boolean onPlatform;
    private double toFrameShift;
    private final double frameShiftVel = .1;
        private int stepCount;
        private boolean startedTimer;


    public PlayerSprite(double left, double top, int width, int height, String image){
        super(left, top, width, height, image, 5.5 , 0);
        onPlatform = false;
        onGround = true;
        stepCount = 0;
        startedTimer = false;
        //System.out.println("onPlatform: " + onPlatform + "  onGround: " + onGround+ " X: " + getLeft() + "  YVel: " + getVY());
    }

    public boolean onGround(){
        return onGround;
    }
    public boolean onPlatform(){return onPlatform;}
    public void land(){
        onGround = true;
    }
    public void jump(){
        if(onGround || onPlatform) {
            onGround = false;
            onPlatform = false;
            //System.out.println("jumping; " + "onPlatform: " + onPlatform + "  onGround: " + onGround+ " X: " + getLeft() + "  YVel: " + getVY());
            setVY(jump);
            setTop(getTop()+getVY());
        }
    }
    @Override public void setVY(double velocity){

        //System.out.println(velocity);
        super.setVY(velocity);

    }
    public boolean standingOn(Sprite sprite){
//        if(sprite instanceof Sprite) System.out.println("not spike");
//        double mid = getLeft()+(getWidth()/2);
        if (((getLeft()+getWidth() >= sprite.getLeft() && getLeft()+getWidth() < sprite.getLeft()) ||
            (getLeft() <= sprite.getLeft() + sprite.getWidth() && getLeft() > sprite.getLeft())) &&
                (getTop() + getHeight() >= sprite.getTop()-(getVY()/2) && getTop() + getHeight() <= sprite.getTop()+getHeight()+ (getVY()/2))) {
            return true;
        }

//        LineSegment[] hitBox = getHitBox();
//        LineSegment[] sHitBox = sprite.getHitBox();
//        double x11 = hitBox[1].getP1().getX();
//        double x12 = hitBox[1].getP2().getX();
//        double y1 = hitBox[1].getP1().getY();
//        double x21 = sHitBox[1].getP1().getX();
//        double x22 = sHitBox[1].getP2().getX();
//        double y2 = sHitBox[1].getP1().getY();
//        //System.out.println(y1 + " , " + y2);
//        if((x11 >= x21 && x11 <= x22) || (x12 >= x21 && x12 <= x22) && (y1 >= y2 && y1 <= y2)) return true;
        return false;
    }

    @Override public void step(World world) {
        //if(getLeft()+getWidth()<200)stepCount++;
//        if(startedTimer){
//            world.endTimer();
////            System.out.println("ended");
//            startedTimer = false;
//
//        }
//        if (!startedTimer) {
//            //System.out.println("started");
//            startedTimer = true;
//            world.startTimer();
//        }
        //if(getLeft()+getWidth() > 476) world.endTimer();
        //System.out.println(getTop());
        setLeft(getLeft() + getVX());
        //if(getLeft() >= 2390 && getLeft() <= 2410) System.out.println("onPlatform: " + onPlatform + "  onGround: " + onGround+ " X: " + getLeft() + "  YVel: " + getVY());
        if(!onGround && !onPlatform){//in air

            //System.out.println("airborne");
            setTop(getTop() + getVY());
            if (getTop() + getHeight() >= world.getHeight()-groundShift) {//checks if landed
                //if(getLeft() > 450) world.endTimer();
                setTop(world.getHeight() - getHeight()-groundShift);
                onGround = true;
                //System.out.println("landed; "+"onPlatform: " + onPlatform + "  onGround: " + onGround+ " X: " + getLeft() + "  YVel: " + getVY());
                setVY(0);
                world.setFrameShift(0);
            }
            else {
                setVY(getVY()+.4);
                //System.out.println(getVY());
            }


            if(getTop()+getHeight() < 2*groundShift-world.getFrameShift()){
                //world.setFrameShift((int)(2*groundShift+world.getFrameShift()-(getTop()+getHeight())));
                toFrameShift = 2*groundShift-(getTop()+getHeight());
                world.incFrameShift((int)(toFrameShift* frameShiftVel));
//                System.out.println("shift cam up");
//                System.out.println(world.getFrameShift());
            }
            if(getTop()+getHeight() > world.getHeight()-world.getFrameShift()-groundShift) {
                //world.setFrameShift((int) (getTop() + getHeight() - (world.getHeight() - world.getFrameShift() - groundShift)));
                toFrameShift = getTop() + getHeight() - (world.getHeight()-groundShift);
                world.incFrameShift((int)(toFrameShift* frameShiftVel));
                //System.out.println("shift cam down");
                //System.out.println(world.getFrameShift());
            }
        }
        //System.out.println("onGround = " + onGround + " and getVY() = " + getVY());
        if(!onGround && getVY() > 0) {//descending
            boolean oldPlatformStat = onPlatform;
            onPlatform = false;
            //System.out.println("descending");
            for(int i = 0; i < world.getNumSpikeSprites(); i++){
                Sprite sprite = world.getSpikeSprite(i);
                if (sprite.overlap(this)) {
                   // System.out.println("descending death");
                    System.exit(0);
                }
            }
            for (int i = 1; i < world.getNumSprites(); i++) {
                Sprite sprite = world.getSprite(i);
                //System.out.println("i = " + i + ":  " + sprite.getClass().getName());
//                if (sprite instanceof SpikeSprite) {
//                    //System.out.println("spike " + " spikeLeft: " + sprite.getLeft() + " thisLeft: " + getLeft());
//                    if (sprite.overlap(this)) {
//                        System.out.println("descending death");
//                        System.exit(0);
//                    }
//                }
//                else
                    if (standingOn(sprite) && getVY() >= 0) {
                    if(!onPlatform) setTop(sprite.getTop() - getHeight());
                    onPlatform = true;
                    //System.out.println(oldPlatformStat == onPlatform);
                    //break;
                }
            }
            //if(onPlatform != oldPlatformStat) System.out.println("on/off platform; " + "onPlatform: " + onPlatform + "  onGround: " + onGround+ " X: " + getLeft() + "  YVel: " + getVY());
        }
        if(getVY() <= 0){//ascending/neutral
            //System.out.println("ascending/neutral");
            for(int i = 1; i < world.getNumSprites(); i++){
                Sprite sprite = world.getSprite(i);
                //if(sprite instanceof SpikeSprite)
                if(sprite.overlap(this)){
                    //System.out.println("collisionSprite");
                    System.out.println(stepCount);

                    System.exit(0);

                }
            }
            for(int j = 0; j < world.getNumSpikeSprites(); j++){
                Sprite sprite = world.getSpikeSprite(j);
                //System.out.println(j);
                if(sprite.overlap(this)){
                    //System.out.println("collisionSpikeSprite");
                    //System.out.println(stepCount);

                    System.exit(0);

                }
            }
        }
    }
}