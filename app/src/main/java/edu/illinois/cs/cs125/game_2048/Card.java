package edu.illinois.cs.cs125.game_2048;

import android.view.Gravity;
import android.widget.FrameLayout;
import android.content.Context;
import android.widget.TextView;

public class Card extends FrameLayout {

    public Card(Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);


        LayoutParams LayPa = new LayoutParams(-1,-1);
        LayPa.setMargins(10,10,0,0);
        addView(label, LayPa);

        setNumber(0);
    }

    private int number = 0;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if (number <= 0) {
            label.setText("");
        } else {
            label.setText(number + "");
        }
    }

    public boolean equals(Card c) {
        return getNumber() == c.getNumber();
    }

    private TextView label;
}
