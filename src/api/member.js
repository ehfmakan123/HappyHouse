import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function idCheck(userid, success, fail) {
  await api.get(`/user/idcheck/${userid}`).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function findpwd(userInfo, success, fail) {
  await api.post(`user/findpwd`, JSON.stringify(userInfo))
  .then(success)
  .catch(fail);
}

async function deleteMember(userid, success, fail) {
  await api.delete(`/user/info/${userid}`).then(success).catch(fail);
}

async function listMember(userid, success, fail) {
  await api.get(`/user/list`).then(success).catch(fail);
}

async function modifyMember(userInfo, success, fail) {
  console.log("modifyMember 호출");
  await api
    .put(`/user/info`, JSON.stringify(userInfo))
    .then(success)
    .catch(fail);
}

async function joinMember(userInfo, success, fail) {
  await api
    .post(`/user/info`, JSON.stringify(userInfo))
    .then(success)
    .catch(fail);
}

async function getMember(userid, success, fail) {
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function deleteLike(likeInfo, success, fail) {
  await api.delete(`/user/like/delete/${likeInfo.userid}/${likeInfo.aptaddress}`).then(success).catch(fail);
}

async function listLike(userid, success, fail) {
  await api.get(`/user/like/${userid}`).then(success).catch(fail);
}

async function insertLike(likeInfo, success, fail) {
  await api.post(`/user/like/insert`, JSON.stringify(likeInfo)).then(success).catch(fail);
}

async function aptLikeCheck(likeInfo, success, fail) {
  await api.get(`/user/like/${likeInfo.userid}/${likeInfo.aptaddress}`).then(success).catch(fail);
}

// function logout(success, fail)

export {
  login,
  findById,
  idCheck,
  findpwd,
  deleteMember,
  listMember,
  modifyMember,
  joinMember,
  getMember,
  listLike,
  insertLike,
  deleteLike,
  aptLikeCheck
};
