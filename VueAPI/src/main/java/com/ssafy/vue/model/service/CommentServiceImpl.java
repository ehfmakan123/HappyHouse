package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.mapper.CommentMapper;


@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<CommentDto> list(int articleno) {
		return sqlSession.getMapper(CommentMapper.class).list(articleno);
	}
	@Override
	public boolean create(CommentDto commentDto) {
		return sqlSession.getMapper(CommentMapper.class).create(commentDto) == 1;
	}
	@Override
	public boolean modify(CommentDto commentDto) {
		return sqlSession.getMapper(CommentMapper.class).modify(commentDto) == 1;
	}
	@Override
	public boolean delete(int commentno) {
		return sqlSession.getMapper(CommentMapper.class).delete(commentno) == 1;
	}
}
