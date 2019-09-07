package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MyTransferDialogAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Voucher;
import com.yunqin.bearmall.eventbus.VoucherEvent;
import com.yunqin.bearmall.ui.activity.contract.VoucherDialogActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.MyTransferDialogVoucherPersenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/23
 * @Describe
 */
public class VoucherDialogActivity extends BaseActivity implements VoucherDialogActivityContract.UI {



    @BindView(R.id.voucher_list)
    ListView voucher_list;

    @BindView(R.id.empty)
    LinearLayout empty;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;


    private MyTransferDialogAdapter myTransferDialogAdapter;

    private int page_numer = 1;

    private int isLoadMoreOrRefresh = 1;

    VoucherDialogActivityContract.Persenter persenter;



    @Override
    public int layoutId() {
        return R.layout.activity_dialog_voucher;
    }

    @OnClick(R.id.toolbar_back)
    void click(View view){
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_close);

    }

    @Override
    public void init() {

        toolbar_title.setText("转赠券");

        /*设置窗口样式activity宽高start*/
//        setFinishOnTouchOutside(false);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) ((dm.heightPixels)*0.5);   //高度设置为屏幕的0.6
        p.width = (int) dm.widthPixels;    //宽度设置为屏幕的0.7
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.5f;      //设置窗口外黑暗度
        p.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(p);
        /*设置窗口样式activity宽高end*/

        refreshLayout.setHeaderView(new RefreshHeadView(this));
        refreshLayout.setBottomView(new RefreshBottomView(this));
        myTransferDialogAdapter = new MyTransferDialogAdapter(this);
        voucher_list.setAdapter(myTransferDialogAdapter);
        voucher_list.setEmptyView(empty);
        persenter = new MyTransferDialogVoucherPersenter(this,this);
        Constans.params.clear();
        Constans.params.put("page_number",""+page_numer);
        Constans.params.put("queryType","1");
        Constans.params.put("ticketType","0");
        persenter.getData(Constans.params);
        refresh();
        listOnclick();
    }

    private void listOnclick() {
        voucher_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("WW",myTransferDialogAdapter.listBeanGet().get(position).getUsersTicket_id()+"");
                EventBus.getDefault().post(new VoucherEvent( myTransferDialogAdapter.listBeanGet().get(position).getUsersTicket_id()+"", myTransferDialogAdapter.listBeanGet().get(position).getAmount()+""));
                finish();
            }
        });
    }

    private void refresh() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page_numer = 1;
                isLoadMoreOrRefresh= 1;
                Constans.params.clear();
                Constans.params.put("page_number",""+page_numer);
                Constans.params.put("queryType","1");
                Constans.params.put("ticketType","0");
                persenter.getData(Constans.params);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isLoadMoreOrRefresh =2;
                Constans.params.clear();
                Constans.params.put("page_number",++page_numer+"");
                Constans.params.put("queryType","1");
                Constans.params.put("ticketType","0");
                persenter.getData(Constans.params);
            }
        });
    }

    @Override
    public void attach(Voucher voucher) {
        if(voucher.getData().getHas_more() == 0){
            refreshLayout.setEnableLoadmore(false);
        }else {
            refreshLayout.setEnableLoadmore(true);
        }
        if(isLoadMoreOrRefresh == 1){
            myTransferDialogAdapter.setData(voucher.getData().getUsersTicketList());
        }else {
            myTransferDialogAdapter.addData(voucher.getData().getUsersTicketList());
        }
        onFinishRe();
    }
    @Override
    public void onError() {
        onFinishRe();
    }
    public void onFinishRe(){
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

}
