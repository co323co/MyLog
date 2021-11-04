<template>
  <v-app-bar
    app
    absolute
    color="#6A76AB"
    dark
    shrink-on-scroll
    prominent
    src="https://cdn.pixabay.com/photo/2016/11/29/06/18/apple-1867762_960_720.jpg"
    fade-img-on-scroll
  >
    <v-app-bar-title class="ml-5 mb-3"> <h2 class="ml-5">MyLog</h2> </v-app-bar-title>
    <v-spacer></v-spacer>

    <Setting />
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
    </template>
  </v-app-bar>
</template>

<script>
import { getSeriesList } from '@/api/series';
import { mapGetters } from 'vuex';
import Setting from '@/components/setting/Setting';

export default {
  name: 'Header',
  components: {
    Setting, //관리자페이지 버튼
  },
  data() {
    return {
      // seriesList: {},
      active_tab: 0,
    };
  },
  computed: { ...mapGetters(['routerName', 'seriesList']) },
  watch: {},
  created() {
    console.log('이름', this.routerName);

    this.$store.dispatch('getSeries').then((res) => {
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
