package com.socket.xueyi.base;

import android.content.Context;

import net.tsz.afinal.FinalDb;

/**
 * Created by XUEYI on 2015/12/18.
 */
public class CFinal {
    private Context mContext;
    private FinalDb mFinalDb;
    private static CFinal mCFinal;

    public static CFinal getCFinal() {
        if (mCFinal == null) {
            mCFinal = new CFinal();
        }
        return mCFinal;
    }


    public FinalDb getFinalDb(Context mContext) {
        mFinalDb = FinalDb.create(mContext);
        return mFinalDb;
    }



}
