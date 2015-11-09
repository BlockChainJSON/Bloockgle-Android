package anonymous.line.bloockgle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;

import anonymous.line.bloockgle.bloockgle.R;

/**
 * Created by Mr.Marshall on 22/10/2015.
 */
public class BorderEditText extends EditText {

    private String hint;

    public BorderEditText(Context context) {
        super(context);
        initialized();
    }

    public BorderEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialized();
    }

    public BorderEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialized();
    }

    private void initialized(){
        setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_style));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,getPixels(5),0,0);
        setLayoutParams(layoutParams);
        setTextColor(1);
        setTextSize(16);
        setPadding(getPixels(10), getPixels(10), 0, getPixels(10));
    }


    @Override
    public void setTextColor(int color){
        super.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void setTextSize(int unit, float size){
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    }

    private int getPixels(float dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}
