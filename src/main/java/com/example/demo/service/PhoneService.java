package com.example.demo.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.ParamError;
import com.example.demo.common.exception.ParamValidatedException;
import com.example.demo.common.metadata.constant.MsgConst;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.common.trace.TraceContext;
import com.example.demo.dao.mysql.wrapper.PhoneDAO;
import com.example.demo.model.dto.PhoneDTO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.AssertUtils;
import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneDAO phoneDAO;

    @CreateCache(name = "CreateCache", expire = 10, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.LOCAL)
    private Cache<String, PhonePO> phoneDTOCache;

    public Page<PhoneDTO> getPhoneList(Integer pageSize, Integer pageNum, String name, String brand, String remark) {
        Page<PhonePO> phonePOPage = phoneDAO.pageByCondition(pageSize, pageNum, name, brand, remark);
        return SmartBeanUtils.copyPropertiesPage(phonePOPage, PhoneDTO::new);
    }

    public PhoneDTO getPhoneById(Integer id) {
        PhonePO phonePO = phoneDAO.getById(id);
        return SmartBeanUtils.copyProperties(phonePO, PhoneDTO::new);
    }

    public List<PhoneDTO> getPhoneByName(String name) {
        List<PhonePO> phonePOList = phoneDAO.findByName(name);
        phonePOList = phoneDAO.findByName(name);
        return SmartBeanUtils.copyPropertiesList(phonePOList, PhoneDTO::new);
    }

    public List<PhoneDTO> getPhoneByProdDate(LocalDate prodDate) {
        List<PhonePO> phonePOList = phoneDAO.findByProdDate(prodDate);
        return SmartBeanUtils.copyPropertiesList(phonePOList, PhoneDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addPhone(PhoneDTO phoneDTO) {
        AssertUtils.isNull(phoneDTO.getId(), new ParamValidatedException(List.of(new ParamError("id", MsgConst.MUST_NULL))));
        checkPhoneExists(null, phoneDTO.getName());

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.save(phonePO);

        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to add phone, phonePO: {}", FastjsonUtils.toString(phonePO)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyPhone(PhoneDTO phoneDTO) {
        AssertUtils.nonNull(phoneDTO.getId(), new ParamValidatedException(List.of(new ParamError("id", MsgConst.MUST_NULL))));
        checkPhoneExists(phoneDTO.getId(), phoneDTO.getName());

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.updateById(phonePO);

        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to modify phone, phonePO: {}", FastjsonUtils.toString(phonePO)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removePhone(Integer id) {
        boolean result = phoneDAO.removeById(id);
        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to remove phone, id: {}", id));
    }

    public void checkPhoneExists(Integer id, String name) {
        List<PhonePO> phonePOList = phoneDAO.findByIdAndName(id, name);
        AssertUtils.isTrue(CollectionUtils.isEmpty(phonePOList), ReturnCodeEnum.PHONE_ALREADY_EXISTS,
                () -> log.warn("the phone already exists, id: {}, name: {}", id, name)
        );
    }

    public void testTransactionLapse() {
        // 事务不会生效
        testModifyPhone();

        // 显式使用代理对象调用，事务生效
//        ((PhoneService)AopContext.currentProxy()).testModifyPhone();
    }

    @Transactional(rollbackFor = Exception.class)
    public void testModifyPhone() {
        PhonePO phonePO = new PhonePO();
        phonePO.setId(29);
        phonePO.setPhoneCode("test2");
        phonePO.setName("test2");
        phonePO.setBrand("test2");
        phonePO.setProdDate(LocalDate.now());
        phonePO.setPrice(new BigDecimal("100.00"));
        if (phoneDAO.getById(phonePO.getId()) == null) {
            phoneDAO.save(phonePO);
        }
        phonePO.setRemark(TraceContext.getTraceId());
        phoneDAO.updateById(phonePO);

        // 发生异常 update 数据并不会回滚，因为调用 testModifyPhone() 方法的对象 this 始终是原始实例，不是代理对象，所以事务不会生效
        List.of().get(100);
    }

    /**
     * 编程式缓存：灵活但操作麻烦，不推荐使用
     */
    public void testLocalCache() {
        PhonePO phonePO = phoneDTOCache.get("one");
        if (Objects.isNull(phonePO)) {
            phonePO = phoneDAO.findOne();
            phoneDTOCache.put("one", phonePO);
        }
        System.out.println(FastjsonUtils.toString(phonePO));
    }

    /**
     * 声明式缓存：缓存操作简单，推荐使用
     * 1.name（命名空间，全局唯一），key（数据标识，全局唯一），最终的缓存键为 name:key
     * 2.方法参数会作为缓存键，支持 SpEL 表达式，当没有入参时，需要指定不可变的 key，最终的缓存键为 localPhoneCache_:findOne，代表缓存固定的键
     * 3.方法必须有返回值，才能作为缓存值被缓存
     * 4.一旦指定了 expire，则全局配置里的 expireAfterWriteInMillis 和 expireAfterAccessInMillis 都会失效，会以 expire 作为 expireAfterWriteInMillis 进行缓存配置
     */
    @Cached(name = "localPhoneCache", key = "'-findOne'", expire = 10, timeUnit = TimeUnit.SECONDS, cacheType = CacheType.LOCAL)
    public PhonePO testLocalCache2() {
        return phoneDAO.findOne();
    }

    /**
     * 声明式缓存：缓存操作简单，推荐使用
     * #id：代表以入参作为数据标识，最终的缓存键为 phoneCache-${id}，每一个不同的 id 都会缓存一条数据
     * #phonePO.id：如果入参是一个对象(PhonePO phonePO)，此时则代表取对象的id属性为数据标识，最终的缓存键为 phoneCache-${id}
     */
    @Cached(name = "localPhoneCache", key = "'-' + #id", cacheType = CacheType.LOCAL)
    public PhonePO testLocalCache3(Integer id) {
        return phoneDAO.getById(id);
    }

    /**
     * 声明式缓存：缓存操作简单，推荐使用
     * 名称冲突：当使用 @Cached 注解时，确保 name 没有被 @CreateCache 注解所创建的缓存占用，如果名称冲突，会优先使用已存在的缓存实例， @Cached 注解所创建的缓存将不会生效
     */
    @Cached(name = "remotePhoneCache", key = "'-' + #phonePO.id", cacheType = CacheType.REMOTE)
    public PhonePO testRemoteCache(PhonePO phonePO) {
        phonePO = phoneDAO.getById(phonePO.getId());
        return phonePO;
    }

    @Cached(name = "bothPhoneCache", key = "'-' + #phonePO.id", cacheType = CacheType.BOTH)
    public PhonePO testBothCache(PhonePO phonePO) {
        phonePO = phoneDAO.getById(phonePO.getId());
        return phonePO;
    }

    @Cached(name = "remotePhoneListCache", key = "'-name'", cacheType = CacheType.REMOTE)
    public List<PhonePO> testRemoteListCache(String name) {
        List<PhonePO> list = phoneDAO.findByName(name);
        return list;
    }

    @Cached(name = "remotePhoneCache-enabled", key = "'-' + #phonePO.id", cacheType = CacheType.REMOTE, enabled = false)
    public PhonePO testRemoteCacheEnabled(PhonePO phonePO) {
        phonePO = phoneDAO.getById(phonePO.getId());
        return phonePO;
    }

    /**
     * 缓存条件：当 condition 为 true 时，缓存生效，否则不缓存
     * 实现比较麻烦，推荐使用 enabled 控制
     */
    @Cached(name = "remotePhoneCache-condition", key = "'-' + #phonePO.id", cacheType = CacheType.REMOTE, condition = "#useCache != null || #useCache == true")
    public PhonePO testRemoteCacheCondition(PhonePO phonePO, Boolean useCache) {
        phonePO = phoneDAO.getById(phonePO.getId());
        return phonePO;
    }

    /**
     * 缓存更新：当缓存数据被更新时，会触发缓存更新，此时会先删除缓存，再重新缓存数据
     * 注意：此时更新的缓存数据永远是入参的 phonePO
     */
    @CacheUpdate(name = "remotePhoneCache", key = "'-' + #phonePO.id", value = "#phonePO")
    public void testRemoteCacheUpdate(PhonePO phonePO) {
        phoneDAO.updateById(phonePO);
    }

    /**
     * 缓存更新：当缓存数据被更新时，会触发缓存更新，此时会先删除缓存，再重新缓存数据
     * 注意：此时更新的缓存数据是方法的返回值，即是从数据库里查询的最新数据（#result 是 SpEL 表达式的固定变量，代表方法返回值）
     */
    @CacheUpdate(name = "remotePhoneCache", key = "'-' + #phonePO.id", value = "#result")
    public PhonePO testRemoteCacheUpdate2(PhonePO phonePO) {
        phoneDAO.updateById(phonePO);
        return phoneDAO.getById(phonePO.getId());
    }

    /**
     * 缓存删除：最好的方法是更新后直接删除缓存，查询时重新会重新加载缓存
     * 所以推荐使用 @CacheInvalidate 注解代替 @CacheUpdate
     */
    @CacheInvalidate(name = "remotePhoneCache", key = "'-' + #phonePO.id")
    public void testRemoteCacheUpdate3(PhonePO phonePO) {
        phoneDAO.updateById(phonePO);
    }
}
