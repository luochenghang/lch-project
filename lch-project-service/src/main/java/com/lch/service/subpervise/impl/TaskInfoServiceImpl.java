package com.lch.service.subpervise.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lch.common.base.DataService;
import com.lch.common.config.UserUtils;
import com.lch.common.constant.App;
import com.lch.common.exceptions.ServiceException;
import com.lch.component.page.Page;
import com.lch.component.page.PageAdapter;
import com.lch.entity.supervise.TaskInfo;
import com.lch.entity.supervise.UserSimpleInfo;
import com.lch.entity.supervise.Criteria.TaskCriteria;
import com.lch.repo.supervise.GiftInfoRepo;
import com.lch.repo.supervise.TaskInfoRepo;
import com.lch.service.subpervise.TaskInfoService;
import com.lch.utils.ObjectUtils;

@Service
@Transactional(readOnly = true)
public class TaskInfoServiceImpl extends DataService<TaskInfoRepo, TaskInfo> implements TaskInfoService {

	@Autowired
	private GiftInfoRepo giftInforepo;

	@Override
	@Transactional(readOnly = false)
	public int addTask(TaskInfo task) throws ServiceException {

		Long userId = UserUtils.getCurrentUserId();
		// 获取有关联的userId
		Long relatedUser = giftInforepo.getRelatedUser(userId);

		if (relatedUser == null) {
			throw new ServiceException("您还没有与你的好友存在关联关系哦");
		}
		task.setCreateUser(userId);
		// 判断是否是为自己创建任务
		if (task != null && task.getIsForMe() == 1L) {
			// 为自己创建
			task.setUserId(userId);
		} else {
			// 为他建立任务 1 表示审核通过
			task.setUserId(relatedUser);
		}

		return repo.addTask(task);
	}

	@Override
	public Page<TaskInfo> getAllTask(TaskCriteria criteria) {
		// 给我的任务
		setPage(criteria);
		Long userId = UserUtils.getCurrentUserId();
		criteria.setUserId(userId);
		return PageAdapter.adpater(new PageInfo<TaskInfo>(repo.getAllTask(criteria)));
	}

	@Override
	@Transactional(readOnly = false)
	public int auditTask(Long id, Long auditStatus, String reason) throws ServiceException {
		TaskInfo task = repo.getTaskById(id);

		if (task == null) {
			throw new ServiceException("您的任务id不合法,或者不存在该任务!");
		}

		if (task.getAuditStatus() != 0) {
			throw new ServiceException("您已经审核过这个任务了,不能进行审核了");
		}

		return repo.auditTask(id, auditStatus, reason, UserUtils.getCurrentUserId());
	}

