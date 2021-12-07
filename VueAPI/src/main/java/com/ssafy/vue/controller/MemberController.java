package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.LikesDto;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.service.JwtServiceImpl;
import com.ssafy.vue.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러  API V1")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "회원등록", notes = "회원 정보를 수정한다.", response = String.class)
	@PostMapping("/info")
	public ResponseEntity<String> register(@RequestBody @ApiParam(value = "회원 정보.", required = true) MemberDto memberDto) throws Exception {
		if (memberService.registerUser(memberDto) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원 비밀번호 찾기", notes = "회원 비밀번호를 반환한다.", response = Map.class)
	@PostMapping("/findpwd")
	public ResponseEntity<String> findPassword(@RequestBody MemberDto memberDto) throws Exception {
		MemberDto user = memberService.userInfo(memberDto.getUserid());
		if (user == null || !user.getUsername().equals(memberDto.getUsername()) || !user.getEmail().equals(memberDto.getEmail())) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
		return new ResponseEntity<String>(user.getUserpwd(), HttpStatus.OK);
	}

	@ApiOperation(value = "회원수정", notes = "회원 정보를 수정한다.", response = Map.class)
	@PutMapping("/info")
	public ResponseEntity<String> update(@RequestBody MemberDto memberDto) throws Exception {
		if (memberService.updateUser(memberDto) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "회원탈퇴", notes = "회원 정보를 삭제한다.", response = Map.class)
	@DeleteMapping("/info/{userid}")
	public ResponseEntity<String> delete(@PathVariable("userid") String userid) throws Exception {
		if (memberService.deleteUser(userid) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원목록", notes = "회원 정보 리스트를 반환한다.", response = Map.class)
	@GetMapping("/list")
	public ResponseEntity<List<MemberDto>> list() throws Exception {
		return new ResponseEntity<List<MemberDto>>(memberService.listUser(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아이디 중복체크", notes = "아이디가 중복되면 1, 아니면 0을 반환한다.", response = Map.class)
	@GetMapping("/idcheck/{userid}")
	public ResponseEntity<Integer> idCheck(@PathVariable("userid") @ApiParam(value = "중복 체크할 아이디.", required = true) String userid) throws Exception {
		return new ResponseEntity<Integer>(memberService.idCheck(userid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "찜목록 추가", notes = "회원의 좋아요를 추가한다.", response = Map.class)
	@PostMapping("/like/insert")
	public ResponseEntity<Integer> insertLike(@RequestBody LikesDto likesDto) throws Exception {
		return new ResponseEntity<Integer>(memberService.insertLike(likesDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "찜목록 삭제", notes = "회원의 찜목록을 삭제한다.", response = Map.class)
	@DeleteMapping("/like/delete/{userid}/{aptaddress}")
	public ResponseEntity<Integer> deleteLike(@PathVariable("userid") String userid, @PathVariable("aptaddress") String aptaddress) throws Exception {
		return new ResponseEntity<Integer>(memberService.deleteLike(userid, aptaddress), HttpStatus.OK);
	}
	
	@ApiOperation(value = "찜목록 반환", notes = "회원의 찜목록을 반환한다.", response = Map.class)
	@GetMapping("/like/{userid}")
	public ResponseEntity<List<LikesDto>> listLike(@PathVariable("userid") @ApiParam(value = "반환할 user 아이디.", required = true) String userid) throws Exception {
		return new ResponseEntity<List<LikesDto>>(memberService.listLike(userid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 아파트가 찜목록에 있는지 확인", notes = "있으면 1, 없으면 0을 반환한다.", response = Map.class)
	@GetMapping("/like/{userid}/{aptaddress}")
	public ResponseEntity<Integer> aptLikeCheck(@PathVariable("userid") String userid, @PathVariable("aptaddress") String aptaddress) throws Exception {
		return new ResponseEntity<Integer>(memberService.aptLikeCheck(userid, aptaddress), HttpStatus.OK);
	}

}
