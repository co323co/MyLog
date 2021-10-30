<template>
  <div class="regist pa-5">
    <!-- <h3 class="mt-1 mb-3 ml-2">게시글 상세 보기</h3> -->
    <div class="inputform">
      <v-row>
        <v-col>
          <h2>
            {{ post.postTitle }}
          </h2>
        </v-col>
        <v-col class="muted text-right" style="align-self: end">
          {{ postCreated }}
        </v-col>
      </v-row>
      <v-divider></v-divider>
      <!-- <p class="ml-4">|</p> -->
      <!-- <p class="ml-4">조회 {{ post.views }} views</p> -->

      <v-row
        class="pa-5"
        style="min-height: 250px; font-size: 14px"
        v-html="enterToBr(post.postContent)"
      >
      </v-row>
      <div>
        <v-row>
          <v-col class="text-left">
            <v-btn text>댓글 {{ post.commentCount }}</v-btn>
          </v-col>
          <v-col class="text-right">
            <v-btn text @click="mvmodify">수정</v-btn>
            <v-btn text @click="deleteBook" class="ml-2">삭제</v-btn>
          </v-col>
        </v-row>
      </div>
    </div>
    <v-divider class="my-5"></v-divider>
  </div>
</template>

<script>
export default {
  data() {
    return {
      postCreated: '',
    };
  },
  props: {
    post: Object,
  },
  created() {
    let date = new Date(this.post.postCreatedAt);
    this.postCreated = date.getFullYear() + '년 ' + date.getMonth() + '월 ' + date.getDay() + '일';
  },
  computed: {},
  methods: {
    mvmodify() {
      this.$router.push(`/board/${this.board.gubun}/modify/${this.board.id}`);
    },
    deleteBook() {
      console.log(this.board.id);
      if (confirm('정말로 삭제하실 건가요?')) {
        axios.delete(`/board/post/${this.board.id}`).then(({ data }) => {
          let msg = '삭제 처리시 문제가 발생했습니다.';
          if (data) {
            msg = '삭제가 완료되었습니다.';
          }
          alert(msg);
          this.$router.push(`/board/${this.board.gubun}`);
          // this.$router.push('/board');
        });
      }
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, '<br/>');
    },
  },
};
</script>

<style scoped>
.muted {
  color: gray;
  font-weight: bold;
  font-size: 0.9rem;
}
.btitle {
  font-weight: bold;
  font-size: 1.2em;
}
h3 {
  font-family: BMHANNAPro;
}
.regist {
  padding: 5px;
  /* background-color: rgb(250, 242, 215); */
  /* border-radius: 10px; */
}

.v-btn {
  font-size: 1.1em;
}
p {
  margin-bottom: 0;
}
</style>
