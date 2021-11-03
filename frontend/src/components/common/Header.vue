<template>
  <v-app-bar
    app
    absolute
    color="#6A76AB"
    dark
    shrink-on-scroll
    prominent
    src="https://cdn.pixabay.com/photo/2017/06/26/08/14/laptop-2443052_960_720.jpg"
    fade-img-on-scroll
  >
    <v-app-bar-title class="ml-5 mb-3"> <h2 class="ml-5">MyLog</h2> </v-app-bar-title>
    <v-spacer></v-spacer>
    <v-btn text>
      <span class="mr-2">관리자 페이지</span>
      <v-icon>mdi-open-in-new</v-icon>
    </v-btn>
    <template v-slot:extension>
      <!-- 홈 화면 탭 -->
      <v-tabs v-if="routerName == 'main'" align-with-title v-model="active_tab">
        <v-tab @click="$router.push({ path: `/main` }).catch(() => {})"> 홈 </v-tab>
      </v-tabs>
      <!-- 시리즈 구분 탭 -->
      <v-tabs v-if="routerName == 'board'" align-with-title v-model="active_tab">
        <v-tab @click="$router.push({ path: `/board` }).catch(() => {})"> ALL </v-tab>
        <v-tab
          v-for="(series, index) in seriesList"
          :key="index"
          @click="$router.push({ path: `/board?seriesId=${series.id}` }).catch(() => {})"
        >
          {{ series.name }}
        </v-tab>
        <v-tab @click="$router.push({ path: `/board?hasSeries=false` }).catch(() => {})">
          미분류
        </v-tab>
      </v-tabs>

      <!-- 검색  -->
      <!-- <div class="mb-3">
        <v-text-field
          prepend-icon="mdi-magnify"
          label="검색"
          single-line
          hide-details
        ></v-text-field>
      </div> -->
    </template>
  </v-app-bar>
</template>

<script>
import { getSeriesList } from '@/api/series';
import { mapGetters } from 'vuex';

export default {
  name: 'Header',
  data() {
    return { seriesList: {}, active_tab: 0 };
  },
  computed: { ...mapGetters(['routerName']) },
  watch: {},
  created() {
    console.log('이름', this.routerName);
    getSeriesList().then((res) => {
      this.seriesList = res;
      // 현재 url을 참고하여 탭 변경
      let seriesId = this.$route.query.seriesId;
      let hasSeries = this.$route.query.hasSeries;
      if (seriesId) {
        let i;
        for (i = 0; i < res.length; i++) if (res[i].id == seriesId) break;
        let tabIdx = i + 2;
        this.active_tab = tabIdx;
      }
      if (hasSeries) {
        this.active_tab = 1;
      }
    });
  },
  methods: {},
};
</script>

<style scoped>
h2 {
  font-family: BMHANNAPro;
}
</style>
