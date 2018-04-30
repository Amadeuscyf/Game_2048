package edu.illinois.cs.cs125.game_2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    public GameView(Context context, AttributeSet attriSet) {
        super(context, attriSet);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attriSet, int definitionStyle) {
        super(context, attriSet, definitionStyle);
        initGameView();
    }

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new View.OnTouchListener() {

            private float startX, startY, moveX, moveY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        moveX = event.getX() - startX;
                        moveY = event.getY() - startY;

                        if (Math.abs(moveX) > Math.abs(moveY)) {
                            if (moveX < -3) {
                                swipeLeft();
                            } else if (moveX > 3) {
                                swipeRight();
                            }
                        } else {
                            if (moveY < -3) {
                                swipeUp();
                            } else if (moveY > 3) {
                                swipeDown();
                            }
                        }
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int width, int height, int origWidth, int origHeight) {
        super.onSizeChanged(width, height, origWidth, origHeight);

        int cardWidth = (Math.min(width, height) - 10) / 4;
        addCards(cardWidth, cardWidth);

        gameStart();
    }

    private void addCards(final int cardWidth, final int cardHeight) {
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNumber(0);
                addView(c, cardWidth, cardHeight);
                cardsBoard[x][y] = c;
            }
        }
    }

    private void gameStart() {
        MainActivity.getMainActivity().clearScore();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsBoard[x][y].setNumber(0);
            }
        }
        addRandomNumber();
        addRandomNumber();
    }

    private void addRandomNumber() {
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsBoard[x][y].getNumber() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()* emptyPoints.size()));
        cardsBoard[p.x][p.y].setNumber(Math.random()>0.1?2:4);


    }

    private void swipeLeft() {
        boolean merged = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsBoard[x1][y].getNumber() > 0) {

                        if (cardsBoard[x][y].getNumber() <= 0) {
                            cardsBoard[x][y].setNumber(cardsBoard[x1][y].getNumber());
                            cardsBoard[x1][y].setNumber(0);
                            x--;
                            merged = true;
                        } else if (cardsBoard[x][y].equals(cardsBoard[x1][y])) {
                            cardsBoard[x][y].setNumber(2 * cardsBoard[x][y].getNumber());
                            cardsBoard[x1][y].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardsBoard[x][y].getNumber());
                            merged = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merged) {
            addRandomNumber();
            complete();
        }
    }

    private void swipeRight() {
        boolean merged = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {

                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsBoard[x1][y].getNumber() > 0) {

                        if (cardsBoard[x][y].getNumber() <= 0) {
                            cardsBoard[x][y].setNumber(cardsBoard[x1][y].getNumber());
                            cardsBoard[x1][y].setNumber(0);
                            x++;
                            merged = true;
                        } else if (cardsBoard[x][y].equals(cardsBoard[x1][y])) {
                            cardsBoard[x][y].setNumber(2 * cardsBoard[x][y].getNumber());
                            cardsBoard[x1][y].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardsBoard[x][y].getNumber());
                            merged = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merged) {
            addRandomNumber();
            complete();
        }
    }

    private void swipeUp() {
        boolean merged = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsBoard[x][y1].getNumber() > 0) {

                        if (cardsBoard[x][y].getNumber() <= 0) {
                            cardsBoard[x][y].setNumber(cardsBoard[x][y1].getNumber());
                            cardsBoard[x][y1].setNumber(0);
                            y--;
                             merged = true;
                        } else if (cardsBoard[x][y].equals(cardsBoard[x][y1])) {
                            cardsBoard[x][y].setNumber(2 * cardsBoard[x][y].getNumber());
                            cardsBoard[x][y1].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardsBoard[x][y].getNumber());
                            merged = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merged) {
            addRandomNumber();
            complete();
        }
    }

    private void swipeDown() {
        boolean merged = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {

                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsBoard[x][y1].getNumber() > 0) {

                        if (cardsBoard[x][y].getNumber() <= 0) {
                            cardsBoard[x][y].setNumber(cardsBoard[x][y1].getNumber());
                            cardsBoard[x][y1].setNumber(0);
                            y++;
                            merged = true;
                        } else if (cardsBoard[x][y].equals(cardsBoard[x][y1])) {
                            cardsBoard[x][y].setNumber(2 * cardsBoard[x][y].getNumber());
                            cardsBoard[x][y1].setNumber(0);
                            MainActivity.getMainActivity().addScore(cardsBoard[x][y].getNumber());
                            merged = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merged) {
            addRandomNumber();
            complete();
        }
    }
    private void complete() {
        boolean complete = true;
        all:
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (cardsBoard[x][y].getNumber() == 0 || (x > 1 && cardsBoard[x][y].equals(cardsBoard[x-1][y]))
                            || (x<3 && cardsBoard[x][y].equals(cardsBoard[x+1][y]))
                            || (y > 0 && cardsBoard[x][y].equals(cardsBoard[x][y-1]))
                            || (y < 3 && cardsBoard[x][y].equals(cardsBoard[x][y+1]))) {
                        complete = false;
                        break all;
                    }
                }
            }
            MainActivity.getMainActivity().getHighestScore(MainActivity.getMainActivity().score);
            if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("Hello").setMessage("Game is over").setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameStart();
                }
            }).show();
            }
        }

    private Card[][] cardsBoard = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
}
