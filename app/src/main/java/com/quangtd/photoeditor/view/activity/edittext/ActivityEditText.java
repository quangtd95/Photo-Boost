package com.quangtd.photoeditor.view.activity.edittext;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.DecorText;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.utils.ScreenUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.component.CustomDrawSticker;
import com.quangtd.photoeditor.view.component.CustomTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_edit_text)
public class ActivityEditText extends ActivityBase implements View.OnClickListener,
        OnChangeCustomTextListener, OnHandlerItemListener {
    @ViewById(R.id.customTextView) CustomTextView mCustomTextView;
    @ViewById(R.id.drawSticker) CustomDrawSticker mCustomDrawSticker;
    @ViewById(R.id.imgPreview) ImageView mImgPreview;

    private Bitmap mBitmap;
    private DecorText mDecor;
    private String mText;
    @Extra(GlobalDefine.KEY_IMAGE) String mImagePath;
    private String[] mFontArrays;
    private Bitmap mBitmapOrigin;

    @AfterViews
    public void init() {
        mBitmapOrigin = BitmapFactory.decodeFile(mImagePath);
        mImgPreview.setImageBitmap(mBitmapOrigin);
        mCustomTextView.setOnChangeCustomTextListener(this);
        mCustomDrawSticker.setOnHandlerItemListener(this);
        mText = getString(R.string.default_text);
        mFontArrays = getResources().getStringArray(R.array.array_font);
        addText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSave:
                if (mCustomTextView.getVisibility() == View.VISIBLE) {
                    mCustomTextView.setVisibility(View.GONE);
                } else {
                    if (mCustomDrawSticker.getDecorCount() != 0) {
                        addTextToVideo();
                    }
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }

    private void addTextToVideo() {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
    }

    public void showDialogNotify(String message) {
        final Dialog dlNotify = new Dialog(this);
        dlNotify.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlNotify.setContentView(R.layout.dialogn_input_text);
        dlNotify.setCancelable(false);
        dlNotify.setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP | Gravity.START;
        lp.x = 40;
        lp.y = ScreenUtils.getHeightScreen(ActivityEditText.this) / 5;
        dlNotify.getWindow().setAttributes(lp);

        final EditText editText = dlNotify.findViewById(R.id.edtText);
        TextView btnCancel = dlNotify.findViewById(R.id.btnCancel);
        TextView btnOK = dlNotify.findViewById(R.id.btnOK);
        editText.setHint(message);
        int pos = editText.getText().length();
        editText.setSelection(pos);
        editText.requestFocus();
        dlNotify.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btnCancel.setOnClickListener(v -> dlNotify.dismiss());

        btnOK.setOnClickListener(v -> {
            mText = editText.getText().toString().trim();
            if (TextUtils.isEmpty(mText)) {
                mText = editText.getHint().toString().trim();
            }

            mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
            mCustomDrawSticker.clearDataPosition();
            mDecor.setText(mText);

            stringToBitMap(mDecor);
            mCustomDrawSticker.addDecoItem(mDecor);
            dlNotify.dismiss();
        });
        dlNotify.show();
    }

    private boolean mIsPrepared;

    private void addText() {
        if (mIsPrepared) return;
        mIsPrepared = true;
        stringToBitMap(mText);
        mCustomDrawSticker.setVisibility(View.VISIBLE);
        mCustomDrawSticker.post(() -> mCustomDrawSticker.addDecoItemText(mBitmap, 0, mText));
    }

    public void stringToBitMap(String s) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(72);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        Rect rect = new Rect();
        paint.getTextBounds(s, 0, s.length(), rect);

        mBitmap = Bitmap.createBitmap(rect.width() + 80, rect.height() + 40, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawText(s, 40, canvas.getHeight() - 20, paint);
    }

    public void stringToBitMap(DecorText decor) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(decor.getSize() * decor.getScale());
        paint.setColor(decor.getColor());
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setShadowLayer(decor.getShadow(), 2, 2, Color.BLACK);
        Typeface plain;

        if (decor.getFont() != 0) {
            AssetManager assetManager = getAssets();
            plain = Typeface.createFromAsset(assetManager, mFontArrays[decor.getFont()] + ".otf");
        } else {
            plain = Typeface.DEFAULT;
        }

        int type = Typeface.NORMAL;
        if (decor.getStyle() == 0) {
            type = Typeface.NORMAL;
        } else if (decor.getStyle() == 1) {
            type = Typeface.BOLD;
        } else if (decor.getStyle() == 2) {
            type = Typeface.ITALIC;
        } else if (decor.getStyle() == 3) {
            paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        paint.setTypeface(Typeface.create(plain, type));

        Rect rect = new Rect();
        paint.getTextBounds(decor.getText(), 0, decor.getText().length(), rect);

        int paddingWidth = 80;
        int paddingHeight = 40;
        mBitmap = Bitmap.createBitmap(rect.width() + (int) (paddingWidth * decor.getScale()), rect.height() + (int) (paddingHeight * decor.getScale()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        int align;
        if (decor.getAlign() == 0) {
            align = (int) (paddingWidth / 2 * decor.getScale());
        } else if (decor.getAlign() == 1) {
            align = 0;
        } else {
            align = (int) (paddingWidth * decor.getScale());
        }


        canvas.drawText(decor.getText(), align, canvas.getHeight() - paddingHeight / 2 * decor.getScale(), paint);

        decor.setBitmap(mBitmap);
        decor.setWidth(mBitmap.getWidth());
        decor.setHeight(mBitmap.getHeight());
    }

    @Override
    public void onChangeSize(int size) {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
        mCustomDrawSticker.clearDataPosition();
        if (size == 1 && mDecor.getSize() < 96) {
            mDecor.setSize(mDecor.getSize() + 2);
        } else if (size == -1 && mDecor.getSize() > 48) {
            mDecor.setSize(mDecor.getSize() - 2);
        }
        if (mDecor.getSize() >= 96) mCustomTextView.setSizeMax(true);
        if (mDecor.getSize() <= 48) mCustomTextView.setSizeMax(false);

        stringToBitMap(mDecor);
        mCustomDrawSticker.addDecoItem(mDecor);
    }

    @Override
    public void onChangeAlign(int align) {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
        mCustomDrawSticker.clearDataPosition();
        mDecor.setAlign(align);

        stringToBitMap(mDecor);
        mCustomDrawSticker.addDecoItem(mDecor);
    }

    @Override
    public void onChangeType(int type) {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
        mCustomDrawSticker.clearDataPosition();
        mDecor.setStyle(type);

        stringToBitMap(mDecor);
        mCustomDrawSticker.addDecoItem(mDecor);
    }

    @Override
    public void onChangeShadow(int shadow) {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
        mCustomDrawSticker.clearDataPosition();
        if (shadow == 1 && mDecor.getShadow() < 10) {
            mDecor.setShadow(mDecor.getShadow() + 1);
        } else if (shadow == -1 && mDecor.getShadow() > 0) {
            mDecor.setShadow(mDecor.getShadow() - 1);
        }
        if (mDecor.getShadow() >= 10) mCustomTextView.setSizeShadow(true);
        if (mDecor.getShadow() <= 0) mCustomTextView.setSizeShadow(false);

        stringToBitMap(mDecor);
        mCustomDrawSticker.addDecoItem(mDecor);
    }

    @Override
    public void onClickItemFont(int position) {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
        mCustomDrawSticker.clearDataPosition();
        mDecor.setFont(position);
        stringToBitMap(mDecor);
        mCustomDrawSticker.addDecoItem(mDecor);
    }

    @Override
    public void onClickItemColor(int color) {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
        mCustomDrawSticker.clearDataPosition();
        mDecor.setColor(color);

        stringToBitMap(mDecor);
        mCustomDrawSticker.addDecoItem(mDecor);
    }

    @Override
    public void onZoomItem() {
        mDecor = (DecorText) mCustomDrawSticker.getDecors().get(0);
        mCustomDrawSticker.clearDataPosition();
        stringToBitMap(mDecor);
        mCustomDrawSticker.addDecoItem(mDecor);

    }

    @Override
    public void onDeleteItem() {
        mCustomTextView.setVisibility(View.GONE);
    }

    @Override
    public void touchItem() {
        mCustomTextView.setVisibility(View.GONE);
        mIsShowPanel = false;
    }

    @Override
    public void clickItem() {
        //no-op
    }

    private boolean mIsShowPanel = false;

    public void showEditPanel(View view) {
        mIsShowPanel = !mIsShowPanel;
        if (mIsShowPanel) {
            mCustomTextView.setVisibility(View.VISIBLE);
        } else {
            mCustomTextView.setVisibility(View.GONE);
        }
    }

    public void changeText(View view) {
        showDialogNotify(mText);
    }

    @Click(R.id.imgSave)
    public void onClickSave() {
        new SaveImageAsync().execute();
    }

    @Click(R.id.imgClose)
    public void onCLickClose() {
        finish();
    }

    private class SaveImageAsync extends AsyncTask<Void, Void, String> {
        @Override protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override protected String doInBackground(Void... voids) {
            return EditPhotoUtils.editAndSaveImage(mBitmapOrigin, null, mCustomDrawSticker.getDecors(),true);
        }

        @Override protected void onPostExecute(String path) {
            setResult(RESULT_OK, new Intent().putExtra(GlobalDefine.KEY_IMAGE, path));
            dismissProgressDialog();
            finish();
        }
    }
}
