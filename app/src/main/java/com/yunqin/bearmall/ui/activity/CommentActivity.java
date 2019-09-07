package com.yunqin.bearmall.ui.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PingJiaImgAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.util.UpLoadImage;
import com.yunqin.bearmall.widget.GridSpacingItemDecoration;
import com.yunqin.bearmall.widget.StarBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发表评价
 *
 * @author Master
 */
public class CommentActivity extends BaseActivity implements PingJiaImgAdapter.OnItemClickListener, UpLoadImage.OnUpLoadCallBack {


    @BindView(R.id.toolbar_title)
    TextView mTextView;

    @BindView(R.id.comment_goods_list)
    LinearLayout comment_goods_list;

    @BindView(R.id.send_comment)
    TextView send_comment;
    @BindView(R.id.check_box)
    CheckBox checkBox;


    /**
     * 拍照路径
     */
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
    Uri imageUri;


    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 1;

    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_GALLERY_REQUEST = 3;


    List<View> viewsList = new ArrayList<>();
    List<Integer> itemIds = new ArrayList<>();

    List<List<Uri>> lists = new ArrayList<>();

    List<PingJiaImgAdapter> pingJiaImgAdapterList = new ArrayList<>();
    List<String> types = new ArrayList<>();


    PingJiaImgAdapter lastAdapter = null;
    private int lastIndex;


    @Override
    public int layoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void init() {
        mTextView.setText("发表评价");
        viewsList.clear();
        itemIds.clear();
        lists.clear();

        initCommentView();
        addData();
    }

    private int orders_id;


