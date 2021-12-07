package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CommentDto : 게시글 상세정보에 작성하는 댓글에 대한 글정보")
public class CommentDto {

	@ApiModelProperty(value = "글번호")
	private int commentno;
	@ApiModelProperty(value = "작성자id")
	private String userid;
	@ApiModelProperty(value = "댓글")
	private String comment;
	@ApiModelProperty(value = "작성시각")
	private String commenttime;
	@ApiModelProperty(value = "게시글번호", example = "1")
	private int articleno;

	public int getCommentno() {
		return commentno;
	}

	public void setCommentno(int comment_no) {
		this.commentno = comment_no;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommenttime() {
		return commenttime;
	}

	public void setCommenttime(String commenttime) {
		this.commenttime = commenttime;
	}

	public int getArticleno() {
		return articleno;
	}

	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}

	@Override
	public String toString() {
		return "CommentDto [commentno=" + commentno + ", userid=" + userid + ", comment=" + comment + ", commenttime="
				+ commenttime + ", articleno=" + articleno + "]";
	}

}
