package board;

public class PathChecker {

    private Board board;
    //position of both players WHITE and BLACK
    private int xW, yW, xB, yB;
    private int xWall, yWall;
    private WallType wallType;
    boolean[][] checked = new boolean[9][9];
    private boolean[][] verticalWalls = new boolean[9][9];
    private boolean[][] horizontalWalls = new boolean[9][9];




    public PathChecker(Board board, int xWall, int yWall, WallType wallType) {
        this.board = board;
        this.xWall = xWall -1;
        this.yWall = yWall;
        this.wallType = wallType;
        ////////////////////////////////
        for(int i = 0; i < 9;i++){
            for(int j = 0; j < 9;j++){
                if(board.getHorizontalWalls()[i][j]){
                    horizontalWalls[i][j] = true;
                }
                if(board.getVerticalWalls()[i][j]){
                    verticalWalls[i][j] = true;
                }
            }
        }
        for(int i = 0;i<9;i++){
            horizontalWalls[i][0]=false;
        }
        for(int i = 0;i<9;i++){
            verticalWalls[0][i]=false;
        }



        if(wallType==WallType.HORIZONTAL){
            horizontalWalls[xWall][yWall] = true;
            try{
                horizontalWalls[xWall+1][yWall] = true;
            } catch(ArrayIndexOutOfBoundsException e) {

            }
        }else{
            verticalWalls[xWall][yWall] = true;
            verticalWalls[xWall][yWall+1] = true;
        }


        //////////////////////////////

    }

    public boolean checkWallPlacement(){
        this.xW = (int) board.getPieces()[0].getOldPosX();
        this.yW = (int) board.getPieces()[0].getOldPosY();
        this.xB = (int) board.getPieces()[1].getOldPosX();
        this.yB = (int) board.getPieces()[1].getOldPosY();
        for(int i = 0; i<9;i++){
            for(int j = 0; j<9;j++){
                checked[i][j]=false;
            }
        }
        checked[xW][yW] = true;
        boolean whiteBlocked = move(PlayerType.WHITE, xW,yW);

        for(int i = 0; i<9;i++){
            for(int j = 0; j<9;j++){
                checked[i][j]=false;
            }
        }
        checked[xB][yB] = true;
        boolean blackBlocked = move(PlayerType.BLACK, xB,yB);

        return (whiteBlocked && blackBlocked);
    }

    public boolean checkMove(int x, int y, int oldX, int oldY){

        if(!checked[x][y]){
            if (oldX  == x+1 && y - oldY == 0 && !verticalWalls[oldX][oldY]){
                return true;
            }

            if (oldX == x - 1 && y - oldY == 0 && !verticalWalls[x][y]) {
                return true;
            }

            if (oldY == y+1 && x - oldX == 0 && !horizontalWalls[oldX][oldY]){
                return true;
            }

            if (oldY == y-1 && x - oldX == 0 && !horizontalWalls[x][y]){
                return true;
            }
        }else return false;
        return false;
    }

    public boolean move(PlayerType type, int x, int y){

        if(y==0&&type==PlayerType.WHITE||y==8&&type==PlayerType.BLACK) return true;

        else{
            if(y>0){
                if(checkMove(x,y-1,x,y)){
                    checked[x][y-1] = true;
                    if(move(type, x,y-1)) return true;
                }
            }

            if(x<8){
                if(checkMove(x+1,y,x,y)){
                    checked[x+1][y] = true;
                    if(move(type,x+1,y)) return true;
                }
            }

            if(x>0){
                if(checkMove(x-1,y,x,y)){
                    checked[x-1][y] = true;
                    if(move(type, x-1,y)) return true;
                }
            }

            if(y<8){

                if(checkMove(x,y+1,x,y)){
                    checked[x][y+1] = true;
                    if(move(type, x,y+1)) return true;
                }
            }

            return false;
        }


    }
}