    private void initCommentView() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        orders_id = bundle.getInt("orders_id");
        int count = bundle.getInt("orders_count");

        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_write_goods_comment, null);
            ImageView imageView = view.findViewById(R.id.pic);
            Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_tuijian_small)).load(bundle.getString(String.format("icon%d", i))).into(imageView);
            viewsList.add(view);
            itemIds.add(bundle.getInt(String.format("item%d", i)));
            lists.add(new ArrayList<>());
            types.add(bundle.getString(String.format("SpecificationItems%d", i)));
        }

    }

    @OnClick({R.id.toolbar_back, R.id.send_comment})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                this.finish();
                break;
            case R.id.send_comment:
                showLoading();
                Map<String, Object> mHashMap = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < lists.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("productId", itemIds.get(i));
                        jsonObject.put("content", ((EditText) viewsList.get(i).findViewById(R.id.comment_editext)).getText());
                        jsonObject.put("score", ((StarBar) viewsList.get(i).findViewById(R.id.ratingBar)).getStarMark());
                        StringBuffer stringBuffer = new StringBuffer("[");
                        for (String content : types.get(i).split(",")) {
                            stringBuffer.append("\"").append(content).append("\"").append(",");
                        }
                        String content = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1) + "]";
                        jsonObject.put("specifications", content);
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                mHashMap.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                mHashMap.put("orders_id", orders_id);
                mHashMap.put("reviews", jsonArray.toString());
                mHashMap.put("isAnonymous", checkBox.isChecked() ? 0 : 1);

                UpLoadImage.okHttpUpLoadImage(this, lists, itemIds, mHashMap, this);
                break;
        }
    }


    public void addData() {
        for (int i = 0; i < viewsList.size(); i++) {
            View view = viewsList.get(i);
            RecyclerView recycle_view = view.findViewById(R.id.recycle_view);
            recycle_view.setLayoutManager(new GridLayoutManager(this, 4));

            List<Bitmap> list = new ArrayList<>();
            PingJiaImgAdapter pingJiaImgAdapter = new PingJiaImgAdapter(this, list);
            pingJiaImgAdapter.setOnItemClickListener(this);
            recycle_view.setAdapter(pingJiaImgAdapter);

            recycle_view.addItemDecoration(new GridSpacingItemDecoration(4, 20));

            pingJiaImgAdapterList.add(pingJiaImgAdapter);

            StarBar ratingBar = view.findViewById(R.id.ratingBar);
            ratingBar.setOnStarChangeListener(new StarBar.OnStarChangeListener() {
                @Override
                public void onStarChange(float mark) {
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                params.setMargins(0, 20, 0, 0);
            }
            comment_goods_list.addView(view, params);
        }
    }


    @Override
    public void onItemClickDel(PingJiaImgAdapter pingJiaImgAdapter, int index) {
        for (int i = 0; i < pingJiaImgAdapterList.size(); i++) {
            PingJiaImgAdapter adapter = pingJiaImgAdapterList.get(i);
            if (pingJiaImgAdapter == adapter) {
                List<Bitmap> list = adapter.getData();
                list.remove(index);
                adapter.setData(list);
                lists.get(i).remove(i);
                break;
            }
        }
    }


    @Override
    public void onItemClickAdd(PingJiaImgAdapter pingJiaImgAdapter, int index) {
        lastAdapter = pingJiaImgAdapter;

        final int dataSize = lastAdapter.getData().size();

        for (int i = 0; i < pingJiaImgAdapterList.size(); i++) {
            if (lastAdapter == pingJiaImgAdapterList.get(i)) {
                lastIndex = i;
                break;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择")
                .setPositiveButton("照相机", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        autoObtainCameraPermission();
                    }
                })
                .setNegativeButton("图库", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        ImageSelector.builder()
                                .useCamera(false) // 设置是否使用拍照
                                .setSingle(false)  //设置是否单选
                                .setMaxSelectCount(9 - dataSize) // 图片的最大选择数量，小于等于0时，不限数量。
                                .start(CommentActivity.this, CODE_GALLERY_REQUEST); // 打开相册


                    }
                });
        builder.create().show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        File file = new File(SAVED_IMAGE_DIR_PATH + "/" + System.currentTimeMillis() + ".png");

                        imageUri = Uri.fromFile(file);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            imageUri = FileProvider.getUriForFile(CommentActivity.this, "com.bearmall.app", file);
                        }

                        Intent intentCamera = new Intent();
                        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        //将拍照结果保存至photo_file的Uri中，不保留在相册中
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intentCamera, CODE_CAMERA_REQUEST);


                    } else {
                        Log.e("TAG", "设备没有SD卡");
                    }
                } else {
                    Log.e("TAG", "请允许打开相机！！");
                }

                break;
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == CODE_CAMERA_REQUEST) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    lastAdapter.addData(bitmap);
                    lists.get(lastIndex).add(imageUri);
                    Log.e("TAG", "相机调用成功!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CODE_GALLERY_REQUEST) {
                if (data != null) {

                    List<Bitmap> map = new ArrayList<>();
                    ArrayList<String> backPics = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);

                    for (int i = 0; i < backPics.size(); i++) {
                        try {
                            File file = new File(backPics.get(i));
                            imageUri = Uri.fromFile(file);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            map.add(bitmap);
                            lists.get(lastIndex).add(imageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    lastAdapter.addData(map);
                }
            }
        }


    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 照相
     */
    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "您已经拒绝过一次,请到设置中打开权限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                File file = new File(SAVED_IMAGE_DIR_PATH + "/" + System.currentTimeMillis() + ".png");

                imageUri = Uri.fromFile(file);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri imageUri = FileProvider.getUriForFile(CommentActivity.this, "com.bearmall.app", file);
                    Intent intentCamera = new Intent();
                    intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    //将拍照结果保存至photo_file的Uri中，不保留在相册中
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intentCamera, CODE_CAMERA_REQUEST);
                } else {
                    Intent intentCamera = new Intent();
                    intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    //将拍照结果保存至photo_file的Uri中，不保留在相册中
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intentCamera, CODE_CAMERA_REQUEST);
                }
            } else {
                Toast.makeText(this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSuccess(String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hiddenLoadingView();
                StarActivityUtil.starActivity(CommentActivity.this, EvaluateFinActivity.class);
                CommentActivity.this.finish();
            }
        });
    }

    @Override
    public void onFail(Throwable throwable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hiddenLoadingView();
            }
        });
    }
}
