package es.ulpgc.eite.clean.mvp.sample.preferences;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import com.kizitonwose.colorpreference.ColorShape;

import es.ulpgc.eite.clean.mvp.sample.R;
import com.kizitonwose.colorpreference.*;

public class ColorDialog extends DialogFragment {
    private GridLayout colorGrid;
    private OnColorSelectedListener colorSelectedListener;
    private int numColumns;
    private int[] colorChoices;
    private ColorShape colorShape;
    private int selectedColorValue;
    private static final String NUM_COLUMNS_KEY = "num_columns";
    private static final String COLOR_SHAPE_KEY = "color_shape";
    private static final String COLOR_CHOICES_KEY = "color_choices";
    private static final String SELECTED_COLOR_KEY = "selected_color";

    /**
     * Methods that initial the Color Dialog.
     */
    public static ColorDialog newInstance(int numColumns, ColorShape colorShape, int[] colorChoices, int selectedColorValue) {
        Bundle args = new Bundle();
        args.putInt(NUM_COLUMNS_KEY, numColumns);
        args.putSerializable(COLOR_SHAPE_KEY, colorShape);
        args.putIntArray(COLOR_CHOICES_KEY, colorChoices);
        args.putInt(SELECTED_COLOR_KEY, selectedColorValue);
        ColorDialog dialog = new ColorDialog();
        dialog.setArguments(args);
        return dialog;
    }

    /**
     * Methods that initializes the Color Dialog.
     * @param savedInstanceState saved states of the app.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        numColumns = args.getInt(NUM_COLUMNS_KEY);
        colorShape = (ColorShape) args.getSerializable(COLOR_SHAPE_KEY);
        colorChoices = args.getIntArray(COLOR_CHOICES_KEY);
        selectedColorValue = args.getInt(SELECTED_COLOR_KEY);
    }

    /**
     * Method that sets the color selected.
     * @param colorSelectedListener listener of the color selection.
     */
    public void setOnColorSelectedListener(OnColorSelectedListener colorSelectedListener) {
        this.colorSelectedListener = colorSelectedListener;
        repopulateItems();
    }

    /**
     * Fragment auto-generated method.
     * @param activity actual activity.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnColorSelectedListener) {
            setOnColorSelectedListener((OnColorSelectedListener) activity);
        } else {
            repopulateItems();
        }

    }

    /**
     * Method that creates the dialog itself.
     * Auto-generated method.
     * @param savedInstanceState saved states of the app.
     *
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View rootView = layoutInflater.inflate(R.layout.dialog_colors, null);
        colorGrid = (GridLayout) rootView.findViewById(R.id.color_grid);
        colorGrid.setColumnCount(numColumns);
        repopulateItems();
        return new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .create();
    }

    /**
     * Method that repopulates the items.
     */
    private void repopulateItems() {
        if (colorSelectedListener == null || colorGrid == null) {
            return;
        }

        Context context = colorGrid.getContext();
        colorGrid.removeAllViews();
        for (final int color : colorChoices) {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.grid_item_color, colorGrid, false);

            ColorUtils.setColorViewValue(itemView.findViewById(R.id.color_view), color,
                    color == selectedColorValue, colorShape);

            itemView.setClickable(true);
            itemView.setFocusable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (colorSelectedListener != null) {
                        colorSelectedListener.onColorSelected(color, getTag());
                    }
                    dismiss();
                }
            });
            colorGrid.addView(itemView);
        }
        sizeDialog();
    }

    /**
     * Auto-generated method. Starts the fragment.
     */
    @Override
    public void onStart() {
        super.onStart();
        sizeDialog();
    }

    /**
     * Method that adapts the size dialogs to its content.
     */
    private void sizeDialog() {
        if (colorSelectedListener == null || colorGrid == null) {
            return;
        }

        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }

        final Resources res = colorGrid.getContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();

        // Can't use Integer.MAX_VALUE here (weird issue observed otherwise on 4.2)
        colorGrid.measure(
                View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.AT_MOST));
        int width = colorGrid.getMeasuredWidth();
        int height = colorGrid.getMeasuredHeight();
        int extraPadding = res.getDimensionPixelSize(R.dimen.color_grid_extra_padding);
        width += extraPadding;
        height += extraPadding;
        dialog.getWindow().setLayout(width, height);
    }

    /**
     * Interface to the color selection listener.
     */
    private interface OnColorSelectedListener {
        void onColorSelected(int newColor, String tag);
    }

    /**
     * Class that contains different methods to create the dialog with the color shapes..
     */
    private static class Builder {
        private int numColumns = 5;
        private int[] colorChoices;
        private ColorShape colorShape = ColorShape.CIRCLE;
        private Context context;
        private int selectedColor;
        private String tag;


        public <ColorActivityType extends Activity & OnColorSelectedListener> Builder(@NonNull ColorActivityType context) {
            this.context = context;
            //default colors
            setColorChoices(R.array.default_color_choice_values);
        }

        /**
         * Sets the number of colums of the color shapes.
         * @param numColumns int: columns of color shapes.
         */
        public Builder setNumColumns(int numColumns) {
            this.numColumns = numColumns;
            return this;
        }

        /**
         * Sets the colors to the different colour shapes.
         * @param colorChoicesRes int: colours of the dialog.
         */

        private Builder setColorChoices(@ArrayRes int colorChoicesRes) {
            String[] choices = context.getResources().getStringArray(colorChoicesRes);

            int[] colors = new int[choices.length];
            for (int i = 0; i < choices.length; i++) {
                colors[i] = Color.parseColor(choices[i]);
            }

            this.colorChoices = colors;
            return this;
        }

        /**
         * Method that builds or call the instance of the color dialog.
         */
        protected ColorDialog build() {
            ColorDialog dialog = ColorDialog.newInstance(numColumns, colorShape, colorChoices, selectedColor);
            dialog.setOnColorSelectedListener((OnColorSelectedListener) context);
            return dialog;
        }
    }
}
