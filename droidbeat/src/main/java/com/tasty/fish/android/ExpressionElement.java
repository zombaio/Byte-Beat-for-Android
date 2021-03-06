package com.tasty.fish.android;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tasty.fish.R;
import com.tasty.fish.domain.Listener;
import com.tasty.fish.domain.implementation.Expression;

public class ExpressionElement extends RelativeLayout {
    private final Context _context;
    private TextView _text;
    private HighlightButton _save;
    private HighlightButton _delete;

    private Listener<Expression> _selection;
    private Listener<Expression> _onSave;
    private Listener<Expression> _onDelete;
    private Expression _expression;

    public ExpressionElement(Context context) {
        super(context);
        _context = context;
        create();
    }

    public void setExpression(Expression expression){
        _expression = expression;
        if(_expression.isDirty() && !_expression.isReadOnly()){
            _text.setText(_expression.getName() + " *");
            _text.setTextColor(Color.WHITE);
        }
        else {
            _text.setText(_expression.getName());
            _text.setTextColor(Color.LTGRAY);
        }

        if(_expression.isReadOnly()){
            _save.setAlpha(255 / 2);
            _save.setEnabled(false);
        }
        else {
            _save.setAlpha(255);
            _save.setEnabled(true);
        }

        if(_expression.isReadOnly()){
            _delete.setAlpha(255 / 2);
            _delete.setEnabled(false);
        }
        else {
            _delete.setAlpha(255);
            _delete.setEnabled(true);
        }
    }

    public void create() {
        Context c = _context;
        final int buttonLayoutId = 100;

        int rowHeight = (int)PixelConverter.ToPixels(50);

        setBackgroundColor(0xFF333333);
        AbsListView.LayoutParams
        elementLayoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rowHeight);
        setLayoutParams(elementLayoutParams);
        HighlightLayout
            textLayout = new HighlightLayout(c);
            textLayout.setGravity(Gravity.CENTER_VERTICAL);
            textLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    _selection.onEvent(_expression);
                }
            });
            RelativeLayout.LayoutParams
            textLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            textLayoutParams.addRule(RelativeLayout.LEFT_OF, buttonLayoutId);
            textLayout.setLayoutParams(textLayoutParams);
                _text = new TextView(c);
                _text.setGravity(Gravity.CENTER_VERTICAL);
                _text.setTextSize(20);
            textLayout.addView(_text);
        addView(textLayout);
        LinearLayout
            buttonLayout = new LinearLayout(c);
            buttonLayout.setId(buttonLayoutId);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            RelativeLayout.LayoutParams
            buttonLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            buttonLayout.setLayoutParams(buttonLayoutParams);
                _save = new HighlightButton(c);
                _save.setImageResource(R.drawable.save);
                _save.setLayoutParams(new ViewGroup.LayoutParams(rowHeight, rowHeight));
                _save.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        _onSave.onEvent(_expression);
                    }
                });
            buttonLayout.addView(_save);
                _delete = new HighlightButton(c);
                _delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        _onDelete.onEvent(_expression);
                    }
                });
                _delete.setImageResource(R.drawable.trash);
                _delete.setLayoutParams(new ViewGroup.LayoutParams(rowHeight, rowHeight));

            buttonLayout.addView(_delete);
        addView(buttonLayout);
    }

    public void setSelectionListener(Listener<Expression> listener) {
        _selection = listener;
    }
    public void setSaveListener(Listener<Expression> listener) {
        _onSave = listener;
    }
    public void setDeleteListener(Listener<Expression> listener) {
        _onDelete = listener;
    }
}
