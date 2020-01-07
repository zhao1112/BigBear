package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.NewOrderBean;
import com.newversions.NewOrderChildBean;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.CartItemAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.CartItem;
import com.yunqin.bearmall.bean.CartItemCount;
import com.yunqin.bearmall.bean.MessageItemCount;
import com.yunqin.bearmall.eventbus.TrolleyCountEvent;
import com.yunqin.bearmall.inter.CartProductPlusMinusCallBack;
import com.yunqin.bearmall.inter.ProductAllSelectorCallBack;
import com.yunqin.bearmall.ui.activity.ConfirmActivity;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.VipCenterActivity;
import com.yunqin.bearmall.ui.fragment.contract.TrolleyContract;
import com.yunqin.bearmall.ui.fragment.presenter.TrolleyPresenter;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.DotView;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 */
public class TrolleyFragment extends BaseFragment implements TrolleyContract.UI, ProductAllSelectorCallBack, CartProductPlusMinusCallBack {

    public static final String ORDER_BRAND = "order_brand";
    public static final String ORDER_ITEM = "order_item";
    public static final String ORDER_TYPE = "order_type";
    // TODO  添加类型  用来区分 普通订单 和 砍价订单
    public static final String TYPE_ORDER = "type_order";
    public static final String BARGAIN_RECORD_ID = "bargainRecord_id";

