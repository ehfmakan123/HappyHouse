import { apiInstance, houseInstance } from "./index.js";

const api = apiInstance();
const house = houseInstance();

async function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

async function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

async function dongList(params, success, fail) {
  api.get(`/map/dong`, { params: params }).then(success).catch(fail);
}

async function houseList(params, success, fail) {
  house.get(``, { params: params }).then(success).catch(fail);
}

async function sidoname(params, success, fail) {
  console.log("sidoname 호출 : " + params);
  api.get(`map/sidoname`, { params: params }).then(success).catch(fail);
}

async function gugunname(params, success, fail) {
  console.log("gugunname 호출 : " + params);
  api.get(`map/gugunname`, { params: params }).then(success).catch(fail);
}

export { sidoList, gugunList, houseList, dongList, sidoname, gugunname };
