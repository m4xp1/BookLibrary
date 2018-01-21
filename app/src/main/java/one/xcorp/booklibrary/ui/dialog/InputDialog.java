package one.xcorp.booklibrary.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.annimon.stream.Optional;

import java.lang.ref.WeakReference;

@SuppressWarnings("unused")
public class InputDialog extends DialogFragment {

    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TEXT_INPUT = "textInput";
    public static final String KEY_INPUT_TYPE = "inputType";
    public static final String KEY_POSITIVE_BUTTON = "positiveButton";
    public static final String KEY_NEGATIVE_BUTTON = "negativeButton";

    public static InputDialog newInstance(@Nullable String title, @Nullable String textInput) {
        return newInstance(title, null, textInput, null, null, null);
    }

    public static InputDialog newInstance(@Nullable String title, @Nullable String message,
                                          @Nullable String textInput) {
        return newInstance(title, message, textInput, null, null, null);
    }

    @SuppressWarnings("SameParameterValue")
    public static InputDialog newInstance(@Nullable String title, @Nullable String message,
                                          @Nullable String textInput, @Nullable Integer inputType,
                                          @Nullable String positive, @Nullable String negative) {
        Bundle arguments = new Bundle();
        if (title != null) {
            arguments.putString(KEY_TITLE, title);
        }
        if (message != null) {
            arguments.putString(KEY_MESSAGE, message);
        }
        if (textInput != null) {
            arguments.putString(KEY_TEXT_INPUT, textInput);
        }
        if (inputType != null) {
            arguments.putInt(KEY_INPUT_TYPE, inputType);
        }
        if (positive != null) {
            arguments.putString(KEY_POSITIVE_BUTTON, positive);
        }
        if (negative != null) {
            arguments.putString(KEY_NEGATIVE_BUTTON, negative);
        }

        InputDialog fragment = new InputDialog();
        fragment.setArguments(arguments);

        return fragment;
    }

    private EditText editText;

    private WeakReference<PositiveListener> positiveListener;
    private WeakReference<NegativeListener> negativeListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //noinspection ConstantConditions
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int padding = (int) (20 * Resources.getSystem().getDisplayMetrics().density);

        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setPadding(padding, 0, padding, 0);

        editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        String positive = getString(android.R.string.ok);
        String negative = getString(android.R.string.cancel);

        Bundle arguments = getArguments();
        if (arguments != null) {
            builder.setTitle(arguments.getString(KEY_TITLE));
            builder.setMessage(arguments.getString(KEY_MESSAGE));
            editText.setText(arguments.getString(KEY_TEXT_INPUT));
            editText.setInputType(arguments.getInt(KEY_INPUT_TYPE, InputType.TYPE_CLASS_TEXT));
            positive = arguments.getString(KEY_POSITIVE_BUTTON, positive);
            negative = arguments.getString(KEY_NEGATIVE_BUTTON, negative);
        }

        if (savedInstanceState != null) {
            editText.setText(savedInstanceState
                    .getString(KEY_TEXT_INPUT, editText.getText().toString()));
        }

        frameLayout.addView(editText);
        builder.setView(frameLayout);

        builder.setPositiveButton(positive, (dialog, which) -> {
            PositiveListener positiveListener = Optional.ofNullable(this.positiveListener)
                    .map(WeakReference::get).orElse(null);
            if (positiveListener != null) {
                positiveListener.onPositiveClick(editText.getText().toString());
            }
        });
        builder.setNegativeButton(negative, (dialog, which) -> {
            NegativeListener negativeListener = Optional.ofNullable(this.negativeListener)
                    .map(WeakReference::get).orElse(null);
            if (negativeListener != null) {
                negativeListener.onNegativeClick();
            }
        });

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT_INPUT, editText.getText().toString());
    }

    public void setPositiveListener(@Nullable PositiveListener positiveListener) {
        this.positiveListener = new WeakReference<>(positiveListener);
    }

    public void setNegativeListener(@Nullable NegativeListener negativeListener) {
        this.negativeListener = new WeakReference<>(negativeListener);
    }

    public interface PositiveListener {

        void onPositiveClick(@NonNull String input);
    }

    public interface NegativeListener {

        void onNegativeClick();
    }
}