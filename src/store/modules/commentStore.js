import { listComment } from "@/api/comment.js";

const commentStore = {
  namespaced: true,
  state: {
    comments: [],
  },
  getters: {},
  mutations: {
    SET_COMMENTS: (state, comments) => {
      state.comments = comments;
    },
  },
  actions: {
    getComments({ commit }, articleno) {
      console.log("getComments 호출");
      listComment(
        articleno,
        (response) => {
          commit("SET_COMMENTS", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default commentStore;
