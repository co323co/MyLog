import axios from '@/utils/axios.js';
import { getPostList } from '@/api/post';
import { getCommentList } from '@/api/comment';

export default {
  // 현재 상태들
  state: {
    //현재 보드에 있는 게시글들
    board: [],
    comments: [],
    seriesId: '',
    // seriesname: '',
    commentsDic: {},
  },
  getters: {
    seriesId(state) {
      return state.seriesId;
    },
    board(state) {
      return state.board;
    },
    // seriesname(state) {
    //   return state.seriesname;
    // },
    commentsDic(state) {
      return state.commentsDic;
    },
  },
  mutations: {
    setSeriesId(state, payload) {
      state.seriesId = payload;
    },
    setBoard(state, payload) {
      state.board = payload;
    },
    // setSeriesName(state, payload) {
    //   state.seriesname = payload;
    // },
    setCommentsDic(state, payload) {
      let { postId, commentList } = payload;
      state.commentsDic[postId] = commentList;
      state.commentsDic = Object.assign({}, state.commentsDic);
    },
    deleteCommentsDic(state, payload) {
      delete state.commentsDic[payload];
      state.commentsDic = Object.assign({}, state.commentsDic);
    },
  },
  actions: {
    //현재 보드에 띄워줄 게시글 리스트 가져오기
    getBoard(context, payload) {
      getPostList(payload).then((res) => context.commit('setBoard', res));
    },
    // //시리즈 id로 시리즈 이름 찾기
    // getSeriesName(context, payload) {
    //   axios.get('/series/' + payload).then(({ data }) => {
    //     context.commit('setSeriesName', data.result.name);
    //   });
    // },

    //(페이지, 댓글리스트) 쌍 추가하기
    getCommentsDic(context, payload) {
      getCommentList(payload).then((res) => {
        context.commit('setCommentsDic', {
          postId: payload.postId,
          commentList: res,
        });
      });
    },

    deleteCommentsDic(context, payload) {
      context.commit('deleteCommentsDic', payload);
    },
  },
};
