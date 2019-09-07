package com.yunqin.bearmall.util;

import com.yunqin.bearmall.bean.Address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chihane.jdaddressselector.AddressProvider;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;

public class CustomAddressProvider implements AddressProvider {

    private Address address;

    public CustomAddressProvider(Address address) {
        this.address = address;
    }

    @Override
    public void provideProvinces(AddressReceiver<Province> addressReceiver) {
        List<Province> provinces = new ArrayList<>();
        if (address != null && address.getData() != null && address.getData().getAreaList_0().size() > 0) {
            for (Address.DataBean.AreaListBean bean : address.getData().getAreaList_0()) {
                Province province = new Province();
                province.id = bean.getArea_id();
                province.name = bean.getAreaName();
                provinces.add(province);
            }
        }
        addressReceiver.send(provinces);
    }

    @Override
    public void provideCitiesWith(int i, AddressReceiver<City> addressReceiver) {

        List<City> cities = new ArrayList<>();
        if (address != null && address.getData() != null && address.getData().getAreaList_1().size() > 0) {
            for (Address.DataBean.AreaListBean bean : address.getData().getAreaList_1()) {
                if (bean.getParent_id() == i) {
                    City city = new City();
                    city.province_id = i;
                    city.id =  bean.getArea_id();
                    city.name = bean.getAreaName();
                    cities.add(city);
                }
            }
        }
        addressReceiver.send(cities);
    }

    @Override
    public void provideCountiesWith(int i, AddressReceiver<County> addressReceiver) {


        List<County> counties = new ArrayList<>();
        if (address != null && address.getData() != null && address.getData().getAreaList_2().size() > 0) {
            for (Address.DataBean.AreaListBean bean : address.getData().getAreaList_2()) {
                if (bean.getParent_id() == i) {
                    County county = new County();
                    county.city_id = i;
                    county.id = bean.getArea_id();
                    county.name = bean.getAreaName();
                    counties.add(county);
                }
            }
        }
        addressReceiver.send(counties);
    }

    @Override
    public void provideStreetsWith(int i, AddressReceiver<Street> addressReceiver) {
        addressReceiver.send(null);
    }
}
