package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.LikesDto;
import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	int registerUser(MemberDto memberDto) throws Exception;
	int updateUser(MemberDto memberDto) throws Exception;
	int deleteUser(String userid) throws Exception;
	List<MemberDto> listUser() throws Exception;
	int idCheck(String userid) throws Exception;
	int insertLike(LikesDto likesDto) throws Exception;
	int deleteLike(String userid, String aptaddress) throws Exception;
	List<LikesDto> listLike(String userid) throws Exception;
	int aptLikeCheck(String userid, String aptaddress) throws Exception;
	
}
