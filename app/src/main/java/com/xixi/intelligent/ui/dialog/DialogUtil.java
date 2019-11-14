
package com.xixi.intelligent.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xixi.intelligent.R;

/**
 *  加载圈
 * @author xixi
 *
 */
public class DialogUtil {

    public static Dialog createLoadingDialog(Context context, String tips) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progress_bar, null);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.layout);
        TextView text = (TextView) v.findViewById(R.id.loading_text);
        text.setText(tips);
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog_tran);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;

    }
}
