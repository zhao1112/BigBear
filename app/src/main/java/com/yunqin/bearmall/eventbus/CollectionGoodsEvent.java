package com.yunqin.bearmall.eventbus;

/**
 * @author AYWang
 * @create 2018/7/18
 * @Describe
 */
public class CollectionGoodsEvent {
    private int collection_index;
   public CollectionGoodsEvent(int  collection_index){
       this.collection_index = collection_index;
   }

    public int getCollectionIndex(){
        return collection_index;
    }
}
