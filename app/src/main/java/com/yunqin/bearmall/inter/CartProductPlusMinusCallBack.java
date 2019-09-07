package com.yunqin.bearmall.inter;

import com.yunqin.bearmall.bean.CartItem;

public interface CartProductPlusMinusCallBack {
    void onCartProductPlusMinus(CartItem.ItemList itemList,int type);
}
