<template lang="">
  <div>
    <!-- 댓글 보여주는 form -->
    <div v-if="!isModifyShow" class="comment">
      <div class="head">{{ copyComment.commentWriter }}</div>
      <div class="content mt-2" v-html="enterToBr(copyComment.commentContent)"></div>
      <v-row class="mt-3">
        <v-col
          v-if="copyComment.commentCreatedAt == copyComment.commentUpdatedAt"
          d-inline-block
          class="date"
        >
          {{ copyComment.commentCreatedAt | dateFilter }}
        </v-col>
        <v-col v-else d-inline-block class="date">
          {{ copyComment.commentUpdatedAt | dateFilter }} 수정됨
        </v-col>
        <v-col class="mb-1 text-right">
          <v-btn text @click="isModifyShow = true">수정</v-btn> |
          <v-btn text @click="remove">삭제</v-btn>
        </v-col>
      </v-row>
      <div class="divider"></div>
    </div>
    <!-- 댓글 수정하는 form -->
    <div v-if="isModifyShow" class="comment">
      <div class="head">{{ copyComment.commentWriter }}</div>
      <div class="content">
        <v-textarea hide-details="auto" v-model="copyComment.commentContent" solo></v-textarea>
      </div>
      <v-row offset-lg11>
        <v-col
          v-if="copyComment.commentCreatedAt == copyComment.commentUpdatedAt"
          d-inline-block
          class="date"
        >
          {{ copyComment.commentCreatedAt | dateFilter }}
        </v-col>
        <v-col v-else d-inline-block class="date">
          {{ copyComment.commentUpdatedAt | dateFilter }} 수정됨
        </v-col>
        <v-col class="mb-1 cbtn text-right">
          <v-btn text @click="modify">확인</v-btn> |
          <v-btn text @click="cancle">취소</v-btn>
        </v-col>
      </v-row>
      <div class="divider"></div>
    </div>
  </div>
</template>
<script>
// import { mapGetters } from 'vuex';
import { deleteComment } from '@/api/comment';
export default {
  props: ['comment'],
  component: {},
  computed: {},
  data() {
    return {
      //현재 댓글이 보기 상태인지 수정 상태인지를 나타낸다
      isModifyShow: false,
      //copy_comment의 약자, props가 변경되는 걸 막기 위해 현재 .vue에서 사용할 comment 복사
      copyComment: this.copy(),
    };
  },
  methods: {
    // 댓글 수정
    modify() {
      this.isModifyShow = true;
      // 댓글 수정 로직
      // // 서버 통신
      // this.$axios
      //   .put(`/comment/`, {
      //     bid: this.c_cmt.bid,
      //     seq: this.c_cmt.seq,
      //     content: this.c_cmt.content,
      //   })
      //   .then(({ data }) => {
      //     if (!data) {
      //       let msg = '수정 처리시 문제가 발생했습니다.';
      //       alert(msg);
      //     }
      //     // 수정했으니 댓글 다시 얻기
      //     this.$store.dispatch('getComments', this.c_cmt.bid);

      //     //수정 폼 다시 되돌리기
      //     this.isModifyShow = false;
      //   });
    },
    remove() {
      if (confirm('정말로 삭제할까요?')) {
        deleteComment(this.comment.commentId).then(() => {
          let payload = { postId: this.comment.postId, page: 1, size: 1000 };
          this.$store.dispatch('getCommentsDic', payload);
        });
        // 삭제 로직
        // this.$axios.delete(`/comment/${this.comment.bid}/${this.comment.seq}`).then(({ data }) => {
        //   if (!data) {
        //     let msg = '삭제 처리시 문제가 발생했습니다.';
        //     alert(msg);
        //   }
        //   // 삭제했으니 보여줄 댓글들 다시 얻기.
        //   this.$store.dispatch('getComments', this.comment.bid);
        // });
      }
    },
    cancle() {
      this.isModifyShow = false;
      this.copyComment.commentContent = this.comment.commentContent;
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, '<br />');
    },
    copy() {
      return {
        postId: this.comment.postId,
        commentId: this.comment.commentId,
        commentWriter: this.comment.commentWriter,
        commentContent: this.comment.commentContent,
        commentCreatedAt: this.comment.commentCreatedAt,
        commentUpdatedAt: this.comment.commentUpdatedAt,
      };
    },
  },
  watch: {
    comment: function () {
      this.copyComment = this.copy();
    },
  },
  filters: {
    dateFilter(dateStr) {
      let date = new Date(dateStr);
      return date.getFullYear() + '년 ' + (date.getMonth() + 1) + '월 ' + date.getDate() + '일';
    },
  },
};
</script>

<style>
.comment {
  text-align: left;
  border-radius: 5px;
  /* background-color: #ffffff; */
  padding: 10px 20px;
  margin: 10px;
}
.head {
  font-weight: bold;
  margin-bottom: 5px;
}
.content {
  padding-top: 5px;
  padding-bottom: 5px;
  margin-bottom: 10px;
}
.cbtn {
  color: steelblue;
}
.divider {
  background-color: darkgray;
  height: 1px;
  width: 100%;
}
.date {
  align-self: center;
  font-size: 0.85rem;
}
</style>
