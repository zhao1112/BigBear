package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.CartItem;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.inter.CartProductPlusMinusCallBack;
import com.yunqin.bearmall.inter.ProductAllSelectorCallBack;
import com.yunqin.bearmall.ui.activity.ShopActivity;
import com.yunqin.bearmall.ui.fragment.presenter.TrolleyPresenter;
import com.yunqin.bearmall.widget.CircleImageView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private TrolleyPresenter trolleyPresenter;
    private TextView allProductPrice,tip;
    private List<CartItem.CartBrand> mCartBrands;
    private List<List<CartItem.ItemList>> mItemList;
    private ImageView allCheckImageView;
    private LayoutInflater mInflater;
    private boolean isEdit;

    public CartItemAdapter(Context context, TrolleyPresenter trolleyPresenter, boolean isEdit, List<CartItem.CartBrand> cartBrands,
                           List<List<CartItem.ItemList>> itemList, TextView allProductPrice,TextView tip, ImageView allCheckImageView) {
        this.mContext = context;
        this.trolleyPresenter = trolleyPresenter;
        this.isEdit = isEdit;
        this.mCartBrands = cartBrands;
        this.mItemList = itemList;
        this.allProductPrice = allProductPrice;
        this.tip = tip;
        this.allCheckImageView = allCheckImageView;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setProductData(boolean edit, List<CartItem.CartBrand> cartBrands,
                               List<List<CartItem.ItemList>> itemList) {
        this.isEdit = edit;
        this.mCartBrands = cartBrands;
        this.mItemList = itemList;

    }


    public void setData(List<List<CartItem.ItemList>> mItemList) {
        this.mItemList = mItemList;
    }

    @Override
    public int getGroupCount() {
        return mCartBrands.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mItemList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCartBrands.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CartItem.CartBrand cartBrand = mCartBrands.get(groupPosition);
        List<CartItem.ItemList> itemListList = mItemList.get(groupPosition);
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = mInflater.inflate(R.layout.item_cart_group, null);
            groupViewHolder.brandCheck = convertView.findViewById(R.id.item_cart_group_check);
            groupViewHolder.brandIcon = convertView.findViewById(R.id.item_cart_group_icon);
            groupViewHolder.brandName = convertView.findViewById(R.id.item_cart_group_name);
            groupViewHolder.brandCheckLayout = convertView.findViewById(R.id.item_cart_child_check_layout);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        try {
            if (isEdit) {
                if (cartBrand.isEditCheck()) {
                    groupViewHolder.brandCheck.setBackgroundResource(R.drawable.icon_cart_selector);
                } else {
                    groupViewHolder.brandCheck.setBackgroundResource(R.drawable.icon_cart_selector_normal);
                }
            } else {
                if (cartBrand.isCheck()) {
                    groupViewHolder.brandCheck.setBackgroundResource(R.drawable.icon_cart_selector);
                } else {
                    groupViewHolder.brandCheck.setBackgroundResource(R.drawable.icon_cart_selector_normal);
                }
            }

            groupViewHolder.brandName.setText(mCartBrands.get(groupPosition).getStore_name());
            try {
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product)).load(mCartBrands.get(groupPosition).getLogo()).into(groupViewHolder.brandIcon);
            } catch (Exception e) {
            }

            groupViewHolder.brandCheckLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEdit) {
                        if (cartBrand.isEditCheck()) {
                            cartBrand.setEditCheck(false);
                            for (int i = 0; i < itemListList.size(); i++) {
                                itemListList.get(i).setEditCheck(false);
                            }
                        } else {
                            cartBrand.setEditCheck(true);
                            for (int i = 0; i < itemListList.size(); i++) {
                                itemListList.get(i).setEditCheck(true);
                            }
                        }
                        if (productAllSelectorCallBack != null) {
                            productAllSelectorCallBack.productAllSelector(true);
                        }
                    } else {
                        if (cartBrand.isCheck()) {
                            cartBrand.setCheck(false);
                            for (int i = 0; i < itemListList.size(); i++) {
                                itemListList.get(i).setCheck(false);
                            }
                        } else {
                            cartBrand.setCheck(true);
                            for (int i = 0; i < itemListList.size(); i++) {
                                itemListList.get(i).setCheck(true);
                            }
                        }
                        if (productAllSelectorCallBack != null) {
                            productAllSelectorCallBack.productAllSelector(false);
                        }
                        CartItemAdapter.this.setAllProductPrice();
                    }
                }
            });


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShopActivity.class);
                    intent.putExtra("store_id", String.valueOf(cartBrand.getStore_id()));
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CartItem.CartBrand cartBrand = mCartBrands.get(groupPosition);
        List<CartItem.ItemList> itemListList = mItemList.get(groupPosition);
        CartItem.ItemList itemList = mItemList.get(groupPosition).get(childPosition);
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = mInflater.inflate(R.layout.item_cart_child, null);
            childViewHolder.productCheck = convertView.findViewById(R.id.item_cart_child_check);
            childViewHolder.productCheckLayout = convertView.findViewById(R.id.item_cart_child_check_layout);
            childViewHolder.productImg = convertView.findViewById(R.id.item_cart_child_product_img);
            childViewHolder.productName = convertView.findViewById(R.id.item_cart_child_product_name);
            childViewHolder.productSpecificationItem = convertView.findViewById(R.id.item_cart_child_product_specification);
            childViewHolder.btPriceLayout = convertView.findViewById(R.id.item_cart_child_bt_price_layout);
            childViewHolder.priceLayout = convertView.findViewById(R.id.item_cart_child_price_layout);
            childViewHolder.btPrice = convertView.findViewById(R.id.item_cart_child_bt_price_tv);
            childViewHolder.price = convertView.findViewById(R.id.item_cart_child_price_price);
            childViewHolder.minus = convertView.findViewById(R.id.item_cart_child_minus);
            childViewHolder.count = convertView.findViewById(R.id.item_cart_child_count);
            childViewHolder.plus = convertView.findViewById(R.id.item_cart_child_plus);
            childViewHolder.hui_yuan_zhuan_shu = convertView.findViewById(R.id.hui_yuan_zhuan_shu);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        try {

            if (itemList.isLimitMs()) {
                childViewHolder.hui_yuan_zhuan_shu.setVisibility(View.VISIBLE);
            } else {
                childViewHolder.hui_yuan_zhuan_shu.setVisibility(View.GONE);
            }


            if (itemList.isSurportMsp()) {
                childViewHolder.btPrice.setText("直购价 ¥" + itemList.getMembershipPrice());
            } else {
                childViewHolder.btPrice.setText("直购价 ¥" + itemList.getPrice());
                childViewHolder.btPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            }


            if (isEdit) {
                Log.e("getChildView", itemList.isEditCheck() + "");
                if (itemList.isEditCheck()) {
                    childViewHolder.productCheck.setBackgroundResource(R.drawable.icon_cart_selector);
                } else {
                    childViewHolder.productCheck.setBackgroundResource(R.drawable.icon_cart_selector_normal);
                }
            } else {
                //选中状态
                if (itemList.isCheck()) {
                    childViewHolder.productCheck.setBackgroundResource(R.drawable.icon_cart_selector);
                } else {
                    childViewHolder.productCheck.setBackgroundResource(R.drawable.icon_cart_selector_normal);
                }
            }

            try {
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_shopcar_product)).load(itemList.getProductImages().getThumbnail()).into(childViewHolder.productImg);
            } catch (Exception e) {
            }
            childViewHolder.productName.setText(itemList.getProductName());

            List<ProductDetail.SpecificationValues> specificationValues = itemList.getSpecificationValues();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < specificationValues.size(); i++) {
                sb.append(specificationValues.get(i).getValue() + "  ");
            }
            childViewHolder.productSpecificationItem.setText(sb.toString());

            childViewHolder.price.setText("¥" + itemList.getPrice());

            //选中不同的价格
