package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.SkuListAdapter;
import com.yunqin.bearmall.base.BaseDialog;
import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.util.FileUtils;
import com.yunqin.bearmall.util.RecyclerViewItemDecration;
import com.yunqin.bearmall.widget.ScrollViewFlexboxLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SpecificationItemsBargainDialog extends BaseDialog implements View.OnClickListener {
    private Context mContext;
    private LinearLayout mLayout;
    private int size;
    private int skuSize;
    private int count = 1;
    private BargainProductListBean.BargainProductList mBargainProductList;
    private ProductDetail.Store mStore;
    private long product_id, mSku_id;
    private List<TextView> mSpecificationTextView;
    private List<RecyclerView> mSkuListRecyclerView;
    private List<ProductDetail.SkuList> mSkuList;

    private ProductDetail.SkuList mSkuListObject;//默认选中的sku
    private TextView tv_minus, tv_count, tv_plus;
//    private ProductDetail.ProductImages mProductImage;
//    private String mProductName;

    private ImageView productImg;
    private TextView productPriceTextView, productBTPriceTextView, productSpecificationTextView;
    private Button ok;
    private RelativeLayout cancel;
    private float price;
    private List<ProductDetail.SkuList> jsonSkuList;

    /***
     *
     * @param context
     * @param sku_id
     * @param skuList --- 该商品拥有的规格
     */
    public SpecificationItemsBargainDialog(@NonNull Context context, BargainProductListBean.BargainProductList bargainProductList,int index, ProductDetail.Store store, long product_id, long sku_id, List<ProductDetail.SkuList> skuList) {
        super(context, R.style.ProductDialog);
        this.mContext = context;
        this.mBargainProductList = bargainProductList;
        this.mStore = store;
//        this.mProductName = productName;
        this.product_id = product_id;
        this.mSku_id = sku_id;
        this.mSkuList = skuList;
//        this.mProductImage = productImage;

        mSpecificationTextView = new ArrayList<>();
        mSkuListRecyclerView = new ArrayList<>();

        //为什么使用文件的方式来保留最初始的数据呢
        //由于将activity传过来的数据传给adapter了，adapter内通过改变数据来达到item的选中效果，此时已经改变了这份数据，
        //由于又需要根据原始的数据和adapter里回调回来的规格列表的数据来判断哪个sku_id作为已选中的sku_id
        //所有不能使用已经改变的mSkuList，所有使用文件的方式来获取原始数据
        try {
            String jsonCache = FileUtils.readFile2String(mContext.getCacheDir().getAbsolutePath() + File.separator + "productBargainInfo.txt", "UTF-8");
            Log.e("jsonCache", jsonCache);
            BargainProductListBean bargainProductListBean = new Gson().fromJson(jsonCache,BargainProductListBean.class);
            jsonSkuList = bargainProductListBean.getData().getBargainProductList().get(index).getSkuList();
            if(jsonSkuList == null){
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_specification_items, null);
        initView(view);
        mLayout = view.findViewById(R.id.scorllview_layout);

        size = mBargainProductList.getSpecificationItems().size();//规格分类个数
        skuSize = skuList.size();//规格分类个数

        initspecificationItemsView(context, mBargainProductList.getSpecificationItems());//构建规格名称textview
        initskuListView(context);//构建展示所有规格值的recyclerview
        addViewToLayout(size);//将view添加到scrollview中
        initskuListDefault(mSku_id, skuSize);//如果有sku_id，则将此sku_id的isDefault值设为true

        initAdapterData();

//        if(mBargainProductList.getProductImages().size()>0){
//            Glide.with(mContext).load(mBargainProductList.getProductImages().get(0).getThumbnail()).into(productImg);//显示缩略图
//        }
        Glide.with(mContext).load(mBargainProductList.getProductImages().getThumbnail()).into(productImg);//显示缩略图
        //是否用显示两个价格
        productPriceTextView.setVisibility(View.GONE);
        productBTPriceTextView.setVisibility(View.VISIBLE);

        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager m = ((Activity) context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 1);
        lp.height = (int) (d.getHeight() * 0.67);
        dialogWindow.setAttributes(lp);
    }

    public void showDialog(){

        show();
    }

//    public void setType(int type) {
//        this.type = type;
//    }

    private void initView(View view) {
        productImg = view.findViewById(R.id.dialog_product_img);
        productPriceTextView = view.findViewById(R.id.dialog_specification_items_price);
        productBTPriceTextView = view.findViewById(R.id.dialog_specification_items_bt_price);
        productSpecificationTextView = view.findViewById(R.id.dialog_specification_items_parameter);
        ok = view.findViewById(R.id.dialog_specification_items_ok);
        cancel = view.findViewById(R.id.dialog_specification_items_cancel);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private List<List<ProductDetail.SpecificationValues>> mList;

    private void initAdapterData() {
        mList = new ArrayList<>();
        //根据规格的个数，创建集合来存储相同的数据
        for (int i = 0; i < size; i++) {
            List<ProductDetail.SpecificationValues> mListItem = new ArrayList<>();
            mList.add(mListItem);
        }

        //将相同规格的数据存到集合中
        for (int j = 0; j < mSkuList.size(); j++) {
            List<ProductDetail.SpecificationValues> specificationValuesList = mSkuList.get(j).getSpecificationValues();//规格里的item个数
            for (int k = 0; k < specificationValuesList.size(); k++) {
                mList.get(k).add(specificationValuesList.get(k));
            }
        }

        //去除集合中相同规格的对象
        for (int i = 0; i < mList.size(); i++) {
            List<ProductDetail.SpecificationValues> mListItemRepetition = mList.get(i);
            for (int j = 0; j < mListItemRepetition.size(); j++) {
                for (int k = j + 1; k < mListItemRepetition.size(); k++) {
                    if (mListItemRepetition.get(j).equals(mListItemRepetition.get(k))) {
                        mListItemRepetition.remove(k);
                        k--;
                    }
                }
            }
        }

//        添加适配器
        for (int i = 0; i < mList.size(); i++) {
            List<ProductDetail.SpecificationValues> mListItemRepetition = mList.get(i);

            SkuListAdapter skuListAdapter = new SkuListAdapter(mContext, mSkuListObject, mListItemRepetition, i);
            mSkuListRecyclerView.get(i).setAdapter(skuListAdapter);

            skuListAdapter.setOnItemClickListener(new SkuListAdapter.OnItemClickListener() {
                @Override
                public void OnItemClickObject(List<ProductDetail.SpecificationValues> specificationValues) {

                    for (int j = 0; j < jsonSkuList.size(); j++) {
                        if (jsonSkuList.get(j).getSpecificationValues().equals(specificationValues)) {
                            mSkuListObject = jsonSkuList.get(j);
                        }
                    }
                    priceAndspecificationItems();

                }
            });
        }
    }

    //如果sku_id，则选取此sku_id作为默选项，否则以默认的作为默选项
    private void initskuListDefault(long sku_id, int size) {

        if (sku_id > 0) {
            for (int i = 0; i < size; i++) {
                ProductDetail.SkuList skuListObject = mSkuList.get(i);
                if (skuListObject.getSku_id() == sku_id) {
                    mSkuListObject = skuListObject;
                    priceAndspecificationItems();
                    return;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                ProductDetail.SkuList skuListObject = mSkuList.get(i);
                if (skuListObject.isDefault()) {
                    mSkuListObject = skuListObject;
                    priceAndspecificationItems();
                    return;
                }
            }
        }
    }

    //给scrollview添加规格分类的名称的Textview
    private void addViewToLayout(int size) {
        for (int i = 0; i < size; i++) {
            mLayout.addView(mSpecificationTextView.get(i));
            mLayout.addView(mSkuListRecyclerView.get(i));
        }

//        //加载商品加减的布局
//        TextView tv = initspecificationItemsTextView(mContext, "数量");//为了适配，统一使用xml布局，如果使用代码创建控件，适配有问题
//        mLayout.addView(tv);
//        View viewCount = LayoutInflater.from(mContext).inflate(R.layout.dialog_specification_items_count, null);
//        tv_minus = viewCount.findViewById(R.id.dialog_tv_minus);
//        tv_count = viewCount.findViewById(R.id.dialog_tv_count);
//        tv_plus = viewCount.findViewById(R.id.dialog_tv_plus);
//
//        tv_minus.setOnClickListener(this);
//        tv_plus.setOnClickListener(this);
//        mLayout.addView(viewCount);
    }

    //构建盒子布局管理器来管理recyclerview中item，以便item可以根据自身大小来适配布局
    private void initskuListView(Context context) {
        for (int i = 0; i < size; i++) {
            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.addItemDecoration(new RecyclerViewItemDecration(30));//设置item间距

            ScrollViewFlexboxLayoutManager scrollViewFlexboxLayoutManager = new ScrollViewFlexboxLayoutManager();
            scrollViewFlexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
            //设置是否换行
            scrollViewFlexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
            scrollViewFlexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
            recyclerView.setLayoutManager(scrollViewFlexboxLayoutManager);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, (int) mContext.getResources().getDimension(R.dimen.ds20), 0, 0);
            recyclerView.setLayoutParams(params);
            mSkuListRecyclerView.add(recyclerView);
        }
    }

    //构建规格分类名称TextView，为了适配屏幕,统一使用xml布局
    private void initspecificationItemsView(Context context, List<ProductDetail.SpecificationItems> specificationItems) {
        for (int i = 0; i < specificationItems.size(); i++) {
            initspecificationItemsTextView(context, specificationItems.get(i).getName());
        }
    }

    //使用xml方式生成view，是为了适配
    private TextView initspecificationItemsTextView(Context context, String name) {
        LinearLayout viewCount = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_specifition_items_name, null, false);
        TextView textView = (TextView) viewCount.findViewById(R.id.dialog_specification_items_name_tv);
        viewCount.removeView(textView);
        textView.setText(name);
        mSpecificationTextView.add(textView);
        return textView;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dialog_tv_minus:
                if (count == 1) {
                    return;
                } else {
                    count--;

                    if (count < mSkuListObject.getStock()) {
                        tv_plus.setClickable(true);
                        tv_plus.setTextColor(mContext.getResources().getColor(R.color.product_customer_collect));
                        tv_plus.setBackgroundResource(R.drawable.specifition_item_add_abled);
                    }

                    if (count == 1) {
                        tv_count.setText(count + "");
                        tv_minus.setClickable(false);
                        tv_minus.setTextColor(mContext.getResources().getColor(R.color.item_specification_item_minus_tv));
                        tv_minus.setBackgroundResource(R.drawable.specifition_item_minus_disabled);
                    } else {
                        tv_count.setText(count + "");
//                        tv_minus.setClickable(true);
//                        tv_minus.setTextColor(mContext.getResources().getColor(R.color.product_customer_collect));
//                        tv_minus.setBackgroundResource(R.drawable.specifition_item_minus_abled);
                    }
                    priceAndspecificationItems();
                }

                break;

            case R.id.dialog_tv_plus:
                if (count >= mSkuListObject.getStock()) {
                    Toast.makeText(mContext, "没有库存了...", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    count++;

                    if(count>=2){
                        tv_minus.setClickable(true);
                        tv_minus.setTextColor(mContext.getResources().getColor(R.color.product_customer_collect));
                        tv_minus.setBackgroundResource(R.drawable.specifition_item_minus_abled);
                    }

                    if (count == mSkuListObject.getStock()) {
                        tv_count.setText(count + "");
                        tv_plus.setClickable(false);
                        tv_plus.setTextColor(mContext.getResources().getColor(R.color.item_specification_item_minus_tv));
                        tv_plus.setBackgroundResource(R.drawable.specifition_item_add_disabled);

                    } else {
                        tv_count.setText(count + "");
//                        tv_minus.setClickable(true);
//                        tv_minus.setTextColor(mContext.getResources().getColor(R.color.product_customer_collect));
//                        tv_minus.setBackgroundResource(R.drawable.specifition_item_minus_abled);
                    }
                    priceAndspecificationItems();
                }

                break;

            case R.id.dialog_specification_items_ok:
//                if (type == ProductDetailActivity.ADD_CART) {
//                    Map map = new HashMap();
//                    map.clear();
//                    map.put("product_id", String.valueOf(product_id));
//                    map.put("sku_id", String.valueOf(mSkuListObject.getSku_id()));
//                    map.put("quantity", String.valueOf(count));
//                    mPresenter.joinCart(mContext, map);
//                    dismiss();
//                } else if (type == ProductDetailActivity.BUY_PRICE) {//法币直接购买
//                    dismiss();
//                    addDataToOrderDeatil(false);
//                } else if (type == ProductDetailActivity.BUY_BT_PRICE) {//法币+BT购买
//                    dismiss();
//                    addDataToOrderDeatil(true);
//                }
                dismiss();
                if(onBargainSpecificationItemsListener != null){
                    onBargainSpecificationItemsListener.onBargainSpecificationItems(mSkuListObject.getSku_id());
                }
//                addDataToOrderDeatil(true);
                break;

            case R.id.dialog_specification_items_cancel:
                dismiss();
                break;

            default:
                break;

        }
    }

    /*public void addDataToOrderDeatil(boolean isBTPrice){//flase则直接以法币购买，true以法币+BT方式购买

        List<CartItem.CartBrand> orderCartBrands = null;
        List<List<CartItem.ItemList>> orderItemLists = null;
        List<CartItem.ItemList> itemLists = null;
        CartItem.ItemList itemList = null;

        orderCartBrands = new ArrayList<>();
        orderItemLists = new ArrayList<>();
        itemLists = new ArrayList<>();

        orderCartBrands.clear();
        orderItemLists.clear();
        itemLists.clear();

        CartItem.CartBrand cartBrand = new CartItem.CartBrand();
        cartBrand.setCheck(true);
        cartBrand.setStoreEnabled(true);
        cartBrand.setStore_name(mStore.getStore_name());
        cartBrand.setStore_id(mStore.getStore_id());
        cartBrand.setLogo(mStore.getLogo());
        orderCartBrands.add(cartBrand);

        itemList = new CartItem.ItemList();
        itemList.setQuantity(count);
        itemList.setStock(mSkuListObject.getStock());
        itemList.setCheck(true);
        itemList.setBTPrice(isBTPrice);
//        itemList.setCaption(mBargainProductList.getCaption());
        itemList.setCaption("");
        itemList.setCart_id(0l);
        itemList.setItem_id(0l);
        itemList.setItemCreatedDate("");
        itemList.setPartBtAmount(Float.valueOf(mSkuListObject.getPartBtAmount()));
        itemList.setPartPrice(Float.valueOf(mSkuListObject.getPartPrice()));
        itemList.setPrice(Float.valueOf(mSkuListObject.getPrice()));
        itemList.setProduct_id(product_id);
        itemList.setProductActive(true);
        if(mBargainProductList.getProductImages() != null){
            itemList.setProductImages(mBargainProductList.getProductImages());
        }
        itemList.setProductList(false);
        itemList.setProductMarketable(true);
        itemList.setEditCheck(false);
        itemList.setProductName(mBargainProductList.getProductName());
        itemList.setSku_id(mSkuListObject.getSku_id());
        itemList.setSpecificationValues(mSkuListObject.getSpecificationValues());
        itemList.setStore_id(mStore.getStore_id());

        itemLists.add(itemList);
        orderItemLists.add(itemLists);

        if(onBargainSpecificationItemsListener != null){
            onBargainSpecificationItemsListener.onBargainSpecificationItems(orderCartBrands,orderItemLists,"rmbBT");
        }

//        Intent intent = new Intent(mContext, ConfirmActivity.class);
//        intent.putExtra(ORDER_BRAND,(ArrayList)orderCartBrands);
//        intent.putExtra(ORDER_ITEM,(ArrayList)orderItemLists);
//
//        if(isBTPrice){
//            intent.putExtra(ORDER_TYPE,"rmbBT");
//        }else{
//            intent.putExtra(ORDER_TYPE,"rmb");
//        }
//        mContext.startActivity(intent);

    }*/

    //当所选规格发生变化时，价格和规格名称的textview发生变化
    private void priceAndspecificationItems() {
        price = Float.valueOf(mSkuListObject.getPrice()) * count;

        // TODO qqq
        productPriceTextView.setText("价格：¥" + price);
        productBTPriceTextView.setText("价格：¥" + price );
        StringBuffer sb = new StringBuffer();
        ;
        for (int i = 0; i < mSkuListObject.getSpecificationValues().size(); i++) {
            sb.append(mSkuListObject.getSpecificationValues().get(i).getValue() + "   ");
        }

        productSpecificationTextView.setText("已选：" + sb.toString().trim());

        if (count <= mSkuListObject.getStock()) {
            ok.setClickable(true);
            ok.setBackgroundColor(mContext.getResources().getColor(R.color.main_color));
            ok.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            ok.setClickable(false);
            ok.setBackgroundColor(mContext.getResources().getColor(R.color.product_img_tip_bg));
            ok.setTextColor(mContext.getResources().getColor(R.color.white));
        }
    }

    public OnBargainSpecificationItemsListener onBargainSpecificationItemsListener;

    public interface OnBargainSpecificationItemsListener{
        void onBargainSpecificationItems(long sku_id);
    }

    public void setOnBargainSpecificationItemsListener(OnBargainSpecificationItemsListener onBargainSpecificationItemsListener) {
        this.onBargainSpecificationItemsListener = onBargainSpecificationItemsListener;
    }
}
