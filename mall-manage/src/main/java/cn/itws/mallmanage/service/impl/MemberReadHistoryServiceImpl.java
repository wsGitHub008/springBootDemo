package cn.itws.mallmanage.service.impl;

import cn.itws.mallmanage.nosql.mongodb.document.MemberReadHistory;
import cn.itws.mallmanage.nosql.mongodb.repository.MemberReadHistoryRepository;
import cn.itws.mallmanage.service.MemberReadHistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会员浏览记录管理Service实现类
 * @Author swj
 * @date 2020/4/5 19:12
 */
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {
    //注入
    @Autowired
    private MemberReadHistoryRepository memberReadHistoryRepository;


    //1、生成浏览记录
    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(memberReadHistory);
        return 1;
    }
    //2、批量删除浏览记录
    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> deleteList = new ArrayList<>();
        for (String id : ids) {
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        memberReadHistoryRepository.deleteAll(deleteList);
        return ids.size();
    }
    //3、获取用户浏览历史记录
    @Override
    public List<MemberReadHistory> list(Long memberId) {
        List<MemberReadHistory> historyList = memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(memberId);
        return historyList;
    }
}
