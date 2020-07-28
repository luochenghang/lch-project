package com.lch.service.lamp.impl;

import com.github.pagehelper.PageInfo;
import com.lch.common.base.DataService;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.lch.entity.common.FileResource;
import com.lch.entity.lamp.Goods;
import com.lch.entity.lamp.criteria.GoodsCriteria;
import com.lch.repo.common.ResourceRepo;
import com.lch.repo.lamp.GoodsRepo;
import com.lch.service.common.handle.TokenServiceImpl;
import com.lch.service.lamp.GoodsService;
import com.lch.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GoodsServiceImpl extends DataService<GoodsRepo, Goods> implements GoodsService {

    @Autowired
    private ResourceRepo resourceRepo;

    @Override
    public List<Goods> getAllGoods(Long status, Long goodsTypeId, String title) {

        return repo.getAllGoods(status, goodsTypeId, title, null, null);
    }

    @Override
    public Page<Goods> getPageAllGoods(GoodsCriteria criteria) {
        setPage(criteria);
        return PageAdapter.adpater(new PageInfo<Goods>(
                repo.getAllGoods(criteria.getStatus(), criteria.getGoodsTypeId(),
                        criteria.getQueryStr(),criteria.getOrderBy(), TokenServiceImpl.getCurrentUserId())));
    }

    @Override
    public Goods getGoodsById(Long id) {
        return repo.get(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer delGoods(List<String> ids) throws ServiceException {
        //先删除资源
        for (int i = 0; i < ids.size(); i++) {
            Long objId = Long.parseLong(ids.get(i));
            resourceRepo.delFilesByObjIdAndType(objId, 2L); //删除产品详细图
            resourceRepo.delFilesByObjIdAndType(objId, 3L); //删除产品轮播图
        }
        return repo.batchDelete(ids);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updGoods(Goods goods) throws ServiceException {
        int result = 0;
        Goods goodsDB = repo.get(goods.getId());
        if (goodsDB.getTitle().equals(goods.getTitle()) && goodsDB.getGoodsTypeId().equals(goods.getGoodsTypeId())) {
            //说明本身的title和type没有做修改
            return repo.update(goods);
        }
        if (repo.getGoodsByTypeAndTitle(goods.getTitle(), goods.getGoodsTypeId()) > 0) {
            //存在该产品 不能添加 在controller显示msg
            return result;
        }
        return repo.update(goods);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updGoodsStatus(Long status, Long id) throws ServiceException {
        return repo.updGoodsStatus(status, id);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer addGoods(Goods goods, String filesDetailPath) throws ServiceException {
        //filesDetailPath;
        int result = 0;
        if (repo.getGoodsByTypeAndTitle(goods.getTitle(), goods.getGoodsTypeId()) > 0) {
            //存在该产品 不能添加 在controller显示msg
            return result;
        }
        result = repo.insert(goods);
        if (StringUtils.isNotBlank(filesDetailPath)) {
            List<String> idList = new ArrayList<>(Arrays.asList(filesDetailPath.split(",")));
            for (int i = 0; i < idList.size(); i++) {
                FileResource r = new FileResource();
                //type 2 为商品详细图
                r.setObjId(goods.getId()).setSort((long) (i + 1)).setType(2L).setTypeDesc("商品详细图！").setUrl(idList.get(i));
                result += resourceRepo.addFileResource(r);
            }
        }
        return result;
    }

}
