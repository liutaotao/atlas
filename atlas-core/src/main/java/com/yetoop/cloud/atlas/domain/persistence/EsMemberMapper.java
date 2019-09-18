package com.yetoop.cloud.atlas.domain.persistence;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.EsMember;

public interface EsMemberMapper {
	EsMember queryMember(@Param("mobile") String mobile, @Param("password") String password);

	int checkMobile(String mobile);
	
	EsMember selectByUnionid(String wxUnionid);

	EsMember selectByEncryptKey(@Param("memberId") Integer memberId, @Param("encryptKey") String encryptKey);

	EsMember selectByPhone(String mobile);

	int insertSelective(EsMember record);

	EsMember selectByPrimaryKey(Integer memberId);

	int updateByPrimaryKeySelective(EsMember record);

	int updateByPrimaryKeyWithBLOBs(EsMember record);

}