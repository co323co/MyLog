<template>
  <v-app-bar
    app
    absolute
    color="#6A76AB"
    dark
    shrink-on-scroll
    prominent
    src="https://picsum.photos/1920/1080?random"
    fade-img-on-scroll
  >
    <v-app-bar-title class="ml-5 mb-3"> <h2 class="ml-5">MyLog</h2> </v-app-bar-title>
    <v-spacer></v-spacer>
    <v-btn text>
      <span class="mr-2">새 글 쓰기</span>
      <v-icon>mdi-pencil</v-icon>
    </v-btn>
    <v-btn text>
      <span class="mr-2">관리자 페이지</span>
      <v-icon>mdi-open-in-new</v-icon>
    </v-btn>
    <template v-slot:extension>
      <!-- 시리즈 구분 탭 -->
      <v-tabs align-with-title>
        <v-tab @click="$router.push({ path: `/board` }).catch(() => {})"> ALL </v-tab>
        <v-tab
          v-for="(series, index) in seriesList"
          :key="index"
          @click="$router.push({ path: `/board?seriesId=${series.id}` }).catch(() => {})"
        >
          {{ series.name }}
        </v-tab>
      </v-tabs>
      <!-- 검색  -->
      <div class="mb-3">
        <v-text-field
          prepend-icon="mdi-magnify"
          label="검색"
          single-line
          hide-details
        ></v-text-field>
      </div>
    </template>
  </v-app-bar>
</template>

<script>
import { getSeriesList } from '@/api/series';
export default {
  name: 'Header',
  data() {
    return {
      seriesList: {},
    };
  },
  created() {
    this.setSeriesList();
    console.log(this.seriesList);
  },
  methods: {
    async setSeriesList() {
      try {
        const { data } = await getSeriesList();
        this.seriesList = data.result;
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
h2 {
  font-family: BMHANNAPro;
}
</style>