//            if (itemList.isBTPrice()) {
//                childViewHolder.btPriceLayout.setBackgroundResource(R.drawable.cart_item_price_selector);
//                childViewHolder.btPrice.setTextColor(mContext.getResources().getColor(R.color.white));
//
//                childViewHolder.priceLayout.setBackgroundResource(R.drawable.cart_item_price_normal);
//                childViewHolder.price.setTextColor(mContext.getResources().getColor(R.color.main_color));
//            } else {
//                childViewHolder.btPriceLayout.setBackgroundResource(R.drawable.cart_item_price_normal);
//                childViewHolder.btPrice.setTextColor(mContext.getResources().getColor(R.color.main_color));
//
//                childViewHolder.priceLayout.setBackgroundResource(R.drawable.cart_item_price_selector);
//                childViewHolder.price.setTextColor(mContext.getResources().getColor(R.color.white));
//            }

            childViewHolder.count.setText(itemList.getQuantity() + "");

            if (itemList.getQuantity() > 1) {
                childViewHolder.minus.setClickable(true);
                childViewHolder.minus.setBackgroundResource(R.drawable.specifition_item_minus_abled);
                childViewHolder.minus.setTextColor(mContext.getResources().getColor(R.color.product_customer_collect));
            } else {
                childViewHolder.minus.setClickable(false);
                childViewHolder.minus.setBackgroundResource(R.drawable.specifition_item_minus_disabled);
                childViewHolder.minus.setTextColor(mContext.getResources().getColor(R.color.item_specification_item_minus_tv));
            }

            if (itemList.getQuantity() == itemList.getStock()) {
                childViewHolder.plus.setClickable(false);
                childViewHolder.plus.setBackgroundResource(R.drawable.specifition_item_add_disabled);
                childViewHolder.plus.setTextColor(mContext.getResources().getColor(R.color.item_specification_item_minus_tv));
            } else if (itemList.getQuantity() < itemList.getStock()) {
                childViewHolder.plus.setClickable(true);
                childViewHolder.plus.setBackgroundResource(R.drawable.specifition_item_add_abled);
                childViewHolder.plus.setTextColor(mContext.getResources().getColor(R.color.product_customer_collect));
            }

            childViewHolder.productCheckLayout.setOnClickListener(v -> {
                if (isEdit) {
                    if (itemList.isEditCheck()) {
                        itemList.setEditCheck(false);
                        cartBrand.setEditCheck(false);
                    } else {
                        itemList.setEditCheck(true);
                        for (int i = 0; i < itemListList.size(); i++) {
                            if (!itemListList.get(i).isEditCheck()) {
                                cartBrand.setEditCheck(false);
                                break;
                            } else {
                                cartBrand.setEditCheck(true);
                            }
                        }
                    }
                    if (productAllSelectorCallBack != null) {
                        productAllSelectorCallBack.productAllSelector(true);
                    }
                } else {
                    if (itemList.isCheck()) {
                        itemList.setCheck(false);
                        cartBrand.setCheck(false);
                    } else {
                        itemList.setCheck(true);
                        for (int i = 0; i < itemListList.size(); i++) {
                            if (!itemListList.get(i).isCheck()) {
                                cartBrand.setCheck(false);
                                break;
                            } else {
                                cartBrand.setCheck(true);
                            }
                        }
                    }
                    if (productAllSelectorCallBack != null) {
                        productAllSelectorCallBack.productAllSelector(false);
                    }
                    CartItemAdapter.this.setAllProductPrice();
                }
                CartItemAdapter.this.notifyDataSetChanged();
            });

