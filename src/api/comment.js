import { apiInstance } from "./index.js";

const api = apiInstance();

function listComment(articleno, success, fail) {
  console.log("listComment 호출");
  api.get(`/comment/${articleno}`).then(success).catch(fail);
}

function writeComment(comment, success, fail) {
  console.log("writeComment 호출");
  api.post(`/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function getComment(commentno, success, fail) {
  api.get(`/comment/${commentno}`).then(success).catch(fail);
}

function modifyComment(comment, success, fail) {
  api.put(`/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function deleteComment(commentno, success, fail) {
  api.delete(`/comment/${commentno}`).then(success).catch(fail);
}

export { listComment, writeComment, getComment, modifyComment, deleteComment };
