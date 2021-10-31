<template>
  <div>
    <div v-if="isWriteMode">
      <div class="divier mb-2" />
      <div class="py-1 px-2">
        <span>새 글 쓰기</span>
        <v-icon>mdi-pencil</v-icon>
      </div>

      <div class="divier mt-2" />
      <PostWrite :seriesList="seriesList" />
    </div>
    <div v-if="!isWriteMode">
      <!-- 검색  -->
      <div class="mb-3" style="width: 15%">
        <v-text-field
          prepend-icon="mdi-magnify"
          label="검색"
          single-line
          hide-details
          v-model="search"
        ></v-text-field>
      </div>

      <div class="divier mb-2" />
      <v-row>
        <v-col style="align-self: center">
          <div class="pl-2">전체 {{ board.length }}개의 글</div>
        </v-col>
        <v-col class="text-right">
          <v-btn @click="writePostMode" color="teal darken-2" outlined rounded>
            <span>새 글 쓰기</span>
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
        </v-col>
      </v-row>
      <!-- 글 목록 -->
      <div class="pa-1">
        <!-- 보드 게시글 목록들 -->
        <v-data-table
          v-if="!isDeltePostListMode"
          :headers="headers"
          :items="postList"
          item-key="postId"
          :search="search"
          @click:row="changeDetailMode"
          no-data-text="게시글이 없습니다"
          no-results-text="검색 결과가 없습니다"
          :options="{
            itemsPerPage: 5,
          }"
          :footer-props="{
            itemsPerPageText: '쪽수',
          }"
        >
          <template v-slot:footer.prepend>
            <div class="mt-3">
              <v-btn @click="isDeltePostListMode = true" text color="red lighten-1">
                <v-icon small>mdi-close</v-icon>
                지우기</v-btn
              >
            </div>
          </template>
        </v-data-table>

        <v-data-table
          v-else
          show-select
          v-model="selectedRows"
          :headers="headers"
          :items="postList"
          item-key="postId"
          :search="search"
          @click:row="changeDetailMode"
          no-data-text="게시글이 없습니다"
          no-results-text="검색 결과가 없습니다"
          :options="{
            itemsPerPage: 5,
          }"
          :footer-props="{
            itemsPerPageText: '쪽수',
          }"
        >
          <template v-slot:footer.prepend>
            <div class="mt-3">
              <v-btn @click="deletePostList" text color="green lighten-1">
                <v-icon small>mdi-check</v-icon>
                확인</v-btn
              >
              <v-btn @click="isDeltePostListMode = false" text color="grey lighten-1">
                <v-icon small>mdi-close</v-icon>
                취소</v-btn
              >
            </div>
          </template>
        </v-data-table>
        <!-- <div v-for="(post, index) in board" :key="index"> -->
        <!-- {{ post.postTitle }} -->
        <!-- </div> -->
      </div>
      <div class="divier mt-2" />
      <v-flex v-if="isDetailMode" class="my-7">
        <Post :post="datailPost" :seriesList="seriesList"></Post>
      </v-flex>
      <!-- 게시글들 보여주는 부분 -->
      <v-flex v-else class="my-7">
        <div v-for="(post, index) in board" :key="index">
          <div class="my-5">
            <Post :post="post" :seriesList="seriesList"></Post>
          </div>
        </div>
      </v-flex>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex';
import Post from '@/components/board/Post.vue';
import PostWrite from '@/components/board/PostWrite.vue';
import { getSeriesList } from '@/api/series';
import { deletePost } from '@/api/post';

export default {
  components: {
    Post,
    PostWrite,
  },
  data() {
    return {
      headers: [
        { text: '제목', value: 'title' },
        { text: '작성시간', value: 'time', align: 'end' },
      ],
      postTableOption: {},
      isWriteMode: false,
      postList: [],
      seriesList: [],
      isDetailMode: false,
      datailPost: null,
      search: null,
      isDeltePostListMode: false,
      selectedRows: [],
    };
  },
  computed: {
    ...mapGetters(['seriesname']),
    ...mapGetters(['board']),
  },
  watch: {
    board: function () {
      this.setPostList();
    },
  },
  created() {
    this.refreshBoard();
  },
  methods: {
    refreshBoard() {
      let seriesId = this.$route.query.seriesId;
      let hasSeries = this.$route.query.hasSeries;
      let payload = {
        seriesId: seriesId,
        hasSeries: hasSeries,
        search: null,
        page: 1,
        size: 1000,
      };
      this.$store.dispatch('getBoard', payload);
      //현재 시리즈 위치 기록해두기
      if (!seriesId) seriesId = null;
      this.$store.state.board.seriesId = seriesId;
      getSeriesList().then((res) => (this.seriesList = res));
    },
    deletePostList() {
      console.log(this.selectedRows);
      for (let row of this.selectedRows) {
        deletePost(row.postId).then(() => this.refreshBoard());
      }
      this.isDeltePostListMode = false;
      this.selectedRows = [];
    },
    setPostList() {
      this.postList = this.board.map((post) => {
        post.time = this.dateFilter(new Date(post.postCreatedAt));
        post.title = this.titleFilter(post.postTitle, post.commentCount) + ' ' + post.postId;
        return post;
      });
    },
    changeDetailMode(post) {
      this.datailPost = post;
      this.isDetailMode = true;
    },
    writePostMode() {
      this.isWriteMode = true;
    },
    dateFilter(date) {
      return (
        date.getFullYear() +
        '년 ' +
        (date.getMonth() + 1) +
        '월 ' +
        date.getDate() +
        '일 ' +
        date.getHours() +
        ':' +
        date.getMinutes()
      );
    },
    titleFilter(title, commentCount) {
      if (commentCount == 0) return title;
      return title + ' (' + commentCount + ')';
    },
  },
};
</script>
<style scoped>
.divier {
  height: 2px;
  background-color: black;
}
</style>
