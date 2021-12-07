package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.LikesDto;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}

	@Override
	public int updateUser(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).updateUser(memberDto);
	}

	@Override
	public int deleteUser(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteUser(userid);
	}

	@Override
	public int registerUser(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).registerUser(memberDto);
	}

	@Override
	public List<MemberDto> listUser() throws Exception {
		return sqlSession.getMapper(MemberMapper.class).listUser();
	}

	@Override
	public int idCheck(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).idCheck(userid);
	}

	@Override
	public int insertLike(LikesDto likesDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).insertLike(likesDto);
	}

	@Override
	public int deleteLike(String userid, String aptaddress) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteLike(userid, aptaddress);
	}

	@Override
	public List<LikesDto> listLike(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).listLike(userid);
	}

	@Override
	public int aptLikeCheck(String userid, String aptaddress) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).aptLikeCheck(userid, aptaddress);
	}

}
