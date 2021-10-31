import axios from '@/utils/axios.js';
import { getPostList } from '@/api/post';

export default {
  // 현재 상태들
  state: {
    //현재 보드에 있는 게시글들
    board: {},
    comments: [],
    seriesId: '',
    seriesname: '',
  },
  getters: {
    seriesId(state) {
      return state.seriesId;
    },
    board(state) {
      return state.board;
    },
    seriesname(state) {
      return state.seriesname;
    },
    comments(state) {
      return state.comments;
    },
  },
  mutations: {
    setSeriesId(state, payload) {
      state.seriesId = payload;
    },
    setBoard(state, payload) {
      state.board = payload;
    },
    setSeriesName(state, payload) {
      state.seriesname = payload;
    },
    setComments(state, payload) {
      state.comments = payload;
    },
  },
  actions: {
    // 앞에 /board
    //현재 보드에 띄워줄 게시글 리스트 가져오기
    getBoard(context, payload) {
      getPostList(payload).then((res) => context.commit('setBoard', res));
    },

    getSeriesName(context, payload) {
      axios.get('/series/' + payload).then(({ data }) => {
        context.commit('setSeriesName', data.result.name);
      });
    },
    //시리즈 id로 시리즈 이름 찾기
    getSeriesName(context, payload) {
      axios.get('/series/' + payload).then(({ data }) => {
        context.commit('setSeriesName', data.result.name);
      });
    },
    //게시글 id로 댓글들 찾기
    getComments(context, payload) {
      axios.get('/comment/' + payload).then(({ data }) => {
        context.commit('setComments', data);
      });
    },
  },
};
