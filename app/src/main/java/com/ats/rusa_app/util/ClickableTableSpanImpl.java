package com.ats.rusa_app.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ats.rusa_app.activity.WebViewActivity;
import com.ats.rusa_app.fragment.ContentFragment;

import org.sufficientlysecure.htmltextview.ClickableTableSpan;

public class ClickableTableSpanImpl extends ClickableTableSpan {

    Context context;

    public ClickableTableSpanImpl(Context context) {
        this.context = context;
    }

    @Override
    public ClickableTableSpan newInstance() {
        return new ClickableTableSpanImpl(context);
    }

    @Override
    public void onClick(View widget) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("EXTRA_TABLE_HTML", getTableHtml());
        context.startActivity(intent);
    }
}