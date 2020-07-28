package com.lch.service.lamp.impl;

import com.github.pagehelper.PageInfo;
import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.lch.entity.lamp.Goods;
import com.lch.entity.lamp.PreOrder;
import com.lch.entity.lamp.criteria.PreOrderCriteria;
import com.lch.entity.lamp.vo.OrderVo;
import com.lch.repo.lamp.GoodsRepo;
import com.lch.repo.lamp.OrderRepo;
import com.lch.repo.lamp.app.PreOrderRepo;
import com.lch.service.common.handle.TokenServiceImpl;
import com.lch.service.lamp.PreOrderService;
import com.lch.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class PreOrderServiceImpl extends DataService<PreOrderRepo, PreOrder> implements PreOrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private GoodsRepo goodsRepo;

    @Override
    public List<PreOrder> getAllPreOrder(Long status, Date createDate) {
        // TODO Auto-generated method stub
        return repo.getAllPreOrder(status, createDate);
    }

    @Override
    public Page<PreOrder> getPageAllPreOrder(PreOrderCriteria criteria) {
        setPage(criteria);
        return PageAdapter.adpater(new PageInfo<PreOrder>(
                repo.getAllPreOrder(criteria.getStatus(), criteria.getCreateDate())));
    }

    @Override
    public Integer updPreOrderStatus(Long id, Long status) throws ServiceException {
        if (status == 1L) { //生成订单
            PreOrder preOrder = repo.get(id);
            if (preOrder == null) {
                doThrow("用户收藏商品不存在");
            }
            String orderNo = IdGen.uniqueNum(); //获取唯一的订单编号
            Goods goods = goodsRepo.get(preOrder.getGoodsId());
            OrderVo order = new OrderVo();
            //购买数量为1 最终价格为销售价格 用户类型为1 微信用户 订单为0 待支付订单
            order.setCostPrice(goods.getCostPrice()).setSellPrice(goods.getSellPrice()).setOrderNo(orderNo).setNum(1L)
                    .setStatus(0L).setUserId(preOrder.getUserId()).setFinalPrice(preOrder.getSellPrice()).setUserType(1L)
                    .setGoodsId(preOrder.getGoodsId());
            //添加订单
            orderRepo.insert(order);
            return repo.updPreOrderStatus(id, status);

        }
        if (status == 2L) { //预购订单设置失效
            return repo.updPreOrderStatus(id, status);
        }
        return null;
    }

    @Override
    public int delPreOrder(List<String> idList) throws ServiceException {
        return repo.batchDelete(idList);
    }

    @Override
    public PreOrder getPreOrderById(Long id) {
        return repo.get(id);
    }

    @Override
    public int addPreOrder(PreOrder preOrder) throws ServiceException {
        Long userId = TokenServiceImpl.getCurrentUserId();
        if (userId == null) {
            doThrow("请登录");
        }
        if (repo.getPreOrderIsExist(userId, preOrder.getGoodsId()) != 0) {
			doThrow("你已经收藏过这个商品了");
        }
        return repo.insert(preOrder);
    }

    @Override
    public Integer updPreOrderIsCollect(Long id, Long isCollect) throws ServiceException {
        // TODO Auto-generated method stub
        return repo.updPreOrderIsCollect(id, isCollect);
    }

    @Override
    public PreOrder getPreOrderCountGroupByStatus() {
        return repo.getPreOrderCountGroupByStatus();
    }

    @Override
    public int batchAddOrder(List<String> ids) throws ServiceException {
        for (String str : ids) {
            Long id = Long.valueOf(str);
            PreOrder preOrder = repo.get(id);
            if (preOrder == null) {
                doThrow("用户收藏商品不存在");
            }
            if (preOrder.getStatus() != 0) {
                doThrow("所选资源中编号为" + preOrder.getId() + "是未处理状态！,请移除之后在操作");
            }
        }

        for (String str : ids) {
            Long id = Long.valueOf(str);
            PreOrder preOrder = repo.get(id);
            String orderNo = IdGen.uniqueNum(); //获取唯一的订单编号
            Goods goods = goodsRepo.get(preOrder.getGoodsId());
            OrderVo order = new OrderVo();
            //购买数量为1 最终价格为销售价格 用户类型为1 微信用户 订单为0 待支付订单
            order.setCostPrice(goods.getCostPrice()).setSellPrice(goods.getSellPrice()).setOrderNo(orderNo).setNum(1L)
                    .setStatus(0L).setUserId(preOrder.getUserId()).setFinalPrice(preOrder.getSellPrice()).setUserType(1L)
                    .setGoodsId(preOrder.getGoodsId());
            //添加订单
            orderRepo.insert(order);
            repo.updPreOrderStatus(id, 1L);
        }
        return 1;
    }


}