	@Override
	public TaskInfo getTaskById(Long id) {
		return repo.getTaskById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int updTask(TaskInfo task) throws ServiceException {
		TaskInfo taskInfo = repo.getTaskById(task.getId());
		Long userId = UserUtils.getCurrentUserId();
		if (taskInfo == null) {
			throw new ServiceException("该任务的id存在问题!");
		}
		if (taskInfo.getCreateUser() != userId) {
			throw new ServiceException("该任务不是您创建的,不能编辑别人的哟!");
		}
		if (taskInfo.getAuditStatus() == 2L || taskInfo.getStatus() == 1L || taskInfo.getStatus() == 2L) {
			task.setCreateUser(userId);
			return repo.updTask(task);
		}

		throw new ServiceException("任务只能在审核失败或者任务完成或失败才能进行修改!");
	}

	@Override
	@Transactional(readOnly = false)
	public int delTask(Long id) throws ServiceException {
		TaskInfo taskInfo = repo.getTaskById(id);
		Long userId = UserUtils.getCurrentUserId();
		if (taskInfo == null) {
			throw new ServiceException("该任务的id存在问题!");
		}
		if (taskInfo.getCreateUser() != userId) {
			throw new ServiceException("该任务不是您创建的,不能删除别人的哟!");
		}
		if (taskInfo.getAuditStatus() == 0L || (taskInfo.getStatus() == 0L && taskInfo.getAuditStatus() == 1L)) {
			throw new ServiceException("该任务在审核中或者进行中是不能删除的呀!");
		}
		return repo.delTask(id, userId);
	}

	@Override
	public Page<TaskInfo> getTAAllTask(TaskCriteria criteria) {
		// 他创建的任务
		setPage(criteria);
		Long userId = UserUtils.getCurrentUserId();
		// 获取关联的用户id
		// 获取有关联的userId
		Long relatedUser = giftInforepo.getRelatedUser(userId);
		criteria.setUserId(userId).setCreateUser(relatedUser);

		return PageAdapter.adpater(new PageInfo<TaskInfo>(repo.getTAAllTask(criteria)));
	}

	@Override
	@Transactional(readOnly = false)
	public int auditTaskDone(Long id, Long status) throws ServiceException {
		TaskInfo task = repo.getTaskById(id);

		if (task == null) {
			throw new ServiceException("您的任务id不合法,或者不存在该任务!");
		}

		Long userId = task.getUserId();// 计算金币的用户 不是自己登陆的用户

		if (task.getAuditStatus() != 1) {
			throw new ServiceException("这个任务不是审核通过状态,不能完成或者失败哟!");
		}

		if (task.getStatus() != 0) {
			throw new ServiceException("这个任务不是未进行中的任务哟!");
		}

		if (task.getUserId() == UserUtils.getCurrentUserId()) {// 自己不能操作自己做的的任务哟
			throw new ServiceException("自己不能操作自己做的的任务哟!");
		}

		// 获取用户信息 进行计算段位和金币
		UserSimpleInfo info = repo.getUserSimpleInfo(userId);
		if (info == null) {
			throw new ServiceException("您的信息不正确,请重新登录!");
		}

		Long minPoint = info.getMinPoint();
		Long maxPoint = info.getMaxPoint();

		if (status == 1) {// 任务完成,加胜点,加金币
			// 定义随机数完成(15-25) 失败(10-15)
			Long VICTORY_POINT = ObjectUtils
					.toLong((Math.random() * (App.VICTORY_MAX_POINT - App.VICTORY_MIN_POINT) + App.VICTORY_MIN_POINT));
			if (info.getVictoryPoint() + VICTORY_POINT < maxPoint) {// 加上20胜点之后没有超过段位界限 则直接更新

				repo.updUserSimpleInfo(userId, task.getRewardCoins(), info.getVictoryPoint() + VICTORY_POINT,
						info.getLevelId());
			} else {// 超过界限
					// 获取下一段位的信息
				UserSimpleInfo levelInfo = repo.getLevel(info.getLevelId() + 1L);

				if (levelInfo == null) {// 说明已经是最强段位了,只能在后面加胜点了

					repo.updUserSimpleInfo(userId, task.getRewardCoins(), info.getVictoryPoint() + VICTORY_POINT,
							info.getLevelId());
				} else {
					// 晋级段位,计算胜点
					Long VictoryPoint = info.getVictoryPoint() + VICTORY_POINT - levelInfo.getMinPoint();
					repo.updUserSimpleInfo(userId, task.getRewardCoins(), VictoryPoint, info.getLevelId() + 1L);
				}
			}

		} else {
			// 减少胜点
			Long DEFEAT_POINT = ObjectUtils
					.toLong((Math.random() * (App.DEFEAT_MAX_POINT - App.DEFEAT_MIN_POINT) + App.DEFEAT_MIN_POINT));
			if (info.getVictoryPoint() - DEFEAT_POINT >= minPoint) {// 减去10胜点之后没有下降段位 则直接更新

				repo.updUserSimpleInfo(userId, -task.getPunishCoins(), info.getVictoryPoint() - DEFEAT_POINT,
						info.getLevelId());
			} else {
				// 段位下降
				UserSimpleInfo levelInfo = repo.getLevel(info.getLevelId() - 1L);
				if (levelInfo == null) {// 说明是0了,最低段位

					repo.updUserSimpleInfo(userId, -task.getPunishCoins(), info.getVictoryPoint() - DEFEAT_POINT,
							info.getLevelId());
				} else {
					// 段位下降,计算胜点
					Long VictoryPoint = info.getVictoryPoint() + minPoint - DEFEAT_POINT - levelInfo.getMinPoint();
					repo.updUserSimpleInfo(userId, -task.getPunishCoins(), VictoryPoint, info.getLevelId() - 1L);
				}
			}
		}

		return repo.auditTaskDone(id, status, UserUtils.getCurrentUserId());
	}

	@Override
	public Page<TaskInfo> getTaskCreateByMe(TaskCriteria criteria) {
		// 他创建的任务
		setPage(criteria);
		Long userId = UserUtils.getCurrentUserId();
		criteria.setCreateUser(userId);

		return PageAdapter.adpater(new PageInfo<TaskInfo>(repo.getTaskCreateByMe(criteria)));
	}

	@Override
	public Map<String, Object> getTaskGiftCount() {
		Long userId = UserUtils.getCurrentUserId();

		// 获取有关联的userId
		Long relatedId = giftInforepo.getRelatedUser(userId);

		return repo.getTaskGiftCount(userId, relatedId);
	}

}
