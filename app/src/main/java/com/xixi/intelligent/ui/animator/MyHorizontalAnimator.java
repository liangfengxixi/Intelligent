package com.xixi.intelligent.ui.animator;

import android.os.Parcel;
import android.os.Parcelable;

import com.xixi.intelligent.R;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by xixi on 19/11/15.
 */
public class MyHorizontalAnimator extends FragmentAnimator implements Parcelable{

    public MyHorizontalAnimator() {
        enter = R.anim.my_fragment_enter;
        exit = R.anim.my_fragment_exit;
        popEnter = R.anim.my_fragment_pop_enter;
        popExit = R.anim.my_fragment_pop_exit;
    }

    protected MyHorizontalAnimator(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyHorizontalAnimator> CREATOR = new Creator<MyHorizontalAnimator>() {
        @Override
        public MyHorizontalAnimator createFromParcel(Parcel in) {
            return new MyHorizontalAnimator(in);
        }

        @Override
        public MyHorizontalAnimator[] newArray(int size) {
            return new MyHorizontalAnimator[size];
        }
    };
}
