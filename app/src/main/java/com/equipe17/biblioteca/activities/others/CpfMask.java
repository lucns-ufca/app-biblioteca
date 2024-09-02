package com.equipe17.biblioteca.activities.others;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CpfMask implements TextWatcher {

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }

    private EditText editText;
    private OnTextChangedListener onTextChangedListener;

    public CpfMask(EditText editText, OnTextChangedListener onTextChangedListener) {
        this.editText = editText;
        this.onTextChangedListener = onTextChangedListener;
    }

    boolean isUpdating;
    String old = "";
    final String FORMAT_CPF = "###.###.###-##";

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = unmask(s.toString());
        String mascara = "";
        if (isUpdating) {
            old = str;
            isUpdating = false;
            return;
        }
        onTextChangedListener.onTextChanged(s.toString());
        int i = 0;
        for (char m : FORMAT_CPF.toCharArray()) {
            if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                mascara += m;
                continue;
            }

            try {
                mascara += str.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        isUpdating = true;
        editText.setText(mascara);
        editText.setSelection(mascara.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }
}