//            //以大熊价购买
//            childViewHolder.btPriceLayout.setOnClickListener(v -> {
//                if (isEdit) {
//                    return;
//                } else {
//                    itemList.setBTPrice(true);
//                    setAllProductPrice();
//                    notifyDataSetChanged();
//                }
//            });
//
//            //以法币购买
//            childViewHolder.priceLayout.setOnClickListener(v -> {
//                if (isEdit) {
//                    return;
//                } else {
//                    itemList.setBTPrice(false);
//                    setAllProductPrice();
//                    notifyDataSetChanged();
//                }
//            });

            //商品减
            childViewHolder.minus.setOnClickListener(v -> {
                int quantity = itemList.getQuantity();
                if (quantity == 1) {
                    return;
                } else {
                    Map map = new HashMap();
                    map.clear();
                    map.put("item_id", String.valueOf(itemList.getItem_id()));
                    map.put("type", String.valueOf(0));
                    map.put("quantity", String.valueOf(1));

                    //把当前的item对象传递给fragment，
                    if (cartProductPlusMinusCallBack != null) {
                        cartProductPlusMinusCallBack.onCartProductPlusMinus(itemList, 0);
                    }

                    trolleyPresenter.setItemQuantity(mContext, map);
                }
            });

            //商品加
            childViewHolder.plus.setOnClickListener(v -> {
                int quantity = itemList.getQuantity();
                if (quantity == itemList.getStock()) {
                    Toast.makeText(mContext, "已到达最大库存量了!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Map map = new HashMap();
                    map.clear();
                    map.put("item_id", String.valueOf(itemList.getItem_id()));
                    map.put("type", String.valueOf(1));
                    map.put("quantity", String.valueOf(1));

                    //把当前的item对象传递给fragment，
                    if (cartProductPlusMinusCallBack != null) {
                        cartProductPlusMinusCallBack.onCartProductPlusMinus(itemList, 1);
                    }
                    trolleyPresenter.setItemQuantity(mContext, map);
                }
            });

            convertView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NewProductDetailActivity.class);
                intent.putExtra("productId", "" + itemList.getProduct_id());
                intent.putExtra("sku_id", "" + itemList.getSku_id());
                mContext.startActivity(intent);
            });
        } catch (Resources.NotFoundException e) {
        }
        return convertView;
    }

    public void setAllProductPrice() {
        float price = 0f;
        float btPrice = 0f;
        float youhuiPrice = 0f;
        for (int i = 0; i < mItemList.size(); i++) {
            List<CartItem.ItemList> itemLists = mItemList.get(i);
            for (int j = 0; j < itemLists.size(); j++) {
                CartItem.ItemList itemList = itemLists.get(j);
                if (itemList.isCheck()) {

                    if(itemList.isSurportMsp()){
                        BigDecimal youhui1 = new BigDecimal(String.valueOf(itemList.getMembershipPrice()));
                        BigDecimal youhui2 = new BigDecimal(String.valueOf(itemList.getPrice()));
                        BigDecimal youhui3 = new BigDecimal(String.valueOf(itemList.getQuantity()));

                        BigDecimal youhui4 = new BigDecimal(String.valueOf(youhuiPrice));
                        youhuiPrice=youhui2.subtract(youhui1).multiply(youhui3).add(youhui4).floatValue();
                    }

                    if (itemList.isBTPrice()) {
                        //使用大熊币付款
                        BigDecimal mPartPriceBigDecimal = new BigDecimal(String.valueOf(itemList.getMembershipPrice()));
                        BigDecimal mQuantityBigDecimal = new BigDecimal(String.valueOf(itemList.getQuantity()));
                        BigDecimal mPartBtAmountBigDecimal = new BigDecimal(String.valueOf(itemList.getPartBtAmount()));

                        BigDecimal mPrice = new BigDecimal(String.valueOf(price));
                        BigDecimal mBtPrice = new BigDecimal(String.valueOf(btPrice));

                        price = mPartPriceBigDecimal.multiply(mQuantityBigDecimal).add(mPrice).floatValue();
                        btPrice = mPartBtAmountBigDecimal.multiply(mQuantityBigDecimal).add(mBtPrice).floatValue();

                    } else {

                        BigDecimal mPrice = new BigDecimal(String.valueOf(price));
                        BigDecimal mPriceBigDecimal = new BigDecimal(String.valueOf(itemList.getPrice()));
                        BigDecimal mQuantityBigDecimal = new BigDecimal(String.valueOf(itemList.getQuantity()));

                        price = mPriceBigDecimal.multiply(mQuantityBigDecimal).add(mPrice).floatValue();

                    }
                }
            }
        }

        allProductPrice.setText("¥" + price + "+BC" + btPrice);
        if(BearMallAplication.getInstance().getUser().getData().getMember().isMember()){
            tip.setText("直购价已经省"+youhuiPrice+"元");
        }else {
//            tip.setText("立即成为金熊会员，此单可省"+youhuiPrice+"元");
        }

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView brandName;
        CircleImageView brandIcon;
        ImageView brandCheck;
        RelativeLayout brandCheckLayout;
    }

    class ChildViewHolder {
        ImageView productCheck;
        ImageView productImg;
        TextView productName, productSpecificationItem;
        TextView btPrice, price;
        TextView hui_yuan_zhuan_shu;
        LinearLayout btPriceLayout, priceLayout;
        TextView minus, count, plus;
        RelativeLayout productCheckLayout;
    }

    public void setProductAllSelectorCallBack(ProductAllSelectorCallBack productAllSelectorCallBack) {
        this.productAllSelectorCallBack = productAllSelectorCallBack;
    }

    private ProductAllSelectorCallBack productAllSelectorCallBack;

    public void setCartProductPlusMinusCallBack(CartProductPlusMinusCallBack cartProductPlusMinusCallBack) {
        this.cartProductPlusMinusCallBack = cartProductPlusMinusCallBack;
    }

    private CartProductPlusMinusCallBack cartProductPlusMinusCallBack;

}
