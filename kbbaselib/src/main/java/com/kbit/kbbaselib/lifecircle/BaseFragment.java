package com.kbit.kbbaselib.lifecircle;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public Context getBaseContext() {
        return mContext == null ?  BaseApplication.getContext() : mContext;
    }

    public boolean hideSoftInput() {
        if (getActivity() == null) {
            return true;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        return imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }

    public void showSoftInput() {
        if (getActivity() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            assert imm != null;
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

}
