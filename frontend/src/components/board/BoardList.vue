<template>
  <div>
    <div class="divier mb-2" />
    <div>전체 {{ postList.length }}개의 글</div>
    <div class="divier mt-2" />
    <v-flex class="my-7">
      <div v-for="(post, index) in postList" :key="index">
        <div class="my-5">
          <Post :post="post"></Post>
        </div>
      </div>
    </v-flex>
  </div>
</template>
<script>
import { mapGetters } from 'vuex';
import Post from '@/components/board/Post.vue';
import { getPostList } from '@/api/post';

export default {
  components: {
    Post,
  },
  data() {
    return {
      postList: [],
      word: '',
      headers: [
        { text: '제목', value: 'title' },
        { text: '작성자', value: 'userid' },
        { text: '날짜', value: 'regtime' },
        { text: '조회수', value: 'views' },
      ],
      seriesId: null,
    };
  },
  computed: {
    ...mapGetters(['seriesname']),
  },
  created() {
    console.log('BoardList created', this.seriesname);
    this.seriesId = this.$route.query.seriesId;
    this.setPostList(this.seriesId, null, null, 1, 10);
  },
  methods: {
    async setPostList(seriesId, hasSeries, search, page, size) {
      try {
        const { data } = await getPostList(seriesId, hasSeries, search, page, size);
        this.postList = data.result;
      } catch (error) {
        console.error(error);
      }
    },
    mvRegist() {
      console.log(this.$route.params.gubun);
      this.$router.push(`/board/${this.gubun}/create`);
    },
    mvDetail(row) {
      this.$router.push(`/board/${row.gubun}/view/${row.id}`);
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
