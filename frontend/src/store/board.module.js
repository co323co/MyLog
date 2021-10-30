import axios from '@/utils/axios.js';
export default {
  state: {
    boards: [],
    board: {},
    comments: [],
    seriesname: '',
  },
  getters: {
    boards(state) {
      return state.boards;
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
    setBoards(state, payload) {
      state.boards = payload;
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
    //게시판 구분(gubun)으로 해당 게시판 게시글 전부 찾기
    getBoards(context, payload) {
      axios
        .get('/board/' + payload)
        .then(({ data }) => {
          context.commit('setBoards', data);
        })
        .catch(() => {
          alert('에러발생!');
        });
    },
    //게시글 id로 해당 게시글 찾기
    getBoard(context, payload) {
      axios.get('/board/post/' + payload).then(({ data }) => {
        context.commit('setBoard', data);
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