    @BindView(R.id.fillStatusBarView)
    View fillStatusBarView;
    @BindView(R.id.fragment_trolley_empty_layout)
    LinearLayout cartEmptyLayout;
    @BindView(R.id.fragment_trolley_has_product_layout)
    RelativeLayout cartHasProductLayout;
    @BindView(R.id.cart_expandable_listView)
    ExpandableListView expandableListView;
    @BindView(R.id.item_cart_all_check)
    ImageView allCheckImageView;
    @BindView(R.id.cart_all_product_price)
    TextView allProductPrice;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.cart_selector_product_count)
    TextView selectorProductCount;
    @BindView(R.id.cart_back_edit)
    TextView edit;
    @BindView(R.id.cart_buy_layout)
    LinearLayout buyLayout;
    @BindView(R.id.cart_edit_layout)
    LinearLayout editLayout;
    @BindView(R.id.empty)
    CustomRecommendView empty;
    @BindView(R.id.login_text_layout)
    TextView login_text_layout;
    // 无网络连接View
    @BindView(R.id.not_net_view)
    LinearLayout not_net_view;
    @BindView(R.id.cart_back_img)
    ImageView cart_back_img;


    @OnClick({R.id.item_cart_child_check_layout, R.id.cart_back_edit, R.id.cart_selector_product_count,
            R.id.cart_add_collect, R.id.cart_delete, R.id.login_text_layout, R.id.cart_back_img,
            R.id.xiao_xi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiao_xi:
                InformationFragmentActivity.start(getActivity());
                break;
            case R.id.login_text_layout:
                LoginActivity.starActivity(getActivity());
                break;
            case R.id.item_cart_child_check_layout://全选按钮
                if (isEdit) {
                    if (isAllSelector) {
                        setAllDisSelector(true);
                    } else {
                        setAllSelector(true);
                    }
                    productAllSelector(true);//处理底部菜单价格以及结算按钮
                } else {
                    if (isAllSelector) {
                        setAllDisSelector(false);
                    } else {
                        setAllSelector(false);
                    }
                    productAllSelector(false);//处理底部菜单价格以及结算按钮
                }
                break;
            case R.id.cart_back_edit://编辑/完成
                if (isEdit) {
                    isEdit = false;

                    edit.setText("编辑");
                    buyLayout.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.GONE);

                    //点击完成时，遍历结合，根据数据值，判断全选按钮状态
                    out:
                    for (int i = 0; i < itemLists.size(); i++) {
                        List<CartItem.ItemList> itemList = itemLists.get(i);
                        for (int j = 0; j < itemList.size(); j++) {
                            if (itemList.get(j).isCheck()) {
                                isAllSelector = true;
                            } else {
                                isAllSelector = false;
                                break out;
                            }
                        }
                    }
                    if (isAllSelector) {
                        allCheckImageView.setImageResource(R.drawable.icon_cart_selector);
                    } else {
                        allCheckImageView.setImageResource(R.drawable.icon_cart_selector_normal);
                    }
                    cartItemAdapter.setProductData(false, cartBrands, itemLists);
                    cartItemAdapter.notifyDataSetChanged();
                } else {
                    isEdit = true;

                    edit.setText("完成");
                    buyLayout.setVisibility(View.GONE);
                    editLayout.setVisibility(View.VISIBLE);
                    for (int i = 0; i < cartBrands.size(); i++) {
                        cartBrands.get(i).setEditCheck(false);
                    }

                    //点击编辑时，全选按钮置为未选中
                    for (int i = 0; i < itemLists.size(); i++) {
                        List<CartItem.ItemList> itemList = itemLists.get(i);
                        for (int j = 0; j < itemList.size(); j++) {
                            itemList.get(j).setEditCheck(false);
                        }
                    }
                    isAllSelector = false;
                    allCheckImageView.setImageResource(R.drawable.icon_cart_selector_normal);
                    cartItemAdapter.setProductData(true, cartBrands, itemLists);
                    cartItemAdapter.notifyDataSetChanged();
                }

                break;

            case R.id.cart_add_collect://收藏
                StringBuffer sbCollect = new StringBuffer();
                Map mapCollect = new HashMap();
                mapCollect.clear();
                for (int i = 0; i < itemLists.size(); i++) {
                    List<CartItem.ItemList> itemList = itemLists.get(i);
                    for (int j = 0; j < itemList.size(); j++) {
                        if (itemList.get(j).isEditCheck()) {
                            sbCollect.append(itemList.get(j).getItem_id() + ",");
                        }
                    }
                }
                if (sbCollect.toString().length() > 0) {
                    String item_ids = sbCollect.toString().substring(0, sbCollect.toString().length() - 1);//去掉最后一个逗号

                    mapCollect.put("item_ids", item_ids);

                    showLoading();
                    trolleyPresenter.removeCartItemsForFavorite(getActivity(), mapCollect);

                }
                break;
            case R.id.cart_delete://删除
                StringBuffer sbDelete = new StringBuffer();
                Map mapDelete = new HashMap();
                mapDelete.clear();

                for (int i = 0; i < itemLists.size(); i++) {
                    List<CartItem.ItemList> itemList = itemLists.get(i);
                    for (int j = 0; j < itemList.size(); j++) {
                        if (itemList.get(j).isEditCheck()) {
                            sbDelete.append(itemList.get(j).getItem_id() + ",");
                        }
                    }
                }

                if (sbDelete.toString().length() > 0) {
                    String item_ids = sbDelete.toString().substring(0, sbDelete.toString().length() - 1);//去掉最后一个逗号
                    mapDelete.put("item_ids", item_ids);

                    showLoading();
                    trolleyPresenter.removeCartItems(getActivity(), mapDelete);

                }
                break;
            case R.id.cart_selector_product_count://结算
                addDataToOrderDeatil();
                break;

            case R.id.cart_back_img:
                getActivity().finish();
                break;
        }
    }

    public void addDataToOrderDeatil() {


//-----------------------------

        boolean isDis = false;// 是否有会员专属

        List<NewOrderBean> newOrderBeans = new ArrayList<>();

        for (int i = 0; i < itemLists.size(); i++) {
            List<NewOrderChildBean> newOrderChildBeans = new ArrayList<>();
            List<CartItem.ItemList> itemListsTemp = itemLists.get(i);
            for (int j = 0; j < itemListsTemp.size(); j++) {
                CartItem.ItemList itemList = itemListsTemp.get(j);
                if (itemList.isCheck()) {

                    if (itemList.isLimitMs()) {
                        isDis = true;
                    }

                    StringBuffer stringBuffer = new StringBuffer();
                    for (int y = 0; y < itemList.getSpecificationValues().size(); y++) {
                        stringBuffer.append(itemList.getSpecificationValues().get(y).getValue() + " ");
                    }
                    String name = itemList.getProductName();
                    String img = itemList.getProductImages().getSource();
                    String price = itemList.getPrice() + "";
                    String guiGe = stringBuffer.toString();
                    String count = itemList.getQuantity() + "";
                    int productId = (int) itemList.getProduct_id();
                    int skuId = (int) itemList.getSku_id();
                    int isMsp = itemList.isBTPrice() ? 1 : 0; // 0 原价 、1 会员价
                    NewOrderChildBean newOrderChildBean = new NewOrderChildBean(name, img, price, guiGe, count, productId, skuId, isMsp);
                    newOrderChildBeans.add(newOrderChildBean);
                }
            }

            if (newOrderChildBeans.size() > 0) {
                String shopName = cartBrands.get(i).getStore_name();
                String shopImg = cartBrands.get(i).getLogo();

                NewOrderBean newOrderBean = new NewOrderBean(shopName, shopImg, newOrderChildBeans);
                newOrderBeans.add(newOrderBean);
            }
        }


        if (!BearMallAplication.getInstance().getUser().getData().getMember().isMember() && isDis) {

            new ICustomDialog.Builder(getActivity())
                    // 设置布局
                    .setLayoutResId(R.layout.join_member_layout_car)
                    // 点击空白是否消失
                    .setCanceledOnTouchOutside(false)
                    // 点击返回键是否消失
                    .setCancelable(false)
                    // 设置Dialog的绝对位置
                    .setDialogPosition(Gravity.CENTER)
                    // 设置自定义动画
                    .setAnimationResId(0)
                    // 设置监听ID
                    .setListenedItems(new int[]{R.id.join_member_no, R.id.join_member_ok})
                    // 设置回掉
                    .setOnDialogItemClickListener(new ICustomDialog.OnDialogItemClickListener() {
                        @Override
                        public void onDialogItemClick(ICustomDialog thisDialog, View clickView) {
                            if (clickView.getId() == R.id.join_member_ok) {
                                startActivity(new Intent(getActivity(), VipCenterActivity.class));
                            }
                            thisDialog.dismiss();
                        }
                    })
                    .build().show();
        } else {
            Intent intent = new Intent(getActivity(), ConfirmActivity.class);
            intent.putExtra(ConfirmActivity.INTENT_DATA, (ArrayList) newOrderBeans);
            intent.putExtra("type_order", "0");
            intent.putExtra(ORDER_TYPE, "Trolley");
            startActivity(intent);
        }


    }

    private TrolleyPresenter trolleyPresenter;
    private CartItem cartItem;
    private List<CartItem.CartItemList> cartItemList = new ArrayList<>();
    private List<CartItem.CartBrand> cartBrands = new ArrayList<>();
    private List<List<CartItem.ItemList>> itemLists = new ArrayList<>();
    private CartItemAdapter cartItemAdapter;
    private boolean isAllSelector = false;
    private boolean isEdit = false;


    @BindView(R.id.xiao_xi)
    ImageView xiao_xi;


    @Override
    public int layoutId() {
        return R.layout.fragment_trolley;
    }

    @Override
    public void init() {
        empty.hideTopLayout();
        empty.setTvBottom("-推荐商品-");
        empty.setManager(new GridLayoutManager(getActivity(), 2));
        empty.start(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null) {
            boolean isBack = bundle.getBoolean("isBack");
            if (isBack) {
                cart_back_img.setVisibility(View.VISIBLE);
                xiao_xi.setVisibility(View.GONE);
            } else {
                cart_back_img.setVisibility(View.GONE);
                xiao_xi.setVisibility(View.VISIBLE);
            }
        }
    }

    public static TrolleyFragment newInstance() {
        return new TrolleyFragment();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
    }

    @Override
    public void showXLoading() {
        showLoading();
    }

    //获取购物车列表回调
    @Override
    public void getCartItemList(String data) {
        hiddenLoadingView();
        cartBrands.clear();
        itemLists.clear();
        cartItem = new Gson().fromJson(data, CartItem.class);
        Log.e("TrolleyFragment", cartItem.getData().toString());

        expandableListView.setGroupIndicator(null);
        cartItemList = cartItem.getData().getCartItemList();
        for (int i = 0; i < cartItemList.size(); i++) {
            CartItem.CartBrand cartBrand = new CartItem.CartBrand();
            cartBrand.setLogo(cartItemList.get(i).getLogo());
            cartBrand.setStore_id(cartItemList.get(i).getStore_id());
            cartBrand.setStore_name(cartItemList.get(i).getStore_name());
            cartBrand.setStoreEnabled(cartItemList.get(i).isStoreEnabled());
            cartBrand.setCheck(false);

            cartBrands.add(cartBrand);
            List<CartItem.ItemList> itemListsTemp = cartItemList.get(i).getItemList();
            for (int j = 0; j < itemListsTemp.size(); j++) {
                itemListsTemp.get(j).setCheck(false);

                if (itemListsTemp.get(j).isSurportMsp()) {// 支持会员价
                    if (BearMallAplication.getInstance().getUser().getData().getMember().isMember()) {// 是会员
                        itemListsTemp.get(j).setBTPrice(true);
                    } else {// 不是会员
                        itemListsTemp.get(j).setBTPrice(false);
                    }
                } else {
                    itemListsTemp.get(j).setBTPrice(false);
                }

            }
            itemLists.add(itemListsTemp);
        }

        cartItemAdapter = new CartItemAdapter(getActivity(), trolleyPresenter, isEdit, cartBrands, itemLists, allProductPrice, tip, allCheckImageView);

        cartItemAdapter.setProductAllSelectorCallBack(this);
        cartItemAdapter.setCartProductPlusMinusCallBack(this);
        expandableListView.setAdapter(cartItemAdapter);
        setListViewHeightBasedOnChildren(expandableListView);

        //重新请求时，重置所有状态
        if (isEdit) {
            edit.setText("完成");
            buyLayout.setVisibility(View.GONE);
            editLayout.setVisibility(View.VISIBLE);
        } else {
            edit.setText("编辑");
            buyLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
        }

        productAllSelector(isEdit);

        //二级列表默认全部展开
        int groupCount = expandableListView.getCount();
        for (int i = 0; i < groupCount; i++) {
            expandableListView.expandGroup(i);
        }
        //不能点击收缩：
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        cartItemAdapter.setAllProductPrice();//价格计算放到adapter中，此处直接调用adapter中的方法

    }

    public void setListViewHeightBasedOnChildren(ExpandableListView listView) {
        // 获取ListView对应的Adapter
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            // pre -condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listgroupItem = listAdapter.getGroupView(i, true, null, listView);
            listgroupItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listgroupItem.getMeasuredHeight(); // 统计所有子项的总高度
            System.out.println("height : group" + i + "次" + totalHeight);
            for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                View listchildItem = listAdapter.getChildView(i, j, false, null, listView);
                listchildItem.measure(0, 0); // 计算子项View 的宽高
                totalHeight += listchildItem.getMeasuredHeight(); // 统计所有子项的总高度
                System.out.println("height :" + "group:" + i + " child:" + j + "次" + totalHeight);
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    //获取购物车商品个数回调
    @Override
    public void getCartItemCount(String data) {
        hiddenLoadingView();
        Log.e("getCartItemCount", data);
        CartItemCount cartItemCount = new Gson().fromJson(data, CartItemCount.class);

        EventBus.getDefault().post(new TrolleyCountEvent(cartItemCount.getData().getItemCount()));//向activity发送请求成功的数据

        if (cartItemCount.getData().getItemCount() > 0) {
            edit.setVisibility(View.VISIBLE);
            cartEmptyLayout.setVisibility(View.GONE);
            cartHasProductLayout.setVisibility(View.VISIBLE);

            // TODO 解决魅族123123bug
//            cartBrands = new ArrayList<>();
//            itemLists = new ArrayList<>();
        } else {
            edit.setVisibility(View.INVISIBLE);
            cartEmptyLayout.setVisibility(View.VISIBLE);
            cartHasProductLayout.setVisibility(View.GONE);
        }
    }

    //购物车商品移入收藏回调
    @Override
    public void removeCartItemsForFavorite(String data) {
        hiddenLoadingView();

        //商品移入收藏成功后，重新获取购物车数量，将数据发回给HomeActivity
        Constans.params.clear();
        trolleyPresenter.getCartItemCount(getActivity(), Constans.params);

        for (int i = 0; i < itemLists.size(); i++) {
            List<CartItem.ItemList> itemList = itemLists.get(i);
            for (int j = 0; j < itemList.size(); j++) {
                if (itemList.get(j).isEditCheck()) {
                    itemList.remove(itemList.get(j));
                    j--;
                }
            }

            //店铺下没有商品，则移除此店铺
            if (itemList.size() == 0) {
                itemLists.remove(i);
                cartBrands.remove(i);
                i--;
            }
        }

        if (itemLists.size() == 0) {
            isEdit = false;
            edit.setText("编辑");
            edit.setVisibility(View.INVISIBLE);
            cartEmptyLayout.setVisibility(View.VISIBLE);
            cartHasProductLayout.setVisibility(View.GONE);
        }

        cartItemAdapter.notifyDataSetChanged();
        cartItemAdapter.setAllProductPrice();

    }

    //购物车商品项移除回调
    @Override
    public void removeCartItems(String data) {
        hiddenLoadingView();

        //商品删除成功后，重新获取购物车数量，将数据发回给HomeActivity
        Constans.params.clear();
        trolleyPresenter.getCartItemCount(getActivity(), Constans.params);

        for (int i = 0; i < itemLists.size(); i++) {
            List<CartItem.ItemList> itemList = itemLists.get(i);
            for (int j = 0; j < itemList.size(); j++) {
                if (itemList.get(j).isEditCheck()) {
                    itemList.remove(itemList.get(j));
                    j--;
                }
            }

            //店铺下没有商品，则移除此店铺
            if (itemList.size() == 0) {
                itemLists.remove(i);
                cartBrands.remove(i);
                i--;
            }
        }

        if (itemLists.size() == 0) {
            isEdit = false;
            edit.setText("编辑");
            edit.setVisibility(View.INVISIBLE);
            cartEmptyLayout.setVisibility(View.VISIBLE);
            cartHasProductLayout.setVisibility(View.GONE);
        }

        cartItemAdapter.notifyDataSetChanged();
        cartItemAdapter.setAllProductPrice();

    }

    //通过显示/隐藏来设置状态栏图标的颜色
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

            requestData();


            setDarkStatusIcon(true);

            //每次进入购物车，刷新页面
            edit.setText("编辑");
            isEdit = false;

            //todo 王志伟   未登录 不去请求购物车数据  显示 caerEmptyLayout   未登录右上角显示登陆字样  给用户一个登陆的入口
            if (BearMallAplication.getInstance().getUser() == null) {
                edit.setVisibility(View.INVISIBLE);
                login_text_layout.setVisibility(View.VISIBLE);
                cartEmptyLayout.setVisibility(View.VISIBLE);
                cartHasProductLayout.setVisibility(View.GONE);
            } else {
                showLoading();
                login_text_layout.setVisibility(View.GONE);
                Map map = new HashMap();
                trolleyPresenter = new TrolleyPresenter(this);
                trolleyPresenter.getCartItemCount(getActivity(), map);

            }

        }
    }

    //todo  购物车接口请求了两边  不知道有没有影响
    //todo 王志伟 onHiddenChanged（fragment切换会调用，但是打开一个页面再返回不调用  onresume相反） 和 onResume
    //todo 登陆成功后 让购物车刷新数据  没用EventBus 直接写onResume里面了
    @Override
    public void onResume() {
        super.onResume();
        if (BearMallAplication.getInstance().getUser() != null) {
            showLoading();
            requestData();
            login_text_layout.setVisibility(View.GONE);
            Map map = new HashMap();
            trolleyPresenter = new TrolleyPresenter(this);
            trolleyPresenter.getCartItemCount(getActivity(), map);
        }
    }

    //通过单个商品是否选中，店铺是否选中，来处理全部按钮是否选中，以及选中的个数
    @Override
    public void productAllSelector(boolean isEdit) {
        boolean isAll = false;
        //所有商品状态置为选中状态
        out:
        for (int i = 0; i < itemLists.size(); i++) {
            List<CartItem.ItemList> itemListsTemp = itemLists.get(i);
            for (int j = 0; j < itemListsTemp.size(); j++) {
                if (isEdit) {
                    if (itemListsTemp.get(j).isEditCheck()) {
                        isAll = true;
                    } else {
                        isAll = false;
                        break out;
                    }
                } else {
                    if (itemListsTemp.get(j).isCheck()) {
                        isAll = true;
                    } else {
                        isAll = false;
                        break out;
                    }
                }
            }
        }
        isAllSelector = isAll;
        if (isAllSelector) {
            allCheckImageView.setImageResource(R.drawable.icon_cart_selector);
        } else {
            allCheckImageView.setImageResource(R.drawable.icon_cart_selector_normal);
        }

        int count = 0;
        out:
        for (int i = 0; i < itemLists.size(); i++) {
            List<CartItem.ItemList> itemListsTemp = itemLists.get(i);
            for (int j = 0; j < itemListsTemp.size(); j++) {
                if (isEdit) {
                    if (itemListsTemp.get(j).isEditCheck()) {
                        count++;
                    }
                } else {
                    if (itemListsTemp.get(j).isCheck()) {
                        count++;
                    }
                }
            }
        }

        if (!isEdit) {
            if (count == 0) {
                tip.setVisibility(View.INVISIBLE);
                allProductPrice.setVisibility(View.INVISIBLE);
                selectorProductCount.setClickable(false);
                selectorProductCount.setText("去结算");
                selectorProductCount.setBackgroundColor(getActivity().getResources().getColor(R.color.home_text_unselect));
            } else {
                allProductPrice.setVisibility(View.VISIBLE);
                tip.setVisibility(View.VISIBLE);
                selectorProductCount.setClickable(true);
                selectorProductCount.setText("去结算(" + count + ")");
                selectorProductCount.setBackgroundColor(getActivity().getResources().getColor(R.color.main_color));
            }
        }

        cartItemAdapter.notifyDataSetChanged();
        cartItemAdapter.setAllProductPrice();

    }

    //全选
    public void setAllSelector(boolean isEdit) {
        isAllSelector = true;
        allCheckImageView.setImageResource(R.drawable.icon_cart_selector);
        //所有店铺状态置为选中状态
        for (int i = 0; i < cartBrands.size(); i++) {
            if (isEdit) {
                cartBrands.get(i).setEditCheck(true);
            } else {
                cartBrands.get(i).setCheck(true);
            }
        }
        //所有商品状态置为选中状态
        for (int i = 0; i < itemLists.size(); i++) {
            List<CartItem.ItemList> itemListsTemp = itemLists.get(i);
            for (int j = 0; j < itemListsTemp.size(); j++) {
                if (isEdit) {
                    itemListsTemp.get(j).setEditCheck(true);
                } else {
                    itemListsTemp.get(j).setCheck(true);
                }
            }
        }
        cartItemAdapter.setProductData(isEdit, cartBrands, itemLists);
        cartItemAdapter.notifyDataSetChanged();
    }


    //取消全选
    public void setAllDisSelector(boolean isEdit) {

        isAllSelector = false;
        allCheckImageView.setImageResource(R.drawable.icon_cart_selector_normal);
        //所有店铺状态置为取消状态
        for (int i = 0; i < cartBrands.size(); i++) {
            if (isEdit) {
                cartBrands.get(i).setEditCheck(false);
            } else {
                cartBrands.get(i).setCheck(false);
            }

        }
        //所有商品状态置为取消状态
        for (int i = 0; i < itemLists.size(); i++) {
            List<CartItem.ItemList> itemListsTemp = itemLists.get(i);
            for (int j = 0; j < itemListsTemp.size(); j++) {
                if (isEdit) {
                    itemListsTemp.get(j).setEditCheck(false);
                } else {
                    itemListsTemp.get(j).setCheck(false);
                }
            }
        }
        cartItemAdapter.setProductData(isEdit, cartBrands, itemLists);
        cartItemAdapter.notifyDataSetChanged();

    }


    private CartItem.ItemList mItemListObject;
    private int mType;

    @Override
    public void onCartProductPlusMinus(CartItem.ItemList itemList, int type) {
        showLoading();
        this.mItemListObject = itemList;
        this.mType = type;
    }

    //购物车商品项数量调整
    @Override
    public void setItemQuantity(String data) {
        Log.e("setItemQuantity", data);
        hiddenLoadingView();
        for (int i = 0; i < itemLists.size(); i++) {
            List<CartItem.ItemList> itemList = itemLists.get(i);
            for (int j = 0; j < itemList.size(); j++) {
                if (itemList.get(j).getItem_id() == mItemListObject.getItem_id()) {
                    if (mType == 0) {
                        int quantity = itemList.get(j).getQuantity();
                        quantity--;
                        itemList.get(j).setQuantity(quantity);
                    } else {
                        int quantity = itemList.get(j).getQuantity();
                        quantity++;
                        itemList.get(j).setQuantity(quantity);
                    }
                }
            }
        }

        cartItemAdapter.setAllProductPrice();
        cartItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onShowNotNetView(boolean onShow) {
        hiddenLoadingView();
        not_net_view.setVisibility(onShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onHideLoading() {
        hiddenLoadingView();
    }

    @BindView(R.id.reset_load_data)
    HighlightButton reset_load_data;

    @OnClick(R.id.reset_load_data)
    public void onSelect(View view) {
        showLoading();
        login_text_layout.setVisibility(View.GONE);
        Map map = new HashMap();
        if (trolleyPresenter == null) {
            trolleyPresenter = new TrolleyPresenter(this);
        }
        trolleyPresenter.getCartItemCount(getActivity(), map);
    }






















    @BindView(R.id.dot_view)
    DotView dot_view;

    public void requestData() {
        Map timeMap = new HashMap();
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) != 0l) {
            timeMap.put("lastTime0", (long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) != 0l) {
            timeMap.put("lastTime1", (long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) != 0l) {
            timeMap.put("lastTime2", (long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) != 0l) {
            timeMap.put("lastTime3", (long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) + "");
        }

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getUnreadMessageCount(timeMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                MessageItemCount messageItemCount = new Gson().fromJson(data, MessageItemCount.class);
                int count = messageItemCount.getData().getUnreadMessageCount();
                dot_view.setShowNum(count);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }















}
