package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.PageDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.util.PageNavigation;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Value("10")
	private int PAGE_SIZE;
	@Value("10")
	private int BLOCK_SIZE;

	@Override
	public boolean writeArticle(BoardDto boardDto) throws Exception {
		if(boardDto.getSubject() == null || boardDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).writeArticle(boardDto) == 1;
	}

	@Override
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return sqlSession.getMapper(BoardMapper.class).listArticle(boardParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(boardParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(BoardMapper.class).getTotalCount(boardParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / boardParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = boardParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getArticle(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyArticle(BoardDto boardDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyArticle(boardDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).deleteMemo(articleno);
		return sqlSession.getMapper(BoardMapper.class).deleteArticle(articleno) == 1;
	}

	@Override
	public int countBoard() throws Exception {
		return sqlSession.getMapper(BoardMapper.class).countBoard();
	}

	public PageDto pageInfo(int count, int pageno) {
		if (count == 0) return new PageDto(pageno, 0, 1, 1, 0, 0, 0, 0);
		int countOfPage = count / PAGE_SIZE + 1;
		if (count % PAGE_SIZE == 0) countOfPage--;
		if (pageno > countOfPage) pageno = countOfPage;
		int startPageNum = (pageno-1) * PAGE_SIZE;
		int endPageNum = pageno * PAGE_SIZE;
		if (pageno * PAGE_SIZE > count) endPageNum = count;
		int blockNo = pageno / BLOCK_SIZE + 1;
		if (pageno % BLOCK_SIZE == 0) blockNo--;
		int prev = (blockNo-1) * BLOCK_SIZE;
		int start = prev + 1;
		int end = blockNo * BLOCK_SIZE;
		int next = end + 1;
		if (blockNo * BLOCK_SIZE > countOfPage) {
			end = countOfPage;
			next = 0;
		}
		
		return new PageDto(pageno, count, start, end, prev, next, startPageNum, endPageNum);
	}

	public PageDto listViewBoard(int pageno) throws Exception { 
		PageDto page = pageInfo(sqlSession.getMapper(BoardMapper.class).countBoard(), pageno);
		System.out.println(page);
		page.setBlist(sqlSession.getMapper(BoardMapper.class).listViewBoard(page.getStartPageNum()));
		System.out.println(page);
		
		return page;
	}

}