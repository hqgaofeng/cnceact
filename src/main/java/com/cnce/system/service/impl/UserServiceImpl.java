package com.cnce.system.service.impl;

import com.cnce.common.config.BootdoConfig;
import com.cnce.common.domain.DictDO;
import com.cnce.common.domain.FileDO;
import com.cnce.common.domain.Tree;
import com.cnce.common.service.DictService;
import com.cnce.common.service.FileService;
import com.cnce.common.utils.*;
import com.cnce.system.dao.DeptDao;
import com.cnce.system.dao.UserDao;
import com.cnce.system.dao.UserRoleDao;
import com.cnce.system.domain.DeptDO;
import com.cnce.system.domain.RoleDO;
import com.cnce.system.domain.UserDO;
import com.cnce.system.domain.UserRoleDO;
import com.cnce.system.service.RoleService;
import com.cnce.system.service.UserService;
import com.cnce.system.vo.UserVO;
import com.google.common.base.Joiner;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Value("${super.user}")
	private String superUser;

	@Autowired
	UserDao userMapper;
	@Autowired
	UserRoleDao userRoleMapper;
	@Autowired
	DeptDao deptMapper;
	@Autowired
	private FileService sysFileService;
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DictService dictService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDO get(Long id) {
		List<Long> roleIds = userRoleMapper.listRoleId(id);
		UserDO user = userMapper.get(id);
		user.setDeptName(StringUtils.isEmpty(user.getDeptId())?"":
				deptMapper.get(user.getDeptId()).getName());
		user.setRoleIds(roleIds);
		return user;
	}

	@Override
	public UserDO loadUser(String username) {
		return userMapper.loadUser(username);
	}

	@Override
	public List<UserDO> list(Map<String, Object> map) {
		List<UserDO> list = userMapper.list(map);
		for(int i = 0; i<list.size(); i++){
			List<Long> roleIds = userRoleMapper.listRoleId(list.get(i).getUserId());
			List<String> roleNames = new ArrayList<String>();
			for(Long rid: roleIds){
				RoleDO roleDO = roleService.get(rid);
				roleNames.add(null==roleDO?"":roleDO.getRoleName());
			}
			String roName = Joiner.on(",").join(roleNames);
			list.get(i).setRoleName(roName);
		}
		return list;
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}

	@Transactional
	@Override
	public int save(UserDO user) {
		if(superUser.contains(user.getUsername())){
			user.setAttrId(null);
		}
		if(null==user.getPwdModified()){
			user.setPwdModified(new Date());
		}
		int count = userMapper.save(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getRoleIds();
		userRoleMapper.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return count;
	}

	@Override
	public int update(UserDO user) {
		if(superUser.contains(user.getUsername())){
			user.setAttrId(null);
		}
		int r = userMapper.update(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getRoleIds();
		userRoleMapper.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return r;
	}

	@Override
	public int remove(Long userId) {
		userRoleMapper.removeByUserId(userId);
		return userMapper.remove(userId);
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		boolean exit;
		exit = userMapper.list(params).size() > 0;
		return exit;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public ResultData resetPwd(UserVO userVO,UserDO userDO) {
		if(!Objects.equals(userVO.getUserDO().getUserId(),userDO.getUserId())){
			return new ResultData("201",false,"您修改的不是您登录的账号!");
		}
		if(!Objects.equals(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdOld()),userDO.getPassword())){
			return new ResultData("202",false,"输入的旧密码有误！");
		}
		List<DictDO> pwd_complex = dictService.listByType("check_pwd_complex");
		if(!CollectionUtils.isEmpty(pwd_complex) && pwd_complex.size()>0 &&
				"yes".equals(pwd_complex.get(0).getValue())){
			//校验密码复杂度
			if(!PwdCheckUtil.checkContainCase(userVO.getPwdNew()) ||
					!PwdCheckUtil.checkContainDigit(userVO.getPwdNew()) ||
					!PwdCheckUtil.checkPasswordLength(userVO.getPwdNew(),"8","40")||
					!PwdCheckUtil.checkContainLowerCase(userVO.getPwdNew()) ||
					!PwdCheckUtil.checkContainUpperCase(userVO.getPwdNew())||
					!PwdCheckUtil.checkContainSpecialChar(userVO.getPwdNew())){
				return new ResultData("203",false,"密码强度太弱,请修改密码(包含大小写字母、数字、特殊字符，长度至少为8位)");
			}
		}
		userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdNew()));
		userDO.setPwdModified(new Date());
		int cnt = userMapper.update(userDO);
		if(cnt>0){
			return new ResultData("0",true,"操作成功");
		}else{
			return new ResultData("204",true,"操作失败");
		}
	}
	@Override
	public int adminResetPwd(UserVO userVO) throws Exception {
		UserDO userDO =get(userVO.getUserDO().getUserId());
		if("admin".equals(userDO.getUsername())){
			logger.error("超级管理员的账号不允许直接重置！");
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
		userDO.setPwdModified(new Date());
		return userMapper.update(userDO);
	}

	@Transactional
	@Override
	public int batchremove(Long[] userIds) {
		int count = userMapper.batchRemove(userIds);
		userRoleMapper.batchRemoveByUserId(userIds);
		return count;
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
		Long[] pDepts = deptMapper.listParentDept();
		Long[] uDepts = userMapper.listAllDept();
		Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
		for (DeptDO dept : depts) {
			if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
				continue;
			}
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(dept.getDeptId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<UserDO> users = userMapper.list(new HashMap<String, Object>(16));
		for (UserDO user : users) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getDeptId().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public int updatePersonal(UserDO userDO) {
		return userMapper.update(userDO);
	}

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
		//获取图片后缀
		String prefix = fileName.substring((fileName.lastIndexOf(".")+1));
		String[] str=avatar_data.split(",");
		//获取截取的x坐标
		int x = (int)Math.floor(Double.parseDouble(str[0].split(":")[1]));
		//获取截取的y坐标
		int y = (int)Math.floor(Double.parseDouble(str[1].split(":")[1]));
		//获取截取的高度
		int h = (int)Math.floor(Double.parseDouble(str[2].split(":")[1]));
		//获取截取的宽度
		int w = (int)Math.floor(Double.parseDouble(str[3].split(":")[1]));
		//获取旋转的角度
		int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
		try {
			BufferedImage cutImage = ImageUtils.cutImage(file,x,y,w,h,prefix);
			BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean flag = ImageIO.write(rotateImage, prefix, out);
			//转换后存入数据库
			byte[] b = out.toByteArray();
			FileUtil.uploadFile(b, bootdoConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw  new Exception("图片裁剪错误！！");
		}
		Map<String, Object> result = new HashMap<>();
		if(sysFileService.save(sysFile)>0){
			UserDO userDO = new UserDO();
			userDO.setUserId(userId);
			userDO.setPicId(sysFile.getId());
			if(userMapper.update(userDO)>0){
				result.put("url",sysFile.getUrl());
			}
		}
		return result;
    }

}
