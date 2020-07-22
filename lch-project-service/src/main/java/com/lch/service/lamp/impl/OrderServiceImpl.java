package com.lch.service.lamp.impl;

import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.entity.common.UserBase;
import com.lch.entity.lamp.EchartsData;
import com.lch.entity.lamp.Goods;
import com.lch.entity.lamp.vo.OrderVo;
import com.lch.repo.common.UserBaseRepo;
import com.lch.repo.lamp.GoodsRepo;
import com.lch.repo.lamp.OrderRepo;
import com.lch.service.lamp.OrderService;
import com.lch.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class OrderServiceImpl extends DataService<OrderRepo, OrderVo> implements OrderService {

    @Autowired
    private GoodsRepo goodsRepo;

    @Autowired
    private UserBaseRepo userBaseRepo;

    @Override
    public List<OrderVo> getAllOrder(Long status, String orderNo, String userName, String title, Date createDate) {

        return repo.getAllOrder(status, orderNo, userName, title, createDate);
    }

    @Override
    public OrderVo getOrderCountGroupByStatus() {

        return repo.getOrderCountGroupByStatus();
    }

    @Override
    public Integer updOrderStatus(Long id, Long status) throws ServiceException {
        OrderVo order = repo.get(id);
        if (status == 0L) {
            return 0;
        }
        if (order.getStatus() == 1L) {//已完成订单状态
            return 0;
        }
        if (status == 1L) { //已完成订单 添加卖出数量
            repo.updOrderStatus(id, status);
            Long sellCount = order.getNum(); //购买数量
            //更新商品卖出数量
            return goodsRepo.updGoodsSellCount(sellCount, order.getGoodsId());
        } else {
            // 待完成 先不增加卖出量
            return 0;
        }
    }

    @Override
    public OrderVo getOrderById(Long id) {

        return repo.get(id);
    }

    @Override
    public Integer delOrder(List<String> idList) throws ServiceException {

        return repo.batchDelete(idList);
    }

    @Override
    public Integer updOrder(OrderVo order) throws ServiceException {
        OrderVo orderVo = repo.get(order.getId());
        if (orderVo == null || orderVo.getStatus() == 1L) {//已完成状态不能修改
            return 0;
        }
        Goods goods = goodsRepo.get(order.getGoodsId());
        Long sellCount = order.getNum(); //购买数量
        order.setCostPrice(goods.getCostPrice() * sellCount).setSellPrice(goods.getSellPrice() * sellCount);

        int result = repo.update(order);
        if (order.getStatus() == 1L) { //已完成订单 添加卖出数量
            //更新商品卖出数量
            return goodsRepo.updGoodsSellCount(sellCount, order.getGoodsId());
        } else {
            // 待完成 先不增加卖出量
            return result;
        }
    }

    @Override
    public Integer addOrder(OrderVo order) throws ServiceException {
        String orderNo = IdGen.uniqueNum(); //获取唯一的订单编号
        Goods goods = goodsRepo.get(order.getGoodsId());
        Long sellCount = order.getNum(); //购买数量
        order.setCostPrice(goods.getCostPrice() * sellCount).setSellPrice(goods.getSellPrice() * sellCount).setOrderNo(orderNo);
        //添加订单
        int result = repo.insert(order);
        if (order.getStatus() == 1L) { //已完成订单 添加卖出数量

            //更新商品卖出数量
            return goodsRepo.updGoodsSellCount(sellCount, order.getGoodsId());
        } else {
            // 待完成 先不增加卖出量
            return result;
        }


    }

    @Override
    public List<UserBase> findAllUserByUserType(Long userType) {
        return userBaseRepo.findAllUserByUserType(userType);
    }

    @Override
    public List<OrderVo> getAllOrderAmount(Long status, Integer isCurrentDay, Integer isCurrentMonth) {
        return repo.getAllOrderAmount(status, isCurrentDay, isCurrentMonth);
    }

    @Override
    public OrderVo getAmountCount() {
        return repo.getAmountCount();
    }

    @Override
    public List<EchartsData> getEveryOrderCountAndAmounts(Date dateDay) {
        return repo.getEveryOrderCountAndAmounts(dateDay);
    }

    @Override
    public List<EchartsData> getMonthOrderCountAndAmounts(String dateDay) {
        return repo.getMonthOrderCountAndAmounts(dateDay);
    }

    @Override
    public OrderVo getEarnTotalAndOrderCount() {
        return repo.getEarnTotalAndOrderCount();
    }

    @Override
    public List<OrderVo> getAllOrderByUserId(Long status, Long userId) {
        return repo.getAllOrderByUserId(status, userId);
    }


}
